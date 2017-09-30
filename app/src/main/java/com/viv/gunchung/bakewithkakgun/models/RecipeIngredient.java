package com.viv.gunchung.bakewithkakgun.models;

import org.parceler.Parcel;

/**
 * Created by gunawan on 17/09/17.
 */

@Parcel
public class RecipeIngredient {

    int quantity;
    String measure;
    String ingredient;

    public RecipeIngredient() {
    }

    public RecipeIngredient(int quantity, String measure, String ingredient) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }
}
