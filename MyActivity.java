package com.example.myapp;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        final EditText edtUserName = (EditText) findViewById(R.id.edtUserName);
        final EditText edtPassword = (EditText) findViewById(R.id.edtPassword);
        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        Button btnRegister = (Button) findViewById(R.id.btnRegister);
        Button btnSelect = (Button) findViewById(R.id.btnSelect);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String getUser = edtUserName.getText().toString();
                String getPassword = edtPassword.getText().toString();
                String userName = null;
                String password = null;

                SQLiteDatabase database = openOrCreateDatabase("orcl",MODE_PRIVATE,null);
                Cursor cursor = database.rawQuery("select userName,password from T3", null);
                while (cursor.moveToNext()) {
                    userName = cursor.getString(cursor.getColumnIndex("userName"));
                    password = cursor.getString(cursor.getColumnIndex("password"));
                }
                database.close();
                if (getUser.equals(userName) && getPassword.equals(password)){
                Intent intent = new Intent(MyActivity.this, Info.class);
                startActivity(intent);
            }
                else
                {
                    Toast.makeText(MyActivity.this, "Invalid user name or password", Toast.LENGTH_LONG).show();
            }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MyActivity.this, Register.class);
                startActivity(intent);
            }
        });

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase database = openOrCreateDatabase("orcl",MODE_PRIVATE,null);
                Cursor cursor = database.rawQuery("select userName,password from T3", null);
                String str = "";
                while (cursor.moveToNext()) {

                    String userName = cursor.getString(cursor.getColumnIndex("userName"));
                    String password = cursor.getString(cursor.getColumnIndex("password"));
                    str+= " userName = "+userName+ "   " +"password = "+ password + "   " + "\r\n";
                }
                database.close();
                Toast.makeText(MyActivity.this, str, Toast.LENGTH_LONG).show();

            }
        });
    }
}
