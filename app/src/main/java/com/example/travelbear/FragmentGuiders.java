package com.example.travelbear;


import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentGuiders extends Fragment {

   SQLiteDatabase db;
   Cursor cursor;

    public FragmentGuiders() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_guiders,container,false);
        ListView guidList=view.findViewById(R.id.list_guiders);

        SQLiteOpenHelper travelBareDatabaseHelper =new TravelbearDatabaseHelper(getContext());

        try{

           db=travelBareDatabaseHelper.getReadableDatabase();
           cursor=db.query("GUIDERS",
                   new String[]{"_id","NAME"},
                   null,null,null,null,null);

            SimpleCursorAdapter listAdapter= new SimpleCursorAdapter(inflater.getContext(),
                    android.R.layout.simple_list_item_1,
                    cursor,
                    new String[]{"NAME"},
                    new int[]{android.R.id.text1},0);

                guidList.setAdapter(listAdapter);



        }catch (SQLException e){
            Toast toast=Toast.makeText(getContext(),"DATA base unvailable",Toast.LENGTH_SHORT);
            toast.show();

        }


        AdapterView.OnItemClickListener itemClickListener=new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {

                Intent intent=new Intent(getActivity(),CreateTrip.class);
                intent.putExtra(CreateTrip.EXTRA_GUIDERID,(int) id);
                startActivity(intent);

            }
        };

          guidList.setOnItemClickListener(itemClickListener);
          return view;


    }

}
