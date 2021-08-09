package com.evanconell.concerttrackerapi.model.validation

enum class FieldError(val message: String) {
    BLANK_OR_EMPTY("Field can't be blank or empty.")
}