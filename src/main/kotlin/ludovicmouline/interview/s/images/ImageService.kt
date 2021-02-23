package ludovicmouline.interview.s.images

import ludovicmouline.interview.s.images.sources.ImageSource
import ludovicmouline.interview.s.images.sources.XKCDSource
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
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

data class Image(
    val pictureUrl: String,
    val title: String,
    val description: String,
    val webUrl: String,
    val publishedDate: LocalDate
)

@RestController
class ImageController(
    val assembler: ImageModelAssembler
) {

    val sources = arrayListOf<ImageSource>(XKCDSource())

    @GetMapping("/images")
    fun latestImages(): CollectionModel<EntityModel<Image>> {

        val images = ArrayList<Image>(20)
        sources.forEach { images.addAll(it.pullImages(10)) }
        images.sortedBy { it.publishedDate }

        return this.assembler.toCollectionModel(images)
    }

    @GetMapping("images/{id}")
    fun getImage(@PathVariable id: Long): EntityModel<Image> {
        throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Not Yet implemented.")
    }

}

@Component
class ImageModelAssembler:
    RepresentationModelAssembler<Image, EntityModel<Image>> {
    override fun toModel(entity: Image): EntityModel<Image> =
        EntityModel.of(entity)

    override fun toCollectionModel(entities: MutableIterable<Image>)
    : CollectionModel<EntityModel<Image>> =
        CollectionModel.of(entities.map { toModel(it) })

}