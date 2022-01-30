package io.mzlnk.oauth2.exchange.core.authorizationcode.client;

public class KeycloakAuthorizationCodeExchangeClient extends AbstractAuthorizationCodeExchangeClient {

    private final String host;
    private final String realm;

    public KeycloakAuthorizationCodeExchangeClient(String clientId,
                                                   String clientSecret,
                                                   String redirectUri,
                                                   String host,
                                                   String realm) {
        super(clientId, clientSecret, redirectUri);
        this.host = host;
        this.realm = realm;
    }

    @Override
    public String getClientBaseUrl() {
        return "%s/auth/realms/%s".formatted(this.host, this.realm);
    }

}
