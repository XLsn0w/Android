package com.example.mycalender2;

public class Day {
    private int day;
    private int month;
    private int year;
    private boolean currentMonth;//当月
    private boolean currentDay;//当天

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public boolean isCurrentMonth() {
        return currentMonth;
    }

    public void setCurrentMonth(boolean currentMonth) {
        this.currentMonth = currentMonth;
    }

    public boolean isCurrentDay() {
        return currentDay;
    }

    public void setCurrentDay(boolean currentDay) {
        this.currentDay = currentDay;
    }
}

