package io.mzlnk.oauth2.exchange.core;

import okhttp3.Response;

/**
 * Represents an exception which can be thrown during exchange. It consists of information about an error message and
 * an HTTP response associated with an error.
 */
public class ExchangeException extends RuntimeException {

    private final Response response;

    /**
     * Constructs an exception with given error message and HTTP response
     *
     * @param message  string representation of a error message
     * @param response HTTP response associated with the error
     */
    public ExchangeException(String message, Response response) {
        super(message);
        this.response = response;
    }

    /**
     * Get HTTP response associated with the error represented by this exception.
     *
     * @return HTTP response associated with the error
     */
    public Response getResponse() {
        return response;
    }

}
