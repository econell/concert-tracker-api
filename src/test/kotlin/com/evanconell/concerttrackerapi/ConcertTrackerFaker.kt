package com.evanconell.concerttrackerapi

import com.evanconell.concerttrackerapi.model.command.CreateArtistCommand
import com.evanconell.concerttrackerapi.model.data.Artist
import com.evanconell.concerttrackerapi.model.validation.ValidationError
import com.github.javafaker.Faker
import org.bson.types.ObjectId

object ConcertTrackerFaker : Faker() {

    init {
        println("Initializing Faker")
    }

    fun id(): String = ObjectId.get().toHexString()

    fun ctArtist(): Artist = Artist(
        id = id(),
        name = artist().name()
    )

    fun ctCreateArtistCommand(): CreateArtistCommand = CreateArtistCommand(name = artist().name())

    fun ctValidationError(): ValidationError = ValidationError(
        field = name().firstName(),
        value = name().lastName(),
        message = shakespeare().hamletQuote()
    )
}