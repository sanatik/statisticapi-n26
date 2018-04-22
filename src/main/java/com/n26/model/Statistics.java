package com.n26.model;

public class Statistics {

    private Double sum = 0.0;
    private Double avg = 0.0;
    private Double max = 0.0;
    private Double min = Double.MAX_VALUE;
    private Long count = 0L;

    public void addEntry(double amount) {
        this.sum += amount;

        if(amount > this.max) {
            this.max = amount;
        }
        if(amount < this.min) {
            this.min = amount;
        }
        this.count++;
        calculateAverage();
    }

    public void addEntry(Statistics response) {
        this.sum += response.getSum();

        if(response.getMax() > this.max) {
            this.max = response.getMax();
        }
        if(response.getMin() < this.min) {
            this.min = response.getMin();
        }

        this.count += response.getCount();
        calculateAverage();
    }

    public Double getSum() {
        return sum;
    }

    public Double getAvg() {
        return avg;
    }

    public Double getMax() {
        return max;
    }

    public Double getMin() {
        return min == Double.MAX_VALUE ? 0d : min;
    }

    public Long getCount() {
        return count;
    }

    private void calculateAverage() {
        this.avg = count != 0 ? sum / count : 0D;
    }
}
