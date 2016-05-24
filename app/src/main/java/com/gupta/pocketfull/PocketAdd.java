package com.gupta.pocketfull;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.gupta.pocketfull.database.DBHandler;
import com.gupta.pocketfull.bean.Pockets;

public class PocketAdd extends AppCompatActivity {

    Pockets pocket;
    DBHandler db;
    Button add;
    EditText newPocketName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pocket_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = new DBHandler(this, null, null, 1);
        pocket = new Pockets();

        add = (Button) findViewById(R.id.add);
        newPocketName = (EditText) findViewById(R.id.newpocketname);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pocket.setName(newPocketName.getText().toString());
                db.addPockets(pocket);
                Intent intent = new Intent(getApplicationContext(), Pocket.class);
                startActivity(intent);
            }
        });


/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); */
    }

}
