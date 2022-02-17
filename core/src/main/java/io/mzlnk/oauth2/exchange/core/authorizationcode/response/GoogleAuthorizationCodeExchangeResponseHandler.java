package io.mzlnk.oauth2.exchange.core.authorizationcode.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.mzlnk.oauth2.exchange.core.ExchangeException;
import io.mzlnk.oauth2.exchange.core.authorizationcode.response.dto.GoogleAuthorizationCodeExchangeResponse;
import okhttp3.Response;

import java.util.Map;

public class GoogleAuthorizationCodeExchangeResponseHandler extends AbstractJsonBodyAuthorizationCodeExchangeResponseHandler<GoogleAuthorizationCodeExchangeResponse> {

    public GoogleAuthorizationCodeExchangeResponseHandler(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    protected GoogleAuthorizationCodeExchangeResponse convertValues(Map<String, Object> values) {
        return GoogleAuthorizationCodeExchangeResponse.from(values);
    }

    @Override
    protected GoogleAuthorizationCodeExchangeResponse handleErrorResponse(Response response) {
        return response.code() == 400
                ? this.handleBadRequestResponse(response)
                : super.handleErrorResponse(response);
    }

    private GoogleAuthorizationCodeExchangeResponse handleBadRequestResponse(Response response) {
        var jsonResponse = this.readJsonBody(response);

        var message = "Exchange failed. Cause: Bad Request - %s".formatted(jsonResponse.get("error_description"));
        throw new ExchangeException(message, response);
    }

}
