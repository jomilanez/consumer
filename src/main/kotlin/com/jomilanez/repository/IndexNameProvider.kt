package com.jomilanez.repository

import org.springframework.stereotype.Component
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@Component
class IndexNameProvider {
    fun getIndexName(): String {
        return "articles-" + ZonedDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
    }
}
