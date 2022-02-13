package io.mzlnk.oauth2.exchange.core.authorizationcode.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.mzlnk.oauth2.exchange.core.ExchangeException;
import io.mzlnk.oauth2.exchange.core.authorizationcode.response.dto.GoogleAuthorizationCodeExchangeResponse;
import io.mzlnk.oauth2.exchange.core.utils.OkHttpUtils;
import okhttp3.Response;

import java.util.Map;
import java.util.Optional;

public class GoogleAuthorizationCodeExchangeResponseHandler extends AbstractJsonBodyAuthorizationCodeExchangeResponseHandler<GoogleAuthorizationCodeExchangeResponse> {

    public GoogleAuthorizationCodeExchangeResponseHandler(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    protected GoogleAuthorizationCodeExchangeResponse convertValues(Map<String, Object> values) {
        return GoogleAuthorizationCodeExchangeResponse.from(values);
    }

    @Override
    public GoogleAuthorizationCodeExchangeResponse handleErrorResponse(Response response) {
        String responseBody = Optional
                .ofNullable(response.body())
                .map(OkHttpUtils::convertResponseBodyToString).orElse("");

        return response.code() == 400
                ? handleBadRequestHttpResponse(response, responseBody)
                : handleNonBadRequestHttpResponse(response, responseBody);
    }

    private GoogleAuthorizationCodeExchangeResponse handleBadRequestHttpResponse(Response errorResponse, String responseBody) {
        try {
            var typeRef = new TypeReference<Map<String, Object>>() {
            };
            var values = this.objectMapper.readValue(responseBody, typeRef);

            var message = "Exchange failed. Cause: %s, %s".formatted(values.get("error"), values.get("error_description"));
            throw new ExchangeException(message, responseBody);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(e);
        }
    }

    private GoogleAuthorizationCodeExchangeResponse handleNonBadRequestHttpResponse(Response errorResponse, String responseBody) {
        var message = "Exchange failed. Cause: %s".formatted(errorResponse.message());
        throw new ExchangeException(message, responseBody);
    }
}
