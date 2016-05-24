package com.gupta.pocketfull;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gupta.pocketfull.database.DBHandler;
import com.gupta.pocketfull.bean.Pockets;

import java.util.ArrayList;

public class Pocket extends AppCompatActivity {

    ArrayList<Pockets> pockets = new ArrayList<Pockets>();
    DBHandler db;
    ListView allPocket;
    boolean flag = false;
    String TAG = "Pocket.Java";
    String msg;
    TextView message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pocket);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        allPocket = (ListView) findViewById(R.id.allpocketlist);
        message = (TextView) findViewById(R.id.message);

        message.setVisibility(View.GONE);

        db = new DBHandler(this, null, null, 1);
        //Pockets pocket = new Pockets();

        pockets = db.getPockets();

        int size = pockets.size();
        int length = 1;

        if(size != 0) {
            length = size;
        }

        final String[] pocketName = new String[length];
        final int[] pocketId = new int[length];
        final String[] date = new String[length];
        final String[] lastResetDate = new String[length];

        Log.d(TAG, "Line - 52, Length="+length+" pockets="+pockets);

        if(pockets != null) {

            int i = 0;

            for (Pockets pocket : pockets) {

                pocketName[i] = pocket.getName();
                pocketId[i] = pocket.getPocketId();
                date[i] = pocket.getDate();
                lastResetDate[i] = pocket.getLastResetDate();
                i++;
            }

        }

        Log.d(TAG, "Line - 67, Length="+length+" pockets="+pockets);

        if(size == 0) {
            Toast.makeText(this, "PoCKETNAME = NULL", Toast.LENGTH_LONG).show();

            Log.d(TAG, "Line - 72, Length=" + length + " pockets=" + pockets);
/*
            pocketName = new String[1];
            pocketName[0] = "No Pocket Found"; */
            msg = "No Pocket Found";
            allPocket.setVisibility(View.GONE);
            message.setText(msg);
            message.setVisibility(View.VISIBLE);
            flag = true;
        }
        else {

            Log.d(TAG, "Line - 80, Length="+length+" pockets="+pockets);

            ListAdapter pocketListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, pocketName);
            allPocket.setAdapter(pocketListAdapter);
        }

        Log.d(TAG, "Line - 86, Length="+length+" pockets="+pockets);

        allPocket.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!flag) {
                    /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();*/
                    Log.d(TAG, "Line - 86, Parent=" + parent + " View=" + view + " Position=" + position + " ID=" + id);
                    Pockets login = new Pockets();
                    login.setPocketId(pocketId[position]);
                    login.setName(pocketName[position]);
                    login.setDate(date[position]);
                    login.setLastResetDate(lastResetDate[position]);

                    Bundle bundle = new Bundle();
                    bundle.putInt("ID", login.getPocketId());
                    bundle.putString("NAME", login.getName());
                    bundle.putString("DATE", login.getDate());
                    bundle.putString("RESETDATE", login.getLastResetDate());

                    Intent intent = new Intent(getApplicationContext(), Home.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PocketAdd.class);
                startActivity(intent);
            }
        });

    }

}
