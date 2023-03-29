package seedu.mealcompanion.command.ingredients;

import seedu.mealcompanion.MealCompanionException;
import seedu.mealcompanion.MealCompanionSession;
import seedu.mealcompanion.command.ExecutableCommand;

//@@author TJW0911
/**
 * Represents the "remove" command.
 */

public class RemoveCommand extends ExecutableCommand {

    private static int indexOfExistingIngredient;
    String name;
    String amount;

    public RemoveCommand(String arguments, String flag) {
        this.name = arguments;
        this.amount = flag;
    }


    /**
     * Validates if the remove command can be executed with the arguments provided and throws
     * exception when it cannot be done
     *
     * @param mealCompanionSession the MealCompanionSession containing the list of ingredients
     * @param quantity the quantity of ingredient to be removed
     * @param name the name of the ingredient to be removed
     * @throws MealCompanionException when user input results in unexpected behaviour
     */
    private static void validateInput(MealCompanionSession mealCompanionSession, Double quantity, String name)
            throws MealCompanionException {
        if (quantity <= 0) {
            throw new MealCompanionException("OOPS, quantity must be greater than 0");
        }
        if (indexOfExistingIngredient == -1) {
            throw new MealCompanionException("OOPS, ingredient is not in fridge");
        }
        if (quantity > mealCompanionSession.getIngredients().get(indexOfExistingIngredient).getQuantity()) {
            throw new MealCompanionException("OOPS, quantity to remove is more than quantity in the fridge");
        }
    }

    /**
     * Removes a specified quantity of an ingredient from the ingredient list
     *
     * @param mealCompanionSession the MealCompanionSession containing the list of ingredients
     * @param quantity the quantity of ingredient to be removed
     * @param name the name of the ingredient to be removed
     */

    private static void removeIngredient(MealCompanionSession mealCompanionSession, Double quantity, String name) {
        double fridgeQuantity = mealCompanionSession.getIngredients().get(indexOfExistingIngredient).getQuantity();
        assert fridgeQuantity >= quantity: "fridgeQuantity should be more than quantity to be removed";
        double newQuantity = fridgeQuantity - quantity;
        mealCompanionSession.getIngredients().get(indexOfExistingIngredient).setQuantity(newQuantity);
        mealCompanionSession.getUi().printMessage(
                String.format("Success! new quantity of %s is %f", name, newQuantity));
        if (newQuantity == 0) {
            mealCompanionSession.getIngredients().remove(indexOfExistingIngredient);
            mealCompanionSession.getUi().printMessage(String.format("All %s has been removed", name));
        }
        mealCompanionSession.getIngredientStorage().writeIngredientsToFile(mealCompanionSession.getIngredients());
    }

    /**
     * Executes the remove command
     *
     * @param mealCompanionSession the MealCompanionSession containing the list of ingredients
     */

    public void execute(MealCompanionSession mealCompanionSession) {
        try {
            Double quantity = Double.parseDouble(amount);
            indexOfExistingIngredient = mealCompanionSession.getIngredients().findIndex(name);
            validateInput(mealCompanionSession, quantity, name);
            removeIngredient(mealCompanionSession, quantity, name);
        } catch (NumberFormatException e) {
            mealCompanionSession.getUi().printMessage("OOPS, please input a number for quantity");
        } catch (NullPointerException e) {
            mealCompanionSession.getUi().printMessage("OOPS, Certain fields are empty");
            mealCompanionSession.getUi().printMessage("please follow the format: add <ingredient> /qty <quantity>");
        } catch (Exception e) {
            mealCompanionSession.getUi().printMessage(String.valueOf(e));
        }
    }
}
