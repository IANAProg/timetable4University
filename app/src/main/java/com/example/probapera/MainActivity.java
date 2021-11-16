package com.example.probapera;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.Scanner;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.client.methods.CloseableHttpResponse;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.client.methods.HttpUriRequest;
import cz.msebera.android.httpclient.client.methods.RequestBuilder;
import cz.msebera.android.httpclient.impl.client.CloseableHttpClient;
import cz.msebera.android.httpclient.impl.client.HttpClients;


public class MainActivity extends AppCompatActivity {
    private EditText login;
    private EditText password;
    private Button signIn;
    private Thread secondThread; // эта параша и параша ниже для второго потока, чтоб не ебал мозг с загрузкой графики
    private Runnable runnable; // все спиженно с гайдов, надеюсь пахать будет
    private CloseableHttpResponse response; // ответка на запрос
    private RequestBuilder reqBuilder = RequestBuilder.post().setUri("http://portal.mfmgutu.ru/new_check_auth.php"); // создаем пост-запрос и Определяем куда запрашиваем
        //нихуя я умный, сначала создал переменную на ответ, а потом на запрос
    HttpGet httpGet = new HttpGet("http://portal.mfmgutu.ru/index_ins.php?bl=1&idpg=915");// Определяем куда запрашиваем
    private HttpEntity entity;
    private Scanner sc;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = findViewById(R.id.Login); // вяжем логин с формой
        password = findViewById(R.id.Password); // вяжем пароль с формой
        signIn = findViewById(R.id.signInButton); // вяжем кнопку с формой



    }
    public void init()
    {
        runnable = new Runnable()
        {
            @Override
            public void run()
            {
                CloseableHttpClient client = HttpClients.createDefault(); // запускаем клиент веб-сервиса (или как там эта хуйня называется)
               // System.out.println(3);

                RequestBuilder reqBuilder1 = reqBuilder // указываем что будет в запросе
                        .addParameter("login", login.getText().toString().trim()) // логин
                        .addParameter("pass", password.getText().toString().trim());// пароль
                HttpUriRequest httpPost = reqBuilder1.build();// создаем мужицкий запрос (на женщин)
                System.out.println(5);
                try
                {
                    try
                    {
                        //System.out.println(6);
                        response = client.execute(httpPost); // а вот теперь-ча будем долбить сайт запросом и ждать ответ
                        // System.out.println(7);
                        response = client.execute(httpGet); // заходим на искомую страницу
                        // System.out.println(8);
                        entity = response.getEntity(); // получаем объект странички
                        // System.out.println(9);
                        sc = new Scanner(entity.getContent()); // эта хуйня стартует выкладку страницы
                        //System.out.println(10);

                    } finally
                    {
                        response.close();
                        client.close();
                        if (sc.nextLine().equals("<!DOCTYPE html>"))
                        {
                            Intent intent = new Intent(MainActivity.this, SecondActivity.class); // переход на вторую страницу (если вщ надо будет)
                            intent.putExtra("login",login.getText().toString().trim());
                            intent.putExtra("pass",password.getText().toString().trim());
                            startActivity(intent);
                        }
                    }

                } catch (IOException e)
                {
                    e.printStackTrace();
                    //System.out.println(4);
                }finally
                {
                    try
                    {
                        response.close();
                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }

            }
        };
        secondThread = new Thread(runnable); // создаем второй поток и тыкаем куда его направлять
        secondThread.start(); // запускаем его
        System.out.println(2);

    }
    public void Click(View view)
    {
        if (login.getText().toString().trim().equals("")
                &&
                password.getText().toString().trim().equals(""))
        // проверяем не долбаеб ли пользователь, может он все же воткнул пустые строки?
        // А нахуй надо теребить лишний раз запросами серв?
        {
            Toast.makeText(MainActivity.this, R.string.nothingInText, Toast.LENGTH_LONG).show();
            //деликатно довести до пользователя что он долбаеб
        } else
        {
            System.out.println(1);
            init();
            /*Intent intent = new Intent(MainActivity.this, SecondActivity.class); // переход на вторую страницу (если вщ надо будет)
            startActivity(intent);*/
        }
    }
}

