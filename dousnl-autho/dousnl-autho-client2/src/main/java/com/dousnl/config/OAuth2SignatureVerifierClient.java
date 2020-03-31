package com.dousnl.config;

import org.springframework.security.jwt.crypto.sign.SignatureVerifier;

/**
 * TODO
 *
 * @author hanliang
 * @version 1.0
 * @date 2019/8/30 16:01
 */
@Deprecated
public interface OAuth2SignatureVerifierClient {
    /**
     * Returns the SignatureVerifier used to verify JWT tokens.
     * Fetches the public key from the Authorization server to create
     * this verifier.
     *
     * @return the new verifier used to verify JWT signatures.
     * Will be null if we cannot contact the token endpoint.
     * @throws Exception if we could not create a SignatureVerifier or contact the token endpoint.
     */
    SignatureVerifier getSignatureVerifier() throws Exception;
}
