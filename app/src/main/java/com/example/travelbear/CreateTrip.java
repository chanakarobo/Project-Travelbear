package com.example.travelbear;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class CreateTrip extends AppCompatActivity {
    private ShareActionProvider shareActionProvider;
    String email;

    public static final String EXTRA_GUIDERID="guiderId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trip);

        Toolbar tool= findViewById(R.id.toolbar);
        setSupportActionBar(tool);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        int guiderId=(Integer)getIntent().getExtras().get(EXTRA_GUIDERID);
        SQLiteOpenHelper travelbaredatabaseHelper=new TravelbearDatabaseHelper(this);

        try{

            SQLiteDatabase db=travelbaredatabaseHelper.getReadableDatabase();
            Cursor cursor=db.query("GUIDERS",
                    new String[]{"NAME","GDESCRIPTION","EMAIL","MOBILE","ADDRESS","GIMAGE_RESOURCE_ID"},
                    "_id=?",
                    new String[]{Integer.toString(guiderId)},
                    null,null,null);


            if(cursor.moveToFirst()){

                String nameText=cursor.getString(0);
                int description=cursor.getInt(1);
                email=cursor.getString(2);
                String mobile=cursor.getString(3);
                String address=cursor.getString(4);
                int photoId=cursor.getInt(5);


                TextView name=findViewById(R.id.name);
                name.setText(nameText);

                TextView gdescription=findViewById(R.id.description);
                gdescription.setText(description);

                TextView gmobile=findViewById(R.id.mobile);
                gmobile.setText(mobile);

                TextView gemail=findViewById(R.id.email);
                gemail.setText(email);

                TextView gaddress=findViewById(R.id.address);
                gaddress.setText(address);

                ImageView photo=findViewById(R.id.guiderprofile);
                photo.setImageResource(photoId);



            }
            cursor.close();
            db.close();


        }catch (SQLException e){

            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();

        }


    }

    public void  onClickDone(View view){

        CharSequence text="Your Trip has been started";
        int duration= Snackbar.LENGTH_SHORT;
        Snackbar snackbar=Snackbar.make(findViewById(R.id.coordinator),text,duration);
        snackbar.show();

        String[] To={email};
        Intent emailIntent=new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");

        emailIntent.putExtra(Intent.EXTRA_EMAIL,To);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT,"going For a trip!");

        startActivity(Intent.createChooser(emailIntent,"Send mail..."));
        finish();


    }


}
