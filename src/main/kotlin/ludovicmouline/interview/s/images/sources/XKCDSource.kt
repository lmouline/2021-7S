package ludovicmouline.interview.s.images.sources

import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import ludovicmouline.interview.s.images.Image
import org.springframework.boot.web.client.RestTemplateBuilder
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

class XKCDSource: ImageSource {

    companion object {
        const val API_URL_CURRENT = "https://xkcd.com/info.0.json"
        val restTemplate = RestTemplateBuilder().build()
    }

    override fun pullImages(nb: Int): List<Image> {
        val current: XKCDImage? = restTemplate.getForObject(
            API_URL_CURRENT,
            XKCDImage::class.java
        )

        val images = ArrayList<Image>(nb)
        if(current != null) {
            for (i in 0..nb) {
                val xkcdImg = restTemplate.getForObject(
                    """https://xkcd.com/${current.num - i}/info.0.json""",
                    XKCDImage::class.java
                )

                if(xkcdImg != null) {
                    images.add(
                        Image(
                            pictureUrl = xkcdImg.img,
                            title = xkcdImg.title,
                            description = xkcdImg.alt,
                            webUrl = xkcdImg.img,
                            publishedDate = LocalDate.of(
                                xkcdImg.year.toInt(),
                                xkcdImg.month.toInt(),
                                xkcdImg.day.toInt()
                            )
                        ))
                }



            }
        }


        return images
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class XKCDImage(
    val month: String,
    val year: String,
    val day: String,
    val num: Int,
    val img: String,
    val title: String,
    val alt: String
)
