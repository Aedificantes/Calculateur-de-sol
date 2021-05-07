package com.example.aedificantes_calculateur_se_sol;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;

import com.example.aedificantes_calculateur_se_sol.Schema.GraphPieu;
import com.example.aedificantes_calculateur_se_sol.Schema.HatchTexture;
import com.example.aedificantes_calculateur_se_sol.Schema.HorizontalLineTexture;
import com.example.aedificantes_calculateur_se_sol.Schema.LineTexture;
import com.example.aedificantes_calculateur_se_sol.Schema.PointTexture;

public class CanvaActivty extends AppCompatActivity {

    private ConstraintLayout CL_ConstraintLayout;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canva_activty);
        CL_ConstraintLayout = findViewById(R.id.CL_mainCanvas);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        Bitmap bg = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bg);

        //dessin du sol fixe
        Paint blackTracePaint = new Paint();
        blackTracePaint.setColor(Color.BLACK);
        blackTracePaint.setStrokeWidth(10);
        blackTracePaint.setStyle(Paint.Style.STROKE);
        canvas.drawLine(1500,200,2000,200,blackTracePaint);
        canvas.drawLine(1400,100,1500,200,blackTracePaint);
        canvas.drawLine(1000,100,1400,100,blackTracePaint);


        //dessin des sols personnalisés
        canvas.drawRect(1500, 200, 2000,500, new HatchTexture(5, Color.BLUE));
        canvas.drawRect(1500, 500, 2000,800,new PointTexture(0,Color.parseColor("#3EAB99")));
        canvas.drawRect(1500, 800, 2000,1000,new HorizontalLineTexture(5,Color.parseColor("#DF9C35")));
        canvas.drawRect(1500, 1000, 2000,1200,new LineTexture(20,Color.RED));
        canvas.drawRect(1500, 1200, 2000,1400,new LineTexture(0,Color.RED));
        //dessin du pieu dans le sol
        GraphPieu graphPieu = new GraphPieu(canvas,1750,200,1000,40);
        graphPieu.draw();

        CL_ConstraintLayout.setBackground(new BitmapDrawable(bg));
    }



}