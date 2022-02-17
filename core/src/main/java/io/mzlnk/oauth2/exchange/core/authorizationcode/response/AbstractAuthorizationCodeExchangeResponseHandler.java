package io.mzlnk.oauth2.exchange.core.authorizationcode.response;

import okhttp3.Response;

import java.util.Map;

public abstract class AbstractAuthorizationCodeExchangeResponseHandler<R extends Map<String, Object>> implements AuthorizationCodeExchangeResponseHandler<R> {

    protected abstract R handleSuccessfulResponse(Response response);
    protected abstract R handleErrorResponse(Response response);

    @Override
    public R handleResponse(Response response) {
        return response.isSuccessful() ? handleSuccessfulResponse(response) : handleErrorResponse(response);
    }

}
