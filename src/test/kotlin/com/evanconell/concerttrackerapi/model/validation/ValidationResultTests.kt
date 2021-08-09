package com.evanconell.concerttrackerapi.model.validation

import com.evanconell.concerttrackerapi.ConcertTrackerTestBase
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import strikt.api.expectThat
import strikt.assertions.isFalse
import strikt.assertions.isTrue


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ValidationResultTests : ConcertTrackerTestBase() {

    @Test
    fun isValid_returnsTrue_whenErrorsIsEmpty() {
        // Arrange
        val validationResult = ValidationResult(emptyList())

        // Act
        val actual = validationResult.isValid()

        // Assert
        expectThat(actual).isTrue()
    }

    @Test
    fun isValid_returnsFalse_whenErrorsIsNotEmpty() {
        // Arrange
        val validationResult = ValidationResult(listOf(faker.ctValidationError()))

        // Act
        val actual = validationResult.isValid()

        // Assert
        expectThat(actual).isFalse()
    }
}