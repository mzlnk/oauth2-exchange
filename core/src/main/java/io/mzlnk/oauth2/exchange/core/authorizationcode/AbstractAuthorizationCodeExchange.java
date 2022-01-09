package io.mzlnk.oauth2.exchange.core.authorizationcode;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.mzlnk.oauth2.exchange.core.ExchangeException;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.io.IOException;
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

    protected abstract R convertMapToResponse(Map<String, Object> values);

    protected R makeHttpCall(Request request) {
        try {
            var responseBody = this.httpClient.newCall(request).execute().body().string();

            var typeRef = new TypeReference<Map<String, Object>>() {};
            var values = this.objectMapper.readValue(responseBody, typeRef);

            return this.convertMapToResponse(values);
        } catch (IOException e) {
            throw new ExchangeException(e.getMessage(), e);
        }
    }

}
