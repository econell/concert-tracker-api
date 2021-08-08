package com.evanconell.concerttrackerapi.service

import com.evanconell.concerttrackerapi.model.data.Artist
import com.evanconell.concerttrackerapi.respository.ArtistRepository
import org.springframework.stereotype.Service

interface ArtistService {
    fun getAllArtists() : List<Artist>
    fun getArtistById(id: String) : GetArtistByIdResult
}

sealed class GetArtistByIdResult
class Success(val artist: Artist) : GetArtistByIdResult()
class NotFound(val message: String) : GetArtistByIdResult()

@Service("artistService")
class ArtistServiceImpl(
        private val artistRepo: ArtistRepository
): ArtistService {
    override fun getAllArtists(): List<Artist> = artistRepo.findAll().toList()

    override fun getArtistById(id: String) : GetArtistByIdResult {
        return artistRepo
                .findById(id)
                .takeIf { it.isPresent }
                ?.let { Success(it.get()) }
                ?: NotFound("Artist not found with id $id")
    }
}
