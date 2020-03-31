package com.dousnl.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Map;

/**
 * TODO
 *
 * @author hanliang
 * @version 1.0
 * @date 2019/8/26 18:15
 */
@Configuration
public class JwtConfig {

    //@Autowired
    //private JwtAccessTokenConverter jwtAccessTokenConverter;

    //@Autowired
    //private ResourceServerProperties resourceServerProperties;

    @Autowired
    private RestTemplate restTemplate;

    @Bean
    public TokenStore tokenStore(JwtAccessTokenConverter jwtAccessTokenConverter){
        return new JwtTokenStore(jwtAccessTokenConverter);
    }

    /**
     * v1.1 start project to uaa get public.cert
     * @return
     */
//    @Bean
//    public JwtAccessTokenConverter jwtAccessTokenConverter(OAuth2SignatureVerifierClient signatureVerifierClient) {
//        return new OAuth2JwtAccessTokenConverter(signatureVerifierClient);
//    }
    //对称方式效验
//    @Bean
//    public JwtAccessTokenConverter accessTokenConverter() {
//        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
//        converter.setSigningKey("test-secret");
//        return converter;
//    }
    //非对称方式效验
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        //第一种方式配置公钥(本地配置)
        JwtAccessTokenConverter converter= new JwtAccessTokenConverter();
        Resource resource= new ClassPathResource("public.cert");
        String  publicKey;
        try {
            publicKey=new String(FileCopyUtils.copyToByteArray(resource.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException();
        }
        //第一种方式
        converter.setVerifierKey(publicKey);
        //第二种方式
        converter.setVerifier(new RsaVerifier(publicKey));
        System.out.println(">>>>第一种方式获取公钥>>>uaa publicKey:" + publicKey);

        //第二种方式配置公钥(认证服务器获取)
        //HttpEntity<Void> request = new HttpEntity<Void>(new HttpHeaders());
        //String publicKeyFromUaa=(String)restTemplate.exchange("http://localhost:8080/oauth/token_key",HttpMethod.GET,request,Map.class).getBody()
          //      .get("value");
        //System.out.println(">>>>第二种方式获取公钥>>>uaa publicKeyFromUaa:" + publicKeyFromUaa);

        return converter;
    }

}
