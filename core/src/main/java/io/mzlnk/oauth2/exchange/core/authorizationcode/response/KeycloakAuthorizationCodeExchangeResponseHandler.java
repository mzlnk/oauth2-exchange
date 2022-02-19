package io.mzlnk.oauth2.exchange.core.authorizationcode.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.mzlnk.oauth2.exchange.core.ExchangeException;
import io.mzlnk.oauth2.exchange.core.authorizationcode.response.dto.GoogleAuthorizationCodeExchangeResponse;
import io.mzlnk.oauth2.exchange.core.authorizationcode.response.dto.KeycloakAuthorizationCodeExchangeResponse;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public final class KeycloakAuthorizationCodeExchangeResponseHandler extends AbstractJsonBodyAuthorizationCodeExchangeResponseHandler<KeycloakAuthorizationCodeExchangeResponse> {

    public KeycloakAuthorizationCodeExchangeResponseHandler(@NotNull ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    protected KeycloakAuthorizationCodeExchangeResponse convertValues(@NotNull Map<String, Object> values) {
        return KeycloakAuthorizationCodeExchangeResponse.from(values);
    }

    @Override
    protected KeycloakAuthorizationCodeExchangeResponse handleErrorResponse(@NotNull Response response) {
        return response.code() == 400
                ? this.handleBadRequestResponse(response)
                : super.handleErrorResponse(response);
    }

    private KeycloakAuthorizationCodeExchangeResponse handleBadRequestResponse(Response response) {
        var jsonResponse = this.readJsonBody(response);

        var message = "Exchange failed. Cause: Bad Request - %s".formatted(jsonResponse.get("error"));
        throw new ExchangeException(message, response);
    }

}
