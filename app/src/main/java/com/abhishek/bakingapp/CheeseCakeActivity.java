package com.abhishek.bakingapp;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class CheeseCakeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheesecake);


        //Set Title bar color with back button and title
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#a5d6a7")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Cheese Cake");


        //Set status bar color
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.cheesecake)); //status bar or the time bar at the top
        }
    }

    //BackButton in title bar
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

}
