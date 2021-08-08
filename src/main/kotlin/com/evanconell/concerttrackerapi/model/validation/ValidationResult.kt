package com.evanconell.concerttrackerapi.model.validation

sealed class ValidationResult {
    object Success : ValidationResult()
    data class Failure(val errors: List<ValidationError>) : ValidationResult()
}

data class ValidationError(
    val field: String,
    val value: Any,
    val message: String
)