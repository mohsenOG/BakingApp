package com.oghbaei.bakingapp;


import android.content.Intent;
import android.os.SystemClock;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;


import com.oghbaei.bakingapp.DetailActivity;
import com.oghbaei.bakingapp.queryModel.Recipe;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.oghbaei.bakingapp.RecipeActivity.RECIPE_KEY_RECIPE_ACT_TO_DETAIL_ACT;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;
@RunWith(AndroidJUnit4.class)
public class DetailActivityTest {
    //private IdlingResource mIdlingResource;
    //private Recipe mRecipe;

    @Rule
    public ActivityTestRule<DetailActivity> activityTestRule = new ActivityTestRule<DetailActivity>(DetailActivity.class) {
        @Override
        protected Intent getActivityIntent() {
            Intent i = new Intent();
            Recipe recipe = RecipeDummy.newInstance();
            i.putExtra(RECIPE_KEY_RECIPE_ACT_TO_DETAIL_ACT, recipe);
            return i;
        }
    };

    @Test
    public void detailActivityCanBeSwiped() {
        onView(withId(R.id.cp_container)).perform(swipeLeft());
        onView(withId(R.id.cp_container)).perform(swipeRight());
    }

    @Test
    public void detailActivityDisplayedCorrectly() {
        // Click on Steps in tabbed view.
        Matcher<View> matcher = allOf(withText("Steps"), isDescendantOfA(withId(R.id.tabs)));
        onView(matcher).perform(click());
        SystemClock.sleep(800);
        // Check if the recyclerView is shown.
        onView(withId(R.id.recyclerView_steps)).check(matches(isDisplayed()));

    }
}
