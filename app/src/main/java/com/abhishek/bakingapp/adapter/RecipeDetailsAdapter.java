package com.abhishek.bakingapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.abhishek.bakingapp.R;
import com.abhishek.bakingapp.model.Ingredient;
import com.abhishek.bakingapp.utils.ConstantsUtil;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailsAdapter extends RecyclerView.Adapter<RecipeDetailsAdapter.RecipeDetailsViewHolder>{

    private final Context mContext;
    private final List<Ingredient> mIngredientList;

    public RecipeDetailsAdapter(Context context, List<Ingredient> ingredientList) {
        this.mContext = context;
        this.mIngredientList = ingredientList;
    }

    public class RecipeDetailsViewHolder extends RecyclerView.ViewHolder {

        @Nullable
        @BindView(R.id.iv_measure_icon)
        ImageView unitIcon;

        @Nullable
        @BindView(R.id.tv_ingredient_name)
        TextView ingredientName;

        @Nullable
        @BindView(R.id.tv_measure_value)
        TextView unitNumber;

        @Nullable
        @BindView(R.id.tv_step_count)
        TextView ingredientRowNumber;

        @Nullable
        @BindView(R.id.tv_measure_type)
        TextView ingredientUnitLongName;


        public RecipeDetailsViewHolder(View v) {
            super(v);
            ButterKnife.bind(this,v);
        }
    }

    @NonNull
    @Override
    public RecipeDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recipe_ingredients_card, parent, false);

        return new RecipeDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecipeDetailsViewHolder holder, int position) {
        Ingredient ingredient = mIngredientList.get(position);

        holder.ingredientName.setText(ingredient.getIngredient());
        holder.unitNumber.setText(String.valueOf(ingredient.getQuantity()));
        holder.ingredientRowNumber.setText(String.valueOf(position+1));

        String measure = ingredient.getMeasure();
        int unitNo = 0;

        for(int i = 0; i < ConstantsUtil.units.length; i++){
            if(measure.equals(ConstantsUtil.units[i])){
                unitNo = i;
                break;
            }
        }
        int unitIcon = ConstantsUtil.unitIcons[unitNo];
        Log.d("UNIT_NO: ", String.valueOf(unitIcon));
        String unitLongName = ConstantsUtil.unitName[unitNo];

        holder.unitIcon.setImageResource(unitIcon);
        holder.ingredientUnitLongName.setText(unitLongName);

        final boolean isChecked = false;

/*        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.ingredientChecked.getVisibility() == View.GONE){
                    holder.ingredientChecked.setVisibility(View.VISIBLE);
                }
                else{
                    holder.ingredientChecked.setVisibility(View.GONE);
                }


            }
        });*/

    }

    @Override
    public int getItemCount() {
        return mIngredientList.size();
    }
}

