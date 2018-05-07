package com.oghbaei.bakingapp;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.MenuItem;

import com.oghbaei.bakingapp.queryModel.Recipe;

import butterknife.BindBool;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.oghbaei.bakingapp.RecipeActivity.RECIPE_KEY_RECIPE_ACT_TO_DETAIL_ACT;
import static com.oghbaei.bakingapp.StepActivity.RECIPE_KEY_DETAIL_ACT_TO_STEP_ACT;
import static com.oghbaei.bakingapp.StepActivity.STEP_ID_KEY_DETAIL_ACT_TO_STEP_ACT;


public class DetailActivity extends AppCompatActivity implements DetailFragment.OnDetailFragmentInteractionListener {

    private static final String VIEW_PAGER_ID = "VIEW_PAGER_ID";


    @BindView(R.id.cp_container) protected ViewPager mViewPager;
    @BindBool(R.bool.isLarge) protected boolean mIsLargeScreen;
    protected SectionsPagerAdapter mSectionsPagerAdapter;
    private Recipe mRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        int viewPagerId = -1;
        if (savedInstanceState != null) {
            viewPagerId = savedInstanceState.getInt(VIEW_PAGER_ID, -1);
        }

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mRecipe = extras.getParcelable(RECIPE_KEY_RECIPE_ACT_TO_DETAIL_ACT);
            String title = mRecipe.getName();
            if (title != null && !title.isEmpty()) {
                this.setTitle(mRecipe.getName());
            }
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        if (viewPagerId != -1) {
            mViewPager.setId(viewPagerId);
        }
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(10); // make a big enough number to be sure that Adapter will cache the fragment references.
        // Look at https://stackoverflow.com/a/9646622/6072457

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
            DetailStepWideScreenFragment fragment = mSectionsPagerAdapter.getCurrentFragment();
            if (fragment != null) fragment.replaceStepFragment(stepId);
        } else {
            Intent intent = new Intent(this, StepActivity.class);
            intent.putExtra(RECIPE_KEY_DETAIL_ACT_TO_STEP_ACT, mRecipe);
            intent.putExtra(STEP_ID_KEY_DETAIL_ACT_TO_STEP_ACT, stepId);
            startActivity(intent);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(VIEW_PAGER_ID, mViewPager.getId());
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private DetailStepWideScreenFragment currentFragment;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            currentFragment = null;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: //Ingredients
                    return IngredientsFragment.newInstance(mRecipe);
                case 1: // Details
                {
                    // Show base on screen size.
                    if (mIsLargeScreen) {
                        DetailStepWideScreenFragment detailStepWideScreenFragment = DetailStepWideScreenFragment.newInstance(mRecipe, "0");
                        currentFragment = detailStepWideScreenFragment;
                        return detailStepWideScreenFragment;
                    } else {
                        return DetailFragment.newInstance(mRecipe);
                    }
                }
                default:
                    throw new RuntimeException(this.toString() + " Wrong fragment!");
            }
        }

        @Override
        public int getCount() { return 2; }

        public DetailStepWideScreenFragment getCurrentFragment() {
            return currentFragment;
        }


    }

}
