package com.n26.controller;

import com.n26.model.Transaction;
import com.n26.model.Statistics;
import com.n26.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class StatisticController {

    @Autowired
    private StatisticService statisticService;

    @RequestMapping(value = "/transactions", method = RequestMethod.POST)
    public ResponseEntity insertStatistic(@RequestBody Transaction request) {
        try {
            statisticService.insertStatistic(request);
            return new ResponseEntity(HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
    }

    @RequestMapping(value = "/statistic", method = RequestMethod.GET)
    public Statistics getStatistic() {
        return statisticService.getStatistic();
    }
}
