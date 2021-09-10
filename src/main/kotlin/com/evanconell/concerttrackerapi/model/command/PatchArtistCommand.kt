package com.evanconell.concerttrackerapi.model.command

import java.util.*

class PatchArtistCommand(
    val id: String,
    val name: Optional<String>
) {
}