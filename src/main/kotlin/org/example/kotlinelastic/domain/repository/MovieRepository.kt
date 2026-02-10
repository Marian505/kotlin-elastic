package org.example.kotlinelastic.domain.repository

import kotlinx.coroutines.flow.Flow
import org.example.kotlinelastic.domain.entity.MovieEntity
import org.springframework.data.elasticsearch.repository.CoroutineElasticsearchRepository
import org.springframework.stereotype.Repository

@Repository
interface MovieRepository : CoroutineElasticsearchRepository<MovieEntity, String> {
    suspend fun findByTitle(title: String): Flow<MovieEntity>
}