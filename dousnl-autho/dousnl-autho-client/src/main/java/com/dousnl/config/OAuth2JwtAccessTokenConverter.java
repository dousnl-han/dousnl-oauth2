package com.dousnl.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.jwt.crypto.sign.SignatureVerifier;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * TODO
 *
 * @author hanliang
 * @version 1.0
 * @date 2019/8/30 15:04
 */
@Deprecated
@Slf4j
public class OAuth2JwtAccessTokenConverter extends JwtAccessTokenConverter {

    /**
     * When did we last fetch the public key?
     */
    private long lastKeyFetchTimestamp;
    private long publicKeyRefreshRateLimit = 10 * 1000L;
    private long ttl = 24 * 60 * 60 * 1000L;
    private final OAuth2SignatureVerifierClient signatureVerifierClient;


    public OAuth2JwtAccessTokenConverter(OAuth2SignatureVerifierClient signatureVerifierClient) {
        this.signatureVerifierClient = signatureVerifierClient;
        tryCreateSignatureVerifier();
    }

    private boolean tryCreateSignatureVerifier() {
        long t = System.currentTimeMillis();
        if (t - lastKeyFetchTimestamp < publicKeyRefreshRateLimit) {
            return false;
        }
        try {
            SignatureVerifier verifier = signatureVerifierClient.getSignatureVerifier();
            if (verifier != null) {
                setVerifier(verifier);
                lastKeyFetchTimestamp = t;
                log.debug("Public key retrieved from OAuth2 server to create SignatureVerifier");
                return true;
            }
        } catch (Throwable ex) {
            log.error("could not get public key from OAuth2 server to create SignatureVerifier", ex);
        }
        return false;
    }

    @Override
    protected Map<String, Object> decode(String token) {
        return super.decode(token);
    }

    @Override
    public OAuth2Authentication extractAuthentication(Map<String, ?> map) {
        OAuth2Authentication authentication = super.extractAuthentication(map);
        authentication.setDetails(map);
        return authentication;
    }
}
