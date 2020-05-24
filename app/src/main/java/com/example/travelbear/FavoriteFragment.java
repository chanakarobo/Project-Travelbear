package com.example.travelbear;


import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {
   private SQLiteDatabase db;
   private Cursor favoritesCursor;



    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

       View view=inflater.inflate(R.layout.fragment_favorite, container, false);
       ListView listFavorites=view.findViewById(R.id.list_favorites);

       try{

           SQLiteOpenHelper travelbaredatabasehelper=new TravelbearDatabaseHelper(getContext());
           db=travelbaredatabasehelper.getReadableDatabase();
           favoritesCursor=db.query("LOCATION",
                   new String[]{"_id","LOCATIONS"},
                   "FAVORITE = 1",
                   null,null,null,null);



           CursorAdapter favoriteAdapter=new SimpleCursorAdapter(inflater.getContext(),
                   android.R.layout.simple_list_item_1,
                   favoritesCursor,
                   new String[]{"LOCATIONS"},
                   new int[]{android.R.id.text1});




           listFavorites.setAdapter(favoriteAdapter);



       }catch (SQLException e){

           Toast toast = Toast.makeText(getContext(), "Database unavailable", Toast.LENGTH_SHORT);
           toast.show();
       }

       listFavorites.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
               Intent intent=new Intent(getActivity(),LocationDetail.class);
               intent.putExtra(LocationDetail.EXTRA_LOCATIONID,(int)id);
               startActivity(intent);
           }
       });



        return view;
    }

}
