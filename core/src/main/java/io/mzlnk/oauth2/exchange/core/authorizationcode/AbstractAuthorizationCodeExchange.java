package io.mzlnk.oauth2.exchange.core.authorizationcode;

import com.google.common.base.Preconditions;
import io.mzlnk.oauth2.exchange.core.authorizationcode.client.OAuth2Client;
import io.mzlnk.oauth2.exchange.core.authorizationcode.response.OAuth2TokenResponseHandler;
import io.mzlnk.oauth2.exchange.core.authorizationcode.response.dto.OAuth2TokenResponse;
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
 *     <li>{@link OAuth2Client} representing exchange client</li>
 *     <li>{@link OAuth2TokenResponseHandler} representing HTTP token response handler</li>
 * </ul>
 * <p>
 * The HTTP call is done using {@link OkHttpClient} from OkHttp library.
 */
public abstract class AbstractAuthorizationCodeExchange implements AuthorizationCodeExchange {

    /**
     * HTTP client used for making HTTP calls during exchange
     */
    protected final OkHttpClient httpClient;

    /**
     * Exchange client consisting essential information used during exchange
     */
    protected final OAuth2Client oAuth2Client;

    /**
     * Response handler responsible for handling token response received during exchange
     */
    protected final OAuth2TokenResponseHandler responseHandler;

    /**
     * Construct an exchange from given exchange client and response handler using default HTTP client
     * (here: new {@link OkHttpClient} instance).
     *
     * @param oAuth2Client  non-null instance of an exchange client
     * @param responseHandler non-null instance of a response handler
     * @throws NullPointerException if any of the parameters is null
     */
    protected AbstractAuthorizationCodeExchange(@NotNull OAuth2Client oAuth2Client,
                                                @NotNull OAuth2TokenResponseHandler responseHandler) {
        this(new OkHttpClient(), oAuth2Client, responseHandler);
    }

    /**
     * Construct an exchange from given exchange client, response handler and HTTP client ({@link OkHttpClient implementation}.
     *
     * @param httpClient      non-null instance of a HTTP client
     * @param oAuth2Client  non-null instance of an exchange client
     * @param responseHandler non-null instance of a response handler
     * @throws NullPointerException if any of the parameters is null
     */
    protected AbstractAuthorizationCodeExchange(@NotNull OkHttpClient httpClient,
                                                @NotNull OAuth2Client oAuth2Client,
                                                @NotNull OAuth2TokenResponseHandler responseHandler) {
        Preconditions.checkNotNull(httpClient, "Parameter `httpClient` cannot be null.");
        Preconditions.checkNotNull(oAuth2Client, "Parameter `exchangeClient` cannot be null.");
        Preconditions.checkNotNull(responseHandler, "Parameter `responseHandler` cannot be null.");

        this.httpClient = httpClient;
        this.oAuth2Client = oAuth2Client;
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
    protected OAuth2TokenResponse makeHttpCall(Request request) {
        try {
            var response = this.httpClient.newCall(request).execute();
            return responseHandler.handleResponse(response);
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

}
