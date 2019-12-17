package com.example.debian.ert;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class Splash extends AppCompatActivity {
    ImageView iv;
    TextView title,desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        title = (TextView)findViewById(R.id.splash_txt);
        desc = (TextView)findViewById(R.id.splash_desc);

        Window w = this.getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

//        Custom fonts below
        Typeface gibsonBold = Typeface.createFromAsset(getAssets(),"fonts/gibson-bold.ttf");
        Typeface gibsonRegular = Typeface.createFromAsset(getAssets(),"fonts/Gibson-Regular.ttf");

        title.setTypeface(gibsonBold);
        desc.setTypeface(gibsonRegular);

        iv = (ImageView)findViewById(R.id.splash_img);

        Animation animation = AnimationUtils.loadAnimation(this,R.anim.transition);
        iv.startAnimation(animation);

        final Intent intent = new Intent(this,MainActivity.class);
        Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    startActivity(intent);
                    finish();
                }
            }
        };
        thread.start();
    }
}
