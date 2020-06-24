package com.example.androidproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class CameraClass extends AppCompatActivity {
    Camera camera;
    boolean isPrevious = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_class);
        if(!android.os.Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this,"请安装SD卡！",Toast.LENGTH_SHORT).show();
        }
        SurfaceView surfaceView = findViewById(R.id.camera_sufaceview);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        Button previous = findViewById(R.id.camera_previous);
        previous.setOnClickListener((v)->{
            if (!isPrevious){
                camera = Camera.open();
                isPrevious = true;
            }
            try {
                camera.setPreviewDisplay(surfaceHolder);
                Camera.Parameters parameters = camera.getParameters();

                parameters.setPictureFormat(PixelFormat.JPEG);
                parameters.set("jpeg-quality",80);

                camera.setParameters(parameters);
                camera.startPreview();

                camera.autoFocus(null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        Button takePhoto = findViewById(R.id.camera_takephoto);
        takePhoto.setOnClickListener((v)->{
            if (camera != null){
                camera.takePicture(null,null,jpeg);
            }
        });

    }
    final Camera.PictureCallback jpeg = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(data,0,data.length);
            camera.startPreview();
            isPrevious = false;

            File appDir = new File(Environment.getExternalStorageState(),"/DCIM/Camera/");
            if (!appDir.exists()){
                appDir.mkdir();
            }
            String fileName = System.currentTimeMillis() + ".jpg";
            File file = new File(appDir,fileName);
            try{
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
            }catch (Exception e){
                e.printStackTrace();
            }

            try{
                MediaStore.Images.Media.insertImage(CameraClass.this.getContentResolver(),
                        file.getAbsolutePath(),fileName,null);
            }catch (Exception e){
                e.printStackTrace();
            }
            CameraClass.this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                    Uri.parse("file://" + "")));
            Toast.makeText(CameraClass.this,"照片保存在：" + file,Toast.LENGTH_SHORT).show();
            resetCamera();
        }
    };
    void resetCamera(){
        if (!isPrevious){
            camera.startPreview();
            isPrevious = true;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (camera != null){
            camera.stopPreview();
            camera.release();
        }
    }
}