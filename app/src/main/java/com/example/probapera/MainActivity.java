package com.example.probapera;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;


public class MainActivity extends AppCompatActivity {
    private EditText login;
    private EditText password;
    private Button signIn;
    private Thread secondThread;
    private Runnable runnable;
    private HttpClient client = new DefaultHttpClient();
    private HttpGet request;
    private HttpResponse response;
    private HttpPost post;
    private HttpEntity siteEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = findViewById(R.id.Login);
        password = findViewById(R.id.Password);
        signIn = findViewById(R.id.signInButton);
        try {
            BufferedReader rd = new BufferedReader
                    (new InputStreamReader(
                            response.getEntity().getContent()));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public void init() {
        runnable = new Runnable() {
            @Override
            public void run() {
                request = new HttpGet("http://portal.mfmgutu.ru/index_ins.php?bl=1&idpg=915");
                try {
                    response = client.execute(request);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                siteEntity = response.getEntity();
                System.out.println(siteEntity);
            }
        };
        secondThread = new Thread(runnable);
        secondThread.start();

    }
    public void Click(View view) {
        if (login.getText().toString().trim().equals("") && password.getText().toString().trim().equals("")) {
            Toast.makeText(MainActivity.this, R.string.nothingInText, Toast.LENGTH_LONG).show();
        } else {
            init();
            /*Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            startActivity(intent);*/
        }
    }
}

