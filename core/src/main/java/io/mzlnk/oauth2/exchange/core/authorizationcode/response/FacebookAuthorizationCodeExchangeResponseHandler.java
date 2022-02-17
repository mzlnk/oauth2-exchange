package io.mzlnk.oauth2.exchange.core.authorizationcode.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.mzlnk.oauth2.exchange.core.ExchangeException;
import io.mzlnk.oauth2.exchange.core.authorizationcode.response.dto.FacebookAuthorizationCodeExchangeResponse;
import okhttp3.Response;

import java.util.Map;

public class FacebookAuthorizationCodeExchangeResponseHandler extends AbstractJsonBodyAuthorizationCodeExchangeResponseHandler<FacebookAuthorizationCodeExchangeResponse> {

    public FacebookAuthorizationCodeExchangeResponseHandler(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    protected FacebookAuthorizationCodeExchangeResponse convertValues(Map<String, Object> values) {
        return FacebookAuthorizationCodeExchangeResponse.from(values);
    }

    @Override
    protected FacebookAuthorizationCodeExchangeResponse handleErrorResponse(Response response) {
        return response.code() == 400
                ? this.handleBadRequestResponse(response)
                : super.handleErrorResponse(response);
    }

    private FacebookAuthorizationCodeExchangeResponse handleBadRequestResponse(Response response) {
        var jsonResponse = this.readJsonBody(response);

        var error = (Map<String, Object>) jsonResponse.get("error");

        var message = "Exchange failed. Cause: Bad Request - %s".formatted(error.get("message"));
        throw new ExchangeException(message, response);
    }

}
