package ludovicmouline.interview.strips

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.test.util.ReflectionTestUtils
import org.springframework.web.client.RestTemplate

class XKCDSourceTest {

    @Test
    fun `Assert well formed JSON fom XKCD`() {
        val mockRestTemplate = Mockito.mock(RestTemplate::class.java)
        val xkcd = Mockito.mock(XKCDSource::class.java);
        ReflectionTestUtils.setField(xkcd, "restTemplate", mockRestTemplate);

        Mockito.`when`(mockRestTemplate.getForObject(
            ArgumentMatchers.anyString(),
            ArgumentMatchers.any(Class::class.java)
        ))
            .thenReturn(null)

        println(xkcd.pullImages(10))
    }

    @Test
    fun `Assert null JSON`() {
        val mockRestTemplate = Mockito.mock(RestTemplate::class.java)
        val xkcd = XKCDSource();
        ReflectionTestUtils.setField(xkcd, "restTemplate", mockRestTemplate);

        Mockito.`when`(mockRestTemplate.getForObject(
            ArgumentMatchers.anyString(),
            ArgumentMatchers.any(Class::class.java)
        ))
            .thenReturn(null)

        Assertions.assertDoesNotThrow {
            val images = xkcd.pullImages(10)
            Assertions.assertEquals(0, images.size)
        }
    }

    @Test
    fun `Assert number of calls done`() {
        val nbWanted = 10

        val spyRestTemplate = Mockito.spy(RestTemplateBuilder().build())

        val xkcd = XKCDSource();
        ReflectionTestUtils.setField(xkcd, "restTemplate", spyRestTemplate);

        Mockito.doReturn(XKCDImage(month = "10", title = "title",
            alt = "description", day = "5", num = 10, img = "img",
            year = "2020"))
            .`when`(spyRestTemplate)
            .getForObject(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any(Class::class.java)
            )

        xkcd.pullImages(nbWanted)

        Mockito.verify(spyRestTemplate, Mockito.times(10))
            .getForObject(ArgumentMatchers.anyString(), ArgumentMatchers.any(Class::class.java))

    }

}