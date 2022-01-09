package io.mzlnk.oauth2.exchange.core.authorizationcode;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;

import java.util.Map;

public abstract class AbstractAuthorizationCodeExchange<R extends Map<String, Object>> implements AuthorizationCodeExchange<R> {

    protected final ObjectMapper objectMapper;
    protected final OkHttpClient httpClient;

    protected final String clientId;
    protected final String clientSecret;
    protected final String redirectUri;

    protected AbstractAuthorizationCodeExchange(String clientId,
                                                String clientSecret,
                                                String redirectUri) {
        this(new OkHttpClient(), clientId, clientSecret, redirectUri);
    }

    protected AbstractAuthorizationCodeExchange(OkHttpClient httpClient,
                                                String clientId,
                                                String clientSecret,
                                                String redirectUri) {
        this.objectMapper = new ObjectMapper();
        this.httpClient = httpClient;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
    }

}
