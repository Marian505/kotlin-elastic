package org.example.kotlinelastic

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.elasticsearch.repository.config.EnableReactiveElasticsearchRepositories

@SpringBootApplication
@EnableReactiveElasticsearchRepositories
class KotlinElasticApplication

fun main(args: Array<String>) {
    runApplication<KotlinElasticApplication>(*args)
}
