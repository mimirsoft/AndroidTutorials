package com.mimirsoft.android5tutorial09;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/*

This Tutorial illustrates the basic use of the SharedPreferences class and file.
Here, we will use it to get and store a pair of strings, a login and a password.

The activity starts by checking the app's preferences for a pair of strings, and prepopulates
the text fields with those strings, if any
The user can then edit and save them.  after saving, if the user reopens the app, the strings will
still be there

 */

public class MainActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "myPrefsFile";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //here we get our two text boxes, and our shared preferences object
        final EditText myLoginText = (EditText)findViewById(R.id.myUsername);
        final EditText myPasswordText = (EditText)findViewById(R.id.myPassword);
        final SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        //here, we get our existing settings, if any, and populate the two EditText objects
        myLoginText.setText(settings.getString("login", ""));
        myPasswordText.setText(settings.getString("password", ""));

        //here is our button that does the work
        Button btnSimple = (Button) findViewById(R.id.btnLogin);

        btnSimple.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //here we get our editor object from our settings object
                //the editor is used to save them
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("login", myLoginText.getText().toString());
                editor.putString("password", myPasswordText.getText().toString());
                // must call commit to save what we have just put
                editor.commit();

                //this is just a nice bit of fluff, to flash a message to let the userknow it worked

                TextView messageText = (TextView) findViewById(R.id.message);
                messageText.setText("Preferences Saved!!!");

            }
        });
    }
}
