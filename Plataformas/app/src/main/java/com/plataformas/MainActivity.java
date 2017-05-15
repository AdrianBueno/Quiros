package com.plataformas;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.plataformas.utils.Ar;

import static android.R.attr.button;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        setContentView(R.layout.activity_main);
        configureDisplay();
        Button buttonPlay = (Button)findViewById(R.id.buttonJugar);
        Button buttonExit = (Button)findViewById(R.id.buttonSalir);

        if(buttonPlay != null)
            buttonPlay.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    Intent gameActivity = new Intent(MainActivity.this, GameActivity.class);
                    startActivity(gameActivity);
                    finish();
                }
            });
        if(buttonExit != null)
            buttonExit.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    finish();
                }
            });


    }

    @Override
    public void onBackPressed() {
        finish();
        System.gc();

    }

    private void configureDisplay(){
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        Ar.configurar(size.x, size.y);
    }
}