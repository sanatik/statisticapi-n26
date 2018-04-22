package com.n26.service;

import com.n26.model.Transaction;
import com.n26.model.Statistics;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.TimeUnit;

@Service
public class StatisticService {

    private final long ONE_MINUTE = 60 * 1000;
    private ConcurrentSkipListMap<Long, Statistics> transactions = new ConcurrentSkipListMap<>();

    public void insertStatistic(Transaction request) {
        long diff = getCurrentTime() - request.getTimestamp();
        if(diff > ONE_MINUTE) {
            throw new IllegalArgumentException();
        }
        Long second = TimeUnit.MILLISECONDS.toSeconds(request.getTimestamp());
        transactions.putIfAbsent(second, new Statistics());
        transactions.get(second).addEntry(request.getAmount());
    }

    public Statistics getStatistic() {
        Statistics result = new Statistics();
        Long currentSecond = TimeUnit.MILLISECONDS.toSeconds(getCurrentTime());
        Long currentSecondMinusOneMinute = TimeUnit.MILLISECONDS.toSeconds(getCurrentTime() - ONE_MINUTE);
        Map<Long, Statistics> lastMinuteTransactions = transactions.subMap(currentSecondMinusOneMinute, true, currentSecond, true);
        lastMinuteTransactions.values().forEach(result::addEntry);
        return result;
    }

    private long getCurrentTime() {
        return System.currentTimeMillis();
    }

    /**
     * Just for testing purposes.
     * I understand that in real life such method is unacceptable,
     * but since I store data in memory, this way is easier.
     */
    public void clear() {
        transactions.clear();
    }
}
