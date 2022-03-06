package io.mzlnk.oauth2.exchange.core.authorizationcode.client;

import com.google.common.base.Preconditions;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a template in form of abstract class which can be used to create custom clients for
 * authorization code exchanges. It implements all of the {@link AuthorizationCodeExchangeClient} methods
 * except the {@link AuthorizationCodeExchangeClient#getClientBaseUrl()} which has to be implemented in
 * custom authorization code exchange implementation.
 */
public abstract class AbstractAuthorizationCodeExchangeClient implements AuthorizationCodeExchangeClient {

    private final String clientId;
    private final String clientSecret;
    private final String redirectUri;

    /**
     * Construct an instance with given client ID, client secret (associated with the client ID) and redirection URI.
     *
     * @param clientId     non-null string representation of the client ID
     * @param clientSecret non-null string representation of the client secret
     * @param redirectUri  non-null string representation of the redirection URI
     * @throws NullPointerException if any of the parameters is null
     */
    protected AbstractAuthorizationCodeExchangeClient(@NotNull String clientId,
                                                      @NotNull String clientSecret,
                                                      @NotNull String redirectUri) {

        Preconditions.checkNotNull(clientId, "Parameter `clientId` cannot be null.");
        Preconditions.checkNotNull(clientSecret, "Parameter `clientSecret` cannot be null.");
        Preconditions.checkNotNull(redirectUri, "Parameter `redirectUri` cannot be null.");

        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
    }

    /**
     * Returns the client ID. More details can be found on
     * <a href="https://datatracker.ietf.org/doc/html/rfc6749#section-2.2">Section 2.2</a> of RFC6749 document.
     *
     * @return non-null string representation of the client ID
     */
    @NotNull
    @Override
    public String getClientId() {
        return this.clientId;
    }

    /**
     * Returns the client secret associated with client ID. More details can be found on
     * <a href="https://datatracker.ietf.org/doc/html/rfc6749#section-2.3.1">Section 2.3.1</a> of RFC 6749 document.
     *
     * @return non-null string representation of the client secret
     */
    @NotNull
    @Override
    public String getClientSecret() {
        return this.clientSecret;
    }

    /**
     * Returns the redirection URI used during authorization code flow. More details can be found on
     * <a href="https://datatracker.ietf.org/doc/html/rfc6749#section-4.2.1">Section 4.2.1</a> of RFC 6749 document.
     *
     * @return non-null string representation of the redirection URI
     */
    @NotNull
    @Override
    public String getRedirectUri() {
        return this.redirectUri;
    }

}
