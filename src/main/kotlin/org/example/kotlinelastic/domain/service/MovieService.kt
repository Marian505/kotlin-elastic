package org.example.kotlinelastic.domain.service

import com.example.kotlinelastic.model.MovieDto
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.elasticsearch.client.Request
import org.elasticsearch.client.Response
import org.elasticsearch.client.RestClient
import org.example.kotlinelastic.domain.dto.ProfileDto
import org.example.kotlinelastic.domain.entity.MovieEntity
import org.example.kotlinelastic.domain.mapper.MovieMapper.toDto
import org.example.kotlinelastic.domain.repository.MovieRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate
import org.springframework.data.elasticsearch.core.ElasticsearchOperations
import org.springframework.data.elasticsearch.core.indexOps
import org.springframework.data.elasticsearch.core.query.Criteria
import org.springframework.data.elasticsearch.core.query.CriteriaQuery
import org.springframework.data.elasticsearch.core.search
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import kotlin.system.measureTimeMillis

@Service
class MovieService {

    @Autowired
    private lateinit var restClient: RestClient

    @Autowired
    private lateinit var elasticsearchOperations: ElasticsearchOperations

    @Autowired
    private lateinit var elasticsearchTemplate: ElasticsearchTemplate

    @Autowired
    private lateinit var movieRepository: MovieRepository

    suspend fun createIndex() = elasticsearchOperations.indexOps<MovieEntity>().create()

    suspend fun saveMovie(movie: MovieEntity) = movieRepository.save(movie)

    suspend fun getAll() = movieRepository.findAll()

    suspend fun getById(id: String) = movieRepository.findById(id)
        ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found")

    suspend fun findByTitle(title: String) = movieRepository.findByTitle(title)

    suspend fun advancedSearch(title: String, year: Int, genre: String): List<MovieDto> {

        val criteria = Criteria.where("title").contains(title)
//            .and("year").isEqualTo(year)
            .and("genre").contains(genre)
        val query = CriteriaQuery(criteria)
        val hints = elasticsearchOperations.search<MovieEntity>(query).toList()
        return hints.map { it.content.toDto() }
    }

    suspend fun searchBetweenYears(startYear: Int, endYear: Int): List<MovieDto> {
        val criteria = Criteria.where("year").between(startYear, endYear)
        val query = CriteriaQuery(criteria)
        val hints = elasticsearchOperations.search<MovieEntity>(query).toList()
        return hints.map { it.content.toDto() }
    }

    suspend fun searchDirectorOrActor(director: String, actor: String): List<MovieDto> {
        val criteria = Criteria.where("director").contains(director).or("actors").contains(actor)
        val query = CriteriaQuery(criteria)
        val hints = elasticsearchOperations.search<MovieEntity>(query).toList()
        return hints.map { it.content.toDto() }
    }

    suspend fun advancedSearch(
        genre: String,
        director: String,
        actor: String,
        startYear: Int,
        endYear: Int
    ): Pair<List<MovieDto>, Long> {
        val movieDtos = mutableListOf<MovieDto>()
        val time = measureTimeMillis {
            val criteria = Criteria.where("genre").contains(genre)
                .and("year").between(startYear, endYear)
                .and("director").contains(director)
                .and("actors").contains(actor)
            val query = CriteriaQuery(criteria)
            val hints = elasticsearchOperations.search<MovieEntity>(query).toList()
            movieDtos.addAll(hints.map { it.content.toDto() })
        }
        return Pair(movieDtos, time)
    }

    suspend fun getAllWithProfile(): Pair<List<MovieDto>, ProfileDto> {
        val rawQuery = """
        {
          "profile": true,
          "query": {
            "match_all": {}
          }
        }
        """
        val request = Request("GET", "/movies/_search")
        request.setJsonEntity(rawQuery)
        val response: Response = restClient.performRequest(request)
        val mapper = jacksonObjectMapper()
        val responseBody = mapper.readTree(response.entity.content)
        val profile = mapper.convertValue(responseBody.get("profile"), ProfileDto::class.java)
        val hits = responseBody.get("hits").get("hits")
        val movies = hits.map {
            mapper.convertValue(it.get("_source"), MovieEntity::class.java)
        }
        return Pair(movies.map { it.toDto() }, profile)
    }

    suspend fun deleteById(id: String) = movieRepository.deleteById(id)
}