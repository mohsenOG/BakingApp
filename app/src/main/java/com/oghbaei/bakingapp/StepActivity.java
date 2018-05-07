package com.oghbaei.bakingapp;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.oghbaei.bakingapp.queryModel.Recipe;

import java.util.Objects;

public class StepActivity extends AppCompatActivity implements StepFragment.OnNextPreviousStepClickedListener {

    public static final String RECIPE_KEY_DETAIL_ACT_TO_STEP_ACT = "RECIPE_KEY_DETAIL_ACT_TO_STEP_ACT";
    public static final String STEP_ID_KEY_DETAIL_ACT_TO_STEP_ACT = "STEP_ID_KEY_DETAIL_ACT_TO_STEP_ACT";

    private Recipe mRecipe;
    private String mStepId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        // Set mRecipe and mStepId. They should come from Detail Activity.
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            throw new RuntimeException(this.toString() + " must send recipe/StepId from DetailActivity to StepActivity.");
        }
        mRecipe = extras.getParcelable(RECIPE_KEY_DETAIL_ACT_TO_STEP_ACT);
        mStepId = extras.getString(STEP_ID_KEY_DETAIL_ACT_TO_STEP_ACT);
        // Set activity title
        setActivityTitle(mStepId);

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
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPreviousStepClicked(String previousStepId) {
        // Handle last step click
        StepFragment previousFragment = StepFragment.newInstance(mRecipe, previousStepId);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fl_step_fragment_activity, previousFragment)
                .commit();

        setActivityTitle(previousStepId);
    }

    @Override
    public void onNextStepClicked(String nextStepId) {
        // handle next step button.
        StepFragment nextFragment = StepFragment.newInstance(mRecipe, nextStepId);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fl_step_fragment_activity, nextFragment)
                .commit();

        setActivityTitle(nextStepId);
    }

    private void setActivityTitle(String stepId) {
        String stepTitle = mRecipe.getStep(Integer.valueOf(stepId)).getShortDescription();
        if (stepTitle == null || stepTitle.isEmpty())
            stepTitle = getString(R.string.no_step_title) + " " + mStepId;
        setTitle(stepTitle);
    }

}
