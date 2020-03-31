//package com.dousnl.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.core.io.Resource;
//import org.springframework.security.jwt.crypto.sign.RsaVerifier;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
//import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
//import org.springframework.util.FileCopyUtils;
//import org.springframework.web.client.RestTemplate;
//
//import java.io.IOException;
//
///**
// * TODO
// *
// * @author hanliang
// * @version 1.0
// * @date 2019/8/26 18:15
// */
//@Configuration
//public class JwtConfig {
//
//    @Autowired
//    private JwtAccessTokenConverter jwtAccessTokenConverter;
//
//    //@Autowired
//    //private ResourceServerProperties resourceServerProperties;
//
//    @Bean
//    public TokenStore tokenStore(){
//        return new JwtTokenStore(jwtAccessTokenConverter);
//    }
//
//    /**
//     * v1.1 start project to uaa get public.cert
//     * @return
//     */
////    @Bean
////    public JwtAccessTokenConverter jwtAccessTokenConverter(OAuth2SignatureVerifierClient signatureVerifierClient) {
////        return new OAuth2JwtAccessTokenConverter(signatureVerifierClient);
////    }
//    //对称方式效验
////    @Bean
////    public JwtAccessTokenConverter accessTokenConverter() {
////        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
////        converter.setSigningKey("test-secret");
////        return converter;
////    }
//    //非对称方式效验
//    @Bean
//    public JwtAccessTokenConverter accessTokenConverter() {
//        JwtAccessTokenConverter converter= new JwtAccessTokenConverter();
//        Resource resource= new ClassPathResource("public.cert");
//        String  publicKey;
//        try {
//            publicKey=new String(FileCopyUtils.copyToByteArray(resource.getInputStream()));
//        } catch (IOException e) {
//            throw new RuntimeException();
//        }
//        //第一种方式
//        converter.setVerifierKey(publicKey);
//        //第二种方式
//        converter.setVerifier(new RsaVerifier(publicKey));
//        return converter;
//    }
//    @Bean
//    public RestTemplate loadBalancedRestTemplate() {
//        RestTemplate restTemplate = new RestTemplate();
//        return restTemplate;
//    }
//}
