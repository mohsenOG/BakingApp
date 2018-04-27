package com.oghbaei.bakingapp;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.oghbaei.bakingapp.queryModel.Recipe;

import butterknife.BindBool;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.oghbaei.bakingapp.RecipeActivity.RECIPE_KEY_RECIPE_ACT_TO_DETAIL_ACT;
import static com.oghbaei.bakingapp.StepActivity.RECIPE_KEY_DETAIL_ACT_TO_STEP_ACT;
import static com.oghbaei.bakingapp.StepActivity.RECIPE_KEY_FROM_STEP_ACT_TO_DETAIL_ACT;
import static com.oghbaei.bakingapp.StepActivity.STEP_ID_KEY_DETAIL_ACT_TO_STEP_ACT;


public class DetailActivity extends AppCompatActivity implements DetailFragment.OnDetailFragmentInteractionListener {

    public static final String EXTRA_RECIPE_SAVE_INSTANCE_DETAIL_ACT = "EXTRA_RECIPE_SAVE_INSTANCE_DETAIL_ACT";

    @BindView(R.id.cp_container) protected ViewPager mViewPager;
    protected SectionsPagerAdapter mSectionsPagerAdapter;
    private Recipe mRecipe;
    @BindBool(R.bool.isLarge) protected boolean mIsLargeScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();
        if (extras == null && savedInstanceState == null) {
            throw new RuntimeException(this.toString() + " must send recipe from Recipe Activity to Detail Activity.");
        } else if (extras != null) {
            mRecipe = extras.getParcelable(RECIPE_KEY_RECIPE_ACT_TO_DETAIL_ACT);
            String title = mRecipe.getName();
            if (title != null && !title.isEmpty()) {
                this.setTitle(mRecipe.getName());
            }
        } else {
            mRecipe = savedInstanceState.getParcelable(EXTRA_RECIPE_SAVE_INSTANCE_DETAIL_ACT);
            String title = mRecipe.getName();
            if (title != null && !title.isEmpty()) {
                this.setTitle(mRecipe.getName());
            }
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
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
    public void onDetailPassData(String stepId) {
        if (mIsLargeScreen) {
            //TODO show two panels with step.
        } else {
            Intent intent = new Intent(this, StepActivity.class);
            intent.putExtra(RECIPE_KEY_DETAIL_ACT_TO_STEP_ACT, mRecipe);
            intent.putExtra(STEP_ID_KEY_DETAIL_ACT_TO_STEP_ACT, stepId);
            startActivity(intent);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(EXTRA_RECIPE_SAVE_INSTANCE_DETAIL_ACT, mRecipe);
        super.onSaveInstanceState(outState);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: //Ingredients
                    return IngredientsFragment.newInstance(mRecipe);
                case 1: // Details
                    // TODO Show fragments base on device screen.
                    return DetailFragment.newInstance(mRecipe);
                    default:
                        throw new RuntimeException(this.toString() + "Wrong fragment!");
            }
        }

        @Override
        public int getCount() { return 2; }
    }
}
