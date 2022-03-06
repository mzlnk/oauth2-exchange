package io.mzlnk.oauth2.exchange.core.authorizationcode;

import com.google.common.base.Preconditions;
import io.mzlnk.oauth2.exchange.core.authorizationcode.client.AuthorizationCodeExchangeClient;
import io.mzlnk.oauth2.exchange.core.authorizationcode.response.AuthorizationCodeExchangeResponseHandler;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Map;

/**
 * Represents a generic template for building the exchange implementation for specific OAuth2 authorization code flow.
 * The template makes a proper HTTP request for exchanging authorization code for a token response using information
 * provided by:
 * <ul>
 *     <li>{@link AuthorizationCodeExchangeClient} representing exchange client</li>
 *     <li>{@link AuthorizationCodeExchangeResponseHandler} representing HTTP token response handler</li>
 * </ul>
 * <p>
 * The HTTP call is done using {@link OkHttpClient} from OkHttp library.
 *
 * @param <R> token response type which extends {@link Map} with {@link String}-{@link Object} key-value pairs
 */
public abstract class AbstractAuthorizationCodeExchange<R extends Map<String, Object>> implements AuthorizationCodeExchange<R> {

    /**
     * HTTP client used for making HTTP calls during exchange
     */
    protected final OkHttpClient httpClient;

    /**
     * Exchange client consisting essential information used during exchange
     */
    protected final AuthorizationCodeExchangeClient exchangeClient;

    /**
     * Response handler responsible for handling token response received during exchange
     */
    protected final AuthorizationCodeExchangeResponseHandler<R> responseHandler;

    /**
     * Construct an exchange from given exchange client and response handler using default HTTP client
     * (here: new {@link OkHttpClient} instance).
     *
     * @param exchangeClient  non-null instance of an exchange client
     * @param responseHandler non-null instance of a response handler
     * @throws NullPointerException if any of the parameters is null
     */
    protected AbstractAuthorizationCodeExchange(@NotNull AuthorizationCodeExchangeClient exchangeClient,
                                                @NotNull AuthorizationCodeExchangeResponseHandler<R> responseHandler) {
        this(new OkHttpClient(), exchangeClient, responseHandler);
    }

    /**
     * Construct an exchange from given exchange client, response handler and HTTP client ({@link OkHttpClient implementation}.
     *
     * @param httpClient      non-null instance of a HTTP client
     * @param exchangeClient  non-null instance of an exchange client
     * @param responseHandler non-null instance of a response handler
     * @throws NullPointerException if any of the parameters is null
     */
    protected AbstractAuthorizationCodeExchange(@NotNull OkHttpClient httpClient,
                                                @NotNull AuthorizationCodeExchangeClient exchangeClient,
                                                @NotNull AuthorizationCodeExchangeResponseHandler<R> responseHandler) {
        Preconditions.checkNotNull(httpClient, "Parameter `httpClient` cannot be null.");
        Preconditions.checkNotNull(exchangeClient, "Parameter `exchangeClient` cannot be null.");
        Preconditions.checkNotNull(responseHandler, "Parameter `responseHandler` cannot be null.");

        this.httpClient = httpClient;
        this.exchangeClient = exchangeClient;
        this.responseHandler = responseHandler;
    }

    /**
     * Verifies if incoming authorization code parameter is valid.
     *
     * @param code string representation of an authorization code obtained from HTTP response
     * @throws NullPointerException     if authorization code is null
     * @throws IllegalArgumentException if authorization code is an empty string
     */
    protected final void verifyAuthorizationCode(String code) {
        Preconditions.checkNotNull(code, "Authorization code cannot be null");
        Preconditions.checkArgument(!code.isEmpty(), "Authorization code cannot be empty");
    }

    /**
     * Makes HTTP call using provided HTTP client and then handling returned token response using provided response handler.
     *
     * @param request created HTTP request for a HTTP call
     * @return token response which consists of a response data in {@link Map} form
     * @throws IllegalStateException if HTTP call failed or cannot handle returned response properly
     */
    protected R makeHttpCall(Request request) {
        try {
            var response = this.httpClient.newCall(request).execute();
            return responseHandler.handleResponse(response);
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

}
