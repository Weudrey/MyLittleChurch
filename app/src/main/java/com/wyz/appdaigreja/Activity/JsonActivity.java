package com.wyz.appdaigreja.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.wyz.appdaigreja.Helper.HttpHandler;
import com.wyz.appdaigreja.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class JsonActivity extends Activity {

    private String TAG = MainActivity.class.getSimpleName();
    private Button VoltardeJson;
    private ProgressDialog pDialog;
    private ListView lv;

    // URL to get contacts JSON
    private static String url = "https://api.myjson.com/bins/11gg93";

    ArrayList<HashMap<String, String>> galleryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json);

        VoltardeJson = (Button) findViewById(R.id.VoltardeJson);
        VoltardeJson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JsonActivity.this, PrincipalActivity.class);
                startActivity(intent);
            }
        });

        galleryList = new ArrayList<>();

        lv = (ListView) findViewById(R.id.textJSON);
        new GetGallery().execute();
    }

    private class GetGallery extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(JsonActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray gallery = jsonObj.getJSONArray("Gallery");

                    // looping through All Contacts
                    for (int i = 0; i < gallery.length(); i++) {
                        JSONObject c = gallery.getJSONObject(i);

                        String titulo = c.getString("TITULO");
                        String detalhes = c.getString("DETALHES");
                        String imagem = c.getString("IMAGEM");
                        //String address = c.getString("address");
                        //String gender = c.getString("gender");

                        // Phone node is JSON Object
                        //JSONObject phone = c.getJSONObject("phone");
                        //String mobile = phone.getString("mobile");
                        //String home = phone.getString("home");
                        //String office = phone.getString("office");

                        // tmp hash map for single contact
                        HashMap<String, String> contact = new HashMap<>();

                        // adding each child node to HashMap key => value
                        contact.put("TITULO", titulo);
                        contact.put("DETALHES", detalhes);
                        contact.put("IMAGEM", imagem);
                        //contact.put("mobile", mobile);

                        // adding contact to contact list
                        galleryList.add(contact);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new SimpleAdapter(
                    JsonActivity.this, galleryList,
                    R.layout.jsonarray, new String[]{"TITULO", "DETALHES",
                    "IMAGEM"}, new int[]{R.id.tituloArray,
                    R.id.detalhesArray, R.id.imagemArray});

            lv.setAdapter(adapter);
        }

    }
}
