package com.example.asus.facedetection;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class FaceUnlock extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_unlock);

        Toast.makeText(this,"Welcome",Toast.LENGTH_SHORT).show();
    }
}
