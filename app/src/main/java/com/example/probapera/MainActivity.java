package com.example.probapera;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
//import org.jsoup.Jsoup;


import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText login;
    private EditText password;
    private Button signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = findViewById(R.id.Login);
        password = findViewById(R.id.Password);
        signIn = findViewById(R.id.signInButton);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(login.getText().toString().trim().equals("") && password.getText().toString().trim().equals(""))
                Toast.makeText(MainActivity.this,R.string.nothingInText, Toast.LENGTH_LONG).show();


            }
        });
    }



}