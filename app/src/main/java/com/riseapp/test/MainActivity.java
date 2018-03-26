package com.riseapp.test;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
RelativeLayout url,phone,email,search;
    TextView t1,t2,t3,t4;
        Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        url= (RelativeLayout) findViewById(R.id.url);
        search= (RelativeLayout) findViewById(R.id.search);
        phone= (RelativeLayout) findViewById(R.id.number);
        email= (RelativeLayout) findViewById(R.id.email);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        t1= (TextView) findViewById(R.id.t1);
        t2= (TextView) findViewById(R.id.t2);
        t3= (TextView) findViewById(R.id.t3);
        t4= (TextView) findViewById(R.id.t4);
        View.OnClickListener onClickListener=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, OcrCaptureActivity.class);
                if(view.getId()==R.id.url) {
                    intent.putExtra("Type", "url");
                    Log.d("CameraType", "url");
                    startActivity(intent);
                }
                else if(view.getId()==R.id.search) {
                    intent.putExtra("Type", "search");
                    Log.d("CameraType", "search");
                    startActivity(intent);
                }
                else if(view.getId()==R.id.number) {
                    intent.putExtra("Type", "phone");
                    Log.d("CameraType", "phone");
                    startActivity(intent);
                }
                else if(view.getId()==R.id.email) {
                    intent.putExtra("Type", "email");
                    Log.d("CameraType", "email");
                    startActivity(intent);
                }
            }
        };
        url.setOnClickListener(onClickListener);
        phone.setOnClickListener(onClickListener);
        email.setOnClickListener(onClickListener);
        search.setOnClickListener(onClickListener);
        Typeface montt = Typeface.createFromAsset(getAssets(),
                "Ubuntu-Regular.ttf");
        t1.setTypeface(montt);
        t2.setTypeface(montt);
        t3.setTypeface(montt);
        t4.setTypeface(montt);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
