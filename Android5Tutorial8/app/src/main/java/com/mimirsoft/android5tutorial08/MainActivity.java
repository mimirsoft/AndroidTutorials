package com.mimirsoft.android5tutorial08;

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

import static com.mimirsoft.android5tutorial08.R.id.myEditText;

/* this tutorial continues from Tutorial 5, 6, and 7. This time we override and invoke
/ a custom adapter class, use View recycling AND we use the ViewHolder
**
**But most importantly I this Tutorial use all that to build a list.  An actual useful activity,
* in its most basic form.
*/


public class MainActivity extends AppCompatActivity {

    //we need this as class variable now
    ArrayList<String> noteList = new ArrayList<String>();
    //this is our new adapter
    //It is also a class variable
    FancyAdapter aa=null;

    //this is how we tell if the list entry is new or not
    int list_index = -1;

    EditText myEditText;


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
            //add the note, if it has an index of 0 or higher, it is an existing row
            if(MainActivity.this.list_index > -1) {
                noteList.set(MainActivity.this.list_index, myEditText.getText().toString());
            }
            else {
                //if our list_index is -1, it is a new row
                noteList.add(0, myEditText.getText().toString());
            }
            //update the view
            aa.notifyDataSetChanged();
            //set the index back to 0;
            MainActivity.this.list_index = -1;
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
                convertView=inflater.inflate(R.layout.custom_list_item, parent, false);

                //We are using a class called a ViewHolder, this time, we pass it our convertView
                // View object
                holder=new ViewHolder(convertView);
                //we are using that class to cache the result of the findViewById function
                //the findViewById function is another place we can speed up our scrolling
                //findViewById does DOM parsing, and that can be very slow

                //which we then store in a tag on the view
                convertView.setTag(holder);

                holder.note.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = (Integer) v.getTag();
                        MainActivity.this.list_index = position;
                        myEditText = (EditText) findViewById(R.id.myEditText);
                        myEditText.setText(noteList.get(position));

                    }
                });


            }
            else
            {
                holder=(ViewHolder)convertView.getTag();
            }

            //now we don't have find theviewsbyid to set their contents.  This could get very
            //resource expensive if each row had multiple views
            //instead, the viewholder holds the views for us, already inflated and ready to use
            //
            holder.note.setTag(position);
            //holder.layoutRow.setTag(position);
            holder.position = position;

            //we now use the holder class to set our data
            holder.populateFrom(noteList.get(position));
            return(convertView);

        }
    }

    //This is our ViewHolder class
    //The viewholder object stores each of the component views inside the tag field
    //of the layout, so they can be immediately accessed.  In this case, we only have a textview

    //this version differs from Tutorial 6 as we now have a constructor, and pass it the view,
    //this abstracts the ViewHolder, makes the code neater
    static class ViewHolder {
        public TextView note=null;
        public int position;

        ViewHolder(View row) {
            this.note=(TextView)row.findViewById(R.id.noteText);
        }

        void populateFrom(String r) {
            note.setText(r);
        }

    }


}
