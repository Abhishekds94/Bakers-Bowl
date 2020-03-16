package com.abhishek.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.abhishek.bakingapp.adapter.StepNumberAdapter;
import com.abhishek.bakingapp.model.Step;
import com.abhishek.bakingapp.utils.ConstantsUtil;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.app.FragmentManager;
import androidx.core.app.NavUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TutorialsActivity extends AppCompatActivity implements View.OnClickListener, StepNumberAdapter.OnStepClick{

    public static final String LIST_STATE = "step_list_state";
    public static final String NUMBER_STATE = "step_number_state";
    public static final String LIST_JSON_STATE = "step_list_json_state";
    private boolean ifTablet;
    private int mVideoNumber = 0;

    @BindView(R.id.videoPlayer)
    FrameLayout mFragmentContainer;

    @BindView(R.id.btn_next)
    Button mButtonNextStep;

    @BindView(R.id.btn_prev)
    Button mButtonPreviousStep;

/*    @Nullable
    @BindView(R.id.rv_recipe_steps)
    RecyclerView mRecyclerViewSteps;*/

    boolean isFromWidget;
    StepNumberAdapter mStepNumberAdapter;
    LinearLayoutManager mLinearLayoutManager;
    String mJsonResult;
    ArrayList<Step> mStepArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorials);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ifTablet = false;

/*        // Check if device is a tablet
        if(findViewById(R.id.cooking_tablet) != null){
            ifTablet = true;
        }
        else{
            ifTablet = false;
        }*/

        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra(ConstantsUtil.STEP_INTENT_EXTRA)) {
                mStepArrayList = getIntent().getParcelableArrayListExtra(ConstantsUtil.STEP_INTENT_EXTRA);
            }
            if (intent.hasExtra(ConstantsUtil.JSON_RESULT_EXTRA)) {
                mJsonResult = getIntent().getStringExtra(ConstantsUtil.JSON_RESULT_EXTRA);
            }
            if(intent.getStringExtra(ConstantsUtil.WIDGET_EXTRA) != null){
                isFromWidget = true;
            }
            else{
                isFromWidget = false;
            }
        }
        // If there is no saved state, instantiate fragment
        if(savedInstanceState == null){
            playVideo(mStepArrayList.get(mVideoNumber));
        }

        ButterKnife.bind(this);

        handleUiForDevice();
    }

    public void playVideo(Step step){
        VideoPlayerFragment videoPlayerFragment = new VideoPlayerFragment();
        Bundle stepsBundle = new Bundle();
        stepsBundle.putParcelable(ConstantsUtil.STEP_SINGLE, step);
        videoPlayerFragment.setArguments(stepsBundle);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.videoPlayer, videoPlayerFragment)
                .addToBackStack(null)
                .commit();
    }

    public void playVideoReplace(Step step){
        VideoPlayerFragment videoPlayerFragment = new VideoPlayerFragment();
        Bundle stepsBundle = new Bundle();
        stepsBundle.putParcelable(ConstantsUtil.STEP_SINGLE, step);
        videoPlayerFragment.setArguments(stepsBundle);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.videoPlayer, videoPlayerFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(LIST_STATE, mStepArrayList);
        outState.putString(LIST_JSON_STATE, mJsonResult);
        outState.putInt(NUMBER_STATE, mVideoNumber);
    }

    @Override
    public void onClick(View v) {
        //If it's last step show cooking is over
        if(mVideoNumber == mStepArrayList.size()-1){
            Toast.makeText(this, "Cooking finished", Toast.LENGTH_SHORT).show();
        }
        else{
            if(v.getId() == mButtonPreviousStep.getId()){
                mVideoNumber--;
                if(mVideoNumber < 0){
                    Toast.makeText(this, "Check next step", Toast.LENGTH_SHORT).show();
                }
                else
                    playVideoReplace(mStepArrayList.get(mVideoNumber));
            }
            else if(v.getId() == mButtonNextStep.getId()){
                mVideoNumber++;
                playVideoReplace(mStepArrayList.get(mVideoNumber));
            }
        }
    }


    @Override
    public void onStepClick(int position) {
        mVideoNumber = position;
        playVideoReplace(mStepArrayList.get(position));
    }

    public void handleUiForDevice(){
        if(!ifTablet){
            // Set button listeners
            mButtonNextStep.setOnClickListener(this);
            mButtonPreviousStep.setOnClickListener(this);
        }
        else{//Tablet view
            mStepNumberAdapter = new StepNumberAdapter(this,mStepArrayList, this, mVideoNumber);
            mLinearLayoutManager = new LinearLayoutManager(this);
/*            mRecyclerViewSteps.setLayoutManager(mLinearLayoutManager);
            mRecyclerViewSteps.setAdapter(mStepNumberAdapter);*/
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            mStepArrayList = savedInstanceState.getParcelableArrayList(LIST_STATE);
            mJsonResult = savedInstanceState.getString(LIST_JSON_STATE);
            mVideoNumber = savedInstanceState.getInt(NUMBER_STATE);
        }
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

}