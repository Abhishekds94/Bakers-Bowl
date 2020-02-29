package com.abhishek.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //To make full screen for splash screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        setContentView(R.layout.activity_dashboard);

        findViewById(R.id.optionPie).setOnClickListener(listener_optionPie);
        findViewById(R.id.optionBrownie).setOnClickListener(listener_optionBrownie);
        findViewById(R.id.optionYellowCake).setOnClickListener(listener_optionYellowCake);
        findViewById(R.id.optionCheeseCake).setOnClickListener(listener_optionCheeseCake);

    }

    View.OnClickListener listener_optionPie = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(DashboardActivity.this, NutellaPieActivity.class);
            startActivity(intent);
        }
    };

    View.OnClickListener listener_optionBrownie = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(DashboardActivity.this, BrownieActivity.class);
            startActivity(intent);
        }
    };

    View.OnClickListener listener_optionYellowCake = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(DashboardActivity.this, YellowCakeActivity.class);
            startActivity(intent);
        }
    };

    View.OnClickListener listener_optionCheeseCake = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(DashboardActivity.this, CheeseCakeActivity.class);
            startActivity(intent);
        }
    };


}
