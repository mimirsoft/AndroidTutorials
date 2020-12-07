package com.mimirsoft.android5tutorial05;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
/*
** this tutorial continues from Android 5 Tutorial 3, this time we override and extend
* a custom adapter class FancyAdapter
*
*  A custom adapter is what allows us to later manipulate the contents of lists, such
*  as retrieving them for later use.  We will do that in a later tutorial
*
*  This tutorial also properly implements ListView recycling
*  This is very important for speeding up scrolling in your user interface
*
*/


public class MainActivity extends AppCompatActivity {

    //we need this as class variable now
    ArrayList<String> noteList = new ArrayList<String>();
    //this is our new adapter
    //It is also a class variable
    FancyAdapter aa=null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //this is our ListView element, obtained by id from our XML layout
        ListView myListView = (ListView) findViewById(R.id.myListView);
        //this is our EditText element, obtained by id from our XML layout.
        final EditText myEditText = (EditText) findViewById(R.id.myEditText);


        //we initialize our fancy adapter object, we already declared it above
        //in the class definition
        aa = new FancyAdapter();

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

    //here is where the magic begins
    //we extend our ArrayAdapter, and use it to create custom views
    //as well as execute other more complicated functions
    class FancyAdapter extends ArrayAdapter<String> {
        FancyAdapter() {
            super(MainActivity.this, android.R.layout.simple_list_item_1, noteList);
        }

        public View getView(int position, View convertView,
                            ViewGroup parent) {

            //RECYCLING IS GOOD!!!!
            //we call an if statement on our view that is passed in,
            //to see if it has been recycled or not.  if it has been recycled,
            //then it already exists and we do not need to call the inflater function
            //this saves us A HUGE AMOUNT OF RESOURCES AND PROCESSING
            //this is the proper way to do it
            if (convertView==null) {
                LayoutInflater inflater=getLayoutInflater();
                convertView=inflater.inflate(R.layout.custom_list_item, null);
            }

            //this is not the best way to do this, we will look at a better way in Tutorial 6
            //imagine if we had MULTIPLE textviews per row, and had to call findviewbyID multiple
            //times per row, that would get very slow
            ((TextView)convertView.findViewById(R.id.noteText)).setText(noteList.get(position));

            return(convertView);
        }
    }
}
