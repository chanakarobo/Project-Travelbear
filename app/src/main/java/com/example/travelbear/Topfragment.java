package com.example.travelbear;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class Topfragment extends Fragment {


    public Topfragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View view =inflater.inflate(
                R.layout.fragment_topfragment,container,false);

       RecyclerView  pizzaRecycler=(RecyclerView) view.findViewById(R.id.pizza_recycler);

        String[] topnames=new String[TopImages.topimages.length];

        for(int i=0;i<topnames.length;i++){
            topnames[i]=TopImages.topimages[i].getName();
        }

        int[] topImageIds=new int[TopImages.topimages.length];

        for(int i=0;i<topImageIds.length;i++){

            topImageIds[i]=TopImages.topimages[i].getImageResourceId();
        }


        CaptionedImagesAdapter adapter = new CaptionedImagesAdapter(topnames,topImageIds);
        pizzaRecycler.setAdapter(adapter);
        GridLayoutManager layoutManager=new GridLayoutManager(getActivity(),1);
        pizzaRecycler.setLayoutManager(layoutManager);
        return view;


    }

}
