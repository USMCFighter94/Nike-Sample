package dev.brevitz.nike.library.data.api

import dev.brevitz.nike.core.domain.attemptTransform

data class PositionResponse(val code: String?, val name: String?, val type: String?, val abbreviation: String?) {
    fun toDomain() = attemptTransform {
        require(!name.isNullOrBlank()) { "Must provide position name" }
        name!!
    }
}
