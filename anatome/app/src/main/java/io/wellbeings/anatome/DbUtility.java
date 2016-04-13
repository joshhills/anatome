package io.wellbeings.anatome;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ImageView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.params.BasicHttpParams;

/**
 * Created by Callum on 13/04/16.
 */
public class DbUtility {
    public Map<String, String> mapping;
    String myJSON;
    JSONObject finalJSON;


    public void getUserComments(Context ctx) {
        String choice = "comm";
        Context con = ctx;

        GetDataJSON task = new GetDataJSON(choice, con);

        try {
            //wait for task to finish
            task.execute().get();
            //this fails
            System.out.println(finalJSON.toString());
        }catch(Exception e) {
            e.printStackTrace();
        }

    }




        class GetDataJSON extends AsyncTask<String, Void, String> {
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
                myJSON = result;
                //here i would like to pass the result back to the calling class but i cant get that to work...
                //any ideas?
                try {
                    //save to global
                    finalJSON = new JSONObject(myJSON);
                }catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }



}
