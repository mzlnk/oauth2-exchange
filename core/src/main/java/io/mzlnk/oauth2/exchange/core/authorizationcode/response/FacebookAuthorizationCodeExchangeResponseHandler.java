package io.mzlnk.oauth2.exchange.core.authorizationcode.response;

import com.fasterxml.jackson.databind.ObjectMapper;
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
    public FacebookAuthorizationCodeExchangeResponse handleErrorResponse(Response response) {
        return null;
    }

}
