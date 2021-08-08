package com.evanconell.concerttrackerapi.model

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.TypeAlias

@TypeAlias("artist")
data class Artist(
        @Id var id: String,
        var name: String) {
}