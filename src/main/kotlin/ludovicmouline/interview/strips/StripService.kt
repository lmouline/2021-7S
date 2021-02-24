package ludovicmouline.interview.strips

import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.server.RepresentationModelAssembler
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn

/**
 * Representation of a drawing strip for the final JSON
 */
data class Strip(
    val title: String,

    /**
     * Description of the strip. We put an empty string if no description
     * is provided by the source.
     */
    val description: String = "",

    /**
     * URL of the strip
     */
    val webUrl: String,


    val publishedDate: LocalDate,

    /**
     * URL of the picture file. If there is none, we put the url of the
     * strip.
     */
    val pictureUrl: String = webUrl
)

@RestController
class StripController(val assembler: StripModelAssembler) {

    private val sources = arrayListOf(XKCDSource(), PDLSource())

    companion object {
        const val NB_STRIPS_PER_SOURCE = 10
    }

    @GetMapping("/strips")
    fun latestStrips(): CollectionModel<EntityModel<Strip>> {
        val strips: MutableList<Strip> = sources.flatMap { it.pullImages(
            NB_STRIPS_PER_SOURCE
        ) }
            .sortedByDescending(Strip::publishedDate)
            .toMutableList()

        return this.assembler.toCollectionModel(strips)
    }

}

@Component
class StripModelAssembler:
    RepresentationModelAssembler<Strip, EntityModel<Strip>> {
    override fun toModel(entity: Strip): EntityModel<Strip> =
        EntityModel.of(entity)

    override fun toCollectionModel(entities: MutableIterable<Strip>)
    : CollectionModel<EntityModel<Strip>> =
        CollectionModel.of(
            entities.map { toModel(it) },
            linkTo(methodOn(StripController::class.java).latestStrips()).withSelfRel()
        )

}