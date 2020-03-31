package com.abhishek.bakingapp;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.runner.AndroidJUnit4;

import com.abhishek.bakingapp.utils.ConstantsUtil;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.ViewMatchers;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static androidx.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.IsNot.not;

@RunWith(AndroidJUnit4.class)

public class MainTesting {
    @Rule
public IntentsTestRule<RecipeDetailsActivity> mActivityRule = new IntentsTestRule<>(
        RecipeDetailsActivity.class);

        @Before
        public void stubAllExternalIntents() {
            intending(not(isInternal())).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, null));
        }

        @Test
        public void intentTest(){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            onView(withId(R.id.rv_ingredients_list)).perform(RecyclerViewActions.scrollToPosition(4));
            onView(withId(R.id.rv_ingredients_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
            intended(hasExtraWithKey(ConstantsUtil.RECIPE_INTENT_EXTRA));
            onView(withId(R.id.btn_tutorial)).perform(ViewActions.click());
            intended(hasComponent(TutorialsActivity.class.getName()));
        }

}