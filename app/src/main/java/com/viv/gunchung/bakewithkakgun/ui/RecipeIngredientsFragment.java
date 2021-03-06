package com.viv.gunchung.bakewithkakgun.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.viv.gunchung.bakewithkakgun.R;
import com.viv.gunchung.bakewithkakgun.adapter.RecipeIngredientAdapter;
import com.viv.gunchung.bakewithkakgun.models.Recipe;
import com.viv.gunchung.bakewithkakgun.utilities.BakingUtils;

import org.parceler.Parcels;

import timber.log.Timber;

/**
 * Created by Gunawan on 17/09/2017.
 */

public class RecipeIngredientsFragment extends Fragment {

    private Recipe mSelected;
    protected RecyclerView mRecyclerView;
    protected RecipeIngredientAdapter mAdapter;

    public RecipeIngredientsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Timber.d("onCreateView Start");

        if (savedInstanceState != null) {
            String parcelKey = BakingUtils.SELECTED_RECIPE_KEY;
            mSelected = Parcels.unwrap(savedInstanceState.getParcelable(parcelKey));
        }

        final View rootView = inflater.inflate(R.layout.fragment_recipe_ingredients, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_recipe_ingredient);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAdapter = new RecipeIngredientAdapter(mSelected.getIngredients());
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle currentState) {
        String parcelKey = BakingUtils.SELECTED_RECIPE_KEY;
        currentState.putParcelable(parcelKey, Parcels.wrap(mSelected));
    }

    public void setSelectedRecipe(Recipe mSelected) {
        this.mSelected = mSelected;
    }
}
