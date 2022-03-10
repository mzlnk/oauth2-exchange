package io.mzlnk.oauth2.exchange.core.authorizationcode;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.mzlnk.oauth2.exchange.core.authorizationcode.client.FacebookOAuth2Client;
import io.mzlnk.oauth2.exchange.core.authorizationcode.response.FacebookOAuth2TokenResponseHandler;
import io.mzlnk.oauth2.exchange.core.authorizationcode.response.dto.OAuth2TokenResponse;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import static io.mzlnk.oauth2.exchange.core.utils.OkHttpUtils.defaultOkHttpClient;

/**
 * Represents a ready-for-use exchange implementation for a Facebook authorization provider responsible for exchanging
 * authorization code for a token response in OAuth2 authorization code flow.
 * <br />
 * <p> Details about a HTTP call to Facebook auth provider made during exchange:</p>
 * <ul>
 *     <li><b>URL</b>: https://graph.facebook.com/v12.0/oauth/access_token</li>
 *     <li><b>HTTP method</b>: GET</li>
 *     <li><b>Query parameters:</b>
 *     <ul>
 *         <li><code>client_id</code></li>
 *         <li><code>client_secret</code></li>
 *         <li><code>redirect_uri</code></li>
 *         <li><code>code</code></li>
 *     </ul>
 *     </li>
 * </ul>
 *
 * <p>
 * The exchange implementation is created based on information posted on official
 * <a href="https://developers.facebook.com/docs/facebook-login/manually-build-a-login-flow/">documentation site</a>.
 * </p>
 */
public final class FacebookAuthorizationCodeExchange extends AbstractAuthorizationCodeExchange {

    /**
     * Creates an instance of a builder used to create a {@link FacebookAuthorizationCodeExchange} instance with given
     * parameters.
     *
     * @return newly created instance of a {@link Builder}
     */
    public static Builder builder() {
        return new Builder();
    }

    private FacebookAuthorizationCodeExchange(@NotNull OkHttpClient httpClient,
                                              @NotNull FacebookOAuth2Client oAuth2Client,
                                              @NotNull FacebookOAuth2TokenResponseHandler responseHandler) {
        super(httpClient, oAuth2Client, responseHandler);
    }

    /**
     * Exchange a given authorization code for a token response by making proper HTTP call to a Facebook authorization
     * provider based on information posted on official
     * <a href="https://developers.facebook.com/docs/facebook-login/manually-build-a-login-flow/">documentation site</a>.
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

        var url = HttpUrl.parse(this.oAuth2Client.getTokenUrl())
                .newBuilder()
                .addQueryParameter("client_id", this.oAuth2Client.getClientId())
                .addQueryParameter("client_secret", this.oAuth2Client.getClientSecret())
                .addQueryParameter("redirect_uri", this.oAuth2Client.getRedirectUri())
                .addQueryParameter("code", code)
                .build();

        var request = new Request.Builder()
                .url(url)
                .build();

        return this.makeHttpCall(request);
    }

    /**
     * Represents a builder used to configure and create an instance of a {@link FacebookAuthorizationCodeExchange} exchange.
     */
    public static class Builder {

        private OkHttpClient httpClient;
        private FacebookOAuth2Client exchangeClient;
        private FacebookOAuth2TokenResponseHandler responseHandler;

        private Builder() {

        }

        /**
         * Set an HTTP client used in an exchange.
         *
         * @param httpClient instance of HTTP client ({@link OkHttpClient} implementation)
         * @return builder instance for further chain configuration
         */
        public Builder httpClient(OkHttpClient httpClient) {
            this.httpClient = httpClient;
            return this;
        }

        /**
         * Set an exchange client used in an exchange.
         *
         * @param exchangeClient instance of {@link FacebookOAuth2Client} exchange client
         * @return builder instance for further chain configuration
         */
        public Builder exchangeClient(FacebookOAuth2Client exchangeClient) {
            this.exchangeClient = exchangeClient;
            return this;
        }

        /**
         * Set an response handler used in an exchange.
         *
         * @param responseHandler instance of {@link FacebookOAuth2TokenResponseHandler} response handler
         * @return builder instance for further chain configuration
         */
        public Builder responseHandler(FacebookOAuth2TokenResponseHandler responseHandler) {
            this.responseHandler = responseHandler;
            return this;
        }

        /**
         * Constructs an instance of a {@link FacebookAuthorizationCodeExchange} based on provided parameters.
         *
         * <p>Required parameters:</p>
         * <ul>
         *     <li><code>exchangeClient</code></li>
         * </ul>
         *
         * <p>Optional parameters:</p>
         * <ul>
         *     <li><code>httpClient</code> - new instance of {@link OkHttpClient} used if no HTTP client explicitly provided</li>
         *     <li><code>responseHandler</code> - new instance of {@link FacebookOAuth2TokenResponseHandler} used if no response handler explicitly provided</li>
         * </ul>
         *
         * @return new instance of {@link FacebookAuthorizationCodeExchange} based on provided parameters
         */
        public FacebookAuthorizationCodeExchange build() {
            return new FacebookAuthorizationCodeExchange(
                    Optional.ofNullable(this.httpClient).orElseGet(defaultOkHttpClient()),
                    this.exchangeClient,
                    Optional.ofNullable(this.responseHandler).orElseGet(defaultResponseHandler())
            );
        }

        private static Supplier<FacebookOAuth2TokenResponseHandler> defaultResponseHandler() {
            return () -> new FacebookOAuth2TokenResponseHandler(new ObjectMapper());
        }

    }

}
