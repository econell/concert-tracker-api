package com.evanconell.concerttrackerapi.model.exception

import com.evanconell.concerttrackerapi.model.validation.ValidationError
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
class ValidationException(errors: List<ValidationError>) : RuntimeException(Json.encodeToString(errors))