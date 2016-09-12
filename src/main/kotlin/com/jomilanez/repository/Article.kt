package com.jomilanez.repository

import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType

@Document(indexName = "#{indexNameProvider.indexName}", type = "article")
class Article (@Id
               val id: String,
               @Field(type = FieldType.String)
               val title: String,
               @Field(type = FieldType.String)
               val description: String) {
}
