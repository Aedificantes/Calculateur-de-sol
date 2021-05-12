package com.example.aedificantes_calculateur_se_sol;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.widget.TextView;

import com.example.aedificantes_calculateur_se_sol.Calculator.ResultManager;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamSol.ParamSolData;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.Pieu.PieuManagerData;
import com.example.aedificantes_calculateur_se_sol.Schema.GraphPieu;
import com.example.aedificantes_calculateur_se_sol.Schema.LayerDrawer;
import com.example.aedificantes_calculateur_se_sol.Schema.texturePackage.HatchTexture;
import com.example.aedificantes_calculateur_se_sol.Schema.texturePackage.HorizontalLineTexture;
import com.example.aedificantes_calculateur_se_sol.Schema.texturePackage.LineTexture;
import com.example.aedificantes_calculateur_se_sol.Schema.texturePackage.PointTexture;

import java.util.ArrayList;

public class CanvaActivty extends AppCompatActivity {

    private ConstraintLayout CL_ConstraintLayout;

    private static final int START_TOP = 100;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canva_activty);
        CL_ConstraintLayout = findViewById(R.id.CL_mainCanvas);

        ArrayList<ParamSolData> listParamSolData =  (ArrayList<ParamSolData>)getIntent().getSerializableExtra("listParamSolData");
        PieuManagerData pieuManagerData =  (PieuManagerData) getIntent().getSerializableExtra("pieuManagerData");
        ResultManager calculator = new ResultManager(listParamSolData,pieuManagerData);
        LayerDrawer layerDrawer = new LayerDrawer(listParamSolData,pieuManagerData);


        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        Bitmap bg = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bg);

        //dessin du sol fixe



        layerDrawer.toDraw(canvas,0.70f,0.20f,0.05f,getWindowManager());


        CL_ConstraintLayout.setBackground(new BitmapDrawable(bg));
        /* test de l'affichage du text, l'utilisation de TextView serait plus fonctionelle si la rotation devait être différent de 0,90,180,270
        TextView tv =new TextView(this);
        tv.setText("Textview");
        tv.setRotation(90);
        CL_ConstraintLayout.addView(tv);
        */

    }



}