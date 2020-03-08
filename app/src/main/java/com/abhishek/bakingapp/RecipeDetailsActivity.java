package com.abhishek.bakingapp;

import android.os.Bundle;
import android.view.Window;

import com.abhishek.bakingapp.services.RecipeClient;
import com.abhishek.bakingapp.services.RecipeService;

import androidx.appcompat.app.AppCompatActivity;

public class RecipeDetailsActivity extends AppCompatActivity {

    RecipeService mRecipeService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //To make full screen for splash screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        setContentView(R.layout.activity_recipedetails);

    }
}