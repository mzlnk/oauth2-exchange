package io.mzlnk.oauth2.exchange.core.authorizationcode.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.mzlnk.oauth2.exchange.core.authorizationcode.response.dto.KeycloakAuthorizationCodeExchangeResponse;
import okhttp3.Response;

import java.util.Map;

public class KeycloakAuthorizationCodeExchangeResponseHandler extends AbstractJsonBodyAuthorizationCodeExchangeResponseHandler<KeycloakAuthorizationCodeExchangeResponse> {

    public KeycloakAuthorizationCodeExchangeResponseHandler(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    protected KeycloakAuthorizationCodeExchangeResponse convertValues(Map<String, Object> values) {
        return KeycloakAuthorizationCodeExchangeResponse.from(values);
    }

    @Override
    public KeycloakAuthorizationCodeExchangeResponse handleErrorResponse(Response response) {
        return null;
    }
}
