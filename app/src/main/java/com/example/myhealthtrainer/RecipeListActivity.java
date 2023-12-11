package com.example.myhealthtrainer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myhealthtrainer.adapter.RecipeAdapter;
import com.example.myhealthtrainer.adapter.WorkoutAdapter;
import com.example.myhealthtrainer.model.recipe;
import com.example.myhealthtrainer.model.workout;
import com.example.myhealthtrainer.viewmodel.MainActivityViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * RecipeListActivity class represents an activity that displays a list of recipes.
 */
public class RecipeListActivity extends AppCompatActivity {

    // UI components
    private RecyclerView recyclerView;
    private RecipeAdapter recipeAdapter;
    private List<recipe> recipeList;

    // Firebase components
    private FirebaseFirestore firestore;
    private CollectionReference recipeCollection;

    /**
     * Called when the activity is first created. Responsible for initializing the activity,
     * setting up the RecyclerView, and loading data from Firestore.
     *
     * @param savedInstanceState A Bundle containing the saved state of the activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize recipe list and adapter
        recipeList = new ArrayList<>();
        recipeAdapter = new RecipeAdapter(this, recipeList);
        recyclerView.setAdapter(recipeAdapter);

        // Initialize Firestore components
        firestore = FirebaseFirestore.getInstance();
        recipeCollection = firestore.collection("users/" + MainActivityViewModel.getUser().getUid() + "/recipes");

        // Load data from Firestore
        loadDataFromFirestore();
    }

    /**
     * Loads recipe data from Firestore and updates the RecyclerView.
     */
    private void loadDataFromFirestore() {
        recipeCollection.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        // Extract recipe details from Firestore document
                        String recipeName = document.getString("recipeName");
                        String ingredients = document.getString("ingredientsList");
                        String calories = document.getString("numCalories");
                        String carbs = String.valueOf(document.getLong("numCarbs"));
                        String fat = String.valueOf(document.getLong("numFat"));
                        String protein = String.valueOf(document.getLong("numProtein"));
                        String sodium = String.valueOf(document.getLong("numSodium"));
                        String sugar = String.valueOf(document.getLong("numSugar"));

                        // Create Recipe object and add it to the recipeList
                        recipe Recipe = new recipe(recipeName, ingredients, calories, carbs, fat, protein, sodium, sugar);
                        recipeList.add(Recipe);
                    }
                    // Notify the adapter that the data has changed
                    recipeAdapter.notifyDataSetChanged();
                } else {
                    // Handle errors
                }
            }
        });
    }
}
