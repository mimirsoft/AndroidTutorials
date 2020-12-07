package com.mimirsoft.android5tutorial06;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/* this tutorial continues from Tutorial 5, this time we override and invoke
/ a custom adapter class, use View recycling AND we add the ViewHolder and explain what it is
/ and why we will be using it.  The ViewHolder was mentioned in Tutorial05 in the comments at
/ the bottom but was not used.
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

            //we declare our holder
            ViewHolder holder;
            //RECYCLING IS GOOD!!!!
            //we call an if statement on our view that is passed in,
            //to see if it has been recycled or not.  if it has been recycled,
            //then it already exists and we do not need to call the inflater function
            //this saves us A HUGE AMOUNT OF RESOURCES AND PROCESSING
            //this is the proper way to do it
            if (convertView==null) {
                LayoutInflater inflater=getLayoutInflater();
                convertView=inflater.inflate(R.layout.custom_list_item, null);

                //here is something new.  we are using a class called a view holder
                holder=new ViewHolder();
                //we are using that class to cache the result of the findViewById function
                //the findViewById function is another place we can speed up our scrolling
                //findViewById does DOM parsing, and that can be very slow
                holder.note = (TextView) convertView.findViewById(R.id.noteText);
                //which we then store in a tag on the view
                convertView.setTag(holder);
            }
            else
            {
                holder=(ViewHolder)convertView.getTag();
            }

            //now we don't have find theviewsbyid to set their contents.  This could get very
            //resource expensive if each row had multiple views
            //instead, the viewholder holds the views for us, already inflated and ready to use
            holder.note.setText(noteList.get(position));
            return(convertView);

        }
    }

    //this is new, we need this for our Viewholder technique on line 81
    //the viewholder object stores eachs of the component views inside the tag field
    //of the layout, so they can be immediately accessed.  In this case, we only have a textview
    class ViewHolder {
        public TextView note=null;
    }


}
