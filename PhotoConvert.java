package com.example.asus.facedetection;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Base64;
import android.widget.FrameLayout;

import java.io.ByteArrayOutputStream;

public  class PhotoConvert {


    public Bitmap frameLayoutToBitmap(FrameLayout frameLayout)
    {
        Bitmap bitmap =Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888);
        Canvas canvas =new Canvas(bitmap);
        frameLayout.draw(canvas);
        return bitmap;
    }
     public String Base64(Bitmap bitmap)
     {
         ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
         bitmap.compress(Bitmap.CompressFormat.PNG , 100, byteArrayOutputStream);
         byte [] byteArray = byteArrayOutputStream.toByteArray();
         String encode = Base64.encodeToString(byteArray, Base64.DEFAULT);
         return encode;
     }
}
