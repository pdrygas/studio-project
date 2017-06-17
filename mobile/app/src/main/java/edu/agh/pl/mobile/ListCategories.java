package edu.agh.pl.mobile;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;

public class ListCategories extends AppCompatActivity {

    private ListView list ;
    private String xAuthToken ;
    private ArrayList<String> categoryId;
    private ArrayList<String> categoryTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        xAuthToken = getIntent().getStringExtra("X-AUTH-TOKEN");
        categoryId = new ArrayList<>();
        categoryTitle = new ArrayList<>();

        new getCategories().execute();

        setContentView(R.layout.activity_list_categories);

        CustomCategoriesListAdapter adapter = new CustomCategoriesListAdapter(this,categoryTitle);
        list = (ListView) findViewById(R.id.listView2);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(ListCategories.this, ListResources.class);
                myIntent.putExtra("X-AUTH-TOKEN",xAuthToken);
                myIntent.putExtra("categoryId",categoryId.get(position).toString());
                ListCategories.this.startActivity(myIntent);

            }
        });

    }
    private class getCategories extends AsyncTask<Void,Void,String>{

        @Override
        protected String doInBackground(Void... params){
           RequestHandler rq = new RequestHandler();
            try {
                return rq.executeRequest(rq.getCategories("192.168.193.32"),xAuthToken);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            JSONArray jsonArray;
            try {
                jsonArray = new JSONArray(s);
                for(int i=0;i<jsonArray.length();i++){
                    if(jsonArray.getJSONObject(i).has("title")){
                        categoryTitle.add(jsonArray.getJSONObject(i).get("title").toString());
                    } else {
                        categoryTitle.add("Bez Tytułu");
                    }
                    categoryId.add(jsonArray.getJSONObject(i).get("id").toString());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
