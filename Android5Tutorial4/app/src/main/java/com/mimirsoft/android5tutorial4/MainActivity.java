package com.mimirsoft.android5tutorial4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

//This tutorial illustrates how to use a custom list item in your ListView -
/*
*This is the same as Tutorial 3, with only cosmetic differences.
*
*/

public class MainActivity extends AppCompatActivity {

    //we need this as class variable now
    final ArrayList<String> noteList = new ArrayList<String>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //this is our ListView element, obtained by id from our XML layout
        ListView myListView = (ListView)findViewById(R.id.myListView);
        //this is our EditText element, obtained by id from our XML layout.
        final EditText myEditText = (EditText)findViewById(R.id.myEditText);


        //we declare our ArrayAdapter object
        final ArrayAdapter<String> aa;
        //and now we create and initialize it
        //notice we pass it a layout, and an element ID
        aa=new ArrayAdapter<String>(this,
                R.layout.custom_list_item, R.id.noteText, noteList);

        // here we set the adapter, this turns it on
        myListView.setAdapter(aa);

        //here is the button
        Button btnSimple = (Button) findViewById(R.id.btnSimple);
        //listen for the click
        btnSimple.setOnClickListener(new OnClickListener() {
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
