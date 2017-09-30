package com.viv.gunchung.bakewithkakgun.models;

import org.parceler.Parcel;

import java.util.List;

/**
 * Created by gunawan on 17/09/17.
 */

@Parcel
public class Recipe {

    int id;
    String name;
    List<RecipeIngredient> ingredients;
    List<RecipeStep> steps;
    int servings;
    String image;

    public Recipe() {
    }

    public Recipe(int id, String name, List<RecipeIngredient> ingredients, List<RecipeStep> steps, int servings, String image) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.servings = servings;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public List<RecipeIngredient> getIngredients() {
        return ingredients;
    }

    public List<RecipeStep> getSteps() {
        return steps;
    }

    public int getServings() {
        return servings;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIngredients(List<RecipeIngredient> ingredients) {
        this.ingredients = ingredients;
    }

    public void setSteps(List<RecipeStep> steps) {
        this.steps = steps;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
