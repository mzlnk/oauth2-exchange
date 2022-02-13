package io.mzlnk.oauth2.exchange.core.authorizationcode.response;

import okhttp3.Response;

import java.util.Map;

public interface AuthorizationCodeExchangeResponseHandler<R extends Map<String, Object>> {

    R handleSuccessfulResponse(Response response);

    R handleErrorResponse(Response response);

    default R handleResponse(Response response) {
        return response.isSuccessful() ? handleSuccessfulResponse(response) : handleErrorResponse(response);
    }

}
