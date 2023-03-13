package seedu.duke.command.misc;

import seedu.duke.DukeException;
import seedu.duke.DukeSession;
import seedu.duke.command.ExecutableCommand;
import seedu.duke.ingredient.Ingredient;

/**
 * Represents the "add" command.
 */
public class AddCommand extends ExecutableCommand {

    String name;
    String amount;

    public AddCommand(String arguments, String flag) {
        this.name = arguments;
        this.amount = flag;
    }

    private void addToExistingIngredients(DukeSession dukeSession, Double quantity, int index) {
        double newQuantity = dukeSession.getIngredients().get(index).getQuantity() + quantity;
        dukeSession.getIngredients().get(index).setQuantity(newQuantity);
        dukeSession.getUi().printMessage("Here is the new quantity of the ingredient:");
        dukeSession.getUi().printMessage(String.valueOf(dukeSession.getIngredients().get(index)));
        dukeSession.getIngredientStorage().writeIngredientsToFile(dukeSession.getIngredients());
    }

    private void addNewIngredient(DukeSession dukeSession, Double quantity, String name) {
        Ingredient ingredient = new Ingredient(name, quantity);
        dukeSession.getIngredients().add(ingredient);
        dukeSession.getUi().printMessage("the following ingredient has been added");
        dukeSession.getUi().printMessage(String.valueOf(ingredient));
        dukeSession.getIngredientStorage().writeIngredientToFile(ingredient);
    }

    public int findIndex(DukeSession dukeSession, String name) {
        for (int i = 0; i < dukeSession.getIngredients().size(); i += 1) {
            if (dukeSession.getIngredients().get(i).getName().equals(name)) {
                return i;
            }
        }
        return -1;
    }

    public void execute(DukeSession dukeSession) {
        try {
            Double quantity = Double.parseDouble(amount);
            if (quantity <= 0) {
                throw new DukeException("OOPS, quantity must be greater than 0");
            }
            if (name.isBlank()) {
                throw new DukeException("OOPS, name cannot be blank");
            }
            int indexOfExistingIngredient = findIndex(dukeSession, name);
            if (indexOfExistingIngredient == -1) {
                addNewIngredient(dukeSession, quantity, name);
            } else {
                addToExistingIngredients(dukeSession, quantity, indexOfExistingIngredient);
            }
        } catch (Exception e) {
            dukeSession.getUi().printMessage(String.valueOf(e));
        }
    }
}
