package com.example.travelbear;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView signin=findViewById(R.id.textsignin);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,SignInActivity.class);
                startActivity(intent);
            }
        });


    }

    public void loginmethod(View view){

        TravelbearDatabaseHelper db=new TravelbearDatabaseHelper(this);

        EditText user=findViewById(R.id.username);
        EditText pass=findViewById(R.id.password);

        String username=user.getText().toString();
        String password=pass.getText().toString();

        Boolean checkpassmail=db.emailpassword(username,password);

        if(checkpassmail==true){
            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);

        }else{
            Toast.makeText(LoginActivity.this,"not exiest user",Toast.LENGTH_SHORT).show();
        }



    }
}
