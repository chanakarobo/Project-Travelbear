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
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentLocation extends Fragment {

    private SQLiteDatabase db;
    private Cursor cursor;
    private Cursor beenCursor;

    public FragmentLocation() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =inflater.inflate(
                R.layout.location_fragment,container,false);

        ListView listlocations=view.findViewById(R.id.list_locations);
        ListView listBeen=view.findViewById(R.id.list_been);

        SQLiteOpenHelper travelBareDatabaseHelper = new TravelbearDatabaseHelper(getContext());

        try {

            db = travelBareDatabaseHelper.getReadableDatabase();
            cursor = db.query("LOCATION",
                    new String[]{"_id", "LOCATIONS"},
                    null, null, null, null, null);


            beenCursor=db.query("LOCATION",
                    new String[]{"_id","LOCATIONS"},
                    "BEEN = 1",
                    null,null,null,null);

            SimpleCursorAdapter listAdapter = new SimpleCursorAdapter(inflater.getContext(),
                    android.R.layout.simple_list_item_1,
                    cursor,
                    new String[]{"LOCATIONS"},
                    new int[]{android.R.id.text1, 0});




            CursorAdapter beenAdapter=new SimpleCursorAdapter(inflater.getContext(),
                    android.R.layout.simple_list_item_1,
                    beenCursor,
                    new String[]{"LOCATIONS"},
                    new int[]{android.R.id.text1});

            listlocations.setAdapter(listAdapter);
            listBeen.setAdapter(beenAdapter);

        } catch (SQLException e) {


            Toast toast = Toast.makeText(getContext(), "DATA base unvailable", Toast.LENGTH_SHORT);
            toast.show();
        }

        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listlocations, View Itemview, int position, long id) {

                Intent intent = new Intent(getActivity(), LocationDetail.class);
                intent.putExtra(LocationDetail.EXTRA_LOCATIONID,(int) id);
                getActivity().startActivity(intent);

            }

        };

        listlocations.setOnItemClickListener(itemClickListener);
        return view;


    }
}
