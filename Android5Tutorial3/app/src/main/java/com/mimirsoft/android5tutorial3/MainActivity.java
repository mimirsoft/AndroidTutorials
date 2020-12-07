package com.mimirsoft.android5tutorial3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //this is our ListView element, obtained by id from our XML layout
        ListView myListView = (ListView)findViewById(R.id.myListView);
        //this is our EditText element, obtained by id from our XML layout.
        final EditText myEditText = (EditText)findViewById(R.id.myEditText);

        //we declare and create our array list
        final ArrayList<String> noteList = new ArrayList<String>();
        //we declare our array adapter.  an adapter is a class that binds data to views
        final ArrayAdapter<String> aa;

        // in this case, we are binding an array of strings -  noteList, to the view simple_list_item_1
        // which is a built in view.
        aa = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,noteList);

        // here we set the adapter, this turns it on
        myListView.setAdapter(aa);

        //here is the button
        Button btnSimple = (Button) findViewById(R.id.btnSimple);
        //listen for the click
        btnSimple.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //add the note
                noteList.add(0, myEditText.getText().toString());
                //update the view
                aa.notifyDataSetChanged();
                //erase the text so we can add another note
                myEditText.setText("");
            }
        });
    }
}
