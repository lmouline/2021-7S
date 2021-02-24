package ludovicmouline.interview.strips

import org.junit.Ignore
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@RunWith(SpringJUnit4ClassRunner::class)
@SpringBootTest(classes = [StripApplication::class])
@WebAppConfiguration
class StripsLengthTest(@Autowired val appCtx: WebApplicationContext,
                          @Autowired val controller: StripController) {
    lateinit var mockMvc: MockMvc

    @BeforeEach
    fun setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(appCtx).build()
    }

    @Test
    fun `Assert images return 20 images`(){
        this.mockMvc.perform(MockMvcRequestBuilders.get("/strips"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType("application/hal+json"))
            .andExpect(
                MockMvcResultMatchers.jsonPath("\$._embedded.stripList.length()").value(20))
    }
}

