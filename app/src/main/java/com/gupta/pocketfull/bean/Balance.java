package com.gupta.pocketfull.bean;

/**
 * Created by GUPTA on 19-Apr-16.
 */
public class Balance {

    private int balanaceId;
    private int amount;
    private String date;
    private String details;
    private int pocketId;

    public Balance() {
    }

    public Balance(int amount, String date, String details) {
        this.amount = amount;
        this.date = date;
        this.details = details;
    }

    public Balance(int amount, String date, String details, int pocketId) {
        this.amount = amount;
        this.date = date;
        this.details = details;
        this.pocketId = pocketId;
    }

    public int getBalanaceId() {
        return balanaceId;
    }

    public void setBalanaceId(int balanaceId) {
        this.balanaceId = balanaceId;
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

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getPocketId() {
        return pocketId;
    }

    public void setPocketId(int pocketId) {
        this.pocketId = pocketId;
    }
}
