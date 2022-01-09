package io.mzlnk.oauth2.exchange.core.utils.http

import okhttp3.Protocol

interface HttpResponse {

    String getResponseBody()

    String getMediaType()

    int getResponseCode()

    String getMessage()

    Protocol getProtocol()

}