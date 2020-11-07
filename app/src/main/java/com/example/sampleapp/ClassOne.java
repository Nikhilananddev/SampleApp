package com.example.sampleapp;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import java.util.Random;

import androidx.annotation.RequiresApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Dheeraj Beliya on 29-Oct-2020.
 */
public class ClassOne {
    private static final String TAG = "MainActivity";
    APiInterface apiInterface;

    public void uploadDataAndGenerate(Context context) {
        apiInterface = ClassOne.getClient().create(APiInterface.class);

        // TODO: UPLOAD SOME RANDOM DATA TO CLOUD DATABASE OF YOUR CHOICE. RUN THAT AND BITMAP GENERATION CODE IN PARALLEL

        Random rd = new Random();
        Todo todo = new Todo(rd.nextInt(), "abcd" + rd.nextLong(), false);

        Call<Todo> todoPostCall = apiInterface.postTodo(todo);
        todoPostCall.enqueue(new Callback<Todo>() {
            @Override
            public void onResponse(Call<Todo> call, Response<Todo> response) {
                Log.e(TAG, "onResponse: " + response.body());
            }

            @Override
            public void onFailure(Call<Todo> call, Throwable t) {

            }
        });

        ClassThree.generateBitmap(context);

    }
    private  static final String BASE_URL="https://jsonplaceholder.typicode.com";
    private static Retrofit retrofit=null;
    public static  Retrofit getClient()
    {

        if (retrofit==null)
        {
            retrofit=new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
