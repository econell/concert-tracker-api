package com.evanconell.concerttrackerapi.model.command

import com.evanconell.concerttrackerapi.model.validation.FieldError
import com.evanconell.concerttrackerapi.model.validation.ValidationError
import com.evanconell.concerttrackerapi.model.validation.ValidationResult

class CreateArtistCommand(
    val name: String
) {
    fun validate(): ValidationResult {
        val errors = mutableListOf<ValidationError>()
        this.name
            .takeIf { it.isBlank() }
            ?.let {
                errors.add(
                    ValidationError(
                        field = this::name.name,
                        value = it,
                        message = FieldError.BLANK_OR_EMPTY.message
                    )
                )
            }

        return ValidationResult(errors)
    }
}