package com.example.administrator.findanimal;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button1=(Button)findViewById(R.id.play);
        button1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, GameActivity.class);
                startActivity(intent);

            }
        });
/*
        Button button2=(Button)findViewById(R.id.exit);
        button2.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                System.exit(0);
            }
        });*/
    }
}
