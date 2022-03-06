package io.mzlnk.oauth2.exchange.core.authorizationcode;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.mzlnk.oauth2.exchange.core.authorizationcode.client.KeycloakAuthorizationCodeExchangeClient;
import io.mzlnk.oauth2.exchange.core.authorizationcode.response.KeycloakAuthorizationCodeExchangeResponseHandler;
import io.mzlnk.oauth2.exchange.core.authorizationcode.response.dto.KeycloakAuthorizationCodeExchangeResponse;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import static io.mzlnk.oauth2.exchange.core.utils.OkHttpUtils.defaultOkHttpClient;

/**
 * Represents a ready-for-use exchange implementation for a Keycloak authorization provider responsible for exchanging
 * authorization code for a token response in OAuth2 authorization code flow.
 * <br />
 * <p> Details about a HTTP call to GitHub auth provider made during exchange:</p>
 * <ul>
 *     <li><b>URL</b>: {host}/auth/realms/{realm}/protocol/openid-connect/token</li>
 *     <li><b>HTTP method</b>: POST</li>
 *     <li><b>Content-Type</b>: application/x-www-form-urlencoded</li>
 *     <li><b>Form fields:</b>
 *     <ul>
 *         <li><code>client_id</code></li>
 *         <li><code>client_secret</code></li>
 *         <li><code>redirect_uri</code></li>
 *         <li><code>grant_type</code></li>
 *         <li><code>code</code></li>
 *     </ul>
 *     </li>
 * </ul>
 *
 * <p>
 * The exchange implementation is created based on information posted on official
 * <a href="https://www.keycloak.org/docs/latest/server_admin/">documentation site</a>.
 * </p>
 */
public class KeycloakAuthorizationCodeExchange extends AbstractAuthorizationCodeExchange<KeycloakAuthorizationCodeExchangeResponse> {

    /**
     * Creates an instance of a builder used to create a {@link KeycloakAuthorizationCodeExchange} instance with given
     * parameters.
     *
     * @return newly created instance of a {@link KeycloakAuthorizationCodeExchange.Builder}
     */
    public static Builder builder() {
        return new Builder();
    }

    private KeycloakAuthorizationCodeExchange(@NotNull OkHttpClient httpClient,
                                              @NotNull KeycloakAuthorizationCodeExchangeClient exchangeClient,
                                              @NotNull KeycloakAuthorizationCodeExchangeResponseHandler responseHandler) {
        super(httpClient, exchangeClient, responseHandler);
    }

    /**
     * Exchange a given authorization code for a token response by making proper HTTP call to a Keycloak authorization
     * provider based on information posted on official
     * <a href="https://www.keycloak.org/docs/latest/server_admin/">documentation site</a>.
     *
     * @param code authorization code obtained from incoming HTTP response
     * @return token response which consists of a response data in {@link Map} form
     * @throws NullPointerException if authorization code is null
     * @throws IllegalArgumentException if authorization code is empty
     * @throws IllegalStateException if HTTP call made during exchange failed or cannot handle returned response properly
     */
    @Override
    public KeycloakAuthorizationCodeExchangeResponse exchangeAuthorizationCode(@NotNull String code) {
        verifyAuthorizationCode(code);

        var builder = new FormBody.Builder()
                .add("client_id", this.exchangeClient.getClientId())
                .add("client_secret", this.exchangeClient.getClientSecret())
                .add("code", code)
                .add("grant_type", "authorization_code")
                .add("redirect_uri", this.exchangeClient.getRedirectUri());

        var url = "%s/protocol/openid-connect/token".formatted(this.exchangeClient.getClientBaseUrl());

        var request = new Request.Builder()
                .url(url)
                .post(builder.build())
                .build();

        return this.makeHttpCall(request);
    }

    /**
     * Represents a builder used to configure and create an instance of a {@link KeycloakAuthorizationCodeExchange} exchange.
     */
    public static class Builder {

        private OkHttpClient httpClient;
        private KeycloakAuthorizationCodeExchangeClient exchangeClient;
        private KeycloakAuthorizationCodeExchangeResponseHandler responseHandler;

        private Builder() {

        }

        /**
         * Set an HTTP client used in an exchange.
         * @param httpClient instance of HTTP client ({@link OkHttpClient} implementation)
         * @return builder instance for further chain configuration
         */
        public Builder httpClient(OkHttpClient httpClient) {
            this.httpClient = httpClient;
            return this;
        }

        /**
         * Set an exchange client used in an exchange.
         * @param exchangeClient instance of {@link KeycloakAuthorizationCodeExchangeClient} exchange client
         * @return builder instance for further chain configuration
         */
        public Builder exchangeClient(KeycloakAuthorizationCodeExchangeClient exchangeClient) {
            this.exchangeClient = exchangeClient;
            return this;
        }

        /**
         * Set an response handler used in an exchange.
         * @param responseHandler instance of {@link KeycloakAuthorizationCodeExchangeResponseHandler} response handler
         * @return builder instance for further chain configuration
         */
        public Builder responseHandler(KeycloakAuthorizationCodeExchangeResponseHandler responseHandler) {
            this.responseHandler = responseHandler;
            return this;
        }

        /**
         * Constructs an instance of a {@link KeycloakAuthorizationCodeExchange} based on provided parameters.
         *
         * <p>Required parameters:</p>
         * <ul>
         *     <li><code>exchangeClient</code></li>
         * </ul>
         *
         * <p>Optional parameters:</p>
         * <ul>
         *     <li><code>httpClient</code> - new instance of {@link OkHttpClient} used if no HTTP client explicitly provided</li>
         *     <li><code>responseHandler</code> - new instance of {@link KeycloakAuthorizationCodeExchangeResponseHandler} used if no response handler explicitly provided</li>
         * </ul>
         * @return new instance of {@link FacebookAuthorizationCodeExchange} based on provided parameters
         */
        public KeycloakAuthorizationCodeExchange build() {
            return new KeycloakAuthorizationCodeExchange(
                    Optional.ofNullable(this.httpClient).orElseGet(defaultOkHttpClient()),
                    this.exchangeClient,
                    Optional.ofNullable(this.responseHandler).orElseGet(defaultResponseHandler())
            );
        }

        private static Supplier<KeycloakAuthorizationCodeExchangeResponseHandler> defaultResponseHandler() {
            return () -> new KeycloakAuthorizationCodeExchangeResponseHandler(new ObjectMapper());
        }

    }

}
