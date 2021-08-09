package com.evanconell.concerttrackerapi.service

import com.evanconell.concerttrackerapi.ConcertTrackerTestBase
import com.evanconell.concerttrackerapi.model.data.Artist
import com.evanconell.concerttrackerapi.respository.ArtistRepository
import io.mockk.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import strikt.api.expect
import strikt.api.expectThat
import strikt.assertions.*
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ArtistServiceTests : ConcertTrackerTestBase() {
    private lateinit var repoMock: ArtistRepository
    private lateinit var service: ArtistService

    @BeforeAll
    fun init() {
        repoMock = mockk()
        service = ArtistServiceImpl(repoMock)
    }

    @BeforeEach
    fun setUp() = clearAllMocks()

    @Test
    fun getAllArtists_returnsList() {
        // Arrange
        val expected = listOf(
            faker.ctArtist(),
            faker.ctArtist()
        )
        every { repoMock.findAll() } returns expected

        // Act
        val actual = service.getAllArtists()

        // Assert
        verify { repoMock.findAll() }
        expectThat(actual)
            .containsExactly(expected)
    }


    @Test
    fun getArtistById_returnsSuccess_whenExists() {
        // Arrange
        val id = faker.id()
        val expected = faker.ctArtist()
        every { repoMock.findById(id) } returns Optional.of(expected)

        // Act
        val actual = service.getArtistById(id)

        // Assert
        verify { repoMock.findById(id) }
        expectThat(actual)
            .isA<GetArtistByIdResult.Success>()
            .get(GetArtistByIdResult.Success::artist)
            .isEqualTo(expected)
    }

    @Test
    fun getArtistById_returnsNotFound_whenDoesNotExist() {
        // Arrange
        val id = faker.id()
        every { repoMock.findById(id) } returns Optional.empty()

        // Act
        val actual = service.getArtistById(id)

        // Assert
        verify { repoMock.findById(id) }
        expectThat(actual)
            .isA<GetArtistByIdResult.NotFound>()
            .get(GetArtistByIdResult.NotFound::message)
            .isEqualTo("Artist not found with id $id")
    }

    @Test
    fun createArtist_returnsSuccess_whenGivenValidCommand() {
        // Arrange
        val createArtistCommand = mockk<CreateArtistCommand>()
        val expectedArtistName = faker.artist().name()
        every { createArtistCommand.validate() } returns ValidationResult(emptyList())
        every { createArtistCommand.name } returns expectedArtistName
        val capturedArtist = slot<Artist>()
        val expected = faker.ctArtist()
        every { repoMock.save(capture(capturedArtist)) } returns expected

        // Act
        val actual = service.createArtist(createArtistCommand)

        // Assert
        verify { createArtistCommand.validate() }
        expectThat(actual)
            .isA<CreateArtistResult.Success>()
            .get(CreateArtistResult.Success::artist)
            .isEqualTo(expected)

        expectThat(capturedArtist.isCaptured).isTrue()

        expectThat(capturedArtist.captured) {
            get { id }.isNotEmpty()
            get { name }.isEqualTo(expectedArtistName)
        }
    }
}