package com.oghbaei.bakingapp;

import android.os.Bundle;

import com.oghbaei.bakingapp.queryModel.Ingredient;
import com.oghbaei.bakingapp.queryModel.Recipe;
import com.oghbaei.bakingapp.queryModel.Step;

import java.util.ArrayList;
import java.util.List;

class RecipeDummy {

    private RecipeDummy() {}

    public static Recipe newInstance() {
        Recipe recipe = new Recipe();
        recipe.setId("1");
        recipe.setName("Nutella Pie");
        recipe.setImage("");
        recipe.setServings("8");
        recipe.setIngredients(getIngredients());
        recipe.setSteps(getSteps());


        return recipe;
    }

    private static List<Ingredient> getIngredients() {
        List<Ingredient> ingredients = new ArrayList<>();
        Ingredient ing1 = new Ingredient("2", "CUP", "Graham Cracker crumbs");
        ingredients.add(ing1);
        Ingredient ing2 = new Ingredient("6", "TBLSP", "unsalted butter, melted");
        ingredients.add(ing2);
        return ingredients;
    }

    private static List<Step> getSteps() {
        List<Step> steps = new ArrayList<>();
        Step step1 = new Step("0", "Recipe Introduction", "Recipe Introduction",
                "https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd974_-intro-creampie/-intro-creampie.mp4",
                "");
        steps.add(step1);
        Step step2 = new Step("1", "Starting prep", "1. Preheat the oven to 350\\u00b0F. Butter a 9\\\" deep dish pie pan.",
                "", "");
        steps.add(step2);

        return steps;
    }
}
