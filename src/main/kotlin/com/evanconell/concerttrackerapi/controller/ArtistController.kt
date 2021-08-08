package com.evanconell.concerttrackerapi.controller

import com.evanconell.concerttrackerapi.model.Artist
import com.evanconell.concerttrackerapi.service.ArtistService
import org.springframework.web.bind.annotation.*

@RestController
class ArtistController(private val artistService: ArtistService) {

    @GetMapping("/artist")
    fun getAllArtists(): List<Artist> {
        return artistService.getAllArtists()
    }

}