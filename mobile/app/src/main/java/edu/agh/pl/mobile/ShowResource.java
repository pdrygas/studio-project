package edu.agh.pl.mobile;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.concurrent.ExecutionException;

public class ShowResource extends AppCompatActivity {
    private String xAuthToken;
    private String resourceId;
    private String title;
    private String content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        xAuthToken = getIntent().getStringExtra("X-AUTH-TOKEN");
        resourceId = getIntent().getStringExtra("resourceId");
        setContentView(R.layout.list_element_with_description);
        new getResource().execute();


    }
    private class getResource extends AsyncTask<Void,Void,String> {

        @Override
        protected String doInBackground(Void... params){
            RequestHandler rq = new RequestHandler();
            try {
                return rq.executeRequest(rq.getResource("192.168.193.32",resourceId),xAuthToken);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            JSONObject jsonObject;
            try {
                jsonObject = new JSONObject(s);
                if(jsonObject.has("title")){
                    title = jsonObject.get("title").toString();
                } else {
                    title = "Bez Tytułu";
                }
                content = jsonObject.getString("content").toString();

                TextView tvTitle = (TextView)findViewById(R.id.item);
                tvTitle.setText(title);
                TextView tvContent = (TextView)findViewById(R.id.textView1);
                tvContent.setText(content);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
