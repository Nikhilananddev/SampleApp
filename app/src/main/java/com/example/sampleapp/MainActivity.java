package com.example.sampleapp;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Toast;

import java.io.File;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity {
    private Chronometer chronometer;
    private long pauseOffset;
    private boolean running;
    Button button;
    private static final String TAG = "MainActivity";

//    private static final String TAG = "MainActivity";
//    APiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        apiInterface = ClassOne.getClient().create(APiInterface.class);
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
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
//
//        Handler myHandler = new Handler();
//        int delay = 1000; // 1000 milliseconds == 1 second
//
//        myHandler.post(new Runnable() {
//            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//            public void run() {
//                ClassOne classOne =new ClassOne();
//                classOne.uploadDataAndGenerate(MainActivity.this);
//                // Do your work here
//                myHandler.postDelayed(this, delay);
//            }
//        });
        new Thread(new Runnable() {
            @Override
            public void run() {

                //work
                CreateText createText=new CreateText();
                createText.createMyPDF();


            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                //work
                openPdf();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                //work
                ClassOne classOne = new ClassOne();
                classOne.uploadDataAndGenerate(MainActivity.this);
            }
        }).start();

        if (!running) {
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();
            running = true;

        }
    }

//    public void pauseChronometer(View v) {
//        if (running) {
//            chronometer.stop();
//            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
//            running = false;
//        }
//    }
//
//    public void resetChronometer(View view) {
//        chronometer.setBase(SystemClock.elapsedRealtime());
//        pauseOffset = 0;
//    }

    public void openPdf() {

        File file = new File(Environment.getExternalStorageDirectory().getPath() + "/DCIM/Camera/" + "sample" + ".pdf");
        Uri path = Uri.fromFile(file);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(path);
        intent.setType("*/*");
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(MainActivity.this, "No application found",
                    Toast.LENGTH_SHORT).show();
        }
    }



    }


