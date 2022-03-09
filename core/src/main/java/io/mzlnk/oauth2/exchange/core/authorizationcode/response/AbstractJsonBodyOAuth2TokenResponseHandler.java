package io.mzlnk.oauth2.exchange.core.authorizationcode.response;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import io.mzlnk.oauth2.exchange.core.ExchangeException;
import io.mzlnk.oauth2.exchange.core.authorizationcode.response.dto.OAuth2TokenResponse;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

/**
 * Represents a template for a handler of the incoming HTTP response which consists data in form of a JSON body.
 */
public abstract class AbstractJsonBodyOAuth2TokenResponseHandler extends AbstractOAuth2TokenResponseHandler {

    protected final OAuth2TokenResponse.Factory responseFactory;
    protected final ObjectMapper objectMapper;

    /**
     * Constructs a handler with a given {@link OAuth2TokenResponse.Factory} response factory used to create
     * {@link OAuth2TokenResponse} response and Jackson object mapper used during JSON body deserialization.
     *
     * @param responseFactory response factory used to create {@link OAuth2TokenResponse} response
     * @param objectMapper Jackson object mapper used to parse incoming JSON body
     */
    protected AbstractJsonBodyOAuth2TokenResponseHandler(@NotNull OAuth2TokenResponse.Factory responseFactory,
                                                         @NotNull ObjectMapper objectMapper) {
        Preconditions.checkNotNull(responseFactory, "Parameter `responseFactory` cannot be null.");
        Preconditions.checkNotNull(objectMapper, "Parameter `objectMapper` cannot be null.");
        this.objectMapper = objectMapper;
        this.responseFactory = responseFactory;
    }

    /**
     * Handles successful HTTP response and constructs a {@link Map} like response object which consists of data
     * retrieved from JSON body from incoming HTTP response.
     *
     * @param response incoming successful HTTP response
     * @return {@link OAuth2TokenResponse} response which consists of a HTTP response data
     * @throws NullPointerException  if response body is not present
     * @throws IllegalStateException if response body is invalid/ cannot be deserialized properly
     */
    @Override
    protected OAuth2TokenResponse handleSuccessfulResponse(@NotNull Response response) {
        try {
            var responseBody = Objects.requireNonNull(response.peekBody(Long.MAX_VALUE)).string();

            var typeRef = new TypeReference<Map<String, Object>>() {
            };
            var values = this.objectMapper.readValue(responseBody, typeRef);

            return this.responseFactory.create(values);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Handles error HTTP response by throwing an {@link ExchangeException} which consists of an error message
     * retrieved from the HTTP response
     *
     * @param response incoming error HTTP response
     * @return none
     * @throws ExchangeException exception which consists of an error message
     */
    @Override
    protected OAuth2TokenResponse handleErrorResponse(@NotNull Response response) {
        var message = "Exchange failed. Cause: %s".formatted(response.message());
        throw new ExchangeException(message, response);
    }

    /**
     * Reads and deserialize an JSON body from incoming HTTP request to a {@link Map} object.
     *
     * @param response incoming HTTP response
     * @return {@link Map} representation of the JSON body from incoming HTTP request
     * @throws NullPointerException if response body is not present
     * @throws IllegalStateException if response body is invalid/ cannot be deserialized properly
     */
    protected final Map<String, Object> readJsonBody(@NotNull Response response) {
        try {
            var responseBody = Objects.requireNonNull(response.peekBody(Long.MAX_VALUE)).string();

            var typeRef = new TypeReference<Map<String, Object>>() {
            };
            return this.objectMapper.readValue(responseBody, typeRef);
        } catch (IOException | NullPointerException e) {
            throw new IllegalStateException(e);
        }
    }

}
