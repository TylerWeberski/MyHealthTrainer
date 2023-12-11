package com.example.myhealthtrainer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myhealthtrainer.R;
import com.example.myhealthtrainer.model.recipe;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    private Context context;
    private List<recipe> recipeList;

    public RecipeAdapter(Context context, List<recipe> recipeList) {
        this.context = context;
        this.recipeList = recipeList;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recipe_item, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        recipe Recipe = recipeList.get(position);
        holder.recipeNameView.setText(Recipe.getRecipeName());
        holder.ingredientsView.setText(Recipe.getIngredients());
        holder.caloriesView.setText(Recipe.getCalories());
        holder.carbsView.setText(Recipe.getCarbs());
        holder.fatView.setText(Recipe.getFat());
        holder.proteinView.setText(Recipe.getProtein());
        holder.sodiumView.setText(Recipe.getSodium());
        holder.sugarView.setText(Recipe.getSugar());
    }

    @Override
    public int getItemCount(){
        return recipeList.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder{
        TextView recipeNameView, ingredientsView, caloriesView, carbsView, fatView, proteinView, sodiumView, sugarView;

        public RecipeViewHolder(@NonNull View itemView){
            super(itemView);
            recipeNameView = itemView.findViewById(R.id.recipeNameView);
            ingredientsView = itemView.findViewById(R.id.ingredientsView);
            caloriesView = itemView.findViewById(R.id.caloriesView);
            carbsView = itemView.findViewById(R.id.carbsView);
            fatView = itemView.findViewById(R.id.fatView);
            proteinView = itemView.findViewById(R.id.proteinView);
            sodiumView = itemView.findViewById(R.id.sodiumView);
            sugarView = itemView.findViewById(R.id.sugarView);
        }
    }
}
