package ludovicmouline.interview.s.images

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.error.ErrorAttributeOptions
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes
import org.springframework.stereotype.Component
import org.springframework.web.context.request.WebRequest

@SpringBootApplication
class ImagesApplication



@Component
class ErrorMessage: DefaultErrorAttributes() {
	companion object {
		const val TRACE_KEY = "trace"
	}

	override fun getErrorAttributes(
		webRequest: WebRequest?,
		options: ErrorAttributeOptions?
	): MutableMap<String, Any> {
		val entries: MutableMap<String, Any> =
			super.getErrorAttributes(webRequest, options)
		entries.remove(TRACE_KEY)
		return entries
	}
}

fun main(args: Array<String>) {
	runApplication<ImagesApplication>(*args)
}
