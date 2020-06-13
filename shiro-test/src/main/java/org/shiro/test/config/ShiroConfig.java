package org.shiro.test.config;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.mgt.SessionsSecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShiroConfig {
	
	@Autowired
	private ShiroCaptchaValidateFilter shiroCaptchaValidateFilter;
	@Autowired
	private ShiroSessionFilter shiroSessionFilter;

	
	
	/**
     * LifecycleBeanPostProcessor，这是个DestructionAwareBeanPostProcessor的子类，
     * 负责org.apache.shiro.util.Initializable类型bean的生命周期的，初始化和销毁。
     * 主要是AuthorizingRealm类的子类，以及EhCacheManager类。
     * starter 包已经实现，可以去掉
     */
    /*@Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }*/
	
	// 配置url过滤器
    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();

        chainDefinition.addPathDefinition("/captcha", "anon");
        chainDefinition.addPathDefinition("/logout","anon");
        chainDefinition.addPathDefinition("/layuiadmin/**", "anon");
        chainDefinition.addPathDefinition("/druid/**", "anon");
        chainDefinition.addPathDefinition("/api/**", "anon");
        // all other paths require a logged in user
        chainDefinition.addPathDefinition("/login","anon");
        chainDefinition.addPathDefinition("/**", "authc");
        return chainDefinition;
    }

    @Bean("filterMap")
    public Map<String, Filter> filterMap(){
    	Map<String, Filter> map = new HashMap<>();
    	map.put("captcha", shiroCaptchaValidateFilter);
    	map.put("session", shiroSessionFilter);
    	return map;
    }
    
	// 配置自定义Realm
    @Bean
    public ShiroUserRealm shiroUserRealm() {
    	ShiroUserRealm shiroUserRealm = new ShiroUserRealm();
    	shiroUserRealm.setCredentialsMatcher(credentialsMatcher()); //配置使用哈希密码匹配
        return shiroUserRealm;
    }

	

    // 设置用于匹配密码的CredentialsMatcher
    @Bean
    public HashedCredentialsMatcher credentialsMatcher() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName(Sha256Hash.ALGORITHM_NAME);  // 散列算法，这里使用更安全的sha256算法
        credentialsMatcher.setStoredCredentialsHexEncoded(false);  // 数据库存储的密码字段使用HEX还是BASE64方式加密
        credentialsMatcher.setHashIterations(1024);  // 散列迭代次数
        return credentialsMatcher;
    }

	// 配置security并设置userReaml，避免xxxx required a bean named 'authorizer' that could not be found.的报错
    @Bean
    public SessionsSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(shiroUserRealm());
        return securityManager;
    }

}
