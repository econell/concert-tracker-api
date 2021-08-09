package com.evanconell.concerttrackerapi.service

import com.evanconell.concerttrackerapi.model.command.CreateArtistCommand
import com.evanconell.concerttrackerapi.model.data.Artist
import com.evanconell.concerttrackerapi.model.validation.ValidationError
import com.evanconell.concerttrackerapi.respository.ArtistRepository
import org.bson.types.ObjectId
import org.springframework.stereotype.Service

interface ArtistService {
    fun getAllArtists(): List<Artist>
    fun getArtistById(id: String): GetArtistByIdResult
    fun createArtist(createCommand: CreateArtistCommand): CreateArtistResult
    fun deleteArtistById(id: String): DeleteArtistResult
}

sealed class GetArtistByIdResult {
    data class Success(val artist: Artist) : GetArtistByIdResult()
    data class NotFound(val message: String) : GetArtistByIdResult()
}

sealed class CreateArtistResult {
    data class Success(val artist: Artist) : CreateArtistResult()
    data class ValidationFailure(val errors: List<ValidationError>) : CreateArtistResult()
}

sealed class DeleteArtistResult {
    object Success : DeleteArtistResult()
    data class NotFound(val message: String) : DeleteArtistResult()
}


@Service("artistService")
class ArtistServiceImpl(
    private val artistRepo: ArtistRepository
) : ArtistService {
    override fun getAllArtists(): List<Artist> = artistRepo.findAll().toList()

    override fun getArtistById(id: String): GetArtistByIdResult {
        return artistRepo
            .findById(id)
            .takeIf { it.isPresent }
            ?.let { GetArtistByIdResult.Success(it.get()) }
            ?: GetArtistByIdResult.NotFound("Artist not found with id $id")
    }

    override fun createArtist(createCommand: CreateArtistCommand): CreateArtistResult {
        return createCommand
            .validate()
            .takeUnless { it.isValid() }
            ?.let { CreateArtistResult.ValidationFailure(it.errors) }
            ?: artistRepo.save(
                Artist(
                    id = ObjectId().toHexString(),
                    name = createCommand.name
                )
            ).let { CreateArtistResult.Success(it) }
    }

    override fun deleteArtistById(id: String): DeleteArtistResult {
        return getArtistById(id)
            .let {
                when (it) {
                    is GetArtistByIdResult.Success -> {
                        artistRepo.deleteById(id)
                        DeleteArtistResult.Success
                    }
                    is GetArtistByIdResult.NotFound -> DeleteArtistResult.NotFound(it.message)
                }
            }
    }
}
