package io.mzlnk.oauth2.exchange.core.utils.http.rule

import okhttp3.Request

interface HttpRule {

    boolean matches(Request httpRequest)

}