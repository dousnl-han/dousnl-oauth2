package com.dousnl.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.jwt.crypto.sign.SignatureVerifier;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * TODO
 *
 * @author hanliang
 * @version 1.0
 * @date 2019/8/30 16:01
 */
@Deprecated
@Slf4j
//@Component
public class UaaSignatureVerifierClient implements OAuth2SignatureVerifierClient{
    private final RestTemplate restTemplate;
    private String publicKeyEndpoint="http://localhost:8080/oauth/token_key";

    public UaaSignatureVerifierClient(@Qualifier("loadBalancedRestTemplate") RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public SignatureVerifier getSignatureVerifier() throws Exception {
        try {
            HttpEntity<Void> request = new HttpEntity<Void>(new HttpHeaders());
            String key = (String) restTemplate
                    .exchange(publicKeyEndpoint, HttpMethod.GET, request, Map.class).getBody()
                    .get("value");
            return new RsaVerifier(key);
        } catch (IllegalStateException ex) {
            log.warn("could not contact UAA to get public key");
            return null;
        }
    }
}
