package seedu.duke.recipe;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

/**
 * Singleton class which represents a list of all known ingredients
 */
public class IngredientDatabase {
    private static IngredientDatabase dbInstance = null;
    private HashMap<String, IngredientMetadata> knownIngredients;

    private IngredientDatabase() {
        this.knownIngredients = new HashMap<>();
        Gson gson = new Gson();
        try (Reader reader = new InputStreamReader(this.getClass().getResourceAsStream("/ingredients.json"))) {
            // This is needed for GSON to return the expected instance of List<IngredientMetadata>
            Type ingredientMetadataListType =
                    TypeToken.getParameterized(List.class, IngredientMetadata.class).getType();
            List<IngredientMetadata> ingredientMetadataList = gson.fromJson(reader, ingredientMetadataListType);

            for (IngredientMetadata ingredient : ingredientMetadataList) {
                knownIngredients.put(ingredient.getName(), ingredient);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Invalid ingredients list JSON resource." +
                    "This should be caught by automatic testing");
        }
    }

    /**
     * @return the singleton instance of IngredientDatabase
     */
    public static IngredientDatabase getDbInstance() {
        if (dbInstance == null) {
            dbInstance = new IngredientDatabase();
        }
        return dbInstance;
    }

    /**
     * @return a hashmap between ingredient names and their metadata
     */
    public HashMap<String, IngredientMetadata> getKnownIngredients() {
        return knownIngredients;
    }
}
