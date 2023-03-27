package seedu.mealcompanion.command.misc;

import seedu.mealcompanion.MealCompanionSession;
import seedu.mealcompanion.command.ExecutableCommand;
import seedu.mealcompanion.ingredient.Ingredient;
import seedu.mealcompanion.ingredient.IngredientList;
import seedu.mealcompanion.recipe.Recipe;
import seedu.mealcompanion.recipe.RecipeList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents the "recipe possible" command.
 */
public class RecipePossibleCommand extends ExecutableCommand {

    /**
     * Check if an ingredientList has a sufficient amount of an ingredient.
     *
     * @param recipeIngredient  the ingredient to look for
     * @param fridgeIngredients the list of ingredients to check in
     * @return true if the list of ingredients have sufficient quantity of that ingredient, false otherwise
     */
    private boolean hasEnoughIngredient(Ingredient recipeIngredient, IngredientList fridgeIngredients) {
        String recipeIngredientName = recipeIngredient.getMetadata().getName();
        double recipeIngredientQty = recipeIngredient.getQuantity();
        for (Ingredient fridgeIngredient : fridgeIngredients.getIngredients()) {
            if (fridgeIngredient.getMetadata().getName().equals(recipeIngredientName)
                    && fridgeIngredient.getQuantity() >= recipeIngredientQty) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if a recipe can be made using a list of ingredients given and exclude recipes that contain any allergens.
     *
     * @param recipe the recipe to be made
     * @param fridgeIngredients the list of ingredients used to make the recipe
     * @param allergens the list of allergens specified by the user
     * @return true if the recipe can be made using the list of ingredients without any allergens, false otherwise
     */
    private boolean canMakeRecipe(Recipe recipe, IngredientList fridgeIngredients, List<String> allergens) {
        IngredientList recipeIngredients = recipe.getIngredients();
        for (Ingredient recipeIngredient : recipeIngredients.getIngredients()) {
            if (allergens.contains(recipeIngredient.getMetadata().getName())) {
                return false;
            }
            if (!hasEnoughIngredient(recipeIngredient, fridgeIngredients)) {
                return false;
            }
        }
        return true;
    }

    /**
     * List all recipes that can be made using ingredients that are available and do not contain any allergens.
     *
     * @param mealCompanionSession the MealCompanionSession containing the list of recipes, ingredients, and allergens
     */
    @Override
    public void execute(MealCompanionSession mealCompanionSession) {
        mealCompanionSession.getUi().printMessage("Please enter allergens (comma-separated): ");
        String allergensStr = mealCompanionSession.getUi().getNextCommandString();
        List<String> allergens = Arrays.asList(allergensStr.split(","));
        mealCompanionSession.setAllergens(allergens);
        IngredientList fridgeIngredients = mealCompanionSession.getIngredients();
        RecipeList recipes = mealCompanionSession.getRecipes();
        int index = 1;
        mealCompanionSession.getUi().printMessage("Here are the recipe(s) that you can make:");
        for (Recipe recipe : recipes.getRecipes()) {
            if (canMakeRecipe(recipe, fridgeIngredients, allergens)) {
                mealCompanionSession.getUi().printMessage(index + ". " + recipe.getName());
                index++;
            }
        }
    }

}
