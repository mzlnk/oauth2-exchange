package io.mzlnk.oauth2.exchange.core.authorizationcode;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.mzlnk.oauth2.exchange.core.ExchangeException;
import io.mzlnk.oauth2.exchange.core.authorizationcode.client.AuthorizationCodeExchangeClient;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.io.IOException;
import java.util.Map;

public abstract class AbstractAuthorizationCodeExchange<R extends Map<String, Object>> implements AuthorizationCodeExchange<R> {

    protected final ObjectMapper objectMapper;
    protected final OkHttpClient httpClient;

    protected final AuthorizationCodeExchangeClient exchangeClient;

    protected AbstractAuthorizationCodeExchange(AuthorizationCodeExchangeClient exchangeClient) {
        this(new OkHttpClient(), exchangeClient);
    }

    protected AbstractAuthorizationCodeExchange(OkHttpClient httpClient,
                                                AuthorizationCodeExchangeClient exchangeClient) {
        this.objectMapper = new ObjectMapper();
        this.httpClient = httpClient;
        this.exchangeClient = exchangeClient;
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
