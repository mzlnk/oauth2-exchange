package io.mzlnk.oauth2.exchange.core.authorizationcode;

import io.mzlnk.oauth2.exchange.core.authorizationcode.client.AuthorizationCodeExchangeClient;
import io.mzlnk.oauth2.exchange.core.authorizationcode.response.AuthorizationCodeExchangeResponseHandler;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.io.IOException;
import java.util.Map;

public abstract class AbstractAuthorizationCodeExchange<R extends Map<String, Object>> implements AuthorizationCodeExchange<R> {

    protected final OkHttpClient httpClient;

    protected final AuthorizationCodeExchangeClient exchangeClient;
    protected final AuthorizationCodeExchangeResponseHandler<R> responseHandler;

    protected AbstractAuthorizationCodeExchange(AuthorizationCodeExchangeClient exchangeClient,
                                                AuthorizationCodeExchangeResponseHandler<R> responseHandler) {
        this(new OkHttpClient(), exchangeClient, responseHandler);
    }

    protected AbstractAuthorizationCodeExchange(OkHttpClient httpClient,
                                                AuthorizationCodeExchangeClient exchangeClient,
                                                AuthorizationCodeExchangeResponseHandler<R> responseHandler) {
        this.httpClient = httpClient;
        this.exchangeClient = exchangeClient;
        this.responseHandler = responseHandler;
    }

    protected R makeHttpCall(Request request) {
        try {
            var response = this.httpClient.newCall(request).execute();
            return responseHandler.handleResponse(response);
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

}
