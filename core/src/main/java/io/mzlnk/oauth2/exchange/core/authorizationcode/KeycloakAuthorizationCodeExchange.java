package io.mzlnk.oauth2.exchange.core.authorizationcode;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.mzlnk.oauth2.exchange.core.authorizationcode.client.KeycloakOAuth2Client;
import io.mzlnk.oauth2.exchange.core.authorizationcode.response.KeycloakOAuth2TokenResponseHandler;
import io.mzlnk.oauth2.exchange.core.authorizationcode.response.dto.OAuth2TokenResponse;
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
public class KeycloakAuthorizationCodeExchange extends AbstractAuthorizationCodeExchange {

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
                                              @NotNull KeycloakOAuth2Client oAuth2Client,
                                              @NotNull KeycloakOAuth2TokenResponseHandler responseHandler) {
        super(httpClient, oAuth2Client, responseHandler);
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
    public OAuth2TokenResponse exchangeAuthorizationCode(@NotNull String code) {
        verifyAuthorizationCode(code);

        var builder = new FormBody.Builder()
                .add("client_id", this.oAuth2Client.getClientId())
                .add("client_secret", this.oAuth2Client.getClientSecret())
                .add("code", code)
                .add("grant_type", "authorization_code")
                .add("redirect_uri", this.oAuth2Client.getRedirectUri());

        var request = new Request.Builder()
                .url(this.oAuth2Client.getTokenUrl())
                .post(builder.build())
                .build();

        return this.makeHttpCall(request);
    }

    /**
     * Represents a builder used to configure and create an instance of a {@link KeycloakAuthorizationCodeExchange} exchange.
     */
    public static class Builder {

        private OkHttpClient httpClient;
        private KeycloakOAuth2Client oAuth2Client;
        private KeycloakOAuth2TokenResponseHandler responseHandler;

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
         * Set an OAuth2 client used in an exchange.
         * @param oAuth2Client instance of {@link KeycloakOAuth2Client} exchange client
         * @return builder instance for further chain configuration
         */
        public Builder oAuth2Client(KeycloakOAuth2Client oAuth2Client) {
            this.oAuth2Client = oAuth2Client;
            return this;
        }

        /**
         * Set an response handler used in an exchange.
         * @param responseHandler instance of {@link KeycloakOAuth2TokenResponseHandler} response handler
         * @return builder instance for further chain configuration
         */
        public Builder responseHandler(KeycloakOAuth2TokenResponseHandler responseHandler) {
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
         *     <li><code>responseHandler</code> - new instance of {@link KeycloakOAuth2TokenResponseHandler} used if no response handler explicitly provided</li>
         * </ul>
         * @return new instance of {@link FacebookAuthorizationCodeExchange} based on provided parameters
         */
        public KeycloakAuthorizationCodeExchange build() {
            return new KeycloakAuthorizationCodeExchange(
                    Optional.ofNullable(this.httpClient).orElseGet(defaultOkHttpClient()),
                    this.oAuth2Client,
                    Optional.ofNullable(this.responseHandler).orElseGet(defaultResponseHandler())
            );
        }

        private static Supplier<KeycloakOAuth2TokenResponseHandler> defaultResponseHandler() {
            return () -> new KeycloakOAuth2TokenResponseHandler(new ObjectMapper());
        }

    }

}
