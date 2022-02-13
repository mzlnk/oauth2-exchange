package io.mzlnk.oauth2.exchange.core.authorizationcode.response;

import com.fasterxml.jackson.databind.ObjectMapper;
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
    public OktaAuthorizationCodeExchangeResponse handleErrorResponse(Response response) {
        return null;
    }

}
