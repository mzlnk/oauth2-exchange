package io.mzlnk.oauth2.exchange.core.authorizationcode.response;

import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public abstract class AbstractAuthorizationCodeExchangeResponseHandler<R extends Map<String, Object>> implements AuthorizationCodeExchangeResponseHandler<R> {

    protected abstract R handleSuccessfulResponse(@NotNull Response response);
    protected abstract R handleErrorResponse(@NotNull Response response);

    @Override
    public R handleResponse(@NotNull Response response) {
        return response.isSuccessful() ? handleSuccessfulResponse(response) : handleErrorResponse(response);
    }

}
