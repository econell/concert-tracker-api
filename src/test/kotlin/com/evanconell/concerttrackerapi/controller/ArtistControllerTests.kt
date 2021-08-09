package com.evanconell.concerttrackerapi.controller

import com.evanconell.concerttrackerapi.ConcertTrackerTestBase
import com.evanconell.concerttrackerapi.model.exception.NotFoundException
import com.evanconell.concerttrackerapi.model.exception.ValidationException
import com.evanconell.concerttrackerapi.service.ArtistService
import com.evanconell.concerttrackerapi.service.CreateArtistResult
import com.evanconell.concerttrackerapi.service.DeleteArtistResult
import com.evanconell.concerttrackerapi.service.GetArtistByIdResult
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import strikt.api.expectCatching
import strikt.api.expectThat
import strikt.assertions.containsExactly
import strikt.assertions.isA
import strikt.assertions.isEqualTo
import strikt.assertions.isFailure

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ArtistControllerTests : ConcertTrackerTestBase() {
    private lateinit var artistServiceMock: ArtistService
    private lateinit var controller: ArtistController

    @BeforeAll
    fun init() {
        artistServiceMock = mockk()
        controller = ArtistController(artistServiceMock)
    }

    @BeforeEach
    fun setUp() = clearAllMocks()

    @Test
    fun getAllArtists_returnsArtistList() {
        // Arrange
        val expected = listOf(faker.ctArtist(), faker.ctArtist())
        every { artistServiceMock.getAllArtists() } returns expected

        // Act
        val actual = controller.getAllArtists()

        // Assert
        verify { artistServiceMock.getAllArtists() }
        expectThat(actual)
            .containsExactly(expected)
    }

    @Test
    fun getArtistById_returnsArtist_whenFound() {
        // Arrange
        val id = faker.id()
        val expected = faker.ctArtist()
        every { artistServiceMock.getArtistById(id) } returns GetArtistByIdResult.Success(expected)

        // Act
        val actual = controller.getArtistById(id)

        // Assert
        verify { artistServiceMock.getArtistById(id) }
        expectThat(actual)
            .isEqualTo(expected)
    }

    @Test
    fun getArtistById_throws_whenNotFound() {
        // Arrange
        val id = faker.id()
        val expectedError = faker.shakespeare().asYouLikeItQuote()
        every { artistServiceMock.getArtistById(id) } returns GetArtistByIdResult.NotFound(expectedError)

        // Act & Assert
        expectCatching { controller.getArtistById(id) }
            .isFailure()
            .isA<NotFoundException>()
            .get(NotFoundException::msg)
            .isEqualTo(expectedError)
    }

    @Test
    fun createArtist_returnsArtist_whenCreated() {
        // Arrange
        val command = faker.ctCreateArtistCommand()
        val expected = faker.ctArtist()
        every { artistServiceMock.createArtist(command) } returns CreateArtistResult.Success(expected)

        // Act
        val actual = controller.createArtist(command)

        // Assert
        verify { artistServiceMock.createArtist(command) }
        expectThat(actual)
            .isEqualTo(expected)
    }

    @Test
    fun createArtist_throws_whenValidationError() {
        // Arrange
        val command = faker.ctCreateArtistCommand()
        val expectedErrors = listOf(faker.ctValidationError(), faker.ctValidationError())
        every { artistServiceMock.createArtist(command) } returns CreateArtistResult.ValidationFailure(expectedErrors)

        // Act & Assert
        expectCatching { controller.createArtist(command) }
            .isFailure()
            .isA<ValidationException>()
            .get(ValidationException::errors)
            .containsExactly(expectedErrors)
    }

    @Test
    fun deleteArtistById_returnsVoid_whenFound() {
        // Arrange
        val id = faker.id()
        every { artistServiceMock.deleteArtistById(id) } returns DeleteArtistResult.Success

        // Act
        val actual = controller.deleteArtistById(id)

        // Assert
        verify { artistServiceMock.deleteArtistById(id) }
        expectThat(actual)
            .isEqualTo(ResponseEntity<Void>(HttpStatus.NO_CONTENT))
    }

    @Test
    fun deleteArtistById_throws_whenNotFound() {
        // Arrange
        val id = faker.id()
        val expectedError = faker.shakespeare().asYouLikeItQuote()
        every { artistServiceMock.deleteArtistById(id) } returns DeleteArtistResult.NotFound(expectedError)

        // Act & Assert
        expectCatching { controller.deleteArtistById(id) }
            .isFailure()
            .isA<NotFoundException>()
            .get(NotFoundException::msg)
            .isEqualTo(expectedError)
    }
}