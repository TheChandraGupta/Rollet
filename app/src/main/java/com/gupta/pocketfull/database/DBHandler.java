package com.gupta.pocketfull.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.gupta.pocketfull.bean.Balance;
import com.gupta.pocketfull.bean.CurrentBalanace;
import com.gupta.pocketfull.bean.Expense;
import com.gupta.pocketfull.bean.Pockets;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by GUPTA on 19-Apr-16.
 */
public class DBHandler extends SQLiteOpenHelper{

    // LOG Tag
    private static final String TAG = "DBHandler";

    // Database Version. This Should be increment after every changes in database.
    private static final int DATABASE_VERSION = 1;

    // Database name. This should end with .db extension
    private static final String DATABASE_NAME = "rollet.db";

    // Table Names
    public static final String TABLE_POCKET = "pocket";
    public static final String TABLE_BALANACE = "balance";
    public static final String TABLE_CURRENTBALANCE = "currentbalance";
    public static final String TABLE_EXPENSE = "expense";

    // Common Attributes Names
    public static final String KEY_NAME = "name";
    public static final String KEY_DATE = "date";
    public static final String KEY_AMOUNT = "amount";

    // Attributes of POCKET Table
    public static final String POCKET_ID = "pocketid";
    public static final String POCKET_LASTRESETDATE = "lastresetdate";

    // Attributes of BALANCE Table
    public static final String BALANCE_ID = "balanceid";
    public static final String BALANCE_DETAILS = "details";
    //public static final String BALANCE_POCKETID = "pocketid";

    // Attributes of CURRENTBALANCE Table
    public static final String CURRENTBALANCE_ID = "currentbalanceid";
    //public static final String CURRENTBALANCE_POCKETID = "pocketid";

    // Attribtutes of EXPENSE Table
    public static final String EXPENSE_ID = "expenseid";
    public static final String EXPENSE_DESCRIPTION = "description";
    public static final String EXPENSE_CATEGORY = "category";
    public static final String EXPENSE_MONTH = "month";
    public static final String EXPENSE_YEAR = "year";
    //public static final String EXPENSE_POCKETID = "pocketid";

    // Create Tables
    public static final String CREATE_POCKET = "CREATE TABLE " + TABLE_POCKET + " (" +
            POCKET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_NAME + " TEXT NOT NULL, " +
            KEY_DATE + " TEXT NOT NULL, " +
            POCKET_LASTRESETDATE + " TEXT NOT NULL " +
            ");";
    public static final String CREATE_BALANCE = "CREATE TABLE " + TABLE_BALANACE + " (" +
            BALANCE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_AMOUNT + " INTEGER NOT NULL, " +
            KEY_DATE + " TEXT NOT NULL, " +
            BALANCE_DETAILS + " TEXT NOT NULL, " +
            POCKET_ID + " INTEGER NOT NULL " +
            ");";
    public static final String CREATE_CUREENTBALANCE = "CREATE TABLE " + TABLE_CURRENTBALANCE + " (" +
            CURRENTBALANCE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_AMOUNT + " INTEGER NOT NULL, " +
            KEY_DATE + " TEXT NOT NULL, " +
            POCKET_ID + " INTEGER NOT NULL " +
            ");";
    public static final String CREATE_EXPENSE = "CREATE TABLE " + TABLE_EXPENSE + " (" +
            EXPENSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_NAME + " TEXT NOT NULL, " +
            KEY_AMOUNT + " INTEGER NOT NULL, " +
            EXPENSE_DESCRIPTION + " TEXT NOT NULL, " +
            EXPENSE_CATEGORY + " TEXT NOT NULL, " +
            KEY_DATE + " TEXT NOT NULL, " +
            EXPENSE_MONTH + " TEXT NOT NULL, " +
            EXPENSE_YEAR + " INTEGER NOT NULL, " +
            POCKET_ID + " INTEGER NOT NULL " +
            ");";

    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.d(TAG, "Creating Table......");

        db.execSQL(CREATE_POCKET);
        Log.d(TAG, "POCKET TABLE CREATED");

        db.execSQL(CREATE_BALANCE);
        Log.d(TAG, "BALANCE TABLE CREATED");

        db.execSQL(CREATE_CUREENTBALANCE);
        Log.d(TAG, "CURRENT BALANCE TABLE CREATED");

        db.execSQL(CREATE_EXPENSE);
        Log.d(TAG, "EXPENSE TABLE CREATED");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXIST " + TABLE_EXPENSE);
        Log.d(TAG, "TABLE_EXPENSE TABLE DELETED");

        db.execSQL("DROP TABLE IF EXIST " + TABLE_CURRENTBALANCE);
        Log.d(TAG, "TABLE_CURRENTBALANCE TABLE DELETED");

        db.execSQL("DROP TABLE IF EXIST " + TABLE_BALANACE);
        Log.d(TAG, "TABLE_BALANACE TABLE DELETED");

        db.execSQL("DROP TABLE IF EXIST " + TABLE_POCKET);
        Log.d(TAG, "TABLE_POCKET TABLE DELETED");

        onCreate(db);

    }

    /*
    POCKET TABLE CRUD OPERATION
     */

    // Insert Row into POCKET_TABLE
    public void addPockets(Pockets pocket) {

        String currentDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

        ContentValues values = new ContentValues();

        values.put(KEY_NAME, pocket.getName());
        values.put(KEY_DATE, currentDate);
        values.put(POCKET_LASTRESETDATE, "Not Reseted");

        SQLiteDatabase db = getWritableDatabase();

        db.insert(TABLE_POCKET, null, values);
        Log.d(TAG, "Row Inserted in TABLE_POCKET");

        db.close();

    }

    // DELETE Row from TABLE_POCKET
    public void deletePockets(Pockets pocket) {

        SQLiteDatabase db = getWritableDatabase();

        db.execSQL("DELETE FROM " + TABLE_POCKET + " WHERE " + KEY_NAME + "=\"" + pocket.getName() + "\" ;");

        Log.d(TAG, "Row Deleted in TABLE_POCKET");

    }

    // Display a Row From TABLE_POCKET
    public ArrayList<Pockets> getPockets() {

        Pockets pocket = new Pockets();

        ArrayList<Pockets> pockets = new ArrayList<Pockets>();

        SQLiteDatabase db = getWritableDatabase();

        int i = 0;
        String query = "SELECT * FROM " + TABLE_POCKET + " WHERE 1";

        //Cursor points to the results
        Cursor c = db.rawQuery(query, null);

        c.moveToFirst();

        while (!c.isAfterLast()) {

            pocket = new Pockets();

            pocket.setPocketId(c.getInt(c.getColumnIndex(POCKET_ID)));
            pocket.setName(c.getString(c.getColumnIndex(KEY_NAME)));
            pocket.setDate(c.getString(c.getColumnIndex(KEY_DATE)));
            pocket.setLastResetDate(c.getString(c.getColumnIndex(POCKET_LASTRESETDATE)));

            pockets.add(pocket);
            c.moveToNext();

        }

        db.close();

        Log.d(TAG, "Row Fetched in TABLE_POCKET");

        return pockets;

    }

    /*
    BALANCE TABLE CRUD OPERATION
     */
    // Insert Row into BALANCE TABLE
    public void addBalance(Balance balance) {

        ContentValues values = new ContentValues();

        values.put(KEY_AMOUNT, balance.getAmount());
        values.put(KEY_DATE, balance.getDate());
        values.put(BALANCE_DETAILS, balance.getDetails());
        values.put(POCKET_ID, balance.getPocketId());

        SQLiteDatabase db = getWritableDatabase();

        db.insert(TABLE_BALANACE, null, values);
        Log.d(TAG, "Row Inserted in TABLE_BALANACE");

        db.close();

    }

    // Display Latest Balance
    public Balance getLatestBalance(int pocketId) {

        Balance balance = new Balance();

        SQLiteDatabase db = getWritableDatabase();
/*
        String query = "SELECT * FROM " + TABLE_BALANACE + " ORDER BY " + BALANCE_ID + " DESC WHERE " +
                POCKET_ID + " = " + pocketId;
*/
        //Cursor points to the results
        //Cursor c = db.rawQuery(query, null);
        String[] resultColumns = new String[] {BALANCE_ID, KEY_AMOUNT, KEY_DATE, BALANCE_DETAILS, POCKET_ID};
        String where = POCKET_ID + " = ?";
        String[] whereArgs = new String[] {"" + pocketId};
        String groupBy = null;
        String having = null;
        String orderBy = BALANCE_ID + " DESC";
        String limit = "1";
        Cursor c = db.query(TABLE_BALANACE, resultColumns, where, whereArgs, groupBy, having, orderBy, limit);

        c.moveToFirst();

        while (!c.isAfterLast()) {

            balance.setBalanaceId(c.getInt(c.getColumnIndex(BALANCE_ID)));
            balance.setAmount(c.getInt(c.getColumnIndex(KEY_AMOUNT)));
            balance.setDate(c.getString(c.getColumnIndex(KEY_DATE)));
            balance.setDetails(c.getString(c.getColumnIndex(BALANCE_DETAILS)));
            balance.setPocketId(c.getInt(c.getColumnIndex(POCKET_ID)));

        }

        db.close();

        Log.d(TAG, "Latest Row Fetched in TABLE_BALANACE");

        return balance;

    }

    // Display All BALANCE ROWS
    public Balance[] getAllBalance(int pocketId) {

        Balance[] balance = null;

        SQLiteDatabase db = getWritableDatabase();

        int i = 0;
  /*      String query = "SELECT * FROM " + TABLE_BALANACE + " ORDER BY " + BALANCE_ID + " DESC WHERE "
                + POCKET_ID + " = " + pocketId;

        //Cursor points to the results
        Cursor c = db.rawQuery(query, null);
*/
        String[] resultColumns = new String[] {BALANCE_ID, KEY_AMOUNT, KEY_DATE, BALANCE_DETAILS, POCKET_ID};
        String where = POCKET_ID + " = ?";
        String[] whereArgs = new String[] {"" + pocketId};
        String groupBy = null;
        String having = null;
        String orderBy = BALANCE_ID + " DESC";
        String limit = null;
        Cursor c = db.query(TABLE_BALANACE, resultColumns, where, whereArgs, groupBy, having, orderBy, limit);

        c.moveToFirst();

        while (!c.isAfterLast()) {

            balance[i] = new Balance();

            balance[i].setBalanaceId(c.getInt(c.getColumnIndex(BALANCE_ID)));
            balance[i].setAmount(c.getInt(c.getColumnIndex(KEY_AMOUNT)));
            balance[i].setDate(c.getString(c.getColumnIndex(KEY_DATE)));
            balance[i].setDetails(c.getString(c.getColumnIndex(BALANCE_DETAILS)));
            balance[i].setPocketId(c.getInt(c.getColumnIndex(POCKET_ID)));

            i++;
            c.moveToNext();

        }

        db.close();

        Log.d(TAG, "All Row Fetched in TABLE_BALANACE");

        return balance;
    }

    /*
    CURRENT BALANCE TABLE CRUD OPERATION
     */

    // Insert a Row in Current Balance Table
    public void addCurrentBalance(CurrentBalanace currentBalanace) {

        String currentDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

        ContentValues values = new ContentValues();

        values.put(KEY_AMOUNT, currentBalanace.getAmount());
        values.put(KEY_DATE, currentDate);
        values.put(POCKET_ID, currentBalanace.getPocketId());

        SQLiteDatabase db = getWritableDatabase();

        Log.d(TAG, "PocketID : " + values.getAsInteger(POCKET_ID) + ", Amount : "+values.getAsInteger(KEY_AMOUNT) + ", DATE : " + values.getAsString(KEY_DATE));

        db.insert(TABLE_CURRENTBALANCE, null, values);
        Log.d(TAG, "Row Inserted in TABLE_CURRENTBALANCE");

        db.close();

    }

    // Display Latest Current Balance
    public CurrentBalanace getLatestCurrentBalance(int pocketId) {

        CurrentBalanace balance = new CurrentBalanace();

        SQLiteDatabase db = getWritableDatabase();
/*
        String query = "SELECT * FROM " + TABLE_CURRENTBALANCE + " ORDER BY " + CURRENTBALANCE_ID + " DESC WHERE "
                + POCKET_ID + " = ?";

        //Cursor points to the results
        Cursor c = db.rawQuery(query, new String[]{"" + pocketId});

        String query = "SELECT * FROM " + TABLE_CURRENTBALANCE + " WHERE " + POCKET_ID + " = ?" +
                " ORDER BY " + CURRENTBALANCE_ID + " DESC LIMIT 1";

        //Cursor points to the results
        Cursor c = db.rawQuery(query, new String[]{"" + pocketId});
*/
        String[] resultColumns = new String[] {CURRENTBALANCE_ID, KEY_AMOUNT, KEY_DATE, POCKET_ID};
        String where = POCKET_ID + " = " + pocketId;
        String[] whereArgs = null;
        String groupBy = null;
        String having = null;
        String orderBy = CURRENTBALANCE_ID + " DESC";
        String limit = "1";
        //Cursor c = db.query(TABLE_CURRENTBALANCE, resultColumns, where, whereArgs, groupBy, having, orderBy, limit);
        Cursor c= db.query(false, TABLE_CURRENTBALANCE, null, where, null, null, null, orderBy, limit);

        c.moveToFirst();

        while (!c.isAfterLast()) {

            balance.setCurrentBalanaceId(c.getInt(c.getColumnIndex(CURRENTBALANCE_ID)));
            balance.setAmount(c.getInt(c.getColumnIndex(KEY_AMOUNT)));
            balance.setDate(c.getString(c.getColumnIndex(KEY_DATE)));
            balance.setPocketId(c.getInt(c.getColumnIndex(POCKET_ID)));

            Log.d(TAG, "CurrentBalanceID:" + balance.getCurrentBalanaceId() + " Amount:" + balance.getAmount());

            c.moveToNext();

        }

        db.close();

        Log.d(TAG, "Latest Row Fetched in TABLE_CURRENTBALANCE");

        return balance;

    }

    /*
    EXPENSE TABLE CRUD OPERATION
     */

    // Insert a Row in EXPENSE TABLE
    public void addExpense(Expense expense) {

        ContentValues values = new ContentValues();

        values.put(KEY_NAME, expense.getName());
        values.put(KEY_AMOUNT, expense.getAmount());
        values.put(EXPENSE_DESCRIPTION, expense.getDescription());
        values.put(EXPENSE_CATEGORY, expense.getDescription());
        values.put(KEY_DATE, expense.getDate());
        values.put(EXPENSE_MONTH, expense.getMonth());
        values.put(EXPENSE_YEAR, expense.getYear());
        values.put(POCKET_ID, expense.getPocketId());

        SQLiteDatabase db = getWritableDatabase();

        db.insert(TABLE_EXPENSE, null, values);
        Log.d(TAG, "Row Inserted in TABLE_EXPENSE");

        db.close();
    }

    // Display Latest Expense
    public Expense getLatestExpense(int pocketId) {

        Expense expense = new Expense();

        SQLiteDatabase db = getWritableDatabase();
/*
        String query = "SELECT * FROM " + TABLE_EXPENSE + " ORDER BY " + EXPENSE_ID + " WHERE "
                + POCKET_ID + " = ?";

        //Cursor points to the results
        Cursor c = db.rawQuery(query, new String[]{"" + pocketId});
*/
        String[] resultColumns = new String[] {EXPENSE_ID, KEY_NAME, KEY_AMOUNT, EXPENSE_DESCRIPTION, EXPENSE_CATEGORY, KEY_DATE, EXPENSE_MONTH, EXPENSE_YEAR, POCKET_ID};
        String where = POCKET_ID + " = ?";
        String[] whereArgs = new String[] {"" + pocketId};
        String groupBy = null;
        String having = null;
        String orderBy = EXPENSE_ID + " DESC";
        String limit = "1";
        Cursor c = db.query(TABLE_EXPENSE, resultColumns, where, whereArgs, groupBy, having, orderBy, limit);

        c.moveToFirst();

        while (!c.isAfterLast()) {

            expense.setExpenseId(c.getInt(c.getColumnIndex(EXPENSE_ID)));
            expense.setName(c.getString(c.getColumnIndex(KEY_NAME)));
            expense.setAmount(c.getInt(c.getColumnIndex(KEY_AMOUNT)));
            expense.setDescription(c.getString(c.getColumnIndex(EXPENSE_DESCRIPTION)));
            expense.setCategory(c.getString(c.getColumnIndex(EXPENSE_CATEGORY)));
            expense.setDate(c.getString(c.getColumnIndex(KEY_DATE)));
            expense.setMonth(c.getString(c.getColumnIndex(EXPENSE_MONTH)));
            expense.setYear(c.getInt(c.getColumnIndex(EXPENSE_YEAR)));
            expense.setPocketId(c.getInt(c.getColumnIndex(POCKET_ID)));

            Log.d(TAG, "ExpenseID:" + expense.getExpenseId() + " Amount:" + expense.getAmount());

            c.moveToNext();

        }

        db.close();

        Log.d(TAG, "Latest Row Fetched in TABLE_EXPENSE");

        return expense;

    }

    // Display Expense of a particular Month
    public Expense[] getExpenseByMonth(Expense ex) {

        Expense[] expense = null;

        SQLiteDatabase db = getWritableDatabase();

        int i = 0;
        String query = "SELECT * FROM " + TABLE_EXPENSE + " WHERE " + EXPENSE_MONTH + " = \'" + ex.getMonth() + "\'";

        //Cursor points to the results
        Cursor c = db.rawQuery(query, null);

        c.moveToFirst();

        while (!c.isAfterLast()) {

            expense[i].setExpenseId(c.getInt(c.getColumnIndex(EXPENSE_ID)));
            expense[i].setName(c.getString(c.getColumnIndex(KEY_NAME)));
            expense[i].setAmount(c.getInt(c.getColumnIndex(KEY_AMOUNT)));
            expense[i].setDescription(c.getString(c.getColumnIndex(EXPENSE_DESCRIPTION)));
            expense[i].setCategory(c.getString(c.getColumnIndex(EXPENSE_CATEGORY)));
            expense[i].setDate(c.getString(c.getColumnIndex(KEY_DATE)));
            expense[i].setMonth(c.getString(c.getColumnIndex(EXPENSE_MONTH)));
            expense[i].setYear(c.getInt(c.getColumnIndex(EXPENSE_YEAR)));
            expense[i].setPocketId(c.getInt(c.getColumnIndex(POCKET_ID)));

            i++;
            c.moveToNext();

        }

        db.close();

        Log.d(TAG, "Month Row Fetched in TABLE_EXPENSE");

        return expense;

    }

    // Display Expense of a particular Year
    public Expense[] getExpenseByYear(Expense ex) {

        Expense[] expense = null;

        SQLiteDatabase db = getWritableDatabase();

        int i = 0;
        String query = "SELECT * FROM " + TABLE_EXPENSE + " WHERE " + EXPENSE_YEAR + " = " + ex.getYear();

        //Cursor points to the results
        Cursor c = db.rawQuery(query, null);

        c.moveToFirst();

        while (!c.isAfterLast()) {

            expense[i].setExpenseId(c.getInt(c.getColumnIndex(EXPENSE_ID)));
            expense[i].setName(c.getString(c.getColumnIndex(KEY_NAME)));
            expense[i].setAmount(c.getInt(c.getColumnIndex(KEY_AMOUNT)));
            expense[i].setDescription(c.getString(c.getColumnIndex(EXPENSE_DESCRIPTION)));
            expense[i].setCategory(c.getString(c.getColumnIndex(EXPENSE_CATEGORY)));
            expense[i].setDate(c.getString(c.getColumnIndex(KEY_DATE)));
            expense[i].setMonth(c.getString(c.getColumnIndex(EXPENSE_MONTH)));
            expense[i].setYear(c.getInt(c.getColumnIndex(EXPENSE_YEAR)));
            expense[i].setPocketId(c.getInt(c.getColumnIndex(POCKET_ID)));

            i++;
            c.moveToNext();

        }

        db.close();

        Log.d(TAG, "Year Row Fetched in TABLE_EXPENSE");

        return expense;

    }

    // Display All Expense
    public Expense[] getAllExpense(int pocketId) {

        Expense[] expense = null;

        SQLiteDatabase db = getWritableDatabase();

        int i = 0;
        /*
        String query = "SELECT * FROM " + TABLE_EXPENSE + " ORDER BY " + EXPENSE_ID + " DESC WHERE "
                + POCKET_ID + " = " + pocketId;

        //Cursor points to the results
        Cursor c = db.rawQuery(query, null);
*/
        String[] resultColumns = new String[] {EXPENSE_ID, KEY_NAME, KEY_AMOUNT, EXPENSE_DESCRIPTION, EXPENSE_CATEGORY, KEY_DATE, EXPENSE_MONTH, EXPENSE_YEAR, POCKET_ID};
        String where = POCKET_ID + " = ?";
        String[] whereArgs = new String[] {"" + pocketId};
        String groupBy = null;
        String having = null;
        String orderBy = EXPENSE_ID + " DESC";
        String limit = null;
        Cursor c = db.query(TABLE_EXPENSE, resultColumns, where, whereArgs, groupBy, having, orderBy, limit);

        c.moveToFirst();

        while (!c.isAfterLast()) {

            expense[i].setExpenseId(c.getInt(c.getColumnIndex(EXPENSE_ID)));
            expense[i].setName(c.getString(c.getColumnIndex(KEY_NAME)));
            expense[i].setAmount(c.getInt(c.getColumnIndex(KEY_AMOUNT)));
            expense[i].setDescription(c.getString(c.getColumnIndex(EXPENSE_DESCRIPTION)));
            expense[i].setCategory(c.getString(c.getColumnIndex(EXPENSE_CATEGORY)));
            expense[i].setDate(c.getString(c.getColumnIndex(KEY_DATE)));
            expense[i].setMonth(c.getString(c.getColumnIndex(EXPENSE_MONTH)));
            expense[i].setYear(c.getInt(c.getColumnIndex(EXPENSE_YEAR)));
            expense[i].setPocketId(c.getInt(c.getColumnIndex(POCKET_ID)));

            i++;
            c.moveToNext();

        }

        db.close();

        Log.d(TAG, "All Row Fetched in TABLE_EXPENSE");

        return expense;

    }

}
















