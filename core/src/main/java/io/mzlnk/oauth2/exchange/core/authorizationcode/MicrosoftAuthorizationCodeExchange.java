package io.mzlnk.oauth2.exchange.core.authorizationcode;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.mzlnk.oauth2.exchange.core.authorizationcode.client.MicrosoftOAuth2Client;
import io.mzlnk.oauth2.exchange.core.authorizationcode.response.GoogleOAuth2TokenResponseHandler;
import io.mzlnk.oauth2.exchange.core.authorizationcode.response.MicrosoftOAuth2TokenResponseHandler;
import io.mzlnk.oauth2.exchange.core.authorizationcode.response.dto.MicrosoftOAuth2TokenResponse;
import io.mzlnk.oauth2.exchange.core.authorizationcode.response.dto.OAuth2TokenResponse;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import static io.mzlnk.oauth2.exchange.core.utils.OkHttpUtils.defaultOkHttpClient;

/**
 * Represents a ready-for-use exchange implementation for a Microsoft authorization provider responsible for exchanging
 * authorization code for a token response in OAuth2 authorization code flow.
 * <br />
 * <p> Details about a HTTP call to GitHub auth provider made during exchange:</p>
 * <ul>
 *     <li><b>URL</b>: https://login.microsoftonline.com/{client}/oauth2/v2.0/token</li>
 *     <li><b>HTTP method</b>: POST</li>
 *     <li><b>Content-Type</b>: application/x-www-form-urlencoded</li>
 *     <li><b>Form fields:</b>
 *     <ul>
 *         <li>Required:
 *         <ul>
 *             <li><code>client_id</code></li>
 *             <li><code>client_secret</code></li>
 *             <li><code>redirect_uri</code></li>
 *             <li><code>grant_type</code></li>
 *             <li><code>code</code></li>
 *         </ul>
 *         </li>
 *         <li>Optional:
 *         <ul>
 *             <li><code>scope</code></li>
 *             <li><code>code_verifier</code></li>
 *             <li><code>client_assertion_type</code></li>
 *             <li><code>client_assertion</code></li>
 *         </ul>
 *         </li>
 *     </ul>
 *     </li>
 * </ul>
 *
 * <p>
 * The exchange implementation is created based on information posted on official
 * <a href="https://docs.microsoft.com/en-us/azure/active-directory/develop/v2-oauth2-auth-code-flow">documentation site</a>.
 * </p>
 */
public class MicrosoftAuthorizationCodeExchange extends AbstractAuthorizationCodeExchange {

    /**
     * Creates an instance of a builder used to create a {@link MicrosoftAuthorizationCodeExchange} instance with given
     * parameters.
     *
     * @return newly created instance of a {@link MicrosoftAuthorizationCodeExchange.Builder}
     */
    public static Builder builder() {
        return new Builder();
    }

    private final String scope;
    private final String codeVerifier;
    private final String clientAssertionType;
    private final String clientAssertion;

    private MicrosoftAuthorizationCodeExchange(@NotNull OkHttpClient httpClient,
                                               @NotNull MicrosoftOAuth2Client oAuth2Client,
                                               @NotNull MicrosoftOAuth2TokenResponseHandler responseHandler,
                                               @Nullable String scope,
                                               @Nullable String codeVerifier,
                                               @Nullable String clientAssertionType,
                                               @Nullable String clientAssertion) {
        super(httpClient, oAuth2Client, responseHandler);

        this.scope = scope;
        this.codeVerifier = codeVerifier;
        this.clientAssertionType = clientAssertionType;
        this.clientAssertion = clientAssertion;
    }

    /**
     * Exchange a given authorization code for a token response by making proper HTTP call to a Microsoft authorization
     * provider based on information posted on official
     * <a href="https://docs.microsoft.com/en-us/azure/active-directory/develop/v2-oauth2-auth-code-flow">documentation site</a>.
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

        addFormFieldIfExists(builder, "scope", this.scope);
        addFormFieldIfExists(builder, "code_verifier", this.codeVerifier);
        addFormFieldIfExists(builder, "client_assertion_type", this.clientAssertionType);
        addFormFieldIfExists(builder, "client_assertion", this.clientAssertion);

        var request = new Request.Builder()
                .url(this.oAuth2Client.getTokenUrl())
                .post(builder.build())
                .build();

        return this.makeHttpCall(request);
    }

    private void addFormFieldIfExists(FormBody.Builder builder, String fieldName, String value) {
        if (value != null) {
            builder.add(fieldName, value);
        }
    }

    /**
     * Represents a builder used to configure and create an instance of a {@link MicrosoftAuthorizationCodeExchange} exchange.
     */
    public static class Builder {

        private OkHttpClient httpClient;
        private MicrosoftOAuth2Client exchangeClient;
        private MicrosoftOAuth2TokenResponseHandler responseHandler;

        private String scope;
        private String codeVerifier;
        private String clientAssertionType;
        private String clientAssertion;

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
         * @param exchangeClient instance of {@link MicrosoftOAuth2Client} exchange client
         * @return builder instance for further chain configuration
         */
        public Builder exchangeClient(MicrosoftOAuth2Client exchangeClient) {
            this.exchangeClient = exchangeClient;
            return this;
        }

        /**
         * Set an response handler used in an exchange.
         *
         * @param responseHandler instance of {@link GoogleOAuth2TokenResponseHandler} response handler
         * @return builder instance for further chain configuration
         */
        public Builder responseHandler(MicrosoftOAuth2TokenResponseHandler responseHandler) {
            this.responseHandler = responseHandler;
            return this;
        }

        /**
         * Set a scope value sent in a <code>scope</code> form field of the HTTP call made during exchange.
         *
         * @param scope string representation of the <code>scope</code> form field
         * @return builder instance for further chain configuration
         */
        public Builder scope(String scope) {
            this.scope = scope;
            return this;
        }

        /**
         * Set a code verifier value sent in a <code>code_verifier</code> form field of the HTTP call made during exchange.
         *
         * @param codeVerifier string representation of the <code>code_verifier</code> form field
         * @return builder instance for further chain configuration
         */
        public Builder codeVerifier(String codeVerifier) {
            this.codeVerifier = codeVerifier;
            return this;
        }

        /**
         * Set a client assertion type value sent in a <code>client_assertion_type</code> form field of the HTTP call
         * made during exchange.
         *
         * @param clientAssertionType string representation of the <code>client_assertion_type</code> form field
         * @return builder instance for further chain configuration
         */
        public Builder clientAssertionType(String clientAssertionType) {
            this.clientAssertionType = clientAssertionType;
            return this;
        }

        /**
         * Set a client assertion value sent in a <code>client_assertion</code> form field of the HTTP call made during exchange.
         *
         * @param clientAssertion string representation of the <code>client_assertion</code> form field
         * @return builder instance for further chain configuration
         */
        public Builder clientAssertion(String clientAssertion) {
            this.clientAssertion = clientAssertion;
            return this;
        }

        /**
         * Constructs an instance of a {@link MicrosoftAuthorizationCodeExchange} based on provided parameters.
         *
         * <p>Required parameters:</p>
         * <ul>
         *     <li><code>exchangeClient</code></li>
         * </ul>
         *
         * <p>Optional parameters:</p>
         * <ul>
         *     <li><code>httpClient</code> - new instance of {@link OkHttpClient} used if no HTTP client explicitly provided</li>
         *     <li><code>responseHandler</code> - new instance of {@link MicrosoftOAuth2TokenResponseHandler} used if no response handler explicitly provided</li>
         *     <li><code>scope</code></li>
         *     <li><code>codeVerifier</code></li>
         *     <li><code>clientAssertionType</code></li>
         *     <li><code>clientAssertion</code></li>
         * </ul>
         *
         * @return new instance of {@link MicrosoftAuthorizationCodeExchange} based on provided parameters
         */
        public MicrosoftAuthorizationCodeExchange build() {
            return new MicrosoftAuthorizationCodeExchange(
                    Optional.ofNullable(this.httpClient).orElseGet(defaultOkHttpClient()),
                    this.exchangeClient,
                    Optional.ofNullable(this.responseHandler).orElseGet(defaultResponseHandler()),
                    this.scope,
                    this.codeVerifier,
                    this.clientAssertionType,
                    this.clientAssertion
            );
        }

        private static Supplier<MicrosoftOAuth2TokenResponseHandler> defaultResponseHandler() {
            return () -> new MicrosoftOAuth2TokenResponseHandler(new MicrosoftOAuth2TokenResponse.Factory(), new ObjectMapper());
        }

    }

}
