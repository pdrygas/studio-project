package edu.agh.pl.mobile;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ListResources extends AppCompatActivity {

    private ListView list ;
    private String xAuthToken ;
    private String categoryId ;
    private ArrayList<String> titles;
    private ArrayList<String> contents;
    private ArrayList<String> resourceId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        xAuthToken = getIntent().getStringExtra("X-AUTH-TOKEN");
        categoryId = getIntent().getStringExtra("categoryId");
        titles = new ArrayList<>();
        contents = new ArrayList<>();
        resourceId = new ArrayList<>();

        new getResources().execute();
        setContentView(R.layout.activity_list_resources);
        CustomResourcesListAdapter adapter = new CustomResourcesListAdapter(this, titles,contents);

        list = (ListView) findViewById(R.id.listView1);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent myIntent = new Intent(ListResources.this, ShowResource.class);
                myIntent.putExtra("X-AUTH-TOKEN",xAuthToken);
                myIntent.putExtra("resourceId",resourceId.get(position).toString());
                ListResources.this.startActivity(myIntent);

            }
        });

    }
    private class getResources extends AsyncTask<Void,Void,String>{

        @Override
        protected String doInBackground(Void... params){
            RequestHandler rq = new RequestHandler();
            try {
                if(categoryId!=null) {
                    return rq.executeRequest(rq.getCategoryResources("192.168.193.32", categoryId), xAuthToken);
                } else {
                    return rq.executeRequest(rq.getAllResources("192.168.193.32"),xAuthToken);
                }
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
                        titles.add(jsonArray.getJSONObject(i).get("title").toString());
                    } else {
                        titles.add("Bez Tytułu");
                    }
                    contents.add(jsonArray.getJSONObject(i).get("content").toString().substring(0,4));
                    resourceId.add(jsonArray.getJSONObject(i).get("id").toString());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
