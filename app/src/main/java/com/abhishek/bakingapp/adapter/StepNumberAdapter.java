package com.abhishek.bakingapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.abhishek.bakingapp.R;
import com.abhishek.bakingapp.model.Step;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class StepNumberAdapter extends RecyclerView.Adapter<StepNumberAdapter.StepNumberHolder>{

    private final Context mContext;
    private final ArrayList<Step> mStepArrayList;
    public OnStepClick mOnStepClick;
    private int rowNo = 0;

    public StepNumberAdapter(Context context, ArrayList<Step> stepArrayList, OnStepClick onStepClick, int rowNo) {
        this.mContext = context;
        this.mStepArrayList = stepArrayList;
        this.mOnStepClick = onStepClick;
        this.rowNo = rowNo;
    }

    public class StepNumberHolder extends RecyclerView.ViewHolder {

        @Nullable
        @BindView(R.id.tv_step_number_tablet)
        TextView stepNumber;

        @Nullable
        @BindView(R.id.tv_step_title_tablet)
        TextView stepTitle;

        public StepNumberHolder(View v) {
            super(v);
            ButterKnife.bind(this,v);
        }
    }

    @NonNull
    @Override
    public StepNumberHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.single_step_number, parent, false);

        return new StepNumberHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final StepNumberHolder holder, int position) {

        holder.stepTitle.setText(mStepArrayList.get(position).getShortDescription());
        holder.stepNumber.setText(String.valueOf(position+1));


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnStepClick.onStepClick(holder.getAdapterPosition());
                rowNo = holder.getAdapterPosition();
                notifyDataSetChanged();
            }
        });

        if(rowNo == position){
            holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
        }
        else if(position == 0){
            holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
        }
        else
        {
            holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
        }
    }

    @Override
    public int getItemCount() {
        return mStepArrayList.size();
    }


    public interface OnStepClick {
        void onStepClick(int position);
    }
}
