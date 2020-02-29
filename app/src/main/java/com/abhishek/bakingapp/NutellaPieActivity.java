package com.abhishek.bakingapp;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class NutellaPieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutellapie);


        //Set Title bar color with back button and title
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#bcaaa4")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Nutella Pie");


        //Set status bar color
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.nutella)); //status bar or the time bar at the top
        }
    }

    //BackButton in title bar
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

}
