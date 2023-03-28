package seedu.mealcompanion.ingredient;

import seedu.mealcompanion.MealCompanionException;
import seedu.mealcompanion.recipe.IngredientMetadata;

import java.util.ArrayList;

//@@author TJW0911
public class IngredientList {
    ArrayList<Ingredient> ingredients;

    public IngredientList() {
        ingredients = new ArrayList<>();
    }

    public void add(Ingredient ingredient) {
        ingredients.add(ingredient);
    }

    public Ingredient get(int i) {
        return ingredients.get(i);
    }

    //@@author jingyaaa
    /**
     * Fetch an ingredient by its specified name.
     * @param ingredientName string containing ingredient name to look for
     * @return ingredient found
     */
    public Ingredient get(String ingredientName) throws MealCompanionException {
        for (Ingredient ingredient : ingredients) {
            if (ingredientName.equals(ingredient.getMetadata().getName())) {
                return ingredient;
            }
        }
        throw new MealCompanionException("Oops, ingredient not found");
    }

    //@@author TJW0911
    public void remove(int i) {
        ingredients.remove(i);
    }

    public int size() {
        return ingredients.size();
    }

    public void clear() {
        ingredients.clear();
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    //@@author ngyida
    @Override
    public String toString() {
        StringBuilder ingredientListDetails = new StringBuilder();
        for (int i = 0; i < ingredients.size(); i++) {
            Ingredient ingredient = ingredients.get(i);
            String ingredientDetails = ingredient.toString();
            ingredientListDetails.append((i+1) + ". " + ingredientDetails + System.lineSeparator());
        }
        return String.valueOf(ingredientListDetails);
    }
}
