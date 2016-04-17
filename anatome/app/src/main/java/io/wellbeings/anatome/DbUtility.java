package io.wellbeings.anatome;

import android.content.Context;
import android.os.AsyncTask;

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
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import cz.msebera.android.httpclient.params.BasicHttpParams;

/**
 * Created by Callum on 13/04/16.
 */

public class DbUtility{

    private static final String TAG_RESULTS = "result";
    private static final String TAG_DATE = "App_Date";
    private static final String TAG_TIME = "App_Time";
    private static final String TAG_COM = "Com_Text";
    private static final String TAG_COM_NAME = "Student_Name";
    JSONArray array = null;

    public void addAppointment(String time, String date, Context ctx) {
        String param = "app";

        List<NameValuePair> data = new ArrayList<>();
        data.add(new BasicNameValuePair("time", time));
        data.add(new BasicNameValuePair("date", date));
        data.add(new BasicNameValuePair("name", UtilityManager.getUserUtility(ctx).getName()));
        data.add(new BasicNameValuePair("email", UtilityManager.getUserUtility(ctx).getEmail()));

        addToDb(data, param);
    }

    public void addComment(String text, String area, Context ctx) {
        String param = "comment";

        List<NameValuePair> data = new ArrayList<>();
        data.add(new BasicNameValuePair("text", text));
        data.add(new BasicNameValuePair("date", area));
        data.add(new BasicNameValuePair("name", UtilityManager.getUserUtility(ctx).getName()));

        addToDb(data, param);
    }

    public HashMap<String, String> getAppointment(Context ctx) {
        String result;
        HashMap<String, String> appointments;

        try {
            GetDataJSON g = new GetDataJSON("app", "none", ctx);
            result = g.execute().get();
            appointments = parseAppointment(result);

            return appointments;

        }catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<HashMap<String, String>> getComments(String area, Context ctx) {
        String result;
        ArrayList<HashMap<String, String>> commentList;

        try {
            GetDataJSON g = new GetDataJSON("comment", "liver", ctx);
            result = g.execute().get();

            commentList = parseComment(result);

            return commentList;

        }catch(Exception e) {
            e.printStackTrace();
        }


        return null;
    }

    public ArrayList<HashMap<String, String>> parseComment(String result) {
        ArrayList<HashMap<String, String>> commentList = new ArrayList<HashMap<String, String>>();
        try {
            JSONObject jsonObj = new JSONObject(result);
            array = jsonObj.getJSONArray(TAG_RESULTS);

            for (int i = 0; i < array.length(); i++) {
                JSONObject c = array.getJSONObject(i);
                String name = c.getString(TAG_COM_NAME);
                String comment = c.getString(TAG_COM);

                HashMap<String, String> comments = new HashMap<>();

                comments.put(TAG_COM_NAME, name);
                comments.put(TAG_COM, comment);
                commentList.add(comments);
            }
            return commentList;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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

    public class GetDataJSON extends AsyncTask<String, Void, String> {
            private String choice;
            private Context ctx;
            private String area;

            public GetDataJSON(String choice, String area, Context ctx) {
                this.choice = choice;
                this.ctx = ctx;
                this.area = area;

            }

            //sets up http request to the php files
            //all this works correctly
            @Override
            protected String doInBackground(String... params) {


                DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());

                HttpPost httppost = new HttpPost("http://team9.esy.es/testing.php?type=" + choice.toLowerCase() + "&email=" + UtilityManager.getUserUtility(ctx).getEmail() + "&area=" + area);

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
