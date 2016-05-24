package com.gupta.pocketfull.bean;

/**
 * Created by GUPTA on 19-Apr-16.
 */
public class Pockets {

    private int pocketId;
    private String name;
    private String date;
    private String lastResetDate;

    public Pockets() {
    }

    public Pockets(String name) {
        this.name = name;
    }

    public Pockets(String name, String date) {
        this.name = name;
        this.date = date;
    }

    public Pockets(String name, String date, String lastResetDate) {
        this.name = name;
        this.date = date;
        this.lastResetDate = lastResetDate;
    }

    public int getPocketId() {
        return pocketId;
    }

    public String getLastResetDate() {
        return lastResetDate;
    }

    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public void setPocketId(int pocketId) {
        this.pocketId = pocketId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setLastResetDate(String lastResetDate) {
        this.lastResetDate = lastResetDate;
    }
}
