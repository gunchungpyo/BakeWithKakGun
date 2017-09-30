package com.viv.gunchung.bakewithkakgun;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.viv.gunchung.bakewithkakgun.adapter.RecipeStepAdapter;
import com.viv.gunchung.bakewithkakgun.models.Recipe;
import com.viv.gunchung.bakewithkakgun.models.RecipeStep;
import com.viv.gunchung.bakewithkakgun.ui.RecipeIngredientsFragment;
import com.viv.gunchung.bakewithkakgun.ui.RecipeIntroFragment;
import com.viv.gunchung.bakewithkakgun.ui.RecipeStepsFragment;
import com.viv.gunchung.bakewithkakgun.utilities.BakingUtils;

import org.parceler.Parcels;

import timber.log.Timber;

public class RecipeActivity extends AppCompatActivity implements RecipeStepAdapter.RecipeStepAdapterOnClickHandler {

    private Recipe mSelectedRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity != null) {
            String parcelKey = BakingUtils.SELECTED_RECIPE_KEY;
            mSelectedRecipe = (Recipe) Parcels.unwrap(getIntent().getParcelableExtra(parcelKey));

            String recipeTitle = mSelectedRecipe.getName();
            setTitle(recipeTitle);
        }

        Timber.d("savedInstanceState is null?: ");

        if (savedInstanceState == null) {
            Timber.d("savedInstanceState is NULL BRO");

            // Add the fragment to its container using a FragmentManager and a Transaction
            FragmentManager fragmentManager = getSupportFragmentManager();

            // intro
            RecipeIntroFragment introFragment = new RecipeIntroFragment();
            introFragment.setSelectedRecipe(mSelectedRecipe);
            fragmentManager.beginTransaction()
                    .add(R.id.intro_container, introFragment)
                    .commit();

            // ingredients
            RecipeIngredientsFragment ingredientsFragment = new RecipeIngredientsFragment();
            ingredientsFragment.setSelectedRecipe(mSelectedRecipe);
            fragmentManager.beginTransaction()
                    .add(R.id.ingredient_container, ingredientsFragment)
                    .commit();

            // steps
            RecipeStepsFragment stepsFragment = new RecipeStepsFragment();
            stepsFragment.setSelectedRecipe(mSelectedRecipe);
            fragmentManager.beginTransaction()
                    .add(R.id.step_container, stepsFragment)
                    .commit();

        }
    }

    @Override
    public void onClick(RecipeStep selectedStep, int index) {
        Timber.d("onClick: " + index);
        Timber.d("onClick: " + selectedStep.getShortDescription());

        Context context = this;
        Class destinationClass = StepActivity.class;
        Intent intentToStartRecipeActivity = new Intent(context, destinationClass);

        String parcelRecipeKey = BakingUtils.SELECTED_RECIPE_KEY;
        intentToStartRecipeActivity.putExtra(parcelRecipeKey, Parcels.wrap(mSelectedRecipe));

        String stepIdxKey = BakingUtils.SELECTED_STEP_IDX;
        intentToStartRecipeActivity.putExtra(stepIdxKey, index);

        startActivity(intentToStartRecipeActivity);
    }
}
