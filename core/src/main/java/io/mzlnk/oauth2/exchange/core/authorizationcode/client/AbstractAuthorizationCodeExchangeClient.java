package io.mzlnk.oauth2.exchange.core.authorizationcode.client;

public abstract class AbstractAuthorizationCodeExchangeClient implements AuthorizationCodeExchangeClient {

    private final String clientId;
    private final String clientSecret;
    private final String redirectUri;

    protected AbstractAuthorizationCodeExchangeClient(String clientId,
                                                   String clientSecret,
                                                   String redirectUri) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
    }

    @Override
    public String getClientId() {
        return this.clientId;
    }

    @Override
    public String getClientSecret() {
        return this.clientSecret;
    }

    @Override
    public String getRedirectUri() {
        return this.redirectUri;
    }

}
