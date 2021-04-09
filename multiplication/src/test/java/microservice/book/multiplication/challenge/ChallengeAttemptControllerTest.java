package microservice.book.multiplication.challenge;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import microservice.book.multiplication.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.TestPropertySources;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

/**
 * @author Alexander Bravo
 */

//@ExtendWith(SpringExtension.class) makes sure that our JUnit 5 test loads the extensions
// for Spring so we can use a test context.
@ExtendWith(SpringExtension.class)
//@AutoConfigureJsonTesters tells Spring to configure beans of type JacksonTester for some fields
// we declare in the test. In our case, we use @Autowired to inject two JacksonTester beans from
// the test context. Spring Boot, when instructed via this annotation, takes care of building these
// utility classes. A JacksonTester may be used to serialize and deserialize objects using the same
// configuration (i.e., ObjectMapper) as the app would do in runtime.
@AutoConfigureJsonTesters
//@WebMvcTest, with the controller class as a parameter, makes Spring treat this as a presentation layer test.
// Thus, it’ll load only the relevant configuration around the controller: validation, serializers,
// security, error handlers, etc. (see https://tpd.io/test-autoconf for a full list of included auto-configuration classes).
@WebMvcTest(ChallengeAttemptController.class)
@TestPropertySource("classpath:/test.properties")
public class ChallengeAttemptControllerTest {

    //@MockBean comes with the Spring Boot Test module and helps you develop proper unit tests
    // by allowing you to mock other layers and beans you’re not testing. In our case, we replace
    // the service bean in the context by a mock. We set the expected return values within the test methods,
    // using BDDMockito’s given().
    @MockBean
    private ChallengeService challengeService;
    @Autowired
    //The MockMvc class is what we use in Spring to simulate requests to the presentation
    // layer when we make a test that doesn’t load a real server. It’s provided by the test
    // context so we can just inject it in our test.
    private MockMvc mvc;
    @Autowired
    private JacksonTester<ChallengeAttemptDTO> jsonRequestAttempt;
    @Autowired
    private JacksonTester<ChallengeAttempt> jsonResultAttempt;

    @Test
    void postValidResult() throws Exception {
        // given
        User user = new User(1L, "john");
        long attemptId = 5L;
        ChallengeAttemptDTO attemptDTO = new ChallengeAttemptDTO(50, 70, "john", 3500);
        ChallengeAttempt expectedResponse = new ChallengeAttempt(attemptId, user, 50, 70, 3500, true);
        given(challengeService
            .verifyAttempt(eq(attemptDTO)))
            .willReturn(expectedResponse);
        // when
        MockHttpServletResponse response = mvc.perform(
            post("/attempts").contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequestAttempt.write(attemptDTO).getJson()))
            .andReturn().getResponse();
        // then
        then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        then(response.getContentAsString()).isEqualTo(
            jsonResultAttempt.write(expectedResponse).getJson());
    }

    @Test
    void postInvalidResult() throws Exception {
        // given an attempt with invalid input data
        ChallengeAttemptDTO attemptDTO = new ChallengeAttemptDTO(2000, -70, "john", 1);
        // when
        MockHttpServletResponse response = mvc.perform(
            post("/attempts").contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequestAttempt.write(attemptDTO).getJson()))
            .andReturn().getResponse();
        // then
        then(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
}
