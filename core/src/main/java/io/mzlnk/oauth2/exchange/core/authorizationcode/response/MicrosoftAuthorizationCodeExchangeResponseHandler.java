package io.mzlnk.oauth2.exchange.core.authorizationcode.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.mzlnk.oauth2.exchange.core.ExchangeException;
import io.mzlnk.oauth2.exchange.core.authorizationcode.response.dto.MicrosoftAuthorizationCodeExchangeResponse;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class MicrosoftAuthorizationCodeExchangeResponseHandler extends AbstractJsonBodyAuthorizationCodeExchangeResponseHandler<MicrosoftAuthorizationCodeExchangeResponse> {

    public MicrosoftAuthorizationCodeExchangeResponseHandler(@NotNull ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    protected MicrosoftAuthorizationCodeExchangeResponse convertValues(@NotNull Map<String, Object> values) {
        return MicrosoftAuthorizationCodeExchangeResponse.from(values);
    }

    @Override
    protected MicrosoftAuthorizationCodeExchangeResponse handleErrorResponse(@NotNull Response response) {
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
