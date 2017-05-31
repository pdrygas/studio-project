package edu.agh.pl.mobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class Logged extends AppCompatActivity {

    private Button showAllResources;
    private Button showAllCategories;
    private String xAuthToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        xAuthToken = getIntent().getStringExtra("X-AUTH-TOKEN");
        setContentView(R.layout.activity_logged);
        showAllResources = (Button)findViewById(R.id.allresources);
        showAllCategories = (Button)findViewById(R.id.allcategories);
        showAllCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Logged.this, ListCategories.class);
                myIntent.putExtra("X-AUTH-TOKEN",xAuthToken);
                Logged.this.startActivity(myIntent);
            }
        });
        showAllResources.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Logged.this, ListResources.class);
                myIntent.putExtra("X-AUTH-TOKEN",xAuthToken);
                Logged.this.startActivity(myIntent);
            }
        });
    }
}
