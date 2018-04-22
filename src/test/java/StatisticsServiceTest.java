import com.n26.Application;
import com.n26.model.Statistics;
import com.n26.model.Transaction;
import com.n26.service.StatisticService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class)
public class StatisticsServiceTest {

    @Autowired
    private StatisticService statisticService;

    @Test
    public void testInsertTransaction_NoException() {
        Transaction request = new Transaction();
        request.setAmount(12.4);
        request.setTimestamp(System.currentTimeMillis() - 10);
        try {
            statisticService.insertStatistic(request);
            Assert.assertTrue(true);
        } catch (IllegalArgumentException ie) {
            Assert.assertTrue(false);
        }
    }

    @Test
    public void testInsertTransaction_ExceptionThrown() {
        Transaction request = new Transaction();
        request.setAmount(12.4);
        request.setTimestamp(System.currentTimeMillis() - 60001);
        try {
            statisticService.insertStatistic(request);
            Assert.assertTrue(false);
        } catch (IllegalArgumentException ie) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void testGetStatistic_NoTransactions() {
        Statistics statistics = statisticService.getStatistic();
        Assert.assertEquals(0, statistics.getCount().longValue());
    }

    @Test
    public void testGetStatistic_NoExpiredTransactions() {
        insertTransactions(10);
        Statistics statistics = statisticService.getStatistic();
        Assert.assertEquals(10, statistics.getCount().longValue());
    }

    @Test
    public void testGetStatistic_ExpiredTransactions() throws InterruptedException {
        insertTransactions(2, System.currentTimeMillis() - 59900);
        Thread.sleep(1000);
        insertTransactions(3);
        Thread.sleep(1000);
        insertTransactions(1, System.currentTimeMillis() - 40000);
        Statistics statistics = statisticService.getStatistic();
        Assert.assertEquals(4, statistics.getCount().longValue());
    }

    private void insertTransactions(int amount) {
        insertTransactions(amount, System.currentTimeMillis());
    }

    private void insertTransactions(int amount, long timestamp) {
        for(int i=0; i<amount; i++) {
            Transaction request = new Transaction();
            request.setAmount(12.4);
            request.setTimestamp(timestamp);
            statisticService.insertStatistic(request);
        }
    }

    @After
    public void clear() {
        statisticService.clear();
    }
}
