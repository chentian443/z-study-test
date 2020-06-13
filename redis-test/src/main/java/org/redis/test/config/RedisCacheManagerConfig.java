package org.redis.test.config;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 进行缓存管理的定制
 * 可以不配置，采用springboot默认的也行，默认的cache缓存消亡时间为-1（不消亡）
 */
@Configuration
//使使用 @ConfigurationProperties 注解的类生效。这样RedisProperties类可以不用添加 @Component
@EnableConfigurationProperties(RedisProperties.class) 
public class RedisCacheManagerConfig {
    
    @Autowired
    private RedisProperties redisProperties;
    
    //缓存管理器
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory lettuceConnectionFactory) {
    	
        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig();
        // 设置缓存管理器管理的缓存的默认过期时间
		defaultCacheConfig = defaultCacheConfig.entryTtl(Duration.ofSeconds(redisProperties.getDefaultExpireTime()))
                // 设置 key为string序列化
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                // 设置value为json序列化    new GenericJackson2JsonRedisSerializer()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new JdkSerializationRedisSerializer()))
                // 不缓存空值，如果缓存的value值为空，则会报错；
                // 当然，如果缓存对象为list，list的某个元素为空，这不影响，
                .disableCachingNullValues();

        // 对每个缓存空间应用不同的配置
        Map<String, RedisCacheConfiguration> configMap = new HashMap<>();
        
        Set<String> cacheNames = redisProperties.getNamesAndExpires().keySet();
        for (String key : cacheNames) {
        	long expireTime = Long.parseLong(redisProperties.getNamesAndExpires().get(key));
        	configMap.put(key, defaultCacheConfig.entryTtl(Duration.ofSeconds(expireTime)));
		}

        RedisCacheManager cacheManager = RedisCacheManager.builder(lettuceConnectionFactory)
                .cacheDefaults(defaultCacheConfig)
                .initialCacheNames(cacheNames)
                .withInitialCacheConfigurations(configMap)
                .build();
        return cacheManager;
    }
    
    
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory ) {
        //设置序列化
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
        jackson2JsonRedisSerializer.setObjectMapper(new ObjectMapper().enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL));
        //配置redisTemplate
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        RedisSerializer<String> stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);//key序列化
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);//value序列化
        redisTemplate.setHashKeySerializer(stringSerializer);//Hash key序列化
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);//Hash value序列化
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
    
}
