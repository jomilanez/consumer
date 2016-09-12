package com.jomilanez.repository

import org.elasticsearch.client.Client
import org.elasticsearch.common.io.FileSystemUtils
import org.elasticsearch.common.settings.Settings
import org.elasticsearch.node.Node
import java.io.IOException
import java.nio.file.Paths

import org.elasticsearch.node.NodeBuilder.nodeBuilder

class EmbeddedElasticsearchServer () {

    private val ELASTIC_SEARH_FOLDER = "target/elasticsearch"
    private val node: Node

    init {
        val elasticsearchSettings = Settings.settingsBuilder()
                .put("http.enabled", "false")
                .put("path.data", ELASTIC_SEARH_FOLDER + "/data")
                .put("path.home", ELASTIC_SEARH_FOLDER)

        node = nodeBuilder()
                .local(true)
                .settings(elasticsearchSettings.build())
                .node()
    }

    fun getClient(): Client {
        return node.client()
    }

    fun shutdown() {
        node.close()
        deleteDataDirectory()
    }

    fun deleteDataDirectory () {
        try {
            FileSystemUtils.deleteSubDirectories(Paths.get(ELASTIC_SEARH_FOLDER))
        }
        catch (e: IOException) {
            throw RuntimeException("Could not delete data directory of embedded elasticsearch server", e)
        }
    }
}
