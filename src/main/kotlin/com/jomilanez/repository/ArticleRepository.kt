package com.jomilanez.repository

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository

interface ArticleRepository : ElasticsearchRepository<Article, String> {
}
