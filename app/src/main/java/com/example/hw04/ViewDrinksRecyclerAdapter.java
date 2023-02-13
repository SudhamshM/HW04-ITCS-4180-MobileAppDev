package com.example.hw04;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.util.ArrayList;

public class ViewDrinksRecyclerAdapter extends RecyclerView.Adapter<ViewDrinksRecyclerAdapter.DrinkViewHolder>
{
    private ArrayList<Drink> drinksList;

    public ViewDrinksRecyclerAdapter(ArrayList<Drink> drinksList)
    {
        this.drinksList = drinksList;
    }

    @NonNull
    @Override
    public DrinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.drink_row, parent, false);
        DrinkViewHolder viewHolder = new DrinkViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DrinkViewHolder holder, int position)
    {
        Drink drink = drinksList.get(position);
        holder.alcPercent.setText(drink.alcohol + " % Alcohol");
        holder.drinkSize.setText(drink.size + " oz");
        holder.addedDate.setText(drink.addedOn.toString());
        holder.drink = drink;
    }


    @Override
    public int getItemCount()
    {
        return drinksList.size();
    }

    public static class DrinkViewHolder extends RecyclerView.ViewHolder
    {
        TextView alcPercent, addedDate, drinkSize;
        Drink drink;

        public DrinkViewHolder(@NonNull View itemView)
        {
            super(itemView);
            alcPercent = itemView.findViewById(R.id.textAlcPercentRow);
            addedDate = itemView.findViewById(R.id.textAddedDateRow);
            drinkSize = itemView.findViewById(R.id.textAddedDrinkSizeRow);


        }
    }
}
