package io.mzlnk.oauth2.exchange.core.authorizationcode;

import io.mzlnk.oauth2.exchange.core.authorizationcode.response.MicrosoftAuthorizationCodeExchangeResponse;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.util.Map;

public class MicrosoftAuthorizationCodeExchange extends AbstractAuthorizationCodeExchange<MicrosoftAuthorizationCodeExchangeResponse> {

    private final MicrosoftTenantType tenant;
    private final String scope;
    private final String codeVerifier;
    private final String clientAssertionType;
    private final String clientAssertion;

    private MicrosoftAuthorizationCodeExchange(OkHttpClient client,
                                               String clientId,
                                               String clientSecret,
                                               String redirectUri,
                                               MicrosoftTenantType tenant,
                                               String scope,
                                               String codeVerifier,
                                               String clientAssertionType,
                                               String clientAssertion) {
        super(client, clientId, clientSecret, redirectUri);

        this.tenant = tenant;
        this.scope = scope;
        this.codeVerifier = codeVerifier;
        this.clientAssertionType = clientAssertionType;
        this.clientAssertion = clientAssertion;
    }

    @Override
    public MicrosoftAuthorizationCodeExchangeResponse exchangeAuthorizationCode(String code) {
        var builder = new FormBody.Builder()
                .add("client_id", this.clientId)
                .add("client_secret", this.clientSecret)
                .add("code", code)
                .add("grant_type", "authorization_code")
                .add("redirect_uri", this.redirectUri);

        addFormFieldIfExists(builder, "scope", this.scope);
        addFormFieldIfExists(builder, "code_verifier", this.codeVerifier);
        addFormFieldIfExists(builder, "client_assertion_type", this.clientAssertionType);
        addFormFieldIfExists(builder, "client_assertion", this.clientAssertion);

        var url = String.format("https://login.microsoftonline.com/%s/oauth2/v2.0/token", this.tenant);

        var request = new Request.Builder()
                .url(url)
                .post(builder.build())
                .build();

        return this.makeHttpCall(request);
    }

    @Override
    protected MicrosoftAuthorizationCodeExchangeResponse convertMapToResponse(Map<String, Object> values) {
        return MicrosoftAuthorizationCodeExchangeResponse.from(values);
    }

    private void addFormFieldIfExists(FormBody.Builder builder, String fieldName, String value) {
        if(value != null) {
            builder.add(fieldName, value);
        }
    }

    public static class Builder {

        private OkHttpClient client = new OkHttpClient();
        private String clientId;
        private String clientSecret;
        private String redirectUri;
        private MicrosoftTenantType tenant;
        private String scope;
        private String codeVerifier;
        private String clientAssertionType;
        private String clientAssertion;

        public Builder client(OkHttpClient client) {
            this.client = client;
            return this;
        }

        public Builder clientId(String clientId) {
            this.clientId = clientId;
            return this;
        }

        public Builder clientSecret(String clientSecret) {
            this.clientSecret = clientSecret;
            return this;
        }

        public Builder redirectUri(String redirectUri) {
            this.redirectUri = redirectUri;
            return this;
        }

        public Builder tenant(MicrosoftTenantType tenant) {
            this.tenant = tenant;
            return this;
        }

        public Builder scope(String scope) {
            this.scope = scope;
            return this;
        }

        public Builder codeVerifier(String codeVerifier) {
            this.codeVerifier = codeVerifier;
            return this;
        }

        public Builder clientAssertionType(String clientAssertionType) {
            this.clientAssertionType = clientAssertionType;
            return this;
        }

        public Builder clientAssertion(String clientAssertion) {
            this.clientAssertion = clientAssertion;
            return this;
        }

        public MicrosoftAuthorizationCodeExchange build() {
            return new MicrosoftAuthorizationCodeExchange(
                    this.client,
                    this.clientId,
                    this.clientSecret,
                    this.redirectUri,
                    this.tenant,
                    this.scope,
                    this.codeVerifier,
                    this.clientAssertionType,
                    this.clientAssertion
            );
        }
    }

}
