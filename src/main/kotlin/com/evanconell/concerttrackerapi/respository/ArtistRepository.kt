package com.evanconell.concerttrackerapi.respository

import com.evanconell.concerttrackerapi.model.data.Artist
import org.springframework.data.repository.CrudRepository

interface ArtistRepository : CrudRepository<Artist, String> {
}