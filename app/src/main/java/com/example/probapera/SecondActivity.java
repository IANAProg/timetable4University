package com.example.probapera;

import android.app.Activity;
import android.os.Bundle;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


import java.io.IOException;


public class SecondActivity extends Activity
{
    private Document doc;
    private Document docPost;
    private Thread secThr;
    private Runnable runnable;
    private String login;
    private String pass;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);

        Bundle arguments = getIntent().getExtras(); // инициализация приема данных с мейнактививти

        if (arguments != null)
        {
            login = arguments.get("login").toString(); // ловим логин
            pass = arguments.get("pass").toString(); // ловим пароль
        }
        private void init()
        {
            runnable = new Runnable()
            {
                @Override
                public void run()
                {
                    GetHTML();
                }
            };
            secThr = new Thread(runnable);
            secThr.start();
        }

        private void GetHTML()
        {
            try
            {
                doc = Jsoup.connect("http://portal.mfmgutu.ru/index_ins.php?bl=1&idpg=915").get();
                docPost = (Document) Jsoup.connect("http://portal.mfmgutu.ru/new_check_auth.php");
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }

    }
}