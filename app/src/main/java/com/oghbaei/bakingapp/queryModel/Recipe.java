package com.oghbaei.bakingapp.queryModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mohsen on 18.04.2018.
 *
 */

public class Recipe implements Parcelable {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("ingredients")
    private List<Ingredient> ingredients;
    @SerializedName("steps")
    private List<Step> steps;
    @SerializedName("servings")
    private String servings;
    @SerializedName("image")
    private String image;

    public void setId(String id) {this.id = id;}

    public String getImage() {return image; }

    public void setName(String name) { this.name = name; }

    public void setIngredients(List<Ingredient> ingredients) { this.ingredients = ingredients; }

    public void setSteps(List<Step> steps) { this.steps = steps; }

    public void setServings(String servings) { this.servings = servings; }

    public void setImage(String image) { this.image = image; }

    public String getName() { return name; }

    public String getId() { return id; }

    public List<Step> getSteps() { return steps; }

    public Step getStep(int stepId) {
        return steps.get(stepId);
    }

    public List<Integer> listOfStepIds() {
        List<Integer> ret = new ArrayList<>();
        for (Step step: steps) {
            ret.add(Integer.valueOf(step.getId()));
        }
        return ret;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeTypedList(this.ingredients);
        dest.writeTypedList(this.steps);
        dest.writeString(this.servings);
        dest.writeString(this.image);
    }

    public Recipe() {
    }

    protected Recipe(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.ingredients = in.createTypedArrayList(Ingredient.CREATOR);
        this.steps = in.createTypedArrayList(Step.CREATOR);
        this.servings = in.readString();
        this.image = in.readString();
    }

    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel source) {
            return new Recipe(source);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };
}
