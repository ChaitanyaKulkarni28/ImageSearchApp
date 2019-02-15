package com.example.imagesearchapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button searchButton;
    EditText keyword;
    String key;
    RequestQueue requestQueue;
    GridView gridView;

    public static ArrayList<String> getArrayList() {
        return arrayList;
    }

    static ArrayList<String> arrayList;
    static ArrayList<String> titleList;
    // So that you don't need mention your API key explicitly
    private static final String Authorization_key = BuildConfig.ApiKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // To avoid keyboard automatically popping up without touching edit text
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        searchButton = (Button)findViewById(R.id.Search);
        keyword = (EditText)findViewById(R.id.keywordEntry);
        gridView = (GridView)findViewById(R.id.gridview);
        requestQueue = Volley.newRequestQueue(this);
        arrayList = new ArrayList<String>();
        titleList = new ArrayList<String>();

        // Checks for internet permission and request if not granted
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.INTERNET},0);
        }

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_NETWORK_STATE},0);
        }

        boolean isOnline = isNetworkAvailable();

        if(!isOnline){
            Toast.makeText(getApplicationContext(),"Please check your internet connection", Toast.LENGTH_SHORT).show();
        }

        // When you click on search button it will search for all viral images related to keyword inserted by user
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                key = keyword.getText().toString().trim();

                if(!key.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Searching for "+key, Toast.LENGTH_SHORT).show();
                    try {
                        jsonParse(key);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"Please enter any keyword to search",Toast.LENGTH_SHORT).show();
                }
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent i = new Intent(getApplicationContext(), FullImageActivity.class);
                i.putExtra("id", position);
                startActivity(i);
            }
        });
    }

    // This function will make an API call and extract all titles and links to all images
    public void jsonParse(String key){
        String url = "https://api.imgur.com/3/gallery/search/1?q="+key;
        Log.i("URL ",url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    arrayList.clear();
                    titleList.clear();
                    JSONArray jsonArray = response.getJSONArray("data");

                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String title = jsonObject.getString("title");
                        titleList.add(title);
                        if(jsonObject.has("images")){
                            JSONArray jsonArray1 = jsonObject.getJSONArray("images");

                            for(int j=0; j<jsonArray1.length();j++){
                                JSONObject jsonObject1 = jsonArray1.getJSONObject(j);
                                String link = jsonObject1.getString("link");
                                if(link.endsWith(".jpg") || link.endsWith(".png")) {
                                    arrayList.add(link);
                                    Log.i("Link ",link);
                                }
                            }
                        }
                    }
                    Toast.makeText(getApplicationContext(),String.valueOf(arrayList.size())+" images found",Toast.LENGTH_SHORT).show();
                    gridView.setAdapter(new ImageAdapter(getApplicationContext(),arrayList,titleList));

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            public Map getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", Authorization_key);
                return headers;
            }
        };

        requestQueue.add(jsonObjectRequest);

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}