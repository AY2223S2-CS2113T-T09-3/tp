package seedu.duke.recipe;

import seedu.duke.ingredient.Ingredient;
import seedu.duke.ingredient.IngredientList;

import java.util.ArrayList;

public class RecipeList {
    private ArrayList<Recipe> recipes;


    Ingredient groundBeef = new Ingredient("ground beef", 1);
    Ingredient salt = new Ingredient("salt", 1);
    Ingredient blackPepper = new Ingredient("black pepper", 0.5);
    Ingredient worcestershireSauce = new Ingredient("Worcestershire sauce", 1);
    Ingredient garlicPowder = new Ingredient("garlic powder", 1);
    Ingredient hamburgerBuns = new Ingredient("hamburger buns", 2);
    Ingredient toppings = new Ingredient("toppings (any of your choice", 0);

    public IngredientList getBeefBurgerIngredients() {
        IngredientList beefBurgerIngredients = new IngredientList();
        beefBurgerIngredients.add(groundBeef);
        beefBurgerIngredients.add(salt);
        beefBurgerIngredients.add(blackPepper);
        beefBurgerIngredients.add(worcestershireSauce);
        beefBurgerIngredients.add(garlicPowder);
        beefBurgerIngredients.add(hamburgerBuns);
        beefBurgerIngredients.add(toppings);

        return beefBurgerIngredients;
    }

    Instruction firstStep = new Instruction("In a large bowl, mix the ground beef, salt, pepper, Worcestershire" +
            " sauce and garlic powder until just combined");
    Instruction secondStep = new Instruction("Divide the mixture into 4 equal portions and shape each portion " +
            "into a patty about 1/2 inches thick");
    Instruction thirdStep = new Instruction("Heat a large non-stick pan or griddle over medium heat");
    Instruction fourthStep = new Instruction("Cook the patties for about 4 minutes on each side, or until the " +
            "internal temperature reaches 73C/160F for well-done patties");
    Instruction fifthStep = new Instruction("Toast the hamburger buns on the griddle or in the oven until " +
            "golden brown");
    Instruction sixthStep = new Instruction("Assemble the burgers with your desired toppings and serve. This recipe" +
            " serves two");

    public InstructionList getBeefBurgerInstructions() {
        InstructionList beefBurgerInstructions = new InstructionList();
        beefBurgerInstructions.add(firstStep);
        beefBurgerInstructions.add(secondStep);
        beefBurgerInstructions.add(thirdStep);
        beefBurgerInstructions.add(fourthStep);
        beefBurgerInstructions.add(fifthStep);
        beefBurgerInstructions.add(sixthStep);
        return beefBurgerInstructions;
    }

    Recipe beefBurger = new Recipe("Beef Burger", getBeefBurgerIngredients(), getBeefBurgerInstructions());


    public RecipeList() {
        recipes = new ArrayList<>();
        recipes.add(beefBurger);
    }



    public ArrayList<Recipe> getRecipes() {
        return recipes;
    }
}
