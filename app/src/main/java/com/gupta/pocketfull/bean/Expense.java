package com.gupta.pocketfull.bean;

/**
 * Created by GUPTA on 19-Apr-16.
 */
public class Expense {

    private int expenseId;
    private String name;
    private int amount;
    private String description;
    private String category;
    private String date;
    private String month;
    private int year;
    private int pocketId;

    public Expense() {
    }

    public Expense(String name, int amount, String description, String category) {
        this.name = name;
        this.amount = amount;
        this.description = description;
        this.category = category;
    }

    public Expense(String name, int amount, String description, String category, String date, String month, int year) {
        this.name = name;
        this.amount = amount;
        this.description = description;
        this.category = category;
        this.date = date;
        this.month = month;
        this.year = year;
    }

    public Expense(String name, int amount, String description, String category, String date, String month, int year, int pocketId) {
        this.name = name;
        this.amount = amount;
        this.description = description;
        this.category = category;
        this.date = date;
        this.month = month;
        this.year = year;
        this.pocketId = pocketId;
    }

    public int getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(int expenseId) {
        this.expenseId = expenseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getPocketId() {
        return pocketId;
    }

    public void setPocketId(int pocketId) {
        this.pocketId = pocketId;
    }
}
