package com.abhishek.bakingapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.abhishek.bakingapp.adapter.RecipeAdapter;
import com.abhishek.bakingapp.model.Recipe;
import com.abhishek.bakingapp.services.RecipeClient;
import com.abhishek.bakingapp.services.RecipeService;
import com.google.gson.Gson;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Tag;

public class DashboardActivity extends AppCompatActivity {


    private final String TAG = DashboardActivity.class.getSimpleName();
    public static final String JSON_STATE = "json_state";
    public static final String ARRAYLIST_STATE = "arraylist_state";

    RecipeService mService;
    RecipeAdapter recipeAdapter;
    String mJsonResult;
    ArrayList<Recipe> mArrayList = new ArrayList<>();

    @BindView(R.id.rv_recipe_list) RecyclerView mRecyclerViewRecipes;

    private boolean ifTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null)
            getSupportActionBar().hide();


        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);

//        if(findViewById(R.id.recipe_tablet) != null){
//            ifTablet = true;
//        }
//        else{
//            ifTablet = false;
//        }

        if(savedInstanceState != null){
            mJsonResult = savedInstanceState.getString(JSON_STATE);
            mArrayList = savedInstanceState.getParcelableArrayList(ARRAYLIST_STATE);
            recipeAdapter = new RecipeAdapter(DashboardActivity.this, mArrayList, mJsonResult);
            RecyclerView.LayoutManager mLayoutManager;
            if(ifTablet){
                mLayoutManager = new GridLayoutManager(DashboardActivity.this, 2);
            }
            else{
                mLayoutManager = new LinearLayoutManager(DashboardActivity.this);
            }

            mRecyclerViewRecipes.setLayoutManager(mLayoutManager);
            mRecyclerViewRecipes.setAdapter(recipeAdapter);
        }
        else{
            mService = new RecipeClient().mRecipeService;
            new FetchRecipesAsync().execute();

        }

    }

    private class FetchRecipesAsync extends AsyncTask<Void,Void,Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            fetchRecipes();
            return null;
        }
    }

    private void fetchRecipes() {
        Call<ArrayList<Recipe>> call = mService.getRecipes();

        call.enqueue(new Callback<ArrayList<Recipe>>() {
            @Override
            public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {
                //Test if response is succesfull
                ArrayList<Recipe> recipe = response.body();
                Log.i("BAKING_APP", "Recipe"+recipe);
                mArrayList = response.body();
                Log.i("BAKING_APP", "BR-"+mArrayList);
                Toast.makeText(DashboardActivity.this, "Recipe"+recipe, Toast.LENGTH_SHORT).show();
                Toast.makeText(DashboardActivity.this, "BR-"+mArrayList, Toast.LENGTH_SHORT).show();
                mJsonResult = new Gson().toJson(response.body());

                recipeAdapter = new RecipeAdapter(DashboardActivity.this, mArrayList, mJsonResult);
                RecyclerView.LayoutManager mLayoutManager;
                if(ifTablet){
                    mLayoutManager = new GridLayoutManager(DashboardActivity.this, 2);
                }
                else{
                    mLayoutManager = new LinearLayoutManager(DashboardActivity.this);
                }

                mRecyclerViewRecipes.setLayoutManager(mLayoutManager);
                mRecyclerViewRecipes.setAdapter(recipeAdapter);

            }

            @Override
            public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
                Log.d(TAG, t.toString());
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(JSON_STATE, mJsonResult);
        outState.putParcelableArrayList(ARRAYLIST_STATE, mArrayList);
    }
}
