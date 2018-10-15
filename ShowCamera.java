package com.example.asus.facedetection;

import android.content.Context;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.io.IOException;
// Link-local IPv6 Address . . . . . : fe80::3dcb:841c:3da1:cb8a%4
//         IPv4 Address. . . . . . . . . . . : 192.168.0.110
//         Subnet Mask . . . . . . . . . . . : 255.255.255.0
//         Default Gateway . . . . . . . . . : 192.168.0.1
// phone ip   192.168.0.109
public class ShowCamera extends SurfaceView implements SurfaceHolder.Callback{

     Camera camera;
    SurfaceHolder holder;
    public ShowCamera(Context context, Camera camera) {
        super(context);
        this.camera= camera;
        holder=getHolder();
        holder.addCallback(this);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {



    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        if(camera!=null)
        {
            camera.stopPreview();
            camera.release();

        }


    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Camera.Parameters params = camera.getParameters();


        //change the orientations
        if(this.getResources().getConfiguration().orientation!= Configuration.ORIENTATION_LANDSCAPE)
        {
            params.set("orientation","portrait");
            camera.setDisplayOrientation(90);
            params.setRotation(90);
        }
        else{
            params.set("orientation","landscape");
            camera.setDisplayOrientation(0);
            params.setRotation(0);
        }
        camera.setParameters(params);
        try {
            camera.setPreviewDisplay(holder);
            camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}


