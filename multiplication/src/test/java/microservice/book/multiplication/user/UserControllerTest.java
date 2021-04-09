package microservice.book.multiplication.user;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.List;
import microservice.book.multiplication.user.repository.UserRepository;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

/**
 * @author Alexander Bravo
 */
@ExtendWith(SpringExtension.class)
@AutoConfigureJsonTesters
@WebMvcTest(UserController.class)
@TestPropertySource("classpath:/test.properties")
public class UserControllerTest {
    @MockBean
    private UserRepository userRepository;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private JacksonTester<List<User>> json;

    @Test
    void getUserList() throws Exception {
        User user0 = new User(1L, "test1");
        User user1 = new User(2L, "test2");
        List<User> listUser = List.of(user0, user1);
        //given
        given(userRepository.findByIdIn(List.of(1L, 2L))).willReturn(listUser);
        //when
        MockHttpServletResponse response = mvc.perform(
            get("/users/1,2").contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();
        //then
        then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        then(response.getContentAsString()).isEqualTo(json.write(listUser).getJson());
    }

}
