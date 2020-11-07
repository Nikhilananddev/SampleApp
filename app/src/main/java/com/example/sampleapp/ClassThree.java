package com.example.sampleapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;

import androidx.annotation.RequiresApi;

/**
 * Created by Dheeraj Beliya on 29-Oct-2020.
 */
public class ClassThree extends MainActivity {


    public static void generateBitmap(Context context) {
        int i = 0;

        // TODO: GENERATE A NICE A4 REPORT WITH ALL 4 IMAGES AND SOME TEXT THAT ARE PRESENT.

        for (i = 0; i < 100; i++) {
            Bitmap p1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.p1);

            Bitmap p2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.p2);

            Bitmap p3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.p3);

            Bitmap p4 = ClassTwo.getBitmap("https://sfl-report-logos.s3.ap-south-1.amazonaws.com/p1.png");

            PdfDocument reportWithMhr = new PdfDocument();

            PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(10, 10, i).create();

            PdfDocument.Page page = reportWithMhr.startPage(pageInfo);
            Canvas canvas = page.getCanvas();

            canvas.save();

            canvas.rotate(90, canvas.getWidth() / 2, canvas.getHeight() / 2);

            Paint paint = new Paint();
            paint.setColor(Color.BLACK);
            paint.setTextSize(32);

            canvas.drawText("Patient Name : ", 0, 700, paint);

            canvas.drawText("Patient ID : ", 0, 750, paint);

            canvas.drawText("Patient Age : " + " yrs", 0, 800, paint);

            canvas.drawText("Test Duration : ", 0, 850, paint);

            canvas.drawText("Gestational Age : ", 40, 700, paint);

            canvas.drawText("G/P : ", 50, 750, paint);

            canvas.drawText("Risk Factors : ", 50, 800, paint);

            canvas.drawText("Date : ", 50, 850, paint);
            if (p1 != null && p2 !=null && p3 != null && p4 != null|| !p1.isRecycled() || !p2.isRecycled()|| !p3.isRecycled()|| !p4.isRecycled()) {
                return;
            }
                canvas.drawBitmap(p1, 200, 100, null);
                canvas.drawBitmap(p2, 300, 200, null);
                canvas.drawBitmap(p3, 500, 400, null);
                canvas.drawBitmap(p4, 50, 800, null);



            canvas.restore();

            reportWithMhr.finishPage(page);

            String myFilePath =Environment.getExternalStorageDirectory().getPath()+"/DCIM/Camera/"+"report"+".pdf";
            File myFile = new File(myFilePath);
            try {
                reportWithMhr.writeTo(new FileOutputStream(myFile));
            }
            catch (Exception e){
                e.printStackTrace();
            }


            reportWithMhr.close();

        }
    }
}
