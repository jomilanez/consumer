package com.jomilanez.repository

import org.elasticsearch.client.Client
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import java.io.IOException
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import org.assertj.core.api.Assertions.assertThat

@RunWith(SpringRunner::class)
@SpringBootTest
@ActiveProfiles(value = "integration")
class ArticleRepositoryTest {
    @Autowired
    private val repository: ArticleRepository? = null

    private var embeddedElasticsearchServer: EmbeddedElasticsearchServer? = null

    @Test
    @Throws(IOException::class)
    fun indexSimpleDocument() {
        repository!!.save(Article("id", "title", "text"))

        val indexName = "articles-" + ZonedDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE)
        val fields = getClient().prepareGet(indexName, "article", "id").execute().actionGet()
        assertThat(fields.source["title"]).isEqualTo("title")
        assertThat(fields.source["description"]).isEqualTo("text")
    }

    @Before
    fun startEmbeddedElasticsearchServer() {
        embeddedElasticsearchServer = EmbeddedElasticsearchServer()
    }

    @After
    fun shutdownEmbeddedElasticsearchServer() {
        embeddedElasticsearchServer!!.shutdown()
    }

    private fun getClient(): Client {
        return embeddedElasticsearchServer!!.getClient()
    }
}
