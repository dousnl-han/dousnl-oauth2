package com.dousnl.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.sql.DataSource;

/**
 * Oauth2认证服务
 *
 * <p>1.0 oauth uaa配置</p>
 * @author hanliang
 * @version 1.0
 * @date 2019/7/29 9:27
 *
 * <p>1.1 oauth uaa新增redis缓存配置</p>
 * @author hanliang
 * @version 1.1
 * @date 2019/8/29 16:22
 *
 */
@Configuration
@EnableAuthorizationServer
public class Oauth2JwtServerConfig extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private DataSource dataSource;

    //@Autowired
    //private RedisConnectionFactory redisConnectionFactory;


    /**
     * v1.0对称加密
     * @return
     */
//    @Bean
//    public JwtAccessTokenConverter accessTokenConverter() {
//        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
//        converter.setSigningKey("test-secret");
//        return converter;
//    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("dousnl-jwt.jks"),
                "dousnl123".toCharArray());
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair("dousnl-jwt"));
        return converter;
    }

    /**
     * v1.0
     * @return
     */
//    @Bean
//    public JwtTokenStore tokenStore(){
//        return new JwtTokenStore(accessTokenConverter());
//    }

    /**
     * v1.1 redis token缓存
     * @return
     */
    @Bean
    public TokenStore tokenStore(){
        //return new RedisTokenStore(redisConnectionFactory);
        //return new InMemoryTokenStore();
        return new JwtTokenStore(accessTokenConverter());
    }

//    @Bean
//    public RedisConnectionFactory redisConnectionFactory(){
//        return new JedisConnectionFactory();
//    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                 .tokenStore(tokenStore())
                .accessTokenConverter(accessTokenConverter());
        //endpoints.tokenServices(defaultTokenServices());
    }
    /**
     * <p>注意，自定义TokenServices的时候，需要设置@Primary，否则报错，</p>
     * 此处重写默认配置
     * @return
     */
//    @Primary
//    @Bean
//    public DefaultTokenServices defaultTokenServices() {
//        DefaultTokenServices tokenServices = new DefaultTokenServices();
//        tokenServices.setTokenStore(tokenStore());
//        tokenServices.setSupportRefreshToken(true);
//        //tokenServices.setClientDetailsService(clientDetails());
//        // token有效期自定义设置，默认12小时
//        tokenServices.setAccessTokenValiditySeconds(60*60*12);
//        // refresh_token默认30天
//        tokenServices.setRefreshTokenValiditySeconds(60 * 60 * 24 * 7);
//        return  tokenServices;
//    }

    /**
     * 配置客户端基本信息
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsService());
//        clients.inMemory() // 使用in-memory存储
//                .withClient("user-service") // client_id
//                .redirectUris("http://localhost:9000/callback")
//                .secret("123456") // client_secret
//                .authorizedGrantTypes("password","authorization_code","refresh_token")  //刷新token模式
//                .scopes("read_userinfo", "read_contacts"); // 允许的授权范围

    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()")         //url:/oauth/token_key,exposes public key for token verification if using JWT tokens
                .checkTokenAccess("isAuthenticated()") //url:/oauth/check_token allow check token
                .allowFormAuthenticationForClients();
    }

    @Bean
    public ClientDetailsService clientDetailsService(){
        return new JdbcClientDetailsService(dataSource);
    }
}
