package io.mzlnk.oauth2.exchange.core.authorizationcode.client;

import org.jetbrains.annotations.NotNull;

/**
 * Represents the client in OAuth2 flow. In other words it is the application requesting user's account/identity.
 * The client holds information about client ID and associated client secret, redirect URI which will be used
 * during authorization code flow after token exchange. Additionally, the client also holds the information
 * about base URL used to make request to obtain access token from incoming authorization code.
 */
public interface AuthorizationCodeExchangeClient {

    /**
     * Returns the client ID. More details can be found on
     * <a href="https://datatracker.ietf.org/doc/html/rfc6749#section-2.2">Section 2.2</a> of RFC6749 document.
     *
     * @return non-null string representation of the client ID
     */
    @NotNull
    String getClientId();

    /**
     * Returns the client secret associated with client ID. More details can be found on
     * <a href="https://datatracker.ietf.org/doc/html/rfc6749#section-2.3.1">Section 2.3.1</a> of RFC 6749 document.
     *
     * @return non-null string representation of the client secret
     */
    @NotNull
    String getClientSecret();

    /**
     * Returns the redirection URI used during authorization code flow. More details can be found on
     * <a href="https://datatracker.ietf.org/doc/html/rfc6749#section-4.2.1">Section 4.2.1</a> of RFC 6749 document.
     *
     * @return non-null string representation of the redirection URI
     */
    @NotNull
    String getRedirectUri();

    /**
     * Returns the client base URL which the request for exchange authorization code for a token is sent to.
     *
     * @return non-null string representation of the client base URL
     */
    @NotNull
    String getClientBaseUrl();

}
