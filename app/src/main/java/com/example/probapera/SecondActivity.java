package com.example.probapera;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


import java.io.IOException;


public class SecondActivity extends Activity{
    private Document doc;
    private Thread secThr;
    private Runnable runnable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);
    }
    private void init(){
        runnable=new Runnable() {
            @Override
            public void run() {
                GetHTML();
            }
        };
        secThr=new Thread(runnable);
        secThr.start();
    }

    private void GetHTML(){
        try {
            doc= Jsoup.connect("http://portal.mfmgutu.ru/index_ins.php?bl=1&idpg=915").get();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Click(View view) {
        Intent intent=new Intent(SecondActivity.this,MainActivity.class);
        startActivity(intent);
        }
}