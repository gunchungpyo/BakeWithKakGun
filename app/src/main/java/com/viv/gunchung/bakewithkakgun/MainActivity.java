package com.viv.gunchung.bakewithkakgun;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.viv.gunchung.bakewithkakgun.adapter.RecipeAdapter;
import com.viv.gunchung.bakewithkakgun.models.Recipe;
import com.viv.gunchung.bakewithkakgun.utilities.BakingUtils;

import org.parceler.Parcels;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Response;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.RecipeAdapterOnClickHandler, LoaderManager.LoaderCallbacks<List<Recipe>> {

    private static final int RECIPE_LOADER_ID = 22;

    private RecipeAdapter mAdapter;

    @BindView(R.id.tv_error_message)
    TextView mErrorMessageTextView;
    @BindView(R.id.pb_loading_indicator)
    ProgressBar mLoadingIndicator;
    @BindView(R.id.rv_recipe)
    RecyclerView mRecipeRecycleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setRecycleViewGridColumn();
        mAdapter = new RecipeAdapter(this, this);
        mRecipeRecycleView.setAdapter(mAdapter);

        int loaderId = RECIPE_LOADER_ID;
        LoaderManager.LoaderCallbacks<List<Recipe>> callback = MainActivity.this;

        getSupportLoaderManager().initLoader(loaderId, null, callback);

        if (Timber.treeCount() <= 0) {
            Timber.plant(new Timber.DebugTree());
        }

    }

    @Override
    public Loader<List<Recipe>> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<List<Recipe>>(this) {

            List<Recipe> mRecipeData = null;

            @Override
            protected void onStartLoading() {
                Timber.d("onStartLoading Start");

                if (mRecipeData != null) {
                    Timber.d("mRecipeData != null");
                    deliverResult(mRecipeData);
                } else {
                    Timber.d("mRecipeData == null");
                    mLoadingIndicator.setVisibility(View.VISIBLE);
                    forceLoad();
                }
            }

            @Override
            public List<Recipe> loadInBackground() {
                Timber.d("loadInBackground Start");

                try {
                    Response recipeResponse = BakingUtils.getResponseFromServer();
                    List<Recipe> recipes = BakingUtils.parseToRecipeList(recipeResponse);

                    return recipes;
                } catch (IOException e) {
                    Timber.e("Error loadInBackground: " + e.getMessage());
                    return null;
                }
            }

            @Override
            public void deliverResult(List<Recipe> data) {
                Timber.d("deliverResult Start");
                mRecipeData = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<List<Recipe>> loader, List<Recipe> recipes) {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        if (recipes != null) {
            showRecipeDataView();
            mAdapter.setRecipeList(recipes);
        } else {
            showErrorMessage();
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Recipe>> loader) {

    }

    @Override
    public void onClick(Recipe selectedRecipe) {
        Timber.d("onClick: " + selectedRecipe.getName());

        Context context = this;
        Class destinationClass = RecipeActivity.class;
        Intent intentToStartRecipeActivity = new Intent(context, destinationClass);
        String parcelKey = BakingUtils.SELECTED_RECIPE_KEY;

        intentToStartRecipeActivity.putExtra(parcelKey, Parcels.wrap(selectedRecipe));

        startActivity(intentToStartRecipeActivity);
    }

    private void setRecycleViewGridColumn() {
        Configuration config = getResources().getConfiguration();
        if (config.orientation == Configuration.ORIENTATION_PORTRAIT) {
            mRecipeRecycleView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        } else {
            mRecipeRecycleView.setLayoutManager(new GridLayoutManager(this, 2));
        }
        mRecipeRecycleView.setItemAnimator(new DefaultItemAnimator());
    }

    private void showRecipeDataView() {
        mErrorMessageTextView.setVisibility(View.INVISIBLE);
        mRecipeRecycleView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        mRecipeRecycleView.setVisibility(View.INVISIBLE);
        mErrorMessageTextView.setVisibility(View.VISIBLE);
    }
}
