package com.example.myapp;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by user2 on 03/08/2016.
 */
public class Register extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        Button btnBack= (Button) findViewById(R.id.btnBack);
        Button btnRegister= (Button) findViewById(R.id.btnRegister);
        final EditText edtUserName= (EditText) findViewById(R.id.edtUserName);
        final EditText edtPassword= (EditText) findViewById(R.id.edtPassword);
        final EditText edtConfirmPassword= (EditText) findViewById(R.id.edtConfirmPassword);
        final EditText edtFullName= (EditText) findViewById(R.id.edtFullName);
        final EditText edtCity= (EditText) findViewById(R.id.edtCity);
        final EditText edtEmail= (EditText) findViewById(R.id.edtEmail);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this, MyActivity.class);
                startActivity(intent);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = edtUserName.getText().toString();
                String password = edtPassword.getText().toString();
                String confirmPassword = edtConfirmPassword.getText().toString();
                String fullName = edtFullName.getText().toString();
                String city = edtCity.getText().toString();
                String email = edtEmail.getText().toString();

                if(userName != null || password != null || confirmPassword != null){
                if(password.equals(confirmPassword))
                    {
                        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase("orcl", MODE_PRIVATE, null);
                        sqLiteDatabase.execSQL("create table if not exists t3 (userName varchar,password varchar,confirmPassword varchar,fullName varchar,city varchar,email varchar);");
                        sqLiteDatabase.execSQL("Insert into T3 values('" + userName + "', '" + password + "', '" + confirmPassword + "', '" + fullName + "', '" + city + "', '" + email + "')");
                        sqLiteDatabase.close();
                        Toast.makeText(Register.this, "Registered", Toast.LENGTH_SHORT).show();
                    }
                    else {
                    Toast.makeText(Register.this,"Passwords are not equal...", Toast.LENGTH_SHORT).show();
                }
                }
                else
                {
                    Toast.makeText(Register.this,"There is a empty field...", Toast.LENGTH_SHORT).show();
            }
            }
        });
    }
}
