package com.evanconell.concerttrackerapi.service

import com.evanconell.concerttrackerapi.ConcertTrackerTestBase
import com.evanconell.concerttrackerapi.respository.ArtistRepository
import io.mockk.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import strikt.api.expectThat
import strikt.assertions.containsExactly

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ArtistServiceTests: ConcertTrackerTestBase() {
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
}