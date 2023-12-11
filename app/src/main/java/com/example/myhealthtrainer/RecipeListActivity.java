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

public class RecipeListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecipeAdapter recipeAdapter;
    private List<recipe> recipeList;

    private FirebaseFirestore firestore;
    private CollectionReference recipeCollection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recipeList = new ArrayList<>();
        recipeAdapter = new RecipeAdapter(this, recipeList);
        recyclerView.setAdapter(recipeAdapter);

        firestore = FirebaseFirestore.getInstance();
        recipeCollection = firestore.collection("users/" + MainActivityViewModel.getUser().getUid() + "/recipes");

        loadDataFromFirestore();
    }

    private void loadDataFromFirestore() {
        recipeCollection.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String recipeName = document.getString("recipeName");
                        String ingredients = document.getString("ingredientsList");
                        String calories = document.getString("numCaloires");
                        String carbs = document.getString("numCarbs");
                        String fat = document.getString("numFat");
                        String protein = document.getString("numProtein");
                        String sodium = document.getString("numSodium");
                        String sugar = document.getString("numSugar");

                        recipe Recipe = new recipe(recipeName, ingredients, Integer.parseInt(calories), Integer.parseInt(carbs), Integer.parseInt(fat), Integer.parseInt(protein), Integer.parseInt(sodium), Integer.parseInt(sugar));
                        recipeList.add(Recipe);
                    }
                    recipeAdapter.notifyDataSetChanged();
                } else {
                    // Handle errors
                }
            }
        });
    }
}
