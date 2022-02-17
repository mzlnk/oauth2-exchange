package io.mzlnk.oauth2.exchange.core.authorizationcode.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.mzlnk.oauth2.exchange.core.ExchangeException;
import io.mzlnk.oauth2.exchange.core.authorizationcode.response.dto.OktaAuthorizationCodeExchangeResponse;
import okhttp3.Response;

import java.util.Map;

public class OktaAuthorizationCodeExchangeResponseHandler extends AbstractJsonBodyAuthorizationCodeExchangeResponseHandler<OktaAuthorizationCodeExchangeResponse> {

    public OktaAuthorizationCodeExchangeResponseHandler(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    protected OktaAuthorizationCodeExchangeResponse convertValues(Map<String, Object> values) {
        return OktaAuthorizationCodeExchangeResponse.from(values);
    }

    @Override
    protected OktaAuthorizationCodeExchangeResponse handleErrorResponse(Response response) {
        return switch (response.code()) {
            case 400 -> this.handleBadRequestResponse(response);
            case 401 -> this.handleUnauthorizedResponse(response);
            default -> super.handleErrorResponse(response);
        };
    }

    private OktaAuthorizationCodeExchangeResponse handleBadRequestResponse(Response response) {
        var jsonResponse = this.readJsonBody(response);

        var message = "Exchange failed. Cause: Bad Request - %s".formatted(jsonResponse.get("error_description"));
        throw new ExchangeException(message, response);
    }

    private OktaAuthorizationCodeExchangeResponse handleUnauthorizedResponse(Response response) {
        var jsonResponse = this.readJsonBody(response);

        var message = "Exchange failed. Cause: Unauthorized - %s".formatted(jsonResponse.get("error_description"));
        throw new ExchangeException(message, response);
    }

}
