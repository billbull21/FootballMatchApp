package org.billbull.dev.submission2.api

import java.net.URL

class BaseApiRepository {
    fun doRequest(url: String): String {
        return URL(url).readText()
    }
}