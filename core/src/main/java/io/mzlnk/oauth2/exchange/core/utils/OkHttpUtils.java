package io.mzlnk.oauth2.exchange.core.utils;

import okhttp3.ResponseBody;

import java.io.IOException;

public class OkHttpUtils {

    public static String convertResponseBodyToString(ResponseBody responseBody) {
        try {
            return responseBody.string();
        } catch(IOException e) {
            throw new IllegalStateException(e);
        }
    }

}
