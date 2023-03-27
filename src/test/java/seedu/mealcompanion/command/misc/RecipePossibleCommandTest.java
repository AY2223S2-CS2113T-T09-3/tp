package seedu.mealcompanion.command.misc;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.mealcompanion.MealCompanionException;
import seedu.mealcompanion.MealCompanionSession;
import seedu.mealcompanion.ingredient.Ingredient;
import seedu.mealcompanion.ingredient.IngredientList;
import seedu.mealcompanion.recipe.InstructionList;
import seedu.mealcompanion.recipe.Recipe;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

class RecipePossibleCommandTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }


    @Test
    public void recipePossibleAndRecipeNotPossible() throws MealCompanionException {
        MealCompanionSession mealCompanionSession = new MealCompanionSession();
        // add allergens to mealCompanionSession
        mealCompanionSession.setAllergens(Arrays.asList("milk", "eggs"));

        // Add ingredients to mealCompanionSession's ingredients
        mealCompanionSession.getIngredients().add(new Ingredient("bread", 2.0));
        mealCompanionSession.getIngredients().add(new Ingredient("ham", 1.0));

        // Add hamSandwich recipe to mealCompanionSession
        // hamSandwich is possible
        IngredientList hamSandwichIngredients = new IngredientList();
        hamSandwichIngredients.add(new Ingredient("bread", 2.0));
        hamSandwichIngredients.add(new Ingredient("ham", 1.0));
        Recipe hamSandwich = new Recipe("ham sandwich", 200, 30, 10,
                hamSandwichIngredients, new InstructionList());
        mealCompanionSession.getRecipes().add(hamSandwich);

        // Add eggSandwich recipe to mealCompanionSession.
        // eggSandwich is not possible.
        IngredientList eggSandwichIngredients = new IngredientList();
        eggSandwichIngredients.add(new Ingredient("bread", 2.0));
        eggSandwichIngredients.add(new Ingredient("egg", 1.0));
        Recipe eggSandwich = new Recipe("egg sandwich", 200, 30, 10,
                eggSandwichIngredients, new InstructionList());
        mealCompanionSession.getRecipes().add(eggSandwich);

        // Test
        RecipePossibleCommand command = new RecipePossibleCommand();
        command.execute(mealCompanionSession);
        String osName = System.getProperty("os.name");
        String newline = "\n";
        if (osName.contains("Windows")) {
            newline = "\r\n";
        }
        String predictedOutput = "Please enter allergens (comma-separated): " + newline +
                "Here are the recipe(s) that you can make:" + newline + "1. ham sandwich" + newline;
        assertEquals(predictedOutput, outContent.toString());
    }
}

