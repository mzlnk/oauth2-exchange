package io.mzlnk.oauth2.exchange.core.authorizationcode.response;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.mzlnk.oauth2.exchange.core.ExchangeException;
import okhttp3.Response;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public abstract class AbstractJsonBodyAuthorizationCodeExchangeResponseHandler<R extends Map<String, Object>> extends AbstractAuthorizationCodeExchangeResponseHandler<R> {

    protected final ObjectMapper objectMapper;

    protected AbstractJsonBodyAuthorizationCodeExchangeResponseHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    protected abstract R convertValues(Map<String, Object> values);

    @Override
    protected R handleSuccessfulResponse(Response response) {
        try {
            var responseBody = Objects.requireNonNull(response.peekBody(Long.MAX_VALUE)).string();

            var typeRef = new TypeReference<Map<String, Object>>() {};
            var values = this.objectMapper.readValue(responseBody, typeRef);

            return this.convertValues(values);
        } catch (IOException | NullPointerException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    protected R handleErrorResponse(Response response) {
        var message = "Exchange failed. Cause: %s".formatted(response.message());
        throw new ExchangeException(message, response);
    }

    protected final Map<String, Object> readJsonBody(Response response) {
        try {
            var responseBody = Objects.requireNonNull(response.peekBody(Long.MAX_VALUE)).string();

            var typeRef = new TypeReference<Map<String, Object>>() {};
            return this.objectMapper.readValue(responseBody, typeRef);
        } catch(IOException | NullPointerException e) {
            throw new IllegalStateException(e);
        }
    }

}
