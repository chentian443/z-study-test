package org.oauth2.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import lombok.SneakyThrows;

/**
 * WebAsyncManagerIntegrationFilter
 * SecurityContextPersistenceFilter
 * UsernamePasswordAuthenticationFilter
 * BasicAuthenticationFilter
 * 
 * FilterChainProxy
 * 
 * @author chent
 */

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
 
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().passwordEncoder(bCryptPasswordEncoder())
            .withUser("user_1").password(bCryptPasswordEncoder().encode("123456")).authorities("USER")
            .and()
            .withUser("user_2").password(bCryptPasswordEncoder().encode("123456")).authorities("USER");
   }

 
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .requestMatchers().anyRequest()
            .and()
            .authorizeRequests()
            .antMatchers("/oauth/*").permitAll();
    }
    
    @Bean
	@Override
	@SneakyThrows
	public AuthenticationManager authenticationManagerBean() {
		return super.authenticationManagerBean();
	}
    
    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(4);
    }

}
