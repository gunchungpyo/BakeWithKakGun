package com.viv.gunchung.bakewithkakgun;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.viv.gunchung.bakewithkakgun.models.Recipe;
import com.viv.gunchung.bakewithkakgun.models.RecipeStep;
import com.viv.gunchung.bakewithkakgun.ui.StepDetailFragment;
import com.viv.gunchung.bakewithkakgun.utilities.BakingUtils;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class StepActivity extends AppCompatActivity {

    private Recipe mSelectedRecipe;
    private RecipeStep mSelectedStep;
    private int mStepIds;

    @BindView(R.id.tv_step_detail_of)
    TextView mStepOfTextView;
    @BindView(R.id.btn_step_prev)
    TextView mPrevButton;
    @BindView(R.id.btn_step_next)
    TextView mNextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity != null) {
            Timber.d("intentThatStartedThisActivity != null");

            String parcelRecipeKey = BakingUtils.SELECTED_RECIPE_KEY;
            mSelectedRecipe = (Recipe) Parcels.unwrap(getIntent().getParcelableExtra(parcelRecipeKey));

            String stepIdxKey = BakingUtils.SELECTED_STEP_IDX;

            if (savedInstanceState != null) {
                mStepIds = savedInstanceState.getInt(stepIdxKey);
            } else {
                mStepIds = intentThatStartedThisActivity.getIntExtra(stepIdxKey, 0);
            }

            mSelectedStep = mSelectedRecipe.getSteps().get(mStepIds);
        }

        if (savedInstanceState == null) {
            Timber.d("savedInstanceState == null");
            // Add the fragment to its container using a FragmentManager and a Transaction
            FragmentManager fragmentManager = getSupportFragmentManager();

            // detail step
            StepDetailFragment stepDetailFragment = new StepDetailFragment();
            stepDetailFragment.setSelectedStep(mSelectedStep);

            fragmentManager.beginTransaction()
                    .add(R.id.step_detail_container, stepDetailFragment)
                    .commit();
        }

        setTitle();
        setFooter();
        setButtonVisibilty();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public void setFooter() {
        String currentStep = String.valueOf(mStepIds + 1);
        String allStep = String.valueOf(mSelectedRecipe.getSteps().size());
        String footer = currentStep + "/" + allStep;
        mStepOfTextView.setText(footer);
    }

    public void setTitle() {
        String stepTitle = String.valueOf(mStepIds + 1) + ". " + mSelectedStep.getShortDescription();
        setTitle(stepTitle);
    }

    public void setButtonVisibilty() {
        if (mStepIds == 0) {
            mPrevButton.setVisibility(View.INVISIBLE);
        } else {
            mPrevButton.setVisibility(View.VISIBLE);
        }

        int allStep = mSelectedRecipe.getSteps().size();
        if (mStepIds == allStep - 1) {
            mNextButton.setVisibility(View.INVISIBLE);
        } else {
            mNextButton.setVisibility(View.VISIBLE);
        }
    }

    public void swapFragment() {
        mSelectedStep = mSelectedRecipe.getSteps().get(mStepIds);
        FragmentManager fragmentManager = getSupportFragmentManager();

        // detail step
        StepDetailFragment nextDetailFragment = new StepDetailFragment();
        nextDetailFragment.setSelectedStep(mSelectedStep);

        fragmentManager.beginTransaction()
                .replace(R.id.step_detail_container, nextDetailFragment)
                .commit();

        setTitle();
        setFooter();
        setButtonVisibilty();
    }

    public void nextStep(View v) {
        mStepIds++;
        swapFragment();
    }

    public void prevStep(View v) {
        mStepIds--;
        swapFragment();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(BakingUtils.SELECTED_STEP_IDX, mStepIds);
        super.onSaveInstanceState(outState);
    }
}
