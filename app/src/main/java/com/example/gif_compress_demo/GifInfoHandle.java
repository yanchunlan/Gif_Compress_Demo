package com.example.gif_compress_demo;

import android.graphics.Bitmap;

/**
 * author:  ycl
 * date:  2019/08/29 13:37
 * desc:
 */
public class GifInfoHandle {

    private volatile long gifInfoAddress; // 存储gifInfo的native对象地址

    static {
        System.loadLibrary("android_gif");
    }

    public GifInfoHandle(String path) {
        this.gifInfoAddress = openFile(path);
    }

    public long renderFrame(Bitmap bitmap) {
        return renderFrame(gifInfoAddress, bitmap);
    }

    public synchronized int getWidth() {
        return getWidth(gifInfoAddress);
    }

    public synchronized int getHeight() {
        return getHeight(gifInfoAddress);
    }


    public native long openFile(String path);

    public native long renderFrame(long gifInfoAddress, Bitmap bitmap);

    public native int getWidth(long gifInfoAddress);

    public native int getHeight(long gifInfoAddress);

}
