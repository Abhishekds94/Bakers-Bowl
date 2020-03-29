package com.abhishek.bakingapp.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;

import com.abhishek.bakingapp.VideoPlayerFragment;
import com.abhishek.bakingapp.model.Step;

import java.util.ArrayList;

import androidx.legacy.app.FragmentPagerAdapter;


public class StepPagerAdapter extends FragmentPagerAdapter {

    ArrayList<Step> mStepList;
    Bundle stepsBundle = new Bundle();

    public StepPagerAdapter(FragmentManager fm, ArrayList<Step> stepList) {
        super(fm);
        this.mStepList = stepList;
    }

    @Override
    public Fragment getItem(int position) {
        VideoPlayerFragment videoPlayerFragment = new VideoPlayerFragment();
        stepsBundle.putParcelableArrayList("steps", mStepList);
        stepsBundle.putInt("page",position+1);
        stepsBundle.putBoolean("isLastPage",position == getCount() - 1);
        videoPlayerFragment.setArguments(stepsBundle);

        return videoPlayerFragment;
    }

    @Override
    public int getCount() {
        return mStepList.size();
    }
}