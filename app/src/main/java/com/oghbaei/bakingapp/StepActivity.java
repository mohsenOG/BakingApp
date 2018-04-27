package com.oghbaei.bakingapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.oghbaei.bakingapp.queryModel.Ingredient;
import com.oghbaei.bakingapp.queryModel.Recipe;

public class StepActivity extends AppCompatActivity implements StepFragment.OnNextPreviousStepClickedListener {

    public static final String RECIPE_KEY_DETAIL_ACT_TO_STEP_ACT = "RECIPE_KEY_DETAIL_ACT_TO_STEP_ACT";
    public static final String STEP_ID_KEY_DETAIL_ACT_TO_STEP_ACT = "STEP_ID_KEY_DETAIL_ACT_TO_STEP_ACT";
    public static final String RECIPE_KEY_FROM_STEP_ACT_TO_DETAIL_ACT = "RECIPE_KEY_FROM_STEP_ACT_TO_DETAIL_ACT";


    private Recipe mRecipe;
    private String mStepId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Set mRecipe and mStepId. They should come from Detail Activity.
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            throw new RuntimeException(this.toString() + " must send recipe/StepId from DetailActivity to StepActivity.");
        }
        mRecipe = extras.getParcelable(RECIPE_KEY_DETAIL_ACT_TO_STEP_ACT);
        mStepId = extras.getString(STEP_ID_KEY_DETAIL_ACT_TO_STEP_ACT);
        // Set activity title
        String stepTitle = mRecipe.getStep(Integer.valueOf(mStepId)).getShortDescription();
        if (stepTitle == null || stepTitle.isEmpty())
            stepTitle = getString(R.string.no_step_title) + " " + mStepId;
        setTitle(stepTitle);

        StepFragment stepFragment = StepFragment.newInstance(mRecipe, mStepId);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.fl_step_fragment_activity, stepFragment)
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent resultIntent = new Intent();
                resultIntent.putExtra(RECIPE_KEY_FROM_STEP_ACT_TO_DETAIL_ACT, mRecipe);
                //NavUtils.navigateUpTo(DetailActivity, resultIntent);
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onPreviousStepClicked(String previousStepId) {
        //TODO handle previus button
    }

    @Override
    public void onNextStepClicked(String NextStepId) {
        //TODO handle next step button.
    }


}
