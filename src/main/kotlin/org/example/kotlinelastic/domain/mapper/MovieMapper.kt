package org.example.kotlinelastic.domain.mapper

import com.example.kotlinelastic.model.MovieDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.example.kotlinelastic.domain.entity.MovieEntity

object MovieMapper {

    fun MovieDto.toEntity() = MovieEntity(
        title = title,
        year = year,
        rated = rated,
        released = released,
        runtime = runtime,
        genre = genre,
        director = director,
        writer = writer,
        actors = actors,
        language = language,
        country = country,
        awards = awards,
        imdbRating = imdbRating,
        imdbId = imdbId,
        type = type
    )

    fun Flow<MovieEntity>.toDto(): Flow<MovieDto> = map { it.toDto() }

    fun MovieEntity.toDto() = MovieDto(
        title = title,
        year = year,
        rated = rated,
        released = released,
        runtime = runtime,
        genre = genre,
        director = director,
        writer = writer,
        actors = actors,
        language = language,
        country = country,
        awards = awards,
        imdbRating = imdbRating,
        imdbId = imdbId,
        type = type
    )
}