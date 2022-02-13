package io.mzlnk.oauth2.exchange.core.authorizationcode.response;

import com.fasterxml.jackson.databind.ObjectMapper;
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
    public MicrosoftAuthorizationCodeExchangeResponse handleErrorResponse(Response response) {
        return null;
    }

}
