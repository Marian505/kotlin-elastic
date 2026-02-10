package org.example.kotlinelastic.domain.entity

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType

@Document(indexName = "movies")
@JsonNaming(PropertyNamingStrategies.UpperCamelCaseStrategy::class)
data class MovieEntity(

    @JsonProperty("Title")
    @Field(name = "Title", type = FieldType.Text)
    val title: String,

    @JsonProperty("Year")
    @Field(name = "Year", type = FieldType.Integer)
    val year: Int,

    @JsonProperty("Rated")
    @Field(name = "Rated", type = FieldType.Keyword)
    val rated: String,

    @JsonProperty("Released")
    @Field(name = "Released", type = FieldType.Date, format = [], pattern = ["dd MMM yyyy"])
    val released: String,

    @JsonProperty("Runtime")
    @Field(name = "Runtime", type = FieldType.Keyword)
    val runtime: String,

    @JsonProperty("Genre")
    @Field(name = "Genre", type = FieldType.Text)
    val genre: String,

    @JsonProperty("Director")
    @Field(name = "Director", type = FieldType.Text)
    val director: String,

    @JsonProperty("Writer")
    @Field(name = "Writer", type = FieldType.Text)
    val writer: String,

    @JsonProperty("Actors")
    @Field(name = "Actors", type = FieldType.Text)
    val actors: String,

    @JsonProperty("Language")
    @Field(name = "Language", type = FieldType.Text)
    val language: String,

    @JsonProperty("Country")
    @Field(name = "Country", type = FieldType.Text)
    val country: String,

    @JsonProperty("Awards")
    @Field(name = "Awards", type = FieldType.Text)
    val awards: String,

    @JsonProperty("imdbRating")
    @Field(name = "imdbRating", type = FieldType.Float)
    val imdbRating: Float,

    @Id
    @JsonProperty("imdbID")
    @Field(name = "imdbID", type = FieldType.Keyword)
    val imdbId: String,

    @JsonProperty("Type")
    @Field(name = "Type", type = FieldType.Keyword)
    val type: String
)

