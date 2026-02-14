package codesignal;

import java.util.*;

import static java.util.Collections.emptyMap;
import static java.util.Collections.emptySortedMap;

// Recipe Manager
//
// == Level 1 ==
// CRUD with Case-Insensitive Name Uniqueness
// Implement basic Create, Read, Update, and Delete operations for recipes.
// When adding or updating a recipe, the recipe name must be treated as
// case-insensitive and must not conflict with existing recipe names.
//
// ADD_RECIPE <recipeId> <name> <ingredients...> — add a new recipe with the given
//   integer ID, name, and list of ingredients. Fails if a recipe with the same ID
//   already exists, or if the name conflicts with an existing recipe (case-insensitive).
//   Returns "true" if added successfully, "false" otherwise.
//
// GET_RECIPE <recipeId> — return the recipe formatted as "<name>: [<ing1>, <ing2>, ...]"
//   with ingredients sorted alphabetically. Returns "" if the recipe doesn't exist.
//
// UPDATE_RECIPE <recipeId> <name> <ingredients...> — update an existing recipe's name
//   and ingredients. The new name must not conflict with other recipes (case-insensitive),
//   but may match the recipe's own current name (different case is OK). Returns "true"
//   if updated, "false" if the recipe doesn't exist or name conflicts with another recipe.
//
// DELETE_RECIPE <recipeId> — delete the recipe. Returns "true" if deleted, "false" if
//   the recipe doesn't exist.
//
// == Level 2 ==
// Search and Sort Recipes
// Implement recipe search functionality with sorting.
//
// SEARCH_RECIPES <ingredient> — find all recipes containing the given ingredient (exact match).
//   Return results formatted as "<id1>(<name1>), <id2>(<name2>), ..." sorted by:
//   1) number of ingredients in ascending order
//   2) recipe ID in ascending numerical order (tiebreaker)
//   Returns "" if no recipes match.
//
// == Level 3 ==
// User Validation for Editing
// Introduce a user system. When performing an edit_recipe operation, validate that
// the provided user_id exists before applying the update.
//
// ADD_USER <userId> — register a new user. Returns "true" if added, "false" if
//   the user already exists.
//
// EDIT_RECIPE <recipeId> <userId> <name> <ingredients...> — update the recipe,
//   but first validate that userId exists. Returns "false" if the user doesn't exist,
//   the recipe doesn't exist, or the name conflicts. Returns "true" on success.
//
// == Level 4 ==
// Version Control
// Edit and update operations append a new version to the recipe's version history
// instead of overwriting existing data. AddRecipe creates version 1.
//
// UPDATE_RECIPE / EDIT_RECIPE — now append a new version instead of overwriting.
//   GetRecipe returns the latest version.
//
// ROLLBACK <recipeId> <version> — create a new version by copying the specified
//   previous version. Check for recipe name conflicts using the same case-insensitive
//   rule (the rolled-back name must not conflict with other recipes' current names).
//   Returns "true" on success, "false" if the recipe doesn't exist, version is invalid,
//   or name conflicts.
//
// GET_VERSION <recipeId> <version> — return the recipe at the specified version,
//   formatted like GET_RECIPE. Returns "" if recipe or version doesn't exist.
public class RecipeManager {

    private final Set<String> recipeNames = new HashSet<>();
    private final Map<Integer, SortedMap<Integer, Recipe>> recipes = new HashMap<>();
    private final Map<String, Set<Recipe>> recipesByIngredients = new HashMap<>();
    private final Set<String> users = new HashSet<>();

    public class Recipe {

        private final int id;
        private final String name;
        private final List<String> ingredients;

        public Recipe(int id, String name, String... ingredients) {
            this.id = id;
            this.name = name;
            this.ingredients = Arrays.asList(ingredients);
        }

        @Override
        public int hashCode() {
            return this.id;
        }

        @Override
        public boolean equals(Object other) {
            if (other == null || !(other instanceof Recipe)) {
                return false;
            }
            return this.hashCode() == other.hashCode();
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder();
            sb.append(name).append(": [");

            if (!ingredients.isEmpty()) {
                Collections.sort(ingredients);
                for (String ingr : ingredients) {
                    sb.append(ingr).append(", ");
                }
                sb.setLength(sb.length() - 2);
            }

            sb.append("]");
            return sb.toString();
        }
    }

    // Level 1
    private String AddRecipe(int recipeId, String name, String... ingredients) {
        if (
            this.recipes.get(recipeId) != null ||
            this.recipeNames.contains(name.toLowerCase())
        ) {
            return "false";
        }

        final Recipe r = new Recipe(recipeId, name, ingredients);
        this.recipeNames.add(name.toLowerCase());

        this.recipes.compute(recipeId, (Integer k, SortedMap<Integer, Recipe> v) -> {
            int version = 1;
            if (v == null) {
                v = new TreeMap<>();
            } else {
                version = v.lastKey() + 1;
            }

            v.put(version, r);
            return v;
        });

        for (String ingr : ingredients) {
            this.recipesByIngredients.compute(
                ingr,
                (String k, Set<Recipe> v) -> {
                    if (v == null) {
                        v = new HashSet<>();
                    }
                    v.add(r);
                    return v;
                }
            );
        }

        return "true";
    }

    private Recipe getLatestRecipe(int recipeId) {
        final Map.Entry<Integer, Recipe> e = this.recipes.getOrDefault(recipeId, emptySortedMap()).lastEntry();
        if (e == null) {
            return null;
        }
        return e.getValue();
    }

    private String GetRecipe(int recipeId) {
        // "<name>: [<ing1>, <ing2>, ...]"
        final Recipe r = getLatestRecipe(recipeId);
        if (r == null) {
            return "";
        }

        return r.toString();
    }

    private String UpdateRecipe(
        int recipeId,
        String name,
        String... ingredients
    ) {
        final Recipe r = this.getLatestRecipe(recipeId);
        if (r == null) {
            return "false";
        }

        if (!r.name.equalsIgnoreCase(name)) {
            if (this.recipeNames.contains(name.toLowerCase())) {
                return "false";
            }

            this.recipeNames.remove(r.name.toLowerCase());
            this.recipeNames.add(name.toLowerCase());
        }

        final Recipe newr = new Recipe(recipeId, name, ingredients);
        // remove old ingredients from reverse index
        for (String ingr : r.ingredients) {
            this.recipesByIngredients.get(ingr).remove(r);
        }
        // add new ingredients to reverse index
        for (String ingrd : ingredients) {
            this.recipesByIngredients.compute(
                ingrd,
                (String k, Set<Recipe> v) -> {
                    if (v == null) {
                        v = new HashSet<>();
                    }
                    v.add(newr);
                    return v;
                }
            );
        }
        // above could be improved by skipping removing/adding ingrs that did not change

        final SortedMap<Integer, Recipe> versions = this.recipes.get(recipeId);
        final int newVersion = versions.lastKey()+1;
        versions.put(newVersion, newr);

        return "true";
    }

    private String DeleteRecipe(int recipeId) {
        final Recipe r = this.getLatestRecipe(recipeId);
        if (r == null) {
            return "false";
        }

        this.recipeNames.remove(r.name.toLowerCase());
        for (String ingr : r.ingredients) {
            this.recipesByIngredients.get(ingr).remove(r);
        }

        this.recipes.remove(recipeId);

        return "true";
    }

    // Level 2
    private String SearchRecipes(String ingredient) {
        // SEARCH_RECIPES <ingredient> — find all recipes containing the given ingredient (exact match).
        //   Return results formatted as "<id1>(<name1>), <id2>(<name2>), ..." sorted by:
        //   1) number of ingredients in ascending order
        //   2) recipe ID in ascending numerical order (tiebreaker)
        //   Returns "" if no recipes match.
        final Set<Recipe> matches = this.recipesByIngredients.get(ingredient);
        if (matches == null || matches.isEmpty()) {
            return "";
        }

        final List<Recipe> sorted = new ArrayList<>(matches);
        sorted.sort(Comparator
                .comparingInt((Recipe l) -> l.ingredients.size())
                .thenComparingInt(l -> l.id)
        );

        final StringBuilder sb = new StringBuilder();
        for (Recipe r: sorted) {
            sb.append(r.id).append("(").append(r.name).append("), ");
        }

        sb.setLength(sb.length()-2);
        return sb.toString();
    }

    // Level 3
    private String AddUser(String userId) {
        if (this.users.contains(userId)) {
            return "false";
        }

        this.users.add(userId);
        return "true";
    }

    private String EditRecipe(
        int recipeId,
        String userId,
        String name,
        String... ingredients
    ) {
        // EDIT_RECIPE <recipeId> <userId> <name> <ingredients...> — update the recipe,
        //   but first validate that userId exists. Returns "false" if the user doesn't exist,
        //   the recipe doesn't exist, or the name conflicts. Returns "true" on success.
        if (!this.users.contains(userId)) {
            return "false";
        }

        return UpdateRecipe(recipeId, name, ingredients);
    }

    // Level 4
    private String Rollback(int recipeId, int version) {
        // ROLLBACK <recipeId> <version> — create a new version by copying the specified
        //   previous version. Check for recipe name conflicts using the same case-insensitive
        //   rule (the rolled-back name must not conflict with other recipes' current names).
        //   Returns "true" on success, "false" if the recipe doesn't exist, version is invalid,
        //   or name conflicts.

        final SortedMap<Integer, Recipe> versions = this.recipes.get(recipeId);
        if (versions == null) {
            return "false";
        }

        final Recipe r = versions.get(version);
        if (r == null) {
            return "false";
        }

        return UpdateRecipe(recipeId, r.name, r.ingredients.toArray(new String[]{}));
    }

    private String GetVersion(int recipeId, int version) {
        // GET_VERSION <recipeId> <version> — return the recipe at the specified version,
        //   formatted like GET_RECIPE. Returns "" if recipe or version doesn't exist.
        final SortedMap<Integer, Recipe> versions = this.recipes.get(recipeId);
        if (versions == null) {
            return "";
        }

        final Recipe r = versions.get(version);
        if (r == null) {
            return "";
        }

        return r.toString();
    }

    public static void main(String[] args) {
        // level 1 - CRUD with case-insensitive name uniqueness
        {
            final RecipeManager rm = new RecipeManager();
            // basic add
            assertEquals(1, "true", (int t) ->
                rm.AddRecipe(1, "Pasta", "tomato", "cheese", "noodles")
            );
            // case-insensitive duplicate name
            assertEquals(2, "false", (int t) ->
                rm.AddRecipe(2, "pasta", "garlic", "oil")
            );
            assertEquals(3, "false", (int t) ->
                rm.AddRecipe(2, "PASTA", "garlic", "oil")
            );
            // different name succeeds
            assertEquals(4, "true", (int t) ->
                rm.AddRecipe(2, "Salad", "lettuce", "tomato", "cucumber")
            );
            // duplicate recipe id
            assertEquals(5, "false", (int t) ->
                rm.AddRecipe(1, "Soup", "water", "salt")
            );
            // get existing recipe (ingredients sorted alphabetically)
            assertEquals(6, "Pasta: [cheese, noodles, tomato]", (int t) ->
                rm.GetRecipe(1)
            );
            assertEquals(7, "Salad: [cucumber, lettuce, tomato]", (int t) ->
                rm.GetRecipe(2)
            );
            // get non-existing recipe
            assertEquals(8, "", (int t) -> rm.GetRecipe(99));
            // update with non-conflicting new name
            assertEquals(9, "true", (int t) ->
                rm.UpdateRecipe(1, "Spaghetti", "tomato", "meatball", "noodles")
            );
            assertEquals(
                10,
                "Spaghetti: [meatball, noodles, tomato]",
                (int t) -> rm.GetRecipe(1)
            );
            // update with name conflicting with another recipe (case-insensitive)
            assertEquals(11, "false", (int t) ->
                rm.UpdateRecipe(1, "SALAD", "stuff")
            );
            // update keeping own name (different case) — no conflict with self
            assertEquals(12, "true", (int t) ->
                rm.UpdateRecipe(1, "spaghetti", "pasta", "sauce")
            );
            assertEquals(13, "spaghetti: [pasta, sauce]", (int t) ->
                rm.GetRecipe(1)
            );
            // update non-existing recipe
            assertEquals(14, "false", (int t) ->
                rm.UpdateRecipe(99, "Anything", "a")
            );
            // delete existing recipe
            assertEquals(15, "true", (int t) -> rm.DeleteRecipe(2));
            assertEquals(16, "", (int t) -> rm.GetRecipe(2));
            // delete non-existing recipe
            assertEquals(17, "false", (int t) -> rm.DeleteRecipe(2));
            // name freed after delete — can reuse
            assertEquals(18, "true", (int t) ->
                rm.AddRecipe(3, "Salad", "kale", "dressing")
            );
        }

        // level 2 - Search and Sort Recipes
        {
            final RecipeManager rm = new RecipeManager();
            assertEquals(1, "true", (int t) ->
                rm.AddRecipe(5, "Bruschetta", "bread", "tomato")
            );
            assertEquals(2, "true", (int t) ->
                rm.AddRecipe(1, "Pasta", "tomato", "cheese", "noodles")
            );
            assertEquals(3, "true", (int t) ->
                rm.AddRecipe(
                    2,
                    "Salad",
                    "lettuce",
                    "tomato",
                    "cucumber",
                    "onion"
                )
            );
            assertEquals(4, "true", (int t) ->
                rm.AddRecipe(3, "Toast", "bread", "butter")
            );
            assertEquals(5, "true", (int t) ->
                rm.AddRecipe(4, "Sandwich", "bread", "cheese")
            );
            // search "tomato": Bruschetta(2 ings), Pasta(3 ings), Salad(4 ings)
            assertEquals(6, "5(Bruschetta), 1(Pasta), 2(Salad)", (int t) ->
                rm.SearchRecipes("tomato")
            );
            // search "bread": all have 2 ings — tiebreak by id ascending
            assertEquals(7, "3(Toast), 4(Sandwich), 5(Bruschetta)", (int t) ->
                rm.SearchRecipes("bread")
            );
            // search "cheese": Sandwich(2 ings), Pasta(3 ings)
            assertEquals(8, "4(Sandwich), 1(Pasta)", (int t) ->
                rm.SearchRecipes("cheese")
            );
            // search with no results
            assertEquals(9, "", (int t) -> rm.SearchRecipes("garlic"));
            // delete a recipe and verify search reflects it
            assertEquals(10, "true", (int t) -> rm.DeleteRecipe(5));
            assertEquals(11, "1(Pasta), 2(Salad)", (int t) ->
                rm.SearchRecipes("tomato")
            );
        }

        // level 3 - User Validation for Editing
        {
            final RecipeManager rm = new RecipeManager();
            assertEquals(1, "true", (int t) ->
                rm.AddRecipe(1, "Pasta", "tomato", "cheese")
            );
            assertEquals(2, "true", (int t) ->
                rm.AddRecipe(2, "Salad", "lettuce", "tomato")
            );
            // add users
            assertEquals(3, "true", (int t) -> rm.AddUser("alice"));
            assertEquals(4, "true", (int t) -> rm.AddUser("bob"));
            assertEquals(5, "false", (int t) -> rm.AddUser("alice")); // duplicate
            // edit with valid user
            assertEquals(6, "true", (int t) ->
                rm.EditRecipe(1, "alice", "Pasta", "tomato", "cheese", "garlic")
            );
            assertEquals(7, "Pasta: [cheese, garlic, tomato]", (int t) ->
                rm.GetRecipe(1)
            );
            // edit with non-existent user
            assertEquals(8, "false", (int t) ->
                rm.EditRecipe(1, "charlie", "Pasta", "basil")
            );
            assertEquals(9, "Pasta: [cheese, garlic, tomato]", (int t) ->
                rm.GetRecipe(1)
            ); // unchanged
            // edit non-existing recipe with valid user
            assertEquals(10, "false", (int t) ->
                rm.EditRecipe(99, "alice", "Soup", "water")
            );
            // edit with name conflict (case-insensitive)
            assertEquals(11, "false", (int t) ->
                rm.EditRecipe(1, "bob", "SALAD", "stuff")
            );
            // edit keeping own name (different case) with valid user
            assertEquals(12, "true", (int t) ->
                rm.EditRecipe(2, "bob", "salad", "kale", "dressing")
            );
            assertEquals(13, "salad: [dressing, kale]", (int t) ->
                rm.GetRecipe(2)
            );
        }

        // level 4 - Version Control
        {
            final RecipeManager rm = new RecipeManager();
            assertEquals(1, "true", (int t) -> rm.AddUser("alice"));
            // AddRecipe creates version 1
            assertEquals(2, "true", (int t) ->
                rm.AddRecipe(1, "Pasta", "tomato", "cheese")
            );
            // UpdateRecipe creates version 2
            assertEquals(3, "true", (int t) ->
                rm.UpdateRecipe(1, "Spaghetti", "tomato", "meatball")
            );
            assertEquals(4, "Spaghetti: [meatball, tomato]", (int t) ->
                rm.GetRecipe(1)
            );
            // EditRecipe creates version 3
            assertEquals(5, "true", (int t) ->
                rm.EditRecipe(
                    1,
                    "alice",
                    "Spaghetti Bolognese",
                    "tomato",
                    "meatball",
                    "onion"
                )
            );
            assertEquals(
                6,
                "Spaghetti Bolognese: [meatball, onion, tomato]",
                (int t) -> rm.GetRecipe(1)
            );
            // verify version history
            assertEquals(7, "Pasta: [cheese, tomato]", (int t) ->
                rm.GetVersion(1, 1)
            );
            assertEquals(8, "Spaghetti: [meatball, tomato]", (int t) ->
                rm.GetVersion(1, 2)
            );
            assertEquals(
                9,
                "Spaghetti Bolognese: [meatball, onion, tomato]",
                (int t) -> rm.GetVersion(1, 3)
            );
            assertEquals(10, "", (int t) -> rm.GetVersion(1, 4)); // doesn't exist yet
            // Rollback to version 1 → creates version 4
            assertEquals(11, "true", (int t) -> rm.Rollback(1, 1));
            assertEquals(12, "Pasta: [cheese, tomato]", (int t) ->
                rm.GetRecipe(1)
            );
            assertEquals(13, "Pasta: [cheese, tomato]", (int t) ->
                rm.GetVersion(1, 4)
            );
            // add another recipe
            assertEquals(14, "true", (int t) ->
                rm.AddRecipe(2, "Pasta Primavera", "pasta", "vegetables")
            );
            // UpdateRecipe → version 5
            assertEquals(15, "true", (int t) ->
                rm.UpdateRecipe(1, "Carbonara", "pasta", "egg", "bacon")
            );
            // Rollback to version 4 (name="Pasta") — no conflict with "Pasta Primavera"
            assertEquals(16, "true", (int t) -> rm.Rollback(1, 4));
            assertEquals(17, "Pasta: [cheese, tomato]", (int t) ->
                rm.GetRecipe(1)
            );
            // UpdateRecipe → version 7
            assertEquals(18, "true", (int t) ->
                rm.UpdateRecipe(1, "Risotto", "rice", "broth")
            );
            // add recipe 3 with name "Pasta" (available since recipe 1 is now "Risotto")
            assertEquals(19, "true", (int t) ->
                rm.AddRecipe(3, "Pasta", "flour", "egg")
            );
            // Rollback recipe 1 to version 6 (name="Pasta") — conflicts with recipe 3
            assertEquals(20, "false", (int t) -> rm.Rollback(1, 6));
            assertEquals(21, "Risotto: [broth, rice]", (int t) ->
                rm.GetRecipe(1)
            ); // unchanged
            // invalid rollbacks
            assertEquals(22, "false", (int t) -> rm.Rollback(99, 1)); // non-existent recipe
            assertEquals(23, "false", (int t) -> rm.Rollback(1, 99)); // non-existent version
            assertEquals(24, "false", (int t) -> rm.Rollback(1, 0)); // version 0 doesn't exist
            // EditRecipe with invalid user still fails
            assertEquals(25, "false", (int t) ->
                rm.EditRecipe(1, "unknown", "Risotto", "rice")
            );
        }

        System.out.println("all tests passed");
    }

    public interface Operation<T> {
        public T exec(int t);
    }

    public static <T> void assertEquals(int t, T exp, Operation<T> f) {
        T act = f.exec(t);
        if ((exp == null && act != null) || (exp != null && act == null)) {
            throw new RuntimeException(
                t + ": expected " + exp + " but got " + act
            );
        }
        if (exp != act && !act.equals(exp)) {
            throw new RuntimeException(
                t + ": expected " + exp + " but got " + act
            );
        }
    }
}
