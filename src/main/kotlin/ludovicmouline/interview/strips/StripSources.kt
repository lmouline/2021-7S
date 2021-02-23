package ludovicmouline.interview.strips

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.rometools.rome.feed.synd.SyndEntry
import com.rometools.rome.feed.synd.SyndFeed
import com.rometools.rome.io.SyndFeedInput
import com.rometools.rome.io.XmlReader
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.web.client.RestTemplate
import java.net.URL
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

/**
 * Interface of all the classes that should be responsible of pulling
 * images from other REST API.
 */
interface StripSource {
    /**
     * Tries to retrieve the given number of strips from the source.
     * The actual number of retrieved element is limited by the source capacity.
     *
     * For example, if a source can provide only 5 elements and the the function
     * is called with 10 as parameters, the result will contain only 5 elements.
     *
     */
    fun pullImages(nb: Int) : List<Strip>
}

/**
 * Object representation of the JSON received by the XKCD API
 */
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

fun XKCDImage.toStrip(): Strip =
    Strip(pictureUrl = this.img, title = this.title, description = this.alt,
    webUrl = """${XKCDSource.API_ROUTE}/${this.num}""",
    publishedDate = LocalDate.of(
        this.year.toInt(),
        this.month.toInt(),
        this.day.toInt()
    ))

/**
 * This class retrieves strips form XKCD.
 */
class XKCDSource: StripSource {

    companion object {
        const val API_ROUTE = "https://xkcd.com"
        const val JSON_FILE = "/info.0.json"
        val restTemplate: RestTemplate = RestTemplateBuilder().build()
    }

    override fun pullImages(nb: Int): List<Strip> {
        val images = ArrayList<Strip>(nb)

        restTemplate.getForObject(
            """$API_ROUTE$JSON_FILE""",
            XKCDImage::class.java
        )?.let { current: XKCDImage ->
            for (i in 0 until nb) {
                restTemplate.getForObject(
                    """$API_ROUTE/${current.num - i}$JSON_FILE""",
                    XKCDImage::class.java
                )?.let { images.add(it.toStrip()) }
            }
        }

        return images
    }
}


fun SyndEntry.toStrip(): Strip {
    val urlStr: MatchResult? = PDLSource.regex.find(this.contents.first().value)
    val picUrl: String = urlStr?.value ?: this.uri

    return Strip(
        pictureUrl = picUrl,
        title = this.title,
        webUrl = this.uri,
        publishedDate = Instant.ofEpochMilli(this.publishedDate.time)
            .atZone(ZoneId.of("CET"))
            .toLocalDate()
    )
}


/**
 * This class retrieves strips form PDL.
 */
class PDLSource: StripSource {

    companion object {
        const val API_URL_CURRENT =
            "http://feeds.feedburner.com/PoorlyDrawnLines"
        private const val IMG_URL_PATTERN =
            "http[s]?://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]\\.png"
        val regex = Regex(IMG_URL_PATTERN)
    }

    override fun pullImages(nb: Int): List<Strip> {
        val feedSource = URL(API_URL_CURRENT)
        val input = SyndFeedInput()
        val feed: SyndFeed = input.build(XmlReader(feedSource))
        return feed.entries.map { it.toStrip() }
    }
}