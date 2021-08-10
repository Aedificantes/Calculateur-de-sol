package com.example.aedificantes_calculateur_se_sol.PDF;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfDocument;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.aedificantes_calculateur_se_sol.Calculator.ResultDisplayer;
import com.example.aedificantes_calculateur_se_sol.Calculator.ResultManager;
import com.example.aedificantes_calculateur_se_sol.Details.DetailsDisplayer;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamContainer;
import com.example.aedificantes_calculateur_se_sol.R;
import com.example.aedificantes_calculateur_se_sol.Schema.LayerDrawer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStream;

public class PdfGenerator {

    private static final String LOG_TAG = "CLA_5784";
    private ParamContainer container;

    public PdfGenerator(ParamContainer container) {
        this.container = container;
    }

    public void generate(ViewGroup todraw, Context context, WindowManager windowManager){
        int screenWidth = 2560,screenHeight = 1800;


        PdfDocument document = new PdfDocument();
        // create a page description
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(screenWidth, screenHeight, 1).create();

        // start a page
        PdfDocument.Page page = document.startPage(pageInfo);
        todraw.draw(page.getCanvas());
        document.finishPage(page);



        // start a page
        PdfDocument.PageInfo pageInfo2 = new PdfDocument.PageInfo.Builder(screenWidth, screenHeight, 2).create();
        PdfDocument.Page page2 = document.startPage(pageInfo2);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.fragment_result, null);
        ConstraintLayout CL_result = view.findViewById(R.id.CL_result);

        ResultDisplayer displayer = new ResultDisplayer(CL_result);
        ResultManager resultManager = new ResultManager(container.get_ParamContainerData());
        displayer.updateData(resultManager);
        displayer.show(screenWidth,screenHeight);

        CL_result.measure(screenWidth,screenHeight);
        CL_result.layout(0,0,screenWidth,screenHeight);
        CL_result.setBackgroundColor(Color.parseColor("#F6F6F6"));

        CL_result.draw(page2.getCanvas());

        document.finishPage(page2);


        // start a page
        PdfDocument.PageInfo pageInfo3 = new PdfDocument.PageInfo.Builder(screenWidth, screenHeight, 3).create();
        PdfDocument.Page page3 = document.startPage(pageInfo3);

        LayoutInflater inflaterDrawing = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewDrawing = inflaterDrawing.inflate(R.layout.fragment_drawing, null);
        ConstraintLayout CL_ConstraintLayout = viewDrawing.findViewById(R.id.CL_Frag_drawing);

        ResultManager calculator = new ResultManager(container.get_ParamContainerData());

        Bitmap bg = Bitmap.createBitmap(screenWidth,screenHeight,Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bg);

        //dessin du sol fixe
        LayerDrawer layerDrawer = new LayerDrawer(container.get_ParamContainerData(), canvas, windowManager);

        layerDrawer.toDrawDrawing(0.70f,0.20f,0.05f);
        layerDrawer.toDrawChart(0.10f,0.40f,0.05f);

        CL_ConstraintLayout.setBackground(new BitmapDrawable(bg));

        CL_ConstraintLayout.measure(screenWidth,screenHeight);
        CL_ConstraintLayout.layout(0,0,screenWidth,screenHeight);
        //CL_ConstraintLayout.setBackgroundColor(Color.parseColor("#F6F6F6"));

        CL_ConstraintLayout.draw(page3.getCanvas());

        document.finishPage(page3);

        for(int i =0; i< 3;i++){
            // start a page
            PdfDocument.PageInfo pageInfo4 = new PdfDocument.PageInfo.Builder(screenWidth, screenHeight, 4+i).create();
            PdfDocument.Page page4 = document.startPage(pageInfo4);

            View viewDetail = inflaterDrawing.inflate(R.layout.fragment_detail, null);
            ConstraintLayout CL_ConstraintLayout_details = viewDetail.findViewById(R.id.CL_Frag_detail);


            DetailsDisplayer displayerDetail = new DetailsDisplayer(context,CL_ConstraintLayout_details, null);
            displayerDetail.generateAndDisplay_toPDF(container.get_ParamContainerData(), screenWidth, screenHeight);
            if(i == 0) {
                displayerDetail.display_elements(0, 1, 2, 3, 4);
            }else if(i ==1){
                displayerDetail.display_elements(5);
            }

            if(i == 2){
                displayerDetail.display_elements(6,7,8);
            }

            CL_ConstraintLayout_details.measure(screenWidth,screenHeight);
            CL_ConstraintLayout_details.layout(0,0,screenWidth,screenHeight);

            CL_ConstraintLayout_details.draw(page4.getCanvas());
            document.finishPage(page4);

        }


        // write the document content
        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        try {
            File gpxfile = new File(dir, "PDF_CREATOR.pdf");
            FileOutputStream fos = new FileOutputStream(gpxfile);
            document.writeTo(fos);
            document.close();
            fos.close();
        } catch (Exception e){
            Log.e(LOG_TAG,e.getMessage());
            e.printStackTrace();
        }

        Log.d(LOG_TAG,"PDF WAS CREATED ");
        // close the document
        document.close();
    }
}
