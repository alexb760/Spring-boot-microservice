package microservice.book.gamification.game;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.Collections;
import java.util.List;
import microservice.book.gamification.game.domain.BadgeType;
import microservice.book.gamification.game.domain.LeaderBoardRow;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

/**
 * @author Alexander Bravo
 */
@ExtendWith(SpringExtension.class)
@AutoConfigureJsonTesters
@WebMvcTest(LeaderBoardController.class)
public class LeaderBoardControllerTest {
    @MockBean
    private LeaderBoardService boardService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JacksonTester<List<LeaderBoardRow>> jason;

    @Test
    public void testLeaderBoardRows() throws Exception {
        LeaderBoardRow row =
            new LeaderBoardRow(1L, 300L, Collections.singletonList(BadgeType.BRONZE.getDescription()));
        List<LeaderBoardRow> expectedResult = Collections.singletonList(row);
        given(boardService.getCurrentLeaderBoard()).willReturn(expectedResult);
        //when
        MockHttpServletResponse actual = mockMvc.perform(
            get("/leaders").contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();
        //then
        then(actual.getStatus()).isEqualTo(HttpStatus.OK.value());
        then(actual.getContentAsString()).isEqualTo(jason.write(expectedResult).getJson());
    }
}
