import com.example.christmas.config.JwtTokenConfig
import org.hamcrest.Matchers
import org.hamcrest.core.IsNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.ResultMatcher
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

abstract class DefaultTestSetting {

    abstract val rootPath: String

    private lateinit var _mockMvc: MockMvc

    private lateinit var _jwtTokenConfig: JwtTokenConfig

    @Autowired
    fun setMockMvc(mockMvc: MockMvc) {
        this._mockMvc = mockMvc
    }

    @Autowired
    fun setJwtTokenConfig(jwtTokenConfig: JwtTokenConfig) {
        this._jwtTokenConfig = jwtTokenConfig
    }

    fun ResultActions.checkIsError(status: ResultMatcher, errorMessage: String) =
        andDo(MockMvcResultHandlers.print())
            .andExpect(status)
            .andExpect(MockMvcResultMatchers.jsonPath("$.data", Matchers.`is`(IsNull.nullValue())))
            .andExpect(MockMvcResultMatchers.jsonPath("$.error.message", Matchers.`is`(errorMessage)))
            .andExpect(
                MockMvcResultMatchers.jsonPath(
                    "$.error.message",
                    Matchers.`is`(errorMessage)
                )
            )


    fun doGet(uri: String = "", param: String = "", root: String = rootPath) =
        _mockMvc.perform(
            MockMvcRequestBuilders.get("$root/$uri$param")
                .accept(MediaType.APPLICATION_JSON)
        )

    fun doAuthenticatedGet(token: String = "", uri: String = "", param: String = "", root: String = rootPath) =
        _mockMvc.perform(
            MockMvcRequestBuilders.get("$root/$uri$param")
                .accept(MediaType.APPLICATION_JSON)
                .header(_jwtTokenConfig.header, "Bearer $token")
        )

    fun doPost(uri: String = "", body: String = "", root: String = rootPath) =
        _mockMvc.perform(
            MockMvcRequestBuilders.post("$root/$uri")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

    fun doAuthenticatedPost(token: String = "", uri: String = "", body: String = "", root: String = rootPath) =
        _mockMvc.perform(
            MockMvcRequestBuilders.post("$root/$uri")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .header(_jwtTokenConfig.header, "Bearer $token")
                .content(body)
        )

    fun doDelete(uri: String = "", root: String = rootPath) =
        _mockMvc.perform(
            MockMvcRequestBuilders.delete("$root/$uri")
                .accept(MediaType.APPLICATION_JSON)
        )

    fun doAuthenticatedDelete(token: String = "", uri: String = "", root: String = rootPath) =
        _mockMvc.perform(
            MockMvcRequestBuilders.delete("$root/$uri")
                .accept(MediaType.APPLICATION_JSON)
                .header(_jwtTokenConfig.header, "Bearer $token")
        )

    fun doPut(uri: String = "", body: String = "", root: String = rootPath) =
        _mockMvc.perform(
            MockMvcRequestBuilders.put("$root/$uri")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

    fun doAuthenticatedPut(token: String = "", uri: String = "", body: String = "", root: String = rootPath) =
        _mockMvc.perform(
            MockMvcRequestBuilders.put("$root/$uri")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .header(_jwtTokenConfig.header, "Bearer $token")
                .content(body)
        )
}