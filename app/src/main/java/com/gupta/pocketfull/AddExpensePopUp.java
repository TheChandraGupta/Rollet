package com.gupta.pocketfull;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.gupta.pocketfull.bean.CurrentBalanace;
import com.gupta.pocketfull.database.DBHandler;
import com.gupta.pocketfull.bean.Expense;
import com.gupta.pocketfull.bean.Pockets;

/**
 * Created by GUPTA on 26-Apr-16.
 */
public class AddExpensePopUp extends Activity {

    EditText name, amount, description, date;
    String month, yr;
    int pocketId, year;
    Button addExpense;
    Spinner category;
    boolean flag = false;

    Expense expense = new Expense();

    Pockets pocket;
    Bundle bundle;

    CurrentBalanace currentBalanace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_expense_popup);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*0.9),(int)(height*0.7));

        Intent login = getIntent();
        bundle = login.getExtras();

        pocket = new Pockets();

        pocket.setPocketId(bundle.getInt("ID"));
        pocket.setName(bundle.getString("NAME"));
        pocket.setDate(bundle.getString("DATE"));
        pocket.setLastResetDate(bundle.getString("RESETDATE"));

        pocketId = pocket.getPocketId();

        /*
        Get the inputs
         */

        name = (EditText) findViewById(R.id.name);
        amount = (EditText) findViewById(R.id.amount);
        description = (EditText) findViewById(R.id.description);
        category = (Spinner) findViewById(R.id.category);
        date = (EditText) findViewById(R.id.date);
        addExpense = (Button) findViewById(R.id.addexpensebutton);

        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter.createFromResource(this, R.array.category_list,
                android.R.layout.simple_spinner_item);
        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(staticAdapter);

        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                expense.setCategory((String)parent.getItemAtPosition(position));
                flag = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                flag = false;
            }
        });

        addExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                expense.setName(name.getText().toString());
                expense.setDate(date.getText().toString());
                expense.setAmount(Integer.parseInt(amount.getText().toString()));
                expense.setDescription(description.getText().toString());
                //expense.setCategory(category.getText().toString());

                month = "" + expense.getDate().charAt(3) + expense.getDate().charAt(4);
                yr = expense.getDate().substring(6);
                year = Integer.parseInt(yr);

                expense.setMonth(month);
                expense.setYear(year);
                expense.setPocketId(pocketId);

                Log.d("AddExpensePopUp.Java:","Name:" + expense.getName()
                + " Date:" + expense.getDate() + " Amount:" + expense.getAmount() + " Description:" + expense.getDescription()
                + " Category:" + expense.getCategory() + " Flag:" + flag + " Month:" + month + " Yr:" + yr
                + " Year:" + year);

                if (flag) {
                    DBHandler db = new DBHandler(AddExpensePopUp.this, null, null, 1);

                    currentBalanace = db.getLatestCurrentBalance(pocketId);
                    currentBalanace.setPocketId(pocketId);
                    currentBalanace.setAmount(currentBalanace.getAmount() - expense.getAmount());

                    db.addCurrentBalance(currentBalanace);
                    db.addExpense(expense);

                    Toast.makeText(getApplicationContext(), "Expense Added", Toast.LENGTH_SHORT).show();
/*
                    Intent intent = new Intent(getApplicationContext(), Home.class);
                    intent.putExtras(bundle);
                    startActivity(intent);*/
                    //finish();

                }
                else {
                    Toast.makeText(getApplicationContext(), "Please Select Category", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
}
