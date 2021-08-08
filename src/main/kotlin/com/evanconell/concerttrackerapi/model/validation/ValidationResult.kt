package com.evanconell.concerttrackerapi.model.validation

import kotlinx.serialization.Serializable

data class ValidationResult(val errors: List<ValidationError>) {
    fun isValid(): Boolean = errors.isEmpty()
}

@Serializable
data class ValidationError(
    val field: String,
    val value: String,
    val message: String
)