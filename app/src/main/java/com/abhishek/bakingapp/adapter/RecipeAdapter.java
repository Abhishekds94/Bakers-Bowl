package com.abhishek.bakingapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.abhishek.bakingapp.R;
import com.abhishek.bakingapp.RecipeDetailsActivity;
import com.abhishek.bakingapp.model.Recipe;
import com.abhishek.bakingapp.utils.ConstantsUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>{

    private final Context mContext;
    private final ArrayList<Recipe> mRecipeList;
    private String mJsonResult;
    String recipeJson;

    public RecipeAdapter(Context context, ArrayList<Recipe> recipeList, String jsonResult) {
        this.mContext = context;
        this.mRecipeList = recipeList;
        this.mJsonResult = jsonResult;
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {

        @Nullable
        @BindView(R.id.tv_recipe_name)
        TextView recipeName;

        @Nullable
        @BindView(R.id.iv_recipe_img)
        ImageView recipeIcon;

        public RecipeViewHolder(View v) {
            super(v);
            ButterKnife.bind(this,v);
        }
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recipe_name_card, parent, false);

        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecipeViewHolder holder, int position) {

        holder.recipeName.setText(mRecipeList.get(position).getName());

        switch (position){
            case 0 : holder.recipeIcon.setImageResource(R.drawable.pie);
                break;
            case 1 : holder.recipeIcon.setImageResource(R.drawable.brownie);
                break;
            case 2 : holder.recipeIcon.setImageResource(R.drawable.yellowcake);
                break;
            case 3 : holder.recipeIcon.setImageResource(R.drawable.cheesecake);
                break;
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Recipe recipe = mRecipeList.get(holder.getAdapterPosition());
                recipeJson = jsonToString(mJsonResult, holder.getAdapterPosition());
                Intent intent = new Intent(mContext, RecipeDetailsActivity.class);
                ArrayList<Recipe> recipeArrayList = new ArrayList<>();
                recipeArrayList.add(recipe);
                intent.putParcelableArrayListExtra(ConstantsUtil.RECIPE_INTENT_EXTRA, recipeArrayList);
                intent.putExtra(ConstantsUtil.JSON_RESULT_EXTRA, recipeJson);
                mContext.startActivity(intent);

                SharedPreferences.Editor editor = mContext.getSharedPreferences(ConstantsUtil.YUMMIO_SHARED_PREF, MODE_PRIVATE).edit();
                editor.putString(ConstantsUtil.JSON_RESULT_EXTRA, recipeJson);
                editor.apply();

/*                if(Build.VERSION.SDK_INT > 25){
                    //Start the widget service to update the widget
                    YummioWidgetService.startActionOpenRecipeO(mContext);
                }
                else{
                    //Start the widget service to update the widget
                    YummioWidgetService.startActionOpenRecipe(mContext);
                }*/
            }
        });

    }

    @Override
    public int getItemCount() {
        return mRecipeList.size();
    }

    // Get selected Recipe as Json String
    private String jsonToString(String jsonResult, int position){
        JsonElement jsonElement = new JsonParser().parse(jsonResult);
        JsonArray jsonArray = jsonElement.getAsJsonArray();
        JsonElement recipeElement = jsonArray.get(position);
        return recipeElement.toString();
    }

}