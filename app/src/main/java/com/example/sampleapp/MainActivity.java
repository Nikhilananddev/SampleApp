package com.example.sampleapp;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.ThreadLocalRandom;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private Chronometer chronometer;
    private long pauseOffset;
    private boolean running;
    private static final String TAG = "MainActivity";
    APiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiInterface = ClassOne.getClient().create(APiInterface.class);

        //TODO: HAVE A BUTTON. ON CLICK OF THE BUTTON A TIMER STARTS AND THAT TIMER IS SHOWN ON THE SCREEN.
        //TODO: AT THE SAME TIME CALL uploadDataAndGenerate Function of ClassOne.
        //TODO: ONCE THE REPORT IS GENERATED STOP THE TIMER AND OPEN THAT REPORT.

        chronometer = findViewById(R.id.chronometer);
        chronometer.setFormat("Time: %s");
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if ((SystemClock.elapsedRealtime() - chronometer.getBase()) >= 10000) {
                    chronometer.setBase(SystemClock.elapsedRealtime());
                    Toast.makeText(MainActivity.this, "Bing!", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }


    public void startChronometer(View v) {
        //after 10 sec app refresh

        Handler myHandler = new Handler();
        int delay = 1000; // 1000 milliseconds == 1 second

        myHandler.postDelayed(new Runnable() {
            public void run() {
                postTodo();// Do your work here
                myHandler.postDelayed(this, delay);
            }
        }, delay);

        if (!running) {
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();
            running = true;

        }
    }
    public void pauseChronometer(View v)
    {
        if (running) {
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            running = false;
        }
    }
    public void resetChronometer(View view) {
        chronometer.setBase(SystemClock.elapsedRealtime());
        pauseOffset = 0;
    }

    public void postTodo() {
        Random rd = new Random();
        Todo todo = new Todo(rd.nextInt(), "abcd"+rd.nextLong(), false);

        Call<Todo> todoPostCall = apiInterface.postTodo(todo);
        todoPostCall.enqueue(new Callback<Todo>() {
            @Override
            public void onResponse(Call<Todo> call, Response<Todo> response) {
                Log.e(TAG, "onResponse: " + response.body() );
            }

            @Override
            public void onFailure(Call<Todo> call, Throwable t) {

            }
        });

    }

    }
