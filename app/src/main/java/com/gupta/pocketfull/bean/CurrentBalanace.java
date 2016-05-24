package com.gupta.pocketfull.bean;

/**
 * Created by GUPTA on 19-Apr-16.
 */
public class CurrentBalanace {

    private int currentBalanaceId;
    private int amount;
    private String date;
    private int pocketId;

    public CurrentBalanace() {

    }

    public CurrentBalanace(int amount, String date) {
        this.amount = amount;
        this.date = date;
    }

    public CurrentBalanace(int amount, String date, int pocketId) {
        this.amount = amount;
        this.date = date;
        this.pocketId = pocketId;
    }

    public int getCurrentBalanaceId() {
        return currentBalanaceId;
    }

    public void setCurrentBalanaceId(int currentBalanaceId) {
        this.currentBalanaceId = currentBalanaceId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPocketId() {
        return pocketId;
    }

    public void setPocketId(int pocketId) {
        this.pocketId = pocketId;
    }
}
