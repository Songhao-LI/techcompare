package com.bootcamp.techcompare;

public class Rating {
    private double rate;
    private int count;

    public Rating() {
        this.rate = 0.0;
        this.count = 0;
    }
    public Rating(double rate, int count) {
        this.rate = rate;
        this.count = count;
    }

    public double getRate() {
        return rate;
    }

    public int getCount(){
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setRate(double rate){
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "rating{" +
                "rate=" + rate +
                ", count=" + count +
                '}';
    }
}
