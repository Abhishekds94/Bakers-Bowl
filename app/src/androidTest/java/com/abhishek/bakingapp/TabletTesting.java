package com.abhishek.bakingapp;


import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TabletTesting {
    @Rule
    public ActivityTestRule<RecipeDetailsActivity> mActivityTestRule = new ActivityTestRule<>(RecipeDetailsActivity.class);

    @Test
    public void recipeActivityTabletTest() {
        onView(ViewMatchers.withId(R.id.recipedetails_tablet)).check(matches(isDisplayed()));
    }
}
