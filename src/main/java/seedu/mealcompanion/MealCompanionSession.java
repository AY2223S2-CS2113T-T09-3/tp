package seedu.mealcompanion;

import seedu.mealcompanion.command.ExecutableCommand;
import seedu.mealcompanion.command.factory.ExecutableCommandFactory;
import seedu.mealcompanion.command.factory.ingredients.IngredientsListCommandFactory;
import seedu.mealcompanion.command.factory.ingredients.IngredientsSearchCommandFactory;
import seedu.mealcompanion.command.factory.misc.AddCommandFactory;
import seedu.mealcompanion.command.factory.misc.ByeCommandFactory;
import seedu.mealcompanion.command.factory.misc.ClearCommandFactory;
import seedu.mealcompanion.command.factory.misc.HelloWorldCommandFactory;
import seedu.mealcompanion.command.factory.misc.HelpCommandFactory;
import seedu.mealcompanion.command.factory.misc.RemoveCommandFactory;
import seedu.mealcompanion.command.factory.misc.MakeCommandFactory;
import seedu.mealcompanion.command.factory.misc.RecipeAllCommandFactory;
import seedu.mealcompanion.command.factory.misc.RecipeDetailCommandFactory;
import seedu.mealcompanion.command.factory.misc.RecipePossibleCommandFactory;
import seedu.mealcompanion.command.factory.misc.RecipeNeedCommandFactory;
import seedu.mealcompanion.command.factory.misc.RecipeRandomCommandFactory;
import seedu.mealcompanion.ingredient.IngredientList;
import seedu.mealcompanion.parser.CommandArguments;
import seedu.mealcompanion.parser.CommandTokens;
import seedu.mealcompanion.recipe.RecipeList;
import seedu.mealcompanion.router.CommandRouterNode;
import seedu.mealcompanion.storage.IngredientStorage;
import seedu.mealcompanion.ui.MealCompanionUI;
import java.util.List;
import java.util.ArrayList;



import java.util.Scanner;

public class MealCompanionSession {

    private static final CommandRouterNode COMMAND_TREE =
            new CommandRouterNode()

                    .route("hello", new CommandRouterNode()
                            .route("world", new HelloWorldCommandFactory())
                    )
                    .route("bye", new ByeCommandFactory())
                    .route("add", new AddCommandFactory())
                    .route("clear", new ClearCommandFactory())
                    .route("help", new HelpCommandFactory())
                    .route("remove", new RemoveCommandFactory())
                    .route("make", new MakeCommandFactory())
                    .route("recipe", new CommandRouterNode()
                            .route("possible", new RecipePossibleCommandFactory())
                            .route("all", new RecipeAllCommandFactory())
                            .route("random", new RecipeRandomCommandFactory())
                    )
                    .route("recipe", new CommandRouterNode()
                            .route("detail", new RecipeDetailCommandFactory())
                            .route("need", new RecipeNeedCommandFactory())
                    )
                    .route("ingredients", new CommandRouterNode()
                            .route("list", new IngredientsListCommandFactory())
                            .route("search", new IngredientsSearchCommandFactory())
                    );

    private final IngredientList ingredients;
    private final RecipeList recipes;
    private final MealCompanionUI ui;
    private final MealCompanionControlFlow controlFlow;
    private final IngredientStorage ingredientStorage;
    private final List<String> allergens;




    public MealCompanionSession() {
        this.ui = new MealCompanionUI(new Scanner(System.in));
        this.controlFlow = new MealCompanionControlFlow();
        this.ingredients = new IngredientList();
        this.recipes = new RecipeList();
        this.ingredientStorage = new IngredientStorage();
        this.allergens = new ArrayList<>();
    }

    public void setAllergens(List<String> allergens) {
        this.allergens.clear();
        this.allergens.addAll(allergens);
    }



    /**
     * Returns the <code>MealCompanionUI</code> for the current session.
     *
     * @return Handle to control UI.
     */
    public MealCompanionUI getUi() {
        return ui;
    }

    /**
     * Returns the <code>MealCompanionControlFlow</code> for the current session.
     *
     * @return Handle to set the control flow.
     */
    public MealCompanionControlFlow getControlFlow() {
        return controlFlow;
    }

    /**
     * Returns the <code>IngredientList</code> for the current session.
     *
     * @return Handle to access the Ingredient List.
     */
    public IngredientList getIngredients() {
        return ingredients;
    }

    /**
     * Returns the <code>RecipeList</code> for the current session.
     *
     * @return Handle to access the Recipe List.
     */
    public RecipeList getRecipes() {
        return recipes;
    }

    public IngredientStorage getIngredientStorage() {
        return ingredientStorage;
    }

    /**
     * Runs the read, evaluate, print loop for MealCompanion.
     */
    public void runRepl() {
        ingredientStorage.getFile(this.ingredients);
        this.ui.printIntroduction();
        while (this.controlFlow.shouldRun()) {
            String nextCommand = ui.getNextCommandString();
            CommandTokens tokens = new CommandTokens(nextCommand);
            ExecutableCommandFactory commandFactory = MealCompanionSession.COMMAND_TREE.resolve(tokens);

            if (commandFactory == null) {
                System.out.println("Not a command!");
                continue;
            }

            ExecutableCommand cmd = commandFactory.buildCommand(this, new CommandArguments(tokens));
            cmd.execute(this);
        }
    }
}
