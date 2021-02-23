package ludovicmouline.interview.s.images

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.junit.jupiter.api.Assertions.assertEquals
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity


data class ErrorJson(
    val timestamp: String,
    val status: Int,
    val error: String,
    val message: String,
    val path: String
)

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ErrorHandling(
    @LocalServerPort val port: Int,
    @Autowired val restTemplate: TestRestTemplate
) {


    @Test
    fun `Assertion error handling`() {
        val a: ResponseEntity<ErrorJson> = restTemplate.getForEntity("""http://localhost:$port""", ErrorJson::class.java)
        assertEquals(HttpStatus.NOT_FOUND, a.statusCode)
        assertEquals(HttpStatus.NOT_FOUND.value(), a.body?.status)
        assertEquals("", a.body?.message)
        assertEquals("Not Found", a.body?.error)
        assertEquals("/", a.body?.path)
    }
}

