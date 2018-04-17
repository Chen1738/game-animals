package com.example.administrator.findanimal;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.widget.ImageView;

public class GameEnd extends AppCompatActivity{
    protected boolean _active = true;
    protected int _splashTime = 5000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Connect this java file with the xml layout
        setContentView(R.layout.game_end);

        Resources resources = getResources();
        Drawable success = resources.getDrawable(R.drawable.bg_success);
        Drawable lose = resources.getDrawable(R.drawable.bg_lose);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            boolean retrievedbool = extras.getBoolean("shared_data");
            ImageView display = findViewById(R.id.bg_image);
            if(retrievedbool == true)
                display.setBackground(success);
            else display.setBackground(lose);
        }

        Thread endTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    while(_active && (waited < _splashTime)) {
                        sleep(100);
                        if(_active) waited += 100;
                    }
                }catch(InterruptedException e) {
                    // do nothing
                }finally {
                    finish();
                    // 启动主应用
                    startActivity(new Intent("com.example.administrator.findanimal.MainActivity"));
                    //stop();
                }
            }
        };
        endTread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            _active = false;
        }
        return true;
    }
}
