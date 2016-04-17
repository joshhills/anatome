package io.wellbeings.anatome;

/**
 * Created by Callum on 26/02/16.
 */

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.params.BasicHttpParams;

public class DatabaseUtility {

    String myJSON;
    private static final String TAG_RESULTS = "result";
    private static final String TAG_DATE = "App_Date";
    private static final String TAG_TIME = "App_Time";
    JSONArray array = null;
    TextView dateView;
    TextView timeView;


     void addToDb(final List<NameValuePair> data, final String param){
        class ExecutePost extends AsyncTask<String, Void, String> {


            @Override
            protected String doInBackground(String... params) {


                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://team9.esy.es/connect.php?type=" + param.toLowerCase());
                    httpPost.setEntity(new UrlEncodedFormEntity(data));

                    HttpResponse response = httpClient.execute(httpPost);

                    HttpEntity entity = response.getEntity();


                } catch (ClientProtocolException e) {

                } catch (IOException e) {

                }
                return "success";
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
            }
        }
        ExecutePost s = new ExecutePost();
        s.execute();
    }


    /*JOSH LOOK HERE*/

    //call this from other class passing in textview(i know this is probably a bad idea but i dont understand the alternative)
    public void grabApp(final String choice, final TextView datetextView, final TextView timetextView, Context ctx) {

        //store text views as globals for use in the aysnc
        this.dateView = datetextView;
        this.timeView = timetextView;

        //call aysnc
        getData(choice, ctx);


    }

    //this is task where choice for example "App" would be used to get appointments
    public void getData(final String choice, final Context con) {
        class GetDataJSON extends AsyncTask<String, Void, String>{
            final Context ctx = con;

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


            //this would be called from onPostExecute it is used to split up the JSON and set the text fields
            //would be better in the calling class

          /*  public void Appointmentshow() {

                try {
                    JSONObject jsonObj = new JSONObject(myJSON);
                    array = jsonObj.getJSONArray(TAG_RESULTS);
                    HashMap<String, String> appointments = new HashMap<>();
                    for (int i = 0; i < 2; i++) {
                        JSONObject c = array.getJSONObject(i);
                        String id = c.getString(TAG_DATE);
                        String name = c.getString(TAG_TIME);



                        appointments.put(TAG_DATE, id);
                        appointments.put(TAG_TIME, name);
                    }

                    dateView.setText(appointments.get(TAG_DATE).toString());
                    timeView.setText(appointments.get(TAG_TIME).toString());


                } catch (Exception e) {
                    e.printStackTrace();
                }
            } */

            @Override
            protected void onPostExecute(String result) {
                myJSON = result;
               //here i would like to pass the result back to the calling class but i cant get that to work...
                //any ideas?
            }
        }

        //this executes the whole request
        GetDataJSON g = new GetDataJSON();
        try{
        g.execute().get();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }



}
