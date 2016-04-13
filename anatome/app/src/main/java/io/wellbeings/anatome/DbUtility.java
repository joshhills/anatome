package io.wellbeings.anatome;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.params.BasicHttpParams;

/**
 * Created by Callum on 13/04/16.
 */

public class DbUtility implements AsyncResponse{


    String myJSON;
    private static final String TAG_RESULTS = "result";
    private static final String TAG_DATE = "App_Date";
    private static final String TAG_TIME = "App_Time";
    HashMap<String, String> finalMap = new HashMap<>();
    JSONArray array = null;



    public void getAppointment(Context ctx) {
        String choice = "app";
        Context con = ctx;

        GetDataJSON aysncTask = new GetDataJSON(choice, con);
        //aysncTask.delegate = this;
        try {
            //wait for task to finish
            aysncTask.execute();
        }catch(Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void processFinish(String output) {
        System.out.println(output);
        parseAppointment(output);
    }

    public HashMap<String, String> parseAppointment(String result) {
        try {
            JSONObject jsonObj = new JSONObject(result);
            array = jsonObj.getJSONArray(TAG_RESULTS);
            HashMap<String, String> appointments = new HashMap<>();
            for (int i = 0; i < 2; i++) {
                JSONObject c = array.getJSONObject(i);
                String id = c.getString(TAG_DATE);
                String name = c.getString(TAG_TIME);

                appointments.put(TAG_DATE, id);
                appointments.put(TAG_TIME, name);

                return appointments;
            }


        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public HashMap<String, String> getFinalMap() {
        return finalMap;
    }

    public class GetDataJSON extends AsyncTask<String, Void, String> {
           // public AsyncResponse delegate = null;
            private String choice;
            private Context ctx;

            public GetDataJSON(String choice, Context ctx) {
                this.choice = choice;
                this.ctx = ctx;

            }

            //sets up http request to the php files
            //all this works correctly
            @Override
            protected String doInBackground(String... params) {


                DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());

                HttpPost httppost = new HttpPost("http://team9.esy.es/testing.php?type=" + choice.toLowerCase() + "&email=" + UtilityManager.getUserUtility(ctx).getEmail());

                // Depends on your web service
                httppost.setHeader("Content-type", "application/json");

                InputStream inputStream = null;
                String result = null;
                try {
                    HttpResponse response = httpclient.execute(httppost);
                    HttpEntity entity = response.getEntity();

                    inputStream = entity.getContent();
                    // json is UTF-8 by default
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    while ((line = reader.readLine()) != null)
                    {
                        sb.append(line + "\n");
                    }
                    result = sb.toString();
                } catch (Exception e) {
                    // Oops
                }
                finally{
                    try {
                        if (inputStream != null) inputStream.close();
                    } catch (Exception squish) {
                    }
                }
                return result;
            }


            @Override
            protected void onPostExecute(String result) {
                //delegate.processFinish(result);
            }
        }



}
