/*
Android 5 Tutorial 10 - Basic Asyncronous Remote Request

MimirSoft Tutorials  Copyright (C) 2017  Kevin Milhoan, Mimir Software Corporation

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.

Email:mimirsoft@gmail.com

*/



/*
This tutorial covers how to use an asyncronous task to handle a basic remote request.

Our MainActivity has to implement an interface, so that it can receive the call back from our
AsyncTask object.

We will also use GSON class to handle the JSON object that our remote URL returns
to us.

 */

package com.mimirsoft.android5tutorial10;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MainActivityAsyncResponse {

    public static final String URL = "http://tutorial.mimirsoft.com/android5/tutorial10.php";

    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            //Log.d(MainActivity.TAG, "trying task execute");//this is for debugging
            MainActivityAsyncTask ourRequest =  new MainActivityAsyncTask();
            ourRequest.delegate = this;
            ourRequest.execute(this.URL);
            //Log.d(MainActivity.TAG, "execute");//this is for debugging

        } catch (Exception e) {
            Log.e("log_tag", "Error in http connection " + e.toString());
        }
    }


    public void processFinish(OurResponse serverData){
    //Here is where you will receive the result returned from the async class
    //of onPostExecute(result) method.
        if (serverData == null) {
            Log.d(MainActivity.TAG, "null json object returned");
        }else if (serverData != null) {
            FancyAdapter adapter = new FancyAdapter(MainActivity.this, serverData.results);
            ListView listView = (ListView) findViewById(R.id.myListView);
            listView.setAdapter(adapter);
        }
    }


    //here is where the magic begins
    //we extend our ArrayAdapter, and use it to create custom views
    //as well as execute other more complicated functions
    class FancyAdapter extends ArrayAdapter<String> {
        FancyAdapter(Context context, ArrayList<String> items) {
            super(context,  0, items);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            //we declare our holder
            ViewHolder holder;
            //RECYCLING IS GOOD!!!!
            if (convertView==null) {
                LayoutInflater inflater=getLayoutInflater();
                convertView=inflater.inflate(R.layout.custom_list_item, parent, false);
                //We are using a class called a ViewHolder, this time, we pass it our convertView
                // View object
                holder=new ViewHolder(convertView);
                //we are using that class to cache the result of the findViewById function
                //the findViewById function is another place we can speed up our scrolling
                //findViewById does DOM parsing, and that can be very slow
                convertView.setTag(holder);
            }
            else
            {
                holder=(ViewHolder)convertView.getTag();
            }
            //now we don't have find theviewsbyid to set their contents.  This could get very
            //resource expensive if each row had multiple views
            //instead, the viewholder holds the views for us, already inflated and ready to use
            holder.note.setTag(position);
            //we now use the holder class to set our data
            holder.populateFrom(getItem(position));
            return(convertView);
        }
    }

    static class ViewHolder {
        public TextView note=null;

        ViewHolder(View row) {
            this.note=(TextView)row.findViewById(R.id.noteText);
        }

        void populateFrom(String r) {
            note.setText(r);
        }

    }
}
