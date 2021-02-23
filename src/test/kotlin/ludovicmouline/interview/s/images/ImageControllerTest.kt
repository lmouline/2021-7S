package ludovicmouline.interview.s.images

import org.hamcrest.CoreMatchers
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*


@WebMvcTest(ImageController::class)
class ImageControllerTest(
    @Autowired val mockMvc: MockMvc
) {

    @Test
    fun `Assert images return 20 images`(){
        this.mockMvc.perform(get("/images"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("\$.length()").value(20))
    }
}