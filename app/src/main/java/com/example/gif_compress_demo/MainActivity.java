package com.example.gif_compress_demo;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.gif_compress_demo.utils.PermissionUtils;
import com.example.gif_compress_demo.utils.UriParseUtils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private static final int REQUEST_CODE_PERMISSIONS = 0x1001;
    private static final int REQUESTCODE = 200;

    private Button btn;
    private ImageView ivGlide;
    private ImageView ivGiflib;

    private Bitmap bitmap;
    private GifInfoHandle infoHandle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        ivGlide = (ImageView) findViewById(R.id.iv_glide);
        ivGiflib = (ImageView) findViewById(R.id.iv_giflib);
        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                PermissionUtils.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE_PERMISSIONS,
                        okRunnable);
                break;
        }
    }

    private Runnable okRunnable = new Runnable() {
        @Override
        public void run() {
            //调用图库，获取所有本地图片
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, REQUESTCODE);
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionUtils.onRequestPermissionsResult(requestCode == REQUEST_CODE_PERMISSIONS,
                grantResults,
                okRunnable,
                new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "没有获得必要的权限", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUESTCODE && resultCode == RESULT_OK) {
            if (data != null) {
                Uri uri = data.getData();
                if (uri == null) {
                    return;
                }
                String path = UriParseUtils.getPath(this, uri);
                setImagePath(path);
            }
        }
    }

    private void setImagePath(String path) {
        Log.d(TAG, "initImageView: " + path);
        if (!path.endsWith("gif")) {
            return;
        }

        Glide.with(MainActivity.this).load(path).into(ivGlide);

        infoHandle = new GifInfoHandle(path);
        int width = infoHandle.getWidth();
        int height = infoHandle.getHeight();
        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        // nextTime
        long nextFrameTime = infoHandle.renderFrame(bitmap);
        ivGiflib.setImageBitmap(bitmap);
        handler.sendEmptyMessageAtTime(1, nextFrameTime);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            // nextTime
            long nextFrameTime = infoHandle.renderFrame(bitmap);
            ivGiflib.setImageBitmap(bitmap);
            handler.sendEmptyMessageAtTime(1, nextFrameTime);
        }
    };

}
