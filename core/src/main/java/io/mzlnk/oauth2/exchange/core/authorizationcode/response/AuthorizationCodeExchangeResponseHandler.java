package io.mzlnk.oauth2.exchange.core.authorizationcode.response;

import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public interface AuthorizationCodeExchangeResponseHandler<R extends Map<String, Object>> {

    R handleResponse(@NotNull Response response);

}
