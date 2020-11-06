package com.example.sampleapp;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Dheeraj Beliya on 29-Oct-2020.
 */
public class ClassOne {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void uploadDataAndGenerate(Context context) {

        // TODO: UPLOAD SOME RANDOM DATA TO CLOUD DATABASE OF YOUR CHOICE. RUN THAT AND BITMAP GENERATION CODE IN PARALLEL



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
