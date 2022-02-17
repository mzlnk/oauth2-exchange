package io.mzlnk.oauth2.exchange.core.authorizationcode.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.mzlnk.oauth2.exchange.core.ExchangeException;
import io.mzlnk.oauth2.exchange.core.authorizationcode.response.dto.MicrosoftAuthorizationCodeExchangeResponse;
import okhttp3.Response;

import java.util.Map;

public class MicrosoftAuthorizationCodeExchangeResponseHandler extends AbstractJsonBodyAuthorizationCodeExchangeResponseHandler<MicrosoftAuthorizationCodeExchangeResponse> {

    public MicrosoftAuthorizationCodeExchangeResponseHandler(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    protected MicrosoftAuthorizationCodeExchangeResponse convertValues(Map<String, Object> values) {
        return MicrosoftAuthorizationCodeExchangeResponse.from(values);
    }

    @Override
    protected MicrosoftAuthorizationCodeExchangeResponse handleErrorResponse(Response response) {
        return response.code() == 400
                ? this.handleBadRequestResponse(response)
                : super.handleErrorResponse(response);
    }

    private MicrosoftAuthorizationCodeExchangeResponse handleBadRequestResponse(Response response) {
        var jsonResponse = this.readJsonBody(response);

        var message = "Exchange failed. Cause: Bad Request - %s".formatted(jsonResponse.get("error_description"));
        throw new ExchangeException(message, response);
    }

}
