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


package com.mimirsoft.android5tutorial10;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by kmilhoan on 4/17/17.
 */

public class MainActivityAsyncTask extends AsyncTask<String, Void, OurResponse> {

    public MainActivityAsyncResponse delegate = null;

    public static final String TAG = MainActivityAsyncTask.class.getSimpleName();

    @Override
    protected OurResponse doInBackground (String...params) {
        String targetURL = params[0];
        try {
            Log.d(this.TAG, "targetURL= " + targetURL);//this is for debugging
            URL url = new URL(targetURL);
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            c.setRequestProperty("Content-Type",
                    "application/json; charset=utf-8");
            c.setRequestProperty("Accept-Charset", "utf-8");
            try {
                Gson gson = new Gson();
                InputStream in = c.getInputStream();
                Log.d(this.TAG, "getting input stream");//this is for debugging
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                OurResponse serverData = gson.fromJson(reader, OurResponse.class);
                if(serverData == null) {
                    try {
                        String line = "";
                        StringBuilder total = new StringBuilder();
                        reader = new BufferedReader(new InputStreamReader(in));
                        while ((line = reader.readLine()) != null) {
                            total.append(line);
                        }
                        Log.e("raw_login_response", total.toString());
                    } catch (IOException e) {
                        Log.e(getClass().getSimpleName(), "error build string" + e.getMessage());
                    }

                }
                //Log.d(MainActivity.TAG, user.toString());//this is for debugging
                reader.close();
                return serverData;

            } catch (IOException e) {
                Log.e(getClass().getSimpleName(), "Exception parsing JSON,possible URL error " + targetURL, e);
                InputStream in = c.getErrorStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String line = "";
                StringBuilder total = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    total.append(line);
                }
                Log.e(getClass().getSimpleName(), total.toString());
            } finally {
                c.disconnect();
            }
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "ERROR HttpURLConnection failed to " + targetURL, e);
        }
        return null;
    }

    @Override
    protected void onPostExecute(OurResponse result) {
        delegate.processFinish(result);
    }
}
