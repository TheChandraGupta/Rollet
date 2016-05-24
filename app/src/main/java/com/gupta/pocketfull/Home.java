package com.gupta.pocketfull;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.gupta.pocketfull.bean.CurrentBalanace;
import com.gupta.pocketfull.database.DBHandler;
import com.gupta.pocketfull.bean.Expense;
import com.gupta.pocketfull.bean.Pockets;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView currentBalance;
    TextView latestExpense;
    Pockets pocket;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        currentBalance = (TextView) findViewById(R.id.current_balance_amount);
        latestExpense = (TextView) findViewById(R.id.last_expense_single_amount);

        Intent login = getIntent();
        bundle = login.getExtras();

        pocket = new Pockets();

        pocket.setPocketId(bundle.getInt("ID"));
        pocket.setName(bundle.getString("NAME"));
        pocket.setDate(bundle.getString("DATE"));
        pocket.setLastResetDate(bundle.getString("RESETDATE"));

        TextView pocket_name_navbar = (TextView)findViewById(R.id.pocket_name_navbar);
        pocket_name_navbar.setText("" + pocket.getName());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.addnewexpensefab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                Intent popup = new Intent(Home.this, AddExpensePopUp.class);
                popup.putExtras(bundle);
                startActivity(popup);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        DBHandler db = new DBHandler(this, null ,null, 1);
        CurrentBalanace current = new CurrentBalanace();
        Expense expense = new Expense();

        current = db.getLatestCurrentBalance(pocket.getPocketId());
        expense = db.getLatestExpense(pocket.getPocketId());

        Log.d("HOME.JAVA", "Current="+current.getAmount()+" Expense="+expense.getAmount());

        currentBalance.setText("Rs. " + current.getAmount());
        latestExpense.setText("Rs. " + expense.getAmount());

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
*/
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {
            Intent intent = new Intent(getApplicationContext(), Home.class);
            intent.putExtras(bundle);
            startActivity(intent);
        } else if (id == R.id.add_balance) {
            Intent intent = new Intent(getApplicationContext(), AddBalance.class);
            intent.putExtras(bundle);
            startActivity(intent);
        } else if (id == R.id.history) {
            Intent intent = new Intent(getApplicationContext(), BalanceHistory.class);
            intent.putExtras(bundle);
            startActivity(intent);
        } else if (id == R.id.expenses) {
            Intent intent = new Intent(getApplicationContext(), Expenses.class);
            intent.putExtras(bundle);
            startActivity(intent);
        } else if (id == R.id.setting) {
            Intent intent = new Intent(getApplicationContext(), Settings.class);
            intent.putExtras(bundle);
            startActivity(intent);
        } else if (id == R.id.logout) {
            Intent intent = new Intent(getApplicationContext(), Pocket.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
