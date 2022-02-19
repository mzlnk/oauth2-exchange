package io.mzlnk.oauth2.exchange.core.authorizationcode;

import com.google.common.base.Preconditions;
import io.mzlnk.oauth2.exchange.core.authorizationcode.client.AuthorizationCodeExchangeClient;
import io.mzlnk.oauth2.exchange.core.authorizationcode.response.AuthorizationCodeExchangeResponseHandler;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Map;

public abstract class AbstractAuthorizationCodeExchange<R extends Map<String, Object>> implements AuthorizationCodeExchange<R> {

    protected final OkHttpClient httpClient;

    protected final AuthorizationCodeExchangeClient exchangeClient;
    protected final AuthorizationCodeExchangeResponseHandler<R> responseHandler;

    protected AbstractAuthorizationCodeExchange(@NotNull AuthorizationCodeExchangeClient exchangeClient,
                                                @NotNull AuthorizationCodeExchangeResponseHandler<R> responseHandler) {
        this(new OkHttpClient(), exchangeClient, responseHandler);
    }

    protected AbstractAuthorizationCodeExchange(@NotNull OkHttpClient httpClient,
                                                @NotNull AuthorizationCodeExchangeClient exchangeClient,
                                                @NotNull AuthorizationCodeExchangeResponseHandler<R> responseHandler) {
        Preconditions.checkNotNull(httpClient, "Parameter `httpClient` cannot be null.");
        Preconditions.checkNotNull(exchangeClient, "Parameter `exchangeClient` cannot be null.");
        Preconditions.checkNotNull(responseHandler, "Parameter `responseHandler` cannot be null.");

        this.httpClient = httpClient;
        this.exchangeClient = exchangeClient;
        this.responseHandler = responseHandler;
    }

    protected final void verifyAuthorizationCode(String code) {
        Preconditions.checkNotNull(code, "Authorization code cannot be null");
        Preconditions.checkArgument(!code.isEmpty(), "Authorization code cannot be empty");
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
