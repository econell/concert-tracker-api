package com.evanconell.concerttrackerapi.service

import com.evanconell.concerttrackerapi.model.Artist
import com.evanconell.concerttrackerapi.respository.ArtistRepository
import org.springframework.stereotype.Service

interface ArtistService {
    fun getAllArtists() : List<Artist>
}

@Service("artistService")
class ArtistServiceImpl(
  private val artistRepo: ArtistRepository
): ArtistService {
    override fun getAllArtists(): List<Artist> = artistRepo.findAll().toList()
}