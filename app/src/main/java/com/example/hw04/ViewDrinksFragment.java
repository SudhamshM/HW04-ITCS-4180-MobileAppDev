package com.example.hw04;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hw04.databinding.FragmentViewDrinksBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class ViewDrinksFragment extends Fragment
{
    private static final String ARG_PARAM_DRINKS = "ARG_PARAM_DRINKS";
    public ArrayList<Drink> mDrinks;
    ViewDrinksRecyclerAdapter adapter;

    int alcPctSortOrder = 0;
    int addedDateSortOrder = 0;
    public ViewDrinksFragment()
    {
        // Required empty public constructor
    }

    public static ViewDrinksFragment newInstance(ArrayList<Drink> drinks)
    {
        ViewDrinksFragment fragment = new ViewDrinksFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_DRINKS, drinks);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mDrinks = (ArrayList<Drink>) getArguments().getSerializable(ARG_PARAM_DRINKS);
        }
    }

    FragmentViewDrinksBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        binding = FragmentViewDrinksBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void sortAlcByPct(int order)
    {
        Log.d("hw04", "onClick: imageAlcAscend order: " + alcPctSortOrder);
        if (order == 1 && alcPctSortOrder != 1)
        {
            Collections.sort(this.mDrinks, new Comparator<Drink>()
            {
                @Override
                public int compare(Drink drink1, Drink drink2)
                {
                    return Double.compare(drink1.alcohol, drink2.alcohol);
                }
            });
            alcPctSortOrder = 1;
        }
        else if (order == -1 && alcPctSortOrder != -1)
        {
            Collections.sort(this.mDrinks, new Comparator<Drink>()
            {

                @Override
                public int compare(Drink drink1, Drink drink2)
                {
                    return -1 * Double.compare(drink1.alcohol, drink2.alcohol);
                }
            });
            alcPctSortOrder = -1;
        }
    }

    public void sortAlcByDate(int order)
    {
        Log.d("hw04", "onClick: date order: " + addedDateSortOrder);
        if (order == 1 && addedDateSortOrder != 1)
        {
            Collections.sort(this.mDrinks, new Comparator<Drink>()
            {

                @Override
                public int compare(Drink drink1, Drink drink2)
                {
                    return drink1.addedOn.compareTo(drink2.addedOn);
                }
            });
            addedDateSortOrder = 1;
        }
        else if (order == -1 && addedDateSortOrder != -1)
        {
            Collections.sort(this.mDrinks, new Comparator<Drink>()
            {
                @Override
                public int compare(Drink drink1, Drink drink2)
                {
                    return drink2.addedOn.compareTo(drink1.addedOn);
                }
            });
            addedDateSortOrder = -1;
        }

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("View Drinks");
        if (mDrinks.isEmpty())
        {
            Toast.makeText(getActivity(), "All drinks are deleted!", Toast.LENGTH_SHORT).show();
            mListener.closeViewDrinks();

        }

        binding.imageAlcAscend.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                sortAlcByPct(1);
                adapter.notifyDataSetChanged();
            }
        });

        binding.imageAlcDescend.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                sortAlcByPct(-1);
                adapter.notifyDataSetChanged();
            }
        });

        binding.imageDateAscend.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                sortAlcByDate(1);
                adapter.notifyDataSetChanged();
            }
        });

        binding.imageDateDescend.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                sortAlcByDate(-1);
                adapter.notifyDataSetChanged();
            }
        });

        binding.buttonClose.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                mListener.closeViewDrinks();
            }
        });
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerView.setHasFixedSize(true);
        adapter = new ViewDrinksRecyclerAdapter(mDrinks);
        binding.recyclerView.setAdapter(adapter);

    }

    ViewDrinksFragmentListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (ViewDrinksFragmentListener) context;
    }



    interface ViewDrinksFragmentListener
    {
        void closeViewDrinks();

    }

    public class ViewDrinksRecyclerAdapter
            extends RecyclerView.Adapter<ViewDrinksRecyclerAdapter.DrinkViewHolder>
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
            holder.position = position;
            holder.setupDrinkInfo(drink);

            holder.deleteDrinkImage.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    deleteDrink(holder.position);
                }

            });

        }


        @Override
        public int getItemCount()
        {
            return drinksList.size();
        }

        public void deleteDrink(int position)
        {
            this.drinksList.remove(position);
            notifyDataSetChanged();
            if (mDrinks.isEmpty())
            {
                Toast.makeText(getActivity(), "All drinks are deleted.", Toast.LENGTH_SHORT).show();
                mListener.closeViewDrinks();
            }

        }

        public class DrinkViewHolder extends RecyclerView.ViewHolder
        {
            TextView alcPercent, addedDate, drinkSize;
            ImageView deleteDrinkImage;
            Drink drink;
            int position;


            public DrinkViewHolder(@NonNull View itemView)
            {
                super(itemView);
                alcPercent = itemView.findViewById(R.id.textAlcPercentRow);
                addedDate = itemView.findViewById(R.id.textAddedDateRow);
                drinkSize = itemView.findViewById(R.id.textAddedDrinkSizeRow);
                deleteDrinkImage = itemView.findViewById(R.id.imageDeleteDrinkRow);

            }
            public void setupDrinkInfo(Drink drink)
            {
                alcPercent.setText((int) drink.alcohol + "% Alcohol");
                drinkSize.setText((int) drink.size + " oz");
                addedDate.setText(drink.addedOn.toString());
                this.drink = drink;
            }
        }

    }



}