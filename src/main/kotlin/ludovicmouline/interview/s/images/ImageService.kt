package ludovicmouline.interview.s.images

import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.RepresentationModel
import org.springframework.hateoas.server.RepresentationModelAssembler
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import java.util.*

data class Image(
    val pictureUrl: String,
    val title: String,
    val description: String,
    val webUrl: String,
    val publishedDate: Date
)

@RestController
class ImageController {

    @GetMapping("/images")
    fun latestImages(): CollectionModel<EntityModel<Image>> {
        throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Not Yet implemented.")
    }

    @GetMapping("images/{id}")
    fun getImage(@PathVariable id: Long): EntityModel<Image> {
        throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Not Yet implemented.")
    }

}

@Component
class ImageModelAssembler:
    RepresentationModelAssembler<Image, EntityModel<Image>> {
    override fun toModel(entity: Image): EntityModel<Image> {
        TODO("Not yet implemented")
    }

    override fun toCollectionModel(entities: MutableIterable<Image>): CollectionModel<EntityModel<Image>> {
        return super.toCollectionModel(entities)
    }
}