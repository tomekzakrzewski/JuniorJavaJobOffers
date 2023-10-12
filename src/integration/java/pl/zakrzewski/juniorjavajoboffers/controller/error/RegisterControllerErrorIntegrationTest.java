package pl.zakrzewski.juniorjavajoboffers.controller.error;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import pl.zakrzewski.juniorjavajoboffers.BaseIntegrationTest;
import pl.zakrzewski.juniorjavajoboffers.domain.register.dto.RegisterRequestDto;
import pl.zakrzewski.juniorjavajoboffers.domain.register.dto.RegisterResultDto;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RegisterControllerErrorIntegrationTest extends BaseIntegrationTest {

    @Container
    public static final MySQLContainer mySQLContainer = new MySQLContainer("mysql:8.1.0");


    @DynamicPropertySource
    public static void propertyOverride(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
    }


    @Test
    public void should_return_409_conflict_when_user_with_the_given_email_already_exists() throws Exception {
        RegisterRequestDto registerRequestDto = new RegisterRequestDto("abcd", "abcd@gmail.com");

        ResultActions perform = mockMvc.perform(post("/api/v1/register")
                .content(objectMapper.writeValueAsString(registerRequestDto))
                .contentType(MediaType.APPLICATION_JSON)
        );
        perform.andExpect(status().isCreated());

        ResultActions perform1 = mockMvc.perform(post("/api/v1/register")
                .content(objectMapper.writeValueAsString(registerRequestDto))
                .contentType(MediaType.APPLICATION_JSON)
        );
        perform1.andExpect(status().isConflict());
    }

    @Test
    public void should_return_400_bad_requset_when_user_gave_invalid_email() throws Exception{
        RegisterRequestDto registerRequestDto = new RegisterRequestDto("abcd", "abcd");

        ResultActions perform = mockMvc.perform(post("/api/v1/register")
                .content(objectMapper.writeValueAsString(registerRequestDto))
                .contentType(MediaType.APPLICATION_JSON)
        );
        perform.andExpect(status().isBadRequest());
    }

    @Test
    public void should_return_409_conflict_when_token_already_confirmed() throws Exception {
        RegisterRequestDto registerRequestDto = new RegisterRequestDto("abcd", "abcde@gmail.com");

        ResultActions perform = mockMvc.perform(post("/api/v1/register")
                .content(objectMapper.writeValueAsString(registerRequestDto))
                .contentType(MediaType.APPLICATION_JSON)
        );

        MvcResult mvcResult = perform.andExpect(status().isCreated()).andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        RegisterResultDto registerResultDto = objectMapper.readValue(json, RegisterResultDto.class);
        String token = registerResultDto.token();

        ResultActions successConfirmToken = mockMvc.perform(get("/api/v1/confirm?token=" + token)
                .contentType(MediaType.APPLICATION_JSON));
        successConfirmToken.andExpect(status().isOk());

        ResultActions errorConfirmToken = mockMvc.perform(get("/api/v1/confirm?token=" + token)
                .contentType(MediaType.APPLICATION_JSON));
        errorConfirmToken.andExpect(status().isConflict());
    }

}
