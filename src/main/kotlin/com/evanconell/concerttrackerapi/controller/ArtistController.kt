package com.evanconell.concerttrackerapi.controller

import com.evanconell.concerttrackerapi.model.data.Artist
import com.evanconell.concerttrackerapi.model.exception.NotFoundException
import com.evanconell.concerttrackerapi.service.ArtistService
import com.evanconell.concerttrackerapi.service.GetArtistByIdResult
import org.springframework.web.bind.annotation.*

@RestController
class ArtistController(private val artistService: ArtistService) {

    @GetMapping("/artist")
    fun getAllArtists(): List<Artist> {
        return artistService.getAllArtists()
    }

    @GetMapping("/artist/{id}")
    fun getArtistById(@PathVariable id: String): Artist {
        return artistService.getArtistById(id).let {
            when(it) {
                is GetArtistByIdResult.Success -> it.artist
                is GetArtistByIdResult.NotFound -> throw NotFoundException(it.message)
            }
        }
    }
}
