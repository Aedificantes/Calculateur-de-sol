package com.example.aedificantes_calculateur_se_sol.Schema.texturePackage;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;

import com.example.aedificantes_calculateur_se_sol.Schema.texturePackage.Texture;

public class HatchTexture extends Paint implements Texture {
    Bitmap bitmapShar;

    public HatchTexture(int number, int color) {
        super();
        if(number < 0){ number = 0;}
        int size = 25 + number;
        bitmapShar = Bitmap.createBitmap(size,size,Bitmap.Config.ARGB_8888);
        Canvas BitmapShader = new Canvas(bitmapShar);
        Paint tampPaint = new Paint();
        tampPaint.setColor(color);
        tampPaint.setColor(color);
        tampPaint.setStrokeWidth(2);
        BitmapShader.drawLine(0,0,size,size,tampPaint);
        BitmapShader.drawLine(size,0,0,size,tampPaint);
        android.graphics.BitmapShader fillBMPshader = new BitmapShader(bitmapShar, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        this.setStyle(Paint.Style.FILL);
        this.setShader(fillBMPshader);
    }

    @Override
    public Paint getPaint() {
        return this;
    }
}
