package ludovicmouline.interview.strips

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@RunWith(SpringJUnit4ClassRunner::class)
@SpringBootTest(classes = [StripApplication::class])
@WebAppConfiguration
class StripControllerTest(@Autowired val appCtx: WebApplicationContext) {

    lateinit var mockMvc: MockMvc

    @BeforeEach
    fun setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(appCtx).build()
    }

    @Test
    fun `Assert images return 20 images`(){
        this.mockMvc.perform(get("/strips"))
            .andExpect(status().isOk)
            .andExpect(content().contentType("application/hal+json"))
            .andExpect(jsonPath("\$._embedded.stripList.length()").value(20))
    }

    @Test
    fun `Assert all elements are present`() {
        this.mockMvc.perform(get("/strips"))
            .andExpect(status().isOk)
            .andExpect(content().contentType("application/hal+json"))
            .andExpect(
                jsonPath("\$._embedded.stripList[*].title").exists()
            )
            .andExpect(
                jsonPath("\$._embedded.stripList[*].description").exists()
            )
            .andExpect(
                jsonPath("\$._embedded.stripList[*].webUrl").exists()
            )
            .andExpect(
                jsonPath("\$._embedded.stripList[*].publishedDate").exists()
            )
            .andExpect(
                jsonPath("\$._embedded.stripList[*].pictureUrl").exists()
            )
    }
}