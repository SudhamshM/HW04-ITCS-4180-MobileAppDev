package com.example.hw04;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hw04.databinding.FragmentViewDrinksBinding;

import java.util.ArrayList;

public class ViewDrinksFragment extends Fragment
{
    private static final String ARG_PARAM_DRINKS = "ARG_PARAM_DRINKS";
    public ArrayList<Drink> mDrinks;
    ViewDrinksRecyclerAdapter adapter;
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("View Drinks");
        if (mDrinks.isEmpty())
        {
            Toast.makeText(getActivity(), "All drinks are deleted!", Toast.LENGTH_SHORT).show();
        }

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



}