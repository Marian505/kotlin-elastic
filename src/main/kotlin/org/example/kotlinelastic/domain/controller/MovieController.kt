package org.example.kotlinelastic.domain.controller

import com.example.kotlinelastic.model.MovieDto
import kotlinx.coroutines.flow.Flow
import org.example.kotlinelastic.domain.dto.ProfileDto
import org.example.kotlinelastic.domain.mapper.MovieMapper.toDto
import org.example.kotlinelastic.domain.mapper.MovieMapper.toEntity
import org.example.kotlinelastic.domain.service.MovieService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/movies")
class MovieController {

    @Autowired
    private lateinit var movieService: MovieService

    @PostMapping("/create-index")
    suspend fun createIndex(): Boolean = movieService.createIndex()

    @PostMapping
    suspend fun storeMovie(@RequestBody movieDto: MovieDto) = movieService.saveMovie(movieDto.toEntity())

    @GetMapping
    suspend fun getAll(): Flow<MovieDto> =
        movieService.getAll().toDto()

    @GetMapping("/{id}")
    suspend fun getById(@PathVariable id: String): MovieDto =
        movieService.getById(id).toDto()

    @GetMapping("/title")
    suspend fun findByTitle(@RequestParam title: String): Flow<MovieDto> =
        movieService.findByTitle(title).toDto()

    @GetMapping("/search-title-year-genre")
    suspend fun advancedSearch(
        @RequestParam title: String, @RequestParam year: Int, @RequestParam genre: String
    ): List<MovieDto> = movieService.advancedSearch(title, year, genre)

    @GetMapping("/search-between-years")
    suspend fun searchBetweenYears(@RequestParam startYear: Int, @RequestParam endYear: Int): List<MovieDto> =
        movieService.searchBetweenYears(startYear, endYear)

    @GetMapping("/search-director-or-actor")
    suspend fun searchDirectorOrActor(@RequestParam director: String, @RequestParam actor: String): List<MovieDto> =
        movieService.searchDirectorOrActor(director, actor)

    @GetMapping("/search-genre-director-actor-year")
    suspend fun advanceSearch(
        @RequestParam genre: String, @RequestParam director: String, @RequestParam actor: String,
        @RequestParam startYear: Int, @RequestParam endYear: Int
    ): Pair<List<MovieDto>, Long> =
        movieService.advancedSearch(genre, director, actor, startYear, endYear)

//    TODO: des not return all, check it
    @GetMapping("/all-with-profile")
    suspend fun getAllWithProfile(): Pair<List<MovieDto>, ProfileDto> =
        movieService.getAllWithProfile()

    @DeleteMapping("/{id}")
    suspend fun deleteById(@PathVariable id: String) =
        movieService.deleteById(id)
}