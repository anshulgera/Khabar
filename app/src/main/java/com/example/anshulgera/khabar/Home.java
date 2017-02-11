package com.example.anshulgera.khabar;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class Home extends AppCompatActivity {

    ImageButton b1,b2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if(activeNetworkInfo!=null){

            Toast toast = Toast.makeText(this,"INTERNET connected",Toast.LENGTH_SHORT);
            toast.show();

        }
        else{

            setContentView(R.layout.internet_error);
            Toast toast = Toast.makeText(this,"Please connect to INTERNET",Toast.LENGTH_SHORT);
            toast.show();
        }



        setContentView(R.layout.activity_home);

        b1= (ImageButton) findViewById(R.id.ht);
        b2= (ImageButton) findViewById(R.id.nytimes);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                if(activeNetworkInfo!=null){

                    Toast t1=Toast.makeText(Home.this,"Hindustan Times",Toast.LENGTH_SHORT);
                    t1.show();
                    Intent i1=new Intent(Home.this,MainActivity.class);
                    String ht="http://www.hindustantimes.com/rss/india/rssfeed.xml";
                    Bundle bundle=new Bundle();
                    bundle.putString("URL",ht);
                    i1.putExtras(bundle);
                    startActivity(i1);



                }
                else{

                    setContentView(R.layout.internet_error);
                    Toast toast = Toast.makeText(Home.this,"Please connect to INTERNET",Toast.LENGTH_SHORT);
                    toast.show();
                }



            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                if(activeNetworkInfo!=null){

                    Toast t2=Toast.makeText(Home.this,"NewYork Times",Toast.LENGTH_SHORT);
                    t2.show();
                    Intent i2 = new Intent(Home.this,MainActivity.class);
                    String nyt="http://rss.nytimes.com/services/xml/rss/nyt/World.xml";
                    Bundle bundle=new Bundle();
                    bundle.putString("URL",nyt);
                    i2.putExtras(bundle);
                    startActivity(i2);



                }
                else{

                    setContentView(R.layout.internet_error);
                    Toast toast = Toast.makeText(Home.this,"Please connect to INTERNET",Toast.LENGTH_SHORT);
                    toast.show();

                }


            }
        });



    }


    boolean backpress = false;
    @Override
    public void onBackPressed() {
        if (backpress) {
            super.onBackPressed();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            this.finish();
        }

        this.backpress = true;
        Toast.makeText(this, "Press BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                backpress = false;
            }
        }, 2000);

    }
}
