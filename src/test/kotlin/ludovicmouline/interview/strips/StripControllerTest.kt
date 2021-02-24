package ludovicmouline.interview.strips

import org.junit.Before
import org.junit.BeforeClass
import org.junit.Ignore
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.util.ReflectionTestUtils
import org.springframework.test.web.servlet.MockMvc

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import java.time.LocalDate

@RunWith(SpringJUnit4ClassRunner::class)
@SpringBootTest(classes = [StripApplication::class])
@WebAppConfiguration
class StripControllerTest(@Autowired val appCtx: WebApplicationContext,
                          @Autowired val controller: StripController) {

    lateinit var mockMvc: MockMvc


    @BeforeEach
    fun setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(appCtx).build()

        // Mock XKCD source
        val xkcd = Mockito.mock(XKCDSource::class.java);
        Mockito.`when`(xkcd.pullImages(10))
            .thenReturn(arrayListOf(
                Strip(title = "XKCD 1", webUrl = "http://xkcd.com/1",
                    description= "Description 1",
                    pictureUrl = "http://xkcd.com/1.png",
                    publishedDate = LocalDate.of(2015, 8, 21)),
                Strip(title = "XKCD 2", webUrl = "http://xkcd.com/2",
                    description= "Description 2",
                    pictureUrl = "http://xkcd.com/2.png",
                    publishedDate = LocalDate.of(2021, 1, 6)),
                Strip(title = "XKCD 3", webUrl = "http://xkcd.com/3",
                    description= "Description 3",
                    pictureUrl = "http://xkcd.com/3.png",
                    publishedDate = LocalDate.of(2020, 10, 5))
            ))

        // Mock PDL Source
        val pdl = Mockito.mock(PDLSource::class.java)
        Mockito.`when`(pdl.pullImages(10))
            .thenReturn(arrayListOf(
                Strip(title = "PDL 1", webUrl = "http://pdl.com/1",
                    pictureUrl = "http://pdl.com/1.png",
                    publishedDate = LocalDate.of(2013, 7, 6)),
                Strip(title = "PDL 2", webUrl = "http://pdl.com/2",
                    pictureUrl = "http://pdl.com/2.png",
                    publishedDate = LocalDate.of(2013, 7, 7)),
                Strip(title = "PDL 3", webUrl = "http://pdl.com/3",
                    pictureUrl = "http://pdl.com/3.png",
                    publishedDate = LocalDate.of(2021, 10, 5)),
                Strip(title = "PDL 4", webUrl = "http://pdl.com/4",
                    pictureUrl = "http://pdl.com/4.png",
                    publishedDate = LocalDate.of(2018, 10, 5))
            ))

        ReflectionTestUtils.setField(
            controller,
            "sources",
            arrayListOf(xkcd, pdl))
    }

    @Test
    fun `Assert reverse chronological order`() {
        this.mockMvc.perform(get("/strips"))
            .andExpect(status().isOk)
            .andExpect(content().contentType("application/hal+json"))
            .andExpect(
                jsonPath("\$._embedded.stripList[*].title").value(
                    arrayListOf("PDL 3", "XKCD 2", "XKCD 3", "PDL 4", "XKCD 1",
                        "PDL 2", "PDL 1")
                )
            )
    }

    @Test
    fun `Assert all elements are present`() {
        this.mockMvc.perform(get("/strips"))
            .andExpect(status().isOk)
            .andExpect(content().contentType("application/hal+json"))
            .andDo { println(it.response.contentAsString) }
            .andExpect(
                jsonPath("\$._embedded.stripList[*].title").value(
                    arrayListOf("PDL 3", "XKCD 2", "XKCD 3", "PDL 4", "XKCD 1",
                        "PDL 2", "PDL 1")
                )
            )
            .andExpect(
                jsonPath("\$._embedded.stripList[*].description").value(
                    arrayListOf("", "Description 2", "Description 3", "",
                        "Description 1", "", "")
                )
            )
            .andExpect(
                jsonPath("\$._embedded.stripList[*].webUrl").value(
                    arrayListOf("http://pdl.com/3", "http://xkcd.com/2", "http://xkcd.com/3", "http://pdl.com/4", "http://xkcd.com/1",
                        "http://pdl.com/2", "http://pdl.com/1")
                )
            )
            .andExpect(
                jsonPath("\$._embedded.stripList[*].publishedDate").value(
                    arrayListOf("2021-10-05", "2021-01-06", "2020-10-05", "2018-10-05", "2015-08-21",
                        "2013-07-07", "2013-07-06")
                )
            )
            .andExpect(
                jsonPath("\$._embedded.stripList[*].pictureUrl").value(
                    arrayListOf("http://pdl.com/3.png", "http://xkcd.com/2.png", "http://xkcd.com/3.png", "http://pdl.com/4.png", "http://xkcd.com/1.png",
                        "http://pdl.com/2.png", "http://pdl.com/1.png")
                )
            )
    }

}