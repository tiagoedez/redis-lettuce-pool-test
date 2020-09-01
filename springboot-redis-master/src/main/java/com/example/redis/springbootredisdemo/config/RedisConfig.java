package com.example.redis.springbootredisdemo.config;

import java.time.Duration;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Value("${spring.redis.database}")
    private int dbIndex;
    @Value("${spring.redis.host}")
    private String hostname;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.password}")
    private String password;
    @Value("${spring.redis.time-out}")
    private int timeOut;
    @Value("${spring.redis.lettuce.pool.max-active}")
    private int maxTotal;
    @Value("${spring.redis.lettuce.pool.max-idle}")
    private int maxIdle;
    @Value("${spring.redis.lettuce.pool.min-idle}")
    private int minIdle;
    @Value("${spring.redis.lettuce.pool.max-wait}")
    private int maxWait;
    @Value("${spring.redis.lettuce.shutdown-timeout}")
    private int shutdownTimeOut;
    @Value("${spring.redis.client-name}")
    private String clientName;    


    /**
     * Configure connection pool parameters
     *
     * @return GenericObjectPool
     */
    @Bean
    public GenericObjectPoolConfig getRedisConfig() {
        GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
        genericObjectPoolConfig.setMaxIdle(maxIdle);
        genericObjectPoolConfig.setMaxTotal(maxTotal);
        genericObjectPoolConfig.setMinIdle(minIdle);
        genericObjectPoolConfig.setMaxWaitMillis(maxWait);
        return genericObjectPoolConfig;
    }

    /**
     * Build connection pool
     *
     * @param poolConfig Connection pool configuration
     * @return LettuceClientConfiguration
     */
    @Bean
    public LettuceClientConfiguration getDefaultLettucePool(GenericObjectPoolConfig poolConfig) {
        return LettucePoolingClientConfiguration.builder().shutdownTimeout(Duration.ofMillis(shutdownTimeOut))
        		.clientName(clientName)
                .commandTimeout(Duration.ofMillis(timeOut))
                .poolConfig(poolConfig).build();
    }
    /**
     * Single node redis configuration
     *
     * @return RedisStandaloneConfiguration
     */
    @Bean
    public RedisStandaloneConfiguration getRedisStandaloneConfiguration() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration(hostname, port);
        return configuration;
    }

    /**
     * lettuce Connection factory configuration
     *
     * @return LettuceConnectionFactory implement RedisConnectionFactory
     */
    @Bean
    public LettuceConnectionFactory getLettuceConnectionFactory(RedisStandaloneConfiguration configuration,
                                                                LettuceClientConfiguration clientConfig) {

        LettuceConnectionFactory factory = new LettuceConnectionFactory(configuration, clientConfig);
        //Verify that the connection is valid
        factory.setValidateConnection(true);
        //Select database
        factory.setDatabase(dbIndex);
        return factory;
    }
   /**
     * Custom redisTemplate (the method name must be called redisTemplate because @ Bean configures the Bean name according to the method name)
     * The default redistemplate < K, V > is generic and inconvenient to use. The user-defined value is < string, Object >
     * The default serialization method is JdkSerializationRedisSerializer. The serialized content is not easy to read. Instead, it is serialized into fastjson
     *
     */
    @Bean(name = "redisTemplate")
    public RedisTemplate<String, Object> getRedisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        // Sequence key into string
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        // Sequence value into json
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        // Sequence the key of hash into a string
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        // Sequence the value of hash into json
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
        //Transaction support
        //redisTemplate.setEnableTransactionSupport(true);
        redisTemplate.setConnectionFactory(factory);
        return redisTemplate;
    }

}