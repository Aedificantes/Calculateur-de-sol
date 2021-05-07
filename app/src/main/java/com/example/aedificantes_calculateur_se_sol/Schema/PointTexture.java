package com.example.aedificantes_calculateur_se_sol.Schema;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;

public class PointTexture extends Paint implements Texture {
    Bitmap bitmapShar;

    public PointTexture(int number, int color) {
        super();
        if(number < 0){ number = 0;}
        int size = 25 + number;
        bitmapShar = Bitmap.createBitmap(size,size,Bitmap.Config.ARGB_8888);
        Canvas BitmapShader = new Canvas(bitmapShar);
        Paint tampPaint = new Paint();
        tampPaint.setColor(color);
        BitmapShader.drawCircle(size/2,size/2,5,tampPaint);
        //BitmapShader.drawRect(1f,1f,8,8,tampPaint);
        android.graphics.BitmapShader fillBMPshader = new BitmapShader(bitmapShar, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        this.setStyle(Paint.Style.FILL);
        this.setShader(fillBMPshader);
    }

    @Override
    public Paint getPaint() {
        return this;
    }
}
