package com.wyz.appdaigreja.Activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.wyz.appdaigreja.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class PostJsonActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_json);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
         //   return true;
        //}
        return super.onOptionsItemSelected(item);
    }
    //Get data and return a JSONObject
    private JSONObject convertDataToJSON(){
        EditText txtId = (EditText)findViewById(R.id.txtId);
        EditText txtName = (EditText)findViewById(R.id.txtName);
        EditText txtQuantity = (EditText)findViewById(R.id.txtQuantity);

        int _id = Integer.parseInt(txtId.getText().toString());
        String name = txtName.getText().toString();
        int quantity = Integer.parseInt(txtQuantity.getText().toString());

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("_id", _id);
            jsonObject.put("name", name);
            jsonObject.put("quantity", quantity);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
    private InputStream postJSONObject(JSONObject jsonObject, String urlStr){
        InputStream is = null;
        //HttpClient
        HttpClient httpClient = new DefaultHttpClient();
        //Post the JSONObject as a string message
        String msg = jsonObject.toString();
        //Post
        HttpPost httpPost = new HttpPost(urlStr);
        //Create entity
        try {
            HttpEntity entity = new StringEntity(msg);
            //Set the body of the post, set entity
            httpPost.setEntity(entity);
            //Post this message using the http client
            HttpResponse response = httpClient.execute(httpPost);
            //Get the status line
            StatusLine statusLine = response.getStatusLine();
            //Check the status
            int statusCode = statusLine.getStatusCode();
            if(statusCode == 200){
                is = response.getEntity().getContent();
                return is;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    //Read the string content (JSON Message String from the response).
    public String readStringContent(InputStream inputStream){
        //Read characters
        Reader reader = new InputStreamReader(inputStream);
        //Read line
        BufferedReader bufferedReader = new BufferedReader(reader);
        //Keep reading for lines and append them to a String builder
        StringBuilder builder = new StringBuilder();
        while(true){
            try {
                String line = bufferedReader.readLine();
                if(line!=null){
                    builder.append(line);
                }else{
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return builder.toString();
    }
    public String getTheStatus(String content){
        String status =null;
        //It is JSONObject
        try {
            JSONObject jsonObject = new JSONObject(content);
            status = jsonObject.getString("status");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return status;
    }
    //Put all of these into a long running task
    private class PostJSONTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String result = null;
            String urlStr = params[0];
            JSONObject jsonObject = convertDataToJSON();
            InputStream is = postJSONObject(jsonObject, urlStr);
            if(is!=null){
                String content = readStringContent(is);
                //Read the result
                result = getTheStatus(content);
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getBaseContext(), s, Toast.LENGTH_LONG).show();
        }
    }
    public void onBtnPostClick(View view){
        PostJSONTask postJSONTask = new PostJSONTask();
        postJSONTask.execute("http://vtgames.vn/AnyoneCanCode/Android/admd201506/productservice.php");
    }
}
