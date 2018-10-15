package com.example.asus.facedetection;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Color;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import static android.graphics.Camera.*;

public class MainActivity extends AppCompatActivity {

    FrameLayout framelayout;
    Button ok;
    android.hardware.Camera camera;
    ShowCamera showCamera;
    PhotoConvert photoConvert = new PhotoConvert();
    CountDownTimer timer;

    String url="http://192.168.0.110/testApi/saveServlet";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        framelayout= (FrameLayout) findViewById(R.id.framelayout);
        ok=(Button) findViewById(R.id.ok);
        android.hardware.Camera.CameraInfo cameraInfo= new android.hardware.Camera.CameraInfo();
        int countcamera = android.hardware.Camera.getNumberOfCameras();
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Don't touch me..." , Toast.LENGTH_SHORT ).show();
            }

        });//event on button

        for(int i=0;i<countcamera;i++)
        {
            android.hardware.Camera.getCameraInfo(i,cameraInfo);

                if(cameraInfo.facing== android.hardware.Camera.CameraInfo.CAMERA_FACING_FRONT)
                {
                    try{
                        Log.e("TAG cam",i+"");
                        camera= camera.open(i);
                        showCamera =new ShowCamera(this,camera);
                        framelayout.addView(showCamera);
                        Toast.makeText(this,"Camera is Open now",Toast.LENGTH_SHORT).show();
                        Toast.makeText(this, "Let's move to Servlet", Toast.LENGTH_LONG).show();
                        timer=new CountDownTimer(20000, 1000) {

                            public void onTick(long millisUntilFinished) {
                                Bitmap image=photoConvert.frameLayoutToBitmap(framelayout);
                                String image2=photoConvert.Base64(image);
                                saveRequest(url+"?image="+image2);
                                Log.e("TAG Image",image2);
                                Toast.makeText(MainActivity.this,image2,Toast.LENGTH_SHORT).show();
                                Toast.makeText(MainActivity.this,"added to servlet",Toast.LENGTH_SHORT).show();
                            }

                            public void onFinish() {
                                Toast.makeText(MainActivity.this, "Timer Stop", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainActivity.this, FaceUnlock.class);
                                startActivity(intent);
                            }
                        };
                        timer.start();

                    }
                    catch (RuntimeException e)
                    {

                        Toast.makeText(this,"Please check you permission",Toast.LENGTH_LONG).show();
                    }

                }

        }//camera manipulations


    }



    private void saveRequest(String saveURl){


        RequestQueue queue = Volley.newRequestQueue(this);
// prepare the Request

        StringRequest getRequest = new StringRequest(Request.Method.GET, saveURl, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                    Log.e("Response",response);

                    if(response.toString().equals("success")){
                        //intent to home page
                        timer.cancel();


                    }else{

                    }


            }

        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error",error.toString());
            }
        });

        queue.add(getRequest);//added in queuse

    }
    
    @Override
    public void onBackPressed(){


    }//disable back button
}
