package ludovicmouline.interview.s.images.sources

import ludovicmouline.interview.s.images.Image
import org.springframework.boot.web.client.RestTemplateBuilder
import com.rometools.rome.feed.synd.SyndFeed

import com.rometools.rome.io.SyndFeedInput
import com.rometools.rome.io.XmlReader
import java.net.URL
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*


class PDLSource: ImageSource {

    companion object {
        const val API_URL_CURRENT = "http://feeds.feedburner.com/PoorlyDrawnLines"
        const val IMG_URL_PATTERN = "http[s]?://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]\\.png"
        val regex = Regex(IMG_URL_PATTERN)
    }

    override fun pullImages(nb: Int): List<Image> {
        val feedSource = URL(API_URL_CURRENT)
        val input = SyndFeedInput()
        val feed: SyndFeed = input.build(XmlReader(feedSource))

        return feed.entries.map {
            Image(
                pictureUrl = regex.find(it.contents.first().value)!!.value,
                title = it.title,
                webUrl = it.uri,
                publishedDate = Instant.ofEpochMilli(it.publishedDate.time)
                    .atZone(ZoneId.of("CET"))
                    .toLocalDate(),
                description = ""
            )
        }
    }
}