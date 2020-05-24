package com.example.travelbear;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.ActionBar;

import java.util.ArrayList;
import java.util.List;

public class LocationDetail extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_LOCATIONID="locationId";
    int image_index = 0;
    public static final int MAX_IMAGE_COUNT = 2;
    int [] imageIds={0};
    double longutude,latitude;

    ArrayAdapter<DataModel>commentAdapter;
    ArrayList<DataModel>commentList=new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_detail);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Button btnPrevious=findViewById(R.id.previous_btn);
        btnPrevious.setOnClickListener(this);

        Button btnext=findViewById(R.id.next_btn);
        btnext.setOnClickListener(this);


        int locationId=(Integer)getIntent().getExtras().get(EXTRA_LOCATIONID);
        SQLiteOpenHelper travelbaredatabasehelper=new TravelbearDatabaseHelper(this);
              try{

            SQLiteDatabase db=travelbaredatabasehelper.getReadableDatabase();
            Cursor cursor=db.query("LOCATION",
                    new String[]{"LOCATIONS","DESCRIPTON ","IMAGE_RESOURCE_ID","IMAGE_RESOURCE_ID_2","FAVORITE","BEEN","LATITUDE","LONGTUDE"},
                    "_id=?",
                    new String[]{Integer.toString(locationId)},
                    null,null,null);

            if(cursor.moveToFirst()){

                String locationText=cursor.getString(0);
                int descriptionText=cursor.getInt(1);
                int photoId_1=cursor.getInt(2);
                int photoId_2=cursor.getInt(3);
                boolean isFavorite=(cursor.getInt(4)==1);
                boolean isBeen=(cursor.getInt(5)==1);
                latitude=(cursor.getDouble(6));
                longutude=(cursor.getDouble(7));


                TextView locname=findViewById(R.id.location_name);
                locname.setText(locationText);

                TextView locdes=findViewById(R.id.location_description);
                locdes.setText(descriptionText);

                CheckBox favorite = findViewById(R.id.favorite);
                favorite.setChecked(isFavorite);

                CheckBox been=findViewById(R.id.beencheck);
                been.setChecked(isBeen);

                imageIds=new int[]{photoId_1,photoId_2};


            }
        }catch (SQLException e){
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
        showImages();
        initial();
    }

    public void showImages(){

        ImageView locationview=findViewById(R.id.location_detail);
        locationview.setImageResource(imageIds[image_index]);

    }

    public void onClick(View v){

        switch (v.getId()){

            case(R.id.previous_btn):
                image_index--;
                if(image_index==-1){
                    image_index=MAX_IMAGE_COUNT-1;
                }
                showImages();
                break;

            case (R.id.next_btn):
                image_index++;
                if(image_index==MAX_IMAGE_COUNT){
                    image_index=0;

                }
                showImages();
                break;
        }

    }

    public void insertcomment(View view){

        EditText comment=findViewById(R.id.comment);
        TravelbearDatabaseHelper db=new TravelbearDatabaseHelper(this);

        String commenttxt=comment.getText().toString();
        int locationId=(Integer)getIntent().getExtras().get(EXTRA_LOCATIONID);

        if(commenttxt.equals("")){
            Toast.makeText(LocationDetail.this,"Fields are Empty",Toast.LENGTH_SHORT).show();
        }else{

            boolean insert=db.insertComment(locationId,commenttxt);

            if(insert==true){
                Toast.makeText(LocationDetail.this,"Your comment added",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(LocationDetail.this,"comment didn't added",Toast.LENGTH_SHORT).show();
            }
        }

        Intent t= new Intent(LocationDetail.this,LocationDetail.class);
        startActivity(t);
        finish();
    }

    public void initial(){

        ListView listcomment=findViewById(R.id.list_comment);

        SQLiteOpenHelper travelbaredatabasehelper=new TravelbearDatabaseHelper(this);
        int locationId=(Integer)getIntent().getExtras().get(EXTRA_LOCATIONID);
        SQLiteDatabase db= travelbaredatabasehelper.getReadableDatabase();
        Cursor cursor=db.query("COMMENT_TABLE",
                new String[]{"LOC_ID","COMMENT"},
                "LOC_ID=?",
                new String[]{Integer.toString(locationId)},
                null,null,null);

         if(cursor.moveToFirst()){
             do{
                 commentList.add(new DataModel(cursor.getInt(0),cursor.getString(1)));
             }while (cursor.moveToNext());

         }
         commentAdapter=new SelectArrayAdapter(getApplicationContext(),commentList);
         listcomment.setAdapter(commentAdapter);
    }


    public void  onFavoriteClicked(View view){

        int locationId=(Integer)getIntent().getExtras().get(EXTRA_LOCATIONID);
        new UpdateLocationTask().execute(locationId);
    }

    public void onbeenClicked(View view){

        int locationId=(Integer)getIntent().getExtras().get(EXTRA_LOCATIONID);
        new UpdateLocationTask().execute(locationId);
    }

    public void sharemap(View view){

        String uri = "geo:" + latitude + ","
                +longutude + "?q=" + latitude
                + "," + longutude;
        startActivity(new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse(uri)));
    }

    private class UpdateLocationTask extends AsyncTask<Integer,Void,Boolean>{
        private ContentValues locationValues;


        @Override
        protected void onPreExecute() {

            CheckBox favorite=findViewById(R.id.favorite);
            CheckBox been=findViewById(R.id.beencheck);
            locationValues=new ContentValues();
            locationValues.put("FAVORITE",favorite.isChecked());
            locationValues.put("BEEN",been.isChecked());


        }

        @Override
        protected Boolean doInBackground(Integer... locations) {
            int locationId=locations[0];
            SQLiteOpenHelper travelbaredatabasehelper=new TravelbearDatabaseHelper(LocationDetail.this);

            try{

                SQLiteDatabase db=travelbaredatabasehelper.getWritableDatabase();
                db.update("LOCATION",locationValues,
                        "_id=?",new String[]{Integer.toString(locationId)});

                        db.close();
                       return true;
            }catch(SQLException e){
                return false;
            }

        }

        @Override
        protected void onPostExecute(Boolean  success) {

            if(!success){
                Toast toast = Toast.makeText(LocationDetail.this,"Database unavailable", Toast.LENGTH_SHORT);
                toast.show();

            }

        }
    }

    private static class SelectViewHolder{

        private TextView commentText;

        public SelectViewHolder(TextView commentText) {
            this.commentText = commentText;
        }

        public TextView getCommentText() {
            return commentText;
        }
    }

    private class SelectArrayAdapter extends ArrayAdapter<DataModel>{

        private LayoutInflater inflater;

        public SelectArrayAdapter(Context context, List<DataModel> commentList) {
            super(context, R.layout.comment_list,R.id.comment,commentList);
            inflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            DataModel commentModel=(DataModel)this.getItem(position);

            TextView textComment;

            if(convertView==null){
                convertView=inflater.inflate(R.layout.comment_list,null);
                textComment=convertView.findViewById(R.id.comment);

                convertView.setTag(new SelectViewHolder(textComment));

            }else{
                SelectViewHolder viewHolder=(SelectViewHolder)convertView.getTag();
                textComment=viewHolder.getCommentText();
            }
            textComment=convertView.findViewById(R.id.comment);
            textComment.setText(commentModel.getComment());

            return convertView;

        }
    }


}
