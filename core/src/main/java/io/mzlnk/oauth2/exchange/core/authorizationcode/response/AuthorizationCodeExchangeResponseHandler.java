package io.mzlnk.oauth2.exchange.core.authorizationcode.response;

import okhttp3.Response;

import java.util.Map;

public interface AuthorizationCodeExchangeResponseHandler<R extends Map<String, Object>> {

    R handleResponse(Response response);

}
