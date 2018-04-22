import com.n26.Application;
import com.n26.controller.StatisticController;
import com.n26.model.Transaction;
import com.n26.service.StatisticService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = Application.class)
@WebMvcTest(StatisticController.class)
public class StatisticControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private StatisticService statisticService;

    @Test
    public void testInsertTransaction_ValidJson() throws Exception {
        BDDMockito.doNothing().when(statisticService).insertStatistic(Mockito.any(Transaction.class));
        String request = "{" +
                    "\"amount\": 12.3," +
                    "\"timestamp\": 1478192204000" +
                "}";
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/transactions")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON))
            .andReturn();

        Assert.assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
    }
}
