package org.example.kotlinelastic.domain.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class ProfileDto(
    val shards: List<ShardDto>
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class ShardDto(
    val id: String,
    val searches: List<SearchDto>
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class SearchDto(
    val query: List<QueryDto>
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class QueryDto(
    val type: String,
    val description: String,
    val timeInNanos: Long,
    val breakdown: Map<String, Long>
)