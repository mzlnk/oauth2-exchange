package io.mzlnk.oauth2.exchange.core.authorizationcode;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.mzlnk.oauth2.exchange.core.authorizationcode.client.GitHubAuthorizationCodeExchangeClient;
import io.mzlnk.oauth2.exchange.core.authorizationcode.response.GitHubAuthorizationCodeExchangeResponseHandler;
import io.mzlnk.oauth2.exchange.core.authorizationcode.response.dto.GitHubAuthorizationCodeExchangeResponse;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import static io.mzlnk.oauth2.exchange.core.utils.OkHttpUtils.defaultOkHttpClient;

/**
 * Represents a ready-for-use exchange implementation for a GitHub authorization provider responsible for exchanging
 * authorization code for a token response in OAuth2 authorization code flow.
 * <br />
 * <p> Details about a HTTP call to GitHub auth provider made during exchange:</p>
 * <ul>
 *     <li><b>URL</b>: https://github.com/login/oauth/access_token</li>
 *     <li><b>HTTP method</b>: POST</li>
 *     <li><b>Content-Type</b>: application/x-www-form-urlencoded</li>
 *     <li><b>Form fields:</b>
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
 * <a href="https://docs.github.com/en/developers/apps/building-oauth-apps/authorizing-oauth-apps">documentation site</a>.
 * </p>
 */
public class GitHubAuthorizationCodeExchange extends AbstractAuthorizationCodeExchange<GitHubAuthorizationCodeExchangeResponse> {

    /**
     * Creates an instance of a builder used to create a {@link GitHubAuthorizationCodeExchange} instance with given
     * parameters.
     *
     * @return newly created instance of a {@link GitHubAuthorizationCodeExchange.Builder}
     */
    public static Builder builder() {
        return new Builder();
    }

    private GitHubAuthorizationCodeExchange(@NotNull OkHttpClient httpClient,
                                            @NotNull GitHubAuthorizationCodeExchangeClient exchangeClient,
                                            @NotNull GitHubAuthorizationCodeExchangeResponseHandler responseHandler) {
        super(httpClient, exchangeClient, responseHandler);
    }

    /**
     * Exchange a given authorization code for a token response by making proper HTTP call to a GitHub authorization
     * provider based on information posted on official
     * <a href="https://docs.github.com/en/developers/apps/building-oauth-apps/authorizing-oauth-apps">documentation site</a>.
     *
     * @param code authorization code obtained from incoming HTTP response
     * @return token response which consists of a response data in {@link Map} form
     * @throws NullPointerException if authorization code is null
     * @throws IllegalArgumentException if authorization code is empty
     * @throws IllegalStateException if HTTP call made during exchange failed or cannot handle returned response properly
     */
    @Override
    public GitHubAuthorizationCodeExchangeResponse exchangeAuthorizationCode(@NotNull String code) {
        verifyAuthorizationCode(code);

        var requestBody = new FormBody.Builder()
                .add("client_id", this.exchangeClient.getClientId())
                .add("client_secret", this.exchangeClient.getClientSecret())
                .add("code", code)
                .add("redirect_uri", this.exchangeClient.getRedirectUri())
                .build();

        var request = new Request.Builder()
                .url("%s/login/oauth/access_token".formatted(this.exchangeClient.getClientBaseUrl()))
                .post(requestBody)
                .addHeader("Accept", "application/json")
                .build();

        return this.makeHttpCall(request);
    }

    /**
     * Represents a builder used to configure and create an instance of a {@link GitHubAuthorizationCodeExchange} exchange.
     */
    public static class Builder {

        private OkHttpClient httpClient;
        private GitHubAuthorizationCodeExchangeClient exchangeClient;
        private GitHubAuthorizationCodeExchangeResponseHandler responseHandler;

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
         * @param exchangeClient instance of {@link GitHubAuthorizationCodeExchangeClient} exchange client
         * @return builder instance for further chain configuration
         */
        public Builder exchangeClient(GitHubAuthorizationCodeExchangeClient exchangeClient) {
            this.exchangeClient = exchangeClient;
            return this;
        }

        /**
         * Set an response handler used in an exchange.
         *
         * @param responseHandler instance of {@link GitHubAuthorizationCodeExchangeResponseHandler} response handler
         * @return builder instance for further chain configuration
         */
        public Builder responseHandler(GitHubAuthorizationCodeExchangeResponseHandler responseHandler) {
            this.responseHandler = responseHandler;
            return this;
        }

        /**
         * Constructs an instance of a {@link GitHubAuthorizationCodeExchange} based on provided parameters.
         *
         * <p>Required parameters:</p>
         * <ul>
         *     <li><code>exchangeClient</code></li>
         * </ul>
         *
         * <p>Optional parameters:</p>
         * <ul>
         *     <li><code>httpClient</code> - new instance of {@link OkHttpClient} used if no HTTP client explicitly provided</li>
         *     <li><code>responseHandler</code> - new instance of {@link GitHubAuthorizationCodeExchangeResponseHandler} used if no response handler explicitly provided</li>
         * </ul>
         *
         * @return new instance of {@link FacebookAuthorizationCodeExchange} based on provided parameters
         */
        public GitHubAuthorizationCodeExchange build() {
            return new GitHubAuthorizationCodeExchange(
                    Optional.ofNullable(this.httpClient).orElseGet(defaultOkHttpClient()),
                    this.exchangeClient,
                    Optional.ofNullable(this.responseHandler).orElseGet(defaultResponseHandler())
            );
        }

        private static Supplier<GitHubAuthorizationCodeExchangeResponseHandler> defaultResponseHandler() {
            return () -> new GitHubAuthorizationCodeExchangeResponseHandler(new ObjectMapper());
        }

    }

}
