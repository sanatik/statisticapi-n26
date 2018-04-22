import com.n26.model.Statistics;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
public class StatisticsTest {

    @Test
    public void testAddEntry_Amount() {
        Statistics response = new Statistics();
        response.addEntry(12.456);

        Assert.assertEquals(12.4, response.getSum(), 2);
        Assert.assertEquals(12.4, response.getAvg(), 2);
        Assert.assertEquals(12.4, response.getMax(), 2);
        Assert.assertEquals(12.4, response.getMin(), 2);
        Assert.assertEquals(1, response.getCount().longValue());
    }

    @Test
    public void testAddEntry_MultipleAmount() {
        Statistics response = new Statistics();
        List<Double> amounts = Arrays.asList(12.4, 10d, 817.6, 2734.2, 888.8);
        amounts.forEach(response::addEntry);

        Assert.assertEquals(amounts.stream().mapToDouble(Double::doubleValue).sum(), response.getSum(), 2);
        Assert.assertEquals(amounts.stream().mapToDouble(Double::doubleValue).average().getAsDouble(), response.getAvg(), 2);
        Assert.assertEquals(amounts.stream().mapToDouble(Double::doubleValue).max().getAsDouble(), response.getMax(), 2);
        Assert.assertEquals(amounts.stream().mapToDouble(Double::doubleValue).min().getAsDouble(), response.getMin(), 2);
        Assert.assertEquals(amounts.size(), response.getCount().longValue());
    }

    @Test
    public void testAddEntry_Statistic() {
        Statistics response = new Statistics();
        Statistics statToAdd = new Statistics();
        statToAdd.addEntry(12.4);
        response.addEntry(statToAdd);

        Assert.assertEquals(12.4, response.getSum(), 2);
        Assert.assertEquals(12.4, response.getAvg(), 2);
        Assert.assertEquals(12.4, response.getMax(), 2);
        Assert.assertEquals(12.4, response.getMin(), 2);
        Assert.assertEquals(1, response.getCount().longValue());
    }

    @Test
    public void testAddEntry_MultipleStatistic() {
        Statistics response = new Statistics();
        List<Statistics> statisticsList = new ArrayList<>();
        List<Double> amounts = Arrays.asList(12.4, 10d, 817.6, 2734.2, 888.8);
        amounts.forEach(it -> {
            Statistics s = new Statistics();
            s.addEntry(it);
            statisticsList.add(s);
        });

        statisticsList.forEach(response::addEntry);

        Assert.assertEquals(amounts.stream().mapToDouble(Double::doubleValue).sum(), response.getSum(), 2);
        Assert.assertEquals(amounts.stream().mapToDouble(Double::doubleValue).average().getAsDouble(), response.getAvg(), 2);
        Assert.assertEquals(amounts.stream().mapToDouble(Double::doubleValue).max().getAsDouble(), response.getMax(), 2);
        Assert.assertEquals(amounts.stream().mapToDouble(Double::doubleValue).min().getAsDouble(), response.getMin(), 2);
        Assert.assertEquals(amounts.size(), response.getCount().longValue());
    }
}
