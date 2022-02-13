package io.mzlnk.oauth2.exchange.core.utils;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;

import java.io.IOException;
import java.util.function.Supplier;

public class OkHttpUtils {

    public static Supplier<OkHttpClient> defaultOkHttpClient() {
        return OkHttpClient::new;
    }

    public static String convertResponseBodyToString(ResponseBody responseBody) {
        try {
            return responseBody.string();
        } catch(IOException e) {
            throw new IllegalStateException(e);
        }
    }

}
