package com.evanconell.concerttrackerapi.model.command

import com.evanconell.concerttrackerapi.ConcertTrackerTestBase
import com.evanconell.concerttrackerapi.model.validation.FieldError
import com.evanconell.concerttrackerapi.model.validation.ValidationError
import com.evanconell.concerttrackerapi.model.validation.ValidationResult
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import strikt.api.expectThat
import strikt.assertions.containsExactly
import strikt.assertions.isA
import strikt.assertions.isEmpty


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CreateArtistCommandTests : ConcertTrackerTestBase() {

    @Test
    fun validate_hasNoErrors_forValidCommand() {
        // Arrange
        val command = faker.ctCreateArtistCommand()

        // Act
        val actual = command.validate()

        // Assert
        expectThat(actual)
            .isA<ValidationResult>()
            .get(ValidationResult::errors).and {
                this.isEmpty()
            }
    }

    @Test
    fun validate_addsError_forBlankName() {
        // Arrange
        val command = CreateArtistCommand(name = "")
        val expectedError = ValidationError(
            field = CreateArtistCommand::name.name,
            value = "",
            message = FieldError.BLANK_OR_EMPTY.message
        )

        // Act
        val actual = command.validate()

        // Assert
        expectThat(actual)
            .isA<ValidationResult>()
            .get(ValidationResult::errors).and {
                this.containsExactly(expectedError)
            }
    }
}