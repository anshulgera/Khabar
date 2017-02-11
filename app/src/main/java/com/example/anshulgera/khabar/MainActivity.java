package com.example.anshulgera.khabar;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    public static String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Bundle bundle=getIntent().getExtras();
        url=bundle.getString("URL");

        recyclerView= (RecyclerView) findViewById(R.id.recyclerview);
        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipeContainer);
        swipeRefreshLayout.setColorSchemeColors(android.R.color.holo_red_light,android.R.color.holo_blue_dark,android.R.color.white);
        swipeRefreshLayout.setOnRefreshListener(this);
        ReadRss readRss=new ReadRss(this,recyclerView);
        readRss.execute();//executing AsyncTask here

    }



    @Override
    public void onRefresh() {
        Toast.makeText(this,"Refreshing Feed",Toast.LENGTH_LONG).show();
        finish();
        Intent i=new Intent(MainActivity.this,MainActivity.class);
        startActivity(i);
        swipeRefreshLayout.setRefreshing(false);
    }
}
