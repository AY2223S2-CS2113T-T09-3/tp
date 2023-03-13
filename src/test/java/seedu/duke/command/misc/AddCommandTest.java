package seedu.duke.command.misc;

import org.junit.jupiter.api.Test;
import seedu.duke.DukeSession;
import seedu.duke.ingredient.Ingredient;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AddCommandTest {
    @Test
    public void itemIsInListAndItemNotInList() {
        DukeSession dukeSession = new DukeSession();
        Ingredient ingredient = new Ingredient("apple", 2);
        dukeSession.getIngredients().add(ingredient);

        AddCommand command = new AddCommand("apple", "2");

        assertEquals(0,command.findIndex(dukeSession, "apple"));
        assertEquals(-1,command.findIndex(dukeSession, "orange"));
    }

    @Test
    public void addNewIngredientAndAddToExistingIngredient() {
        DukeSession dukeSession = new DukeSession();
        new AddCommand("apple", "2").execute(dukeSession);
        assertEquals(2, dukeSession.getIngredients().get(0).getQuantity());
        new AddCommand("apple", "3").execute(dukeSession);
        assertEquals(5, dukeSession.getIngredients().get(0).getQuantity());
    }
}
