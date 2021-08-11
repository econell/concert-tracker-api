package com.evanconell.concerttrackerapi.controller

import com.evanconell.concerttrackerapi.model.command.CreateArtistCommand
import com.evanconell.concerttrackerapi.model.data.Artist
import com.evanconell.concerttrackerapi.model.exception.NotFoundException
import com.evanconell.concerttrackerapi.model.exception.ValidationException
import com.evanconell.concerttrackerapi.service.ArtistService
import com.evanconell.concerttrackerapi.service.CreateArtistResult
import com.evanconell.concerttrackerapi.service.DeleteArtistResult
import com.evanconell.concerttrackerapi.service.GetArtistByIdResult
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(originPatterns = ["*"])
@RequestMapping("/artist")
class ArtistController(private val artistService: ArtistService) {

    @GetMapping
    fun getAllArtists(): List<Artist> {
        return artistService.getAllArtists()
    }

    @GetMapping("/{id}")
    fun getArtistById(@PathVariable id: String): Artist {
        return artistService.getArtistById(id).let {
            when (it) {
                is GetArtistByIdResult.Success -> it.artist
                is GetArtistByIdResult.NotFound -> throw NotFoundException(it.message)
            }
        }
    }

    @PostMapping
    fun createArtist(@RequestBody createCommand: CreateArtistCommand): Artist {
        return artistService.createArtist(createCommand).let {
            when (it) {
                is CreateArtistResult.Success -> it.artist
                is CreateArtistResult.ValidationFailure -> throw ValidationException(it.errors)
            }
        }
    }


    @DeleteMapping("/{id}")
    fun deleteArtistById(@PathVariable id: String): ResponseEntity<Void> {
        return artistService.deleteArtistById(id).let {
            when (it) {
                is DeleteArtistResult.Success -> ResponseEntity<Void>(HttpStatus.NO_CONTENT)
                is DeleteArtistResult.NotFound -> throw NotFoundException(it.message)
            }
        }
    }
}
