package io.mzlnk.oauth2.exchange.core.authorizationcode.response;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.Response;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public abstract class AbstractJsonBodyAuthorizationCodeExchangeResponseHandler<R extends Map<String, Object>> implements AuthorizationCodeExchangeResponseHandler<R> {

    protected final ObjectMapper objectMapper;

    protected AbstractJsonBodyAuthorizationCodeExchangeResponseHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    protected abstract R convertValues(Map<String, Object> values);

    @Override
    public R handleSuccessfulResponse(Response response) {
        try {
            var responseBody = Objects.requireNonNull(response.body()).string();

            var typeRef = new TypeReference<Map<String, Object>>() {};
            var values = this.objectMapper.readValue(responseBody, typeRef);

            return this.convertValues(values);
        } catch (IOException | NullPointerException e) {
            throw new IllegalStateException(e);
        }
    }

}
