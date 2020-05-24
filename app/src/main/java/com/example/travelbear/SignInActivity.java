package com.example.travelbear;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class SignInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);


    }

    public void signup(View view){

        TravelbearDatabaseHelper db=new TravelbearDatabaseHelper(this);

        EditText usernametxt=findViewById(R.id.username);
        EditText passwordtxt=findViewById(R.id.password);
        EditText conpassword=findViewById(R.id.confirmpassword);

        String username=usernametxt.getText().toString();
        String password=passwordtxt.getText().toString();
        String confirmpass=conpassword.getText().toString();

        CharSequence text="Text fileds are empty...";
        int duration= Snackbar.LENGTH_SHORT;



        if(username.equals("") || password.equals("") ||confirmpass.equals("")){
            Snackbar snackbar=Snackbar.make(findViewById(R.id.coordinator),text,duration);
            snackbar.show();
        }else{

            if(password.equals(confirmpass)){

                Boolean checkmail=db.checkmail(username);
                if(checkmail==true){
                   Boolean insert=db.insertusers(username,password);
                   if(insert==true){

                       Intent intent=new Intent(SignInActivity.this,LoginActivity.class);
                       startActivity(intent);
                   }
                }else{
                    Snackbar snackbar=Snackbar.make(findViewById(R.id.coordinator),"Your email alraedy exisit",duration);
                    snackbar.show();
                }

            }else{
                Snackbar snackbar=Snackbar.make(findViewById(R.id.coordinator),"Your password didn't match",duration);
                snackbar.show();
            }


        }







    }
}
