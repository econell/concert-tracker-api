package com.evanconell.concerttrackerapi

import com.evanconell.concerttrackerapi.model.data.Artist
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
}