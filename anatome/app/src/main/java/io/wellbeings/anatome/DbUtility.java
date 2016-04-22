package io.wellbeings.anatome;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;

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
 * Black-box into database PHP API provides
 * dynamic functionality across application.
 */

public class DbUtility implements Utility {

    // Log status of utility.
    protected STATUS utilityStatus;

    // Store application context for access to local preferences.
    private static Context ctx;

    // Store useful functional descriptors.
    private static final String TAG_RESULTS = "result";
    private static final String TAG_DATE = "App_Date";
    private static final String TAG_TIME = "App_Time";
    private static final String TAG_COM = "Com_Text";
    private static final String TAG_COM_NAME = "Student_Name";
    private static final String TAG_LAT_LONG = "LatLong";
    JSONArray array = null;

    public DbUtility(Context ctx){

        // Store reference to context.
        this.ctx = ctx;

        // Attempt to start to set-up the utility using the arguments provided.
        try {
            utilityStatus = initialize();
        } catch (Exception e) {
            utilityStatus = STATUS.FAIL;
        }

    }

    @Override
    public STATUS initialize() throws IOException {
        return STATUS.SUCCESS;
    }

    @Override
    public STATUS getState() {
        return utilityStatus;
    }

    @Override
    public STATUS shutdown() {
        return null;
    }

    public boolean isConnectedToInternet(){
        ConnectivityManager connection = (ConnectivityManager)ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connection != null)
        {
            NetworkInfo[] netData = connection.getAllNetworkInfo();
            if (netData != null)
                for (int i = 0; i < netData.length; i++)
                    if (netData[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }

        }
        return false;
    }


    /**
     * Register an appointment on the
     * organization's system.
     *
     * @param time  The time of the appointment.
     * @param date  The date of the appointment.
     */
    public void addAppointment(String time, String date, String pref) throws NetworkException {

        if(isConnectedToInternet()) {
            // Select function.
            String param = "app";

            // Build dataset to pass to API call.
            List<NameValuePair> data = new ArrayList<>();
            data.add(new BasicNameValuePair("time", time));
            data.add(new BasicNameValuePair("date", date));
            data.add(new BasicNameValuePair("pref", pref));
            data.add(new BasicNameValuePair("name", UtilityManager.getUserUtility(ctx).getName()));
            data.add(new BasicNameValuePair("email", UtilityManager.getUserUtility(ctx).getEmail()));

            addToDb(data, param);

            param = "update";

            addToDb(data, param);
        }
        else {
            throw new NetworkException("No Connection: Check your network settings and try again.");
        }
    }

    /**
     * Store social comments in database submitted
     * through application.
     *
     * @param text      The content of the comment.
     * @param section   The section it is relevant to.
     */
    public void addComment(String text, String name, String section) throws NetworkException{

        if(isConnectedToInternet()){
            // Select function.
            String param = "comment";

            // Build dataset to pass to API call.
            List<NameValuePair> data = new ArrayList<>();
            data.add(new BasicNameValuePair("text", text));
            data.add(new BasicNameValuePair("name", name));
            data.add(new BasicNameValuePair("area", section));

            addToDb(data, param);
        }
        else {
            throw new NetworkException("No Connection: Check your network settings and try again.");
        }
    }

    /**
     * Prepopulate database content.
     *
     * @param dates A set of relevant dates.
     */
    public void addAppointmentsToDate (String[] dates) {
        String[] times = {"09:00", "09:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30",
                "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30"};

        for(int i = 0; i < dates.length; i++) {
            for(int j = 0; j < times.length; j++) {
                List<NameValuePair> data = new ArrayList<>();
                data.add(new BasicNameValuePair("date", dates[i]));
                data.add(new BasicNameValuePair("time", times[j]));

                addToDb(data, "fill");
            }
        }
    }

    /**
     *
     * @return
     */
    public HashMap<String, String> getAppointment() throws NetworkException {
        String result;
        HashMap<String, String> appointments;

        if(isConnectedToInternet()) {
            try {
                GetDataJSON g = new GetDataJSON("app", "none");
                result = g.execute().get();
                appointments = parseAppointment(result);

                return appointments;

            }catch(Exception e) {
                e.printStackTrace();
            }
        }
        else {
            throw new NetworkException("No Connection: Check your network settings and try again.");
        }

        return null;
    }

    public String[] getAvailable(String date) throws NetworkException{
        String result;
        String [] available;

        if(isConnectedToInternet()) {
            try {
                GetDataJSON g = new GetDataJSON("available", date);
                result = g.execute().get();
                available = parseAvailable(result);
                return available;
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        else {
            throw new NetworkException("No Connection: Check your network settings and try again.");
        }
        return null;
    }

    public HashMap<String, String> getComments(String area) throws NetworkException{
        String result;
        ArrayList<HashMap<String, String>> commentList;

        if(isConnectedToInternet()) {
            try {
                GetDataJSON g = new GetDataJSON("comment", area);
                result = g.execute().get();

                return parseComment(result);

            }catch(Exception e) {
                e.printStackTrace();
            }
        }
        else {
            throw new NetworkException("No Connection: Check your network settings and try again.");
        }
        return null;
    }

    public String getOrgName() throws NetworkException{
        String result;


        if(isConnectedToInternet()) {
            try {
                GetDataJSON g = new GetDataJSON("orgname", "none");
                result = g.execute().get();

                JSONObject jsonObj = new JSONObject(result);
                array = jsonObj.getJSONArray(TAG_RESULTS);

                String orgName = array.getJSONObject(0).getString("Name");

                return orgName;

            }catch(Exception e) {
                e.printStackTrace();
            }
        }
        else {
            throw new NetworkException("No Connection: Check your network settings and try again.");
        }

        return null;
    }

    public String getOrgDescription() throws NetworkException{
        String result;

        if(isConnectedToInternet()) {
            try {
                GetDataJSON g = new GetDataJSON("orgdesc", "none");
                result = g.execute().get();

                JSONObject jsonObj = new JSONObject(result);
                array = jsonObj.getJSONArray(TAG_RESULTS);

                String orgName = array.getJSONObject(0).getString("Description");

                return orgName;

            }catch(Exception e) {
                e.printStackTrace();
            }
        }
        else {
            throw new NetworkException("No Connection: Check your network settings and try again.");
        }

        return null;
    }

    public LatLng getLatLong() throws NetworkException{
        String result;
        String[] latLong;

        if(isConnectedToInternet()) {
            try {
                GetDataJSON g = new GetDataJSON("org", "none");
                result = g.execute().get();

                latLong = parseLatLong(result);

                return new LatLng(Double.parseDouble(latLong[0]),
                        Double.parseDouble(latLong[1]));

            }catch(Exception e) {
                e.printStackTrace();
            }
        }
        else {
            throw new NetworkException("No Connection: Check your network settings and try again.");
        }

        return null;
    }

    public HashMap<String, String> parseComment(String result) {
        try {
            JSONObject jsonObj = new JSONObject(result);
            array = jsonObj.getJSONArray(TAG_RESULTS);
            HashMap<String, String> comments = new HashMap<>();

            for (int i = 0; i < array.length(); i++) {
                JSONObject c = array.getJSONObject(i);
                String name = c.getString(TAG_COM_NAME);
                String comment = c.getString(TAG_COM);

                comments.put(comment, name);
            }
            return comments;

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

    public String[] parseAvailable(String result) {
        try {
            JSONObject jsonObj = new JSONObject(result);
            array = jsonObj.getJSONArray(TAG_RESULTS);
            String [] available = new String[array.length()];
            for (int i = 0; i < array.length(); i++) {
                JSONObject c = array.getJSONObject(i);
                String time = c.getString(TAG_TIME);

                available[i] = time;
            }

            return available;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String[] parseLatLong(String result) {
        String[] latLong = new String[2];

        try{
            JSONObject jsonObj = new JSONObject(result);
            array = jsonObj.getJSONArray(TAG_RESULTS);
            for (int i = 0; i < array.length(); i++) {
                JSONObject c = array.getJSONObject(i);
                String temp = c.getString(TAG_LAT_LONG);

                String lat = temp.substring( 0, temp.indexOf(","));
                String llong = temp.substring(temp.indexOf(",")+1, temp.length());
                latLong[0] = lat;
                latLong[1] = llong;

                return latLong;
            }
        }catch (Exception e) {
            e.printStackTrace();
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

            public GetDataJSON(String choice, String area) {
                this.choice = choice;
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

        }

}
