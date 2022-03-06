package io.mzlnk.oauth2.exchange.core.utils;

import okhttp3.OkHttpClient;

import java.util.function.Supplier;

/**
 * Utility class for HTTP related functionalities related to {@link OkHttpClient}.
 */
public class OkHttpUtils {

    /**
     * Constructs new {@link OkHttpClient} instance.
     *
     * @return newly created {@link OkHttpClient} instance
     */
    public static Supplier<OkHttpClient> defaultOkHttpClient() {
        return OkHttpClient::new;
    }

}
