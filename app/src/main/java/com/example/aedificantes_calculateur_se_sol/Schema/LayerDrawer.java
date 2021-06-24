package com.example.aedificantes_calculateur_se_sol.Schema;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import com.example.aedificantes_calculateur_se_sol.Calculator.ResultManager;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamSol.ParamSolData;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.Pieu.PieuManagerData;

import java.util.ArrayList;

public class LayerDrawer {
    private ResultManager calculator;
    private PieuManagerData pieuParamManagerData;
    private ArrayList<ParamSolData> paramSolDataList;
    private Paint lineColor,font;

    private float width_screen=0, height_screen=0;
    private Canvas canvas;
    float offset_X_rect, size_X_rect, offset_y;
    float heightMaxLayers;
    float HkheightDraw;
    float sumLayerHeightLegend;
    float diff_outGround; // hauteur en px du pieu sortant du sol
    float pieuDrawHeight; // hauteur du pieu dessiné


    public LayerDrawer(ArrayList<ParamSolData> listParamSolData,PieuManagerData pieuParamManagerData, Canvas canva, WindowManager manager) {
        calculator = new ResultManager(listParamSolData,pieuParamManagerData);
        this.pieuParamManagerData = pieuParamManagerData;
        this.paramSolDataList = listParamSolData;
        lineColor = new Paint();
        lineColor.setColor(Color.BLACK);
        lineColor.setStyle(Paint.Style.STROKE);
        lineColor.setStrokeWidth(4);
        font = new Paint();
        font.setColor(Color.BLACK);
        font.setTextSize(50);

        Display display = manager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width_screen = size.x;
        height_screen = size.y;

        this.canvas = canva;

    }

    public void toDrawChart(float offset_x_prop, float size_X_prop, float offset_y_prop){
        int index_couchePortante = calculator.getLayerCalculator().index_couchePortante();
        offset_X_rect = offset_x_prop * width_screen;
        size_X_rect = size_X_prop * width_screen;
        offset_y = offset_y_prop * height_screen;
        float size_to_right_border_legend;
        float offset_StartX = offset_X_rect + (size_X_rect/10);
        int number_value_chart = 3;
        size_to_right_border_legend = size_X_rect - (size_X_rect/10) - 100;


        heightMaxLayers = height_screen-offset_y-50;
        HkheightDraw = ((pieuParamManagerData.Hk_val()/1000)*height_screen)/(calculator.getLayerCalculator().accumulation_h_couche_index(calculator.getLayerCalculator().index_couchePortante()-1)/1000);
        System.out.println(((pieuParamManagerData.Hk_val()/1000)*height_screen)/(calculator.getLayerCalculator().accumulation_h_couche_index(calculator.getLayerCalculator().index_couchePortante()-1)/1000));


        float[][] tabLayer = drawLayer_focus(index_couchePortante,heightMaxLayers,offset_y+HkheightDraw,offset_X_rect,size_X_rect);
        drawPieu_focus(HkheightDraw,offset_y+HkheightDraw,height_screen,offset_StartX);

        float[] tab_resistance;
        float base_H = pieuParamManagerData.H_val();
        float reduce_height = 0;
        float enfoncement[] = new float[index_couchePortante];

        for(int i=0; i < index_couchePortante; i++){
            enfoncement[i] = (calculator.getLayerCalculator().enfoncement_couche_index(i));
            if(i == index_couchePortante-1){ enfoncement[i] += pieuParamManagerData.Dhel_val();} // if it's support layer, add dhel size
        }
        float org_fdComp = calculator.fd0_comp();
        for(int i=0; i < index_couchePortante; i++){
            for(int j=0; j < number_value_chart+1; j++ ){
                float y_pos =0, first_divider_reduce_height = 0;
                if(j == 0){
                    first_divider_reduce_height = enfoncement[i]/(number_value_chart*3);
                    reduce_height += first_divider_reduce_height;
                    y_pos =  tabLayer[i][0]+ ((tabLayer[i][1]- tabLayer[i][0])/(number_value_chart*3));
                }else{
                    reduce_height +=  enfoncement[i]/number_value_chart;
                    y_pos = tabLayer[i][0]+ ((tabLayer[i][1]- tabLayer[i][0])/number_value_chart)*(j);
                }
                Log.d("toDrawChart"," new H set :"+ reduce_height);
                pieuParamManagerData.set_H_val(reduce_height);
                float x_pos_legend = offset_StartX + (calculator.fd0_comp() * size_to_right_border_legend)/org_fdComp ;
                writeTextCanvas(0,x_pos_legend,y_pos, calculator.fdcomp_toDisplay());
                if(j == 0){
                   reduce_height -= first_divider_reduce_height;
                }
            }

        }
        Log.d("toDrawChart"," filal H set :"+ reduce_height +"original one was "+base_H);


    }

    public void toDrawDrawing(float offset_x_prop, float size_X_prop, float offset_y_prop){

        int index_couchePortante = calculator.getLayerCalculator().index_couchePortante();
        offset_X_rect = offset_x_prop * width_screen;
        size_X_rect = size_X_prop * width_screen;
        offset_y = offset_y_prop * height_screen;

        heightMaxLayers = height_screen-offset_y-50;


        HkheightDraw = ((pieuParamManagerData.Hk_val()/1000)*height_screen)/(calculator.getLayerCalculator().accumulation_h_couche_index(calculator.getLayerCalculator().index_couchePortante()-1)/1000);
        System.out.println(((pieuParamManagerData.Hk_val()/1000)*height_screen)/(calculator.getLayerCalculator().accumulation_h_couche_index(calculator.getLayerCalculator().index_couchePortante()-1)/1000));
        float pieuStartX = offset_X_rect + (size_X_rect/2);


        drawGround(offset_X_rect,offset_y,offset_X_rect+size_X_rect,HkheightDraw);
        float[][] tabLayer = drawLayer(index_couchePortante,heightMaxLayers,offset_y+HkheightDraw,offset_X_rect,size_X_rect);
        drawPieu(HkheightDraw,offset_y+HkheightDraw,height_screen,pieuStartX);
        //drawLegend(tabLayer,width_screen,height_screen,offset_X_rect,size_X_rect,heightMaxLayer,index_coucheportante);


        // dessine la legende

        canvas.save();
        for(int i=0; i < tabLayer.length; i++){
            float start = tabLayer[i][0], height =tabLayer[i][1];
            String str_left = String.valueOf(calculator.getLayerCalculator().enfoncement_couche_index(i));


            if(i == index_couchePortante-1){
                height = (calculator.getLayerCalculator().accumulation_enfoncement_couche_index(i)/1000 *heightMaxLayers)/(calculator.getLayerCalculator().accumulation_h_couche_index(index_couchePortante-1) /1000);
                height += HkheightDraw;
            }else if(i == 0){
                //dessine les lignes gauche dans le cas de la première couche avec d'autres couche après
                canvas.drawLine(offset_X_rect,start-HkheightDraw,offset_X_rect-50,start-HkheightDraw,lineColor);
                canvas.drawLine(offset_X_rect,height,offset_X_rect-50f,height,lineColor);
            }

            //dessine les lignes droite
            canvas.drawLine(size_X_rect+offset_X_rect,start,size_X_rect+offset_X_rect+50,start,lineColor);
            canvas.drawLine(size_X_rect+offset_X_rect,height,size_X_rect+offset_X_rect+50f,height,lineColor);

            if(i == 0 && i != index_couchePortante-1){
                String str_right = String.valueOf(calculator.getLayerCalculator().enfoncement_couche_index(i) + pieuParamManagerData.Hk_val());
                //writeTextCanvas(90,offset_X_rotate+((height-start)/2)+start,offset_Y_rotate+(width_screen-offset_X_rect+50f),str_right);
                writeTextCanvas(90,(offset_X_rect-50f),((height-offset_y)/3)+offset_y,str_right);
            }
            //writeTextCanvas(90,offset_X_rotate+((height-start)/2)+start,offset_Y_rotate+(width_screen-offset_X_rect-size_X_rect-50f),str_left);
            writeTextCanvas(0,(offset_X_rect+size_X_rect+20f),((height-start)/2)+start,str_left);

        }

        //dessine les lignes et le texte pour la hauteur Hk
        canvas.drawLine(size_X_rect+offset_X_rect,offset_y,size_X_rect+offset_X_rect+50,offset_y,lineColor);
        canvas.drawLine(size_X_rect+offset_X_rect,offset_y+HkheightDraw,size_X_rect+offset_X_rect+50f,offset_y+HkheightDraw,lineColor);
        writeTextCanvas(0,(offset_X_rect+size_X_rect+60f),offset_y+(HkheightDraw/2),String.valueOf(pieuParamManagerData.Hk_val()));

        //dessine les lignes et le texte pour la hauteur hors de sol du pieu
        canvas.drawLine(size_X_rect/2+offset_X_rect,offset_y+HkheightDraw-diff_outGround,size_X_rect/2+offset_X_rect+50,offset_y+HkheightDraw-diff_outGround,lineColor);
        canvas.drawLine(size_X_rect/2+offset_X_rect,offset_y+HkheightDraw,size_X_rect/2+offset_X_rect+50,offset_y+HkheightDraw,lineColor);
        writeTextCanvas(0, (float) (size_X_rect*0.75+offset_X_rect),offset_y+HkheightDraw-diff_outGround/2,String.valueOf(calculator.diff_H_Ip_Pieu()));

        //dessine les lignes et le texte pour la hauteur totale du pieu
        canvas.drawLine(offset_X_rect,offset_y+HkheightDraw,offset_X_rect-100,offset_y+HkheightDraw,lineColor);
        canvas.drawLine(offset_X_rect,offset_y+HkheightDraw-diff_outGround + pieuDrawHeight,offset_X_rect-100f,offset_y+HkheightDraw-diff_outGround + pieuDrawHeight,lineColor);
        writeTextCanvas(90, (float) (offset_X_rect-100),offset_y+HkheightDraw-diff_outGround + pieuDrawHeight/2,String.valueOf(pieuParamManagerData.H_val()));



    }

    private void writeTextCanvas(float rotate, float x, float y, String text){
        canvas.save();
        float[] x_y_converted  = convertXPosRotate(rotate,x,y);
        canvas.rotate(rotate,width_screen/2, height_screen/2);
        canvas.drawText(text,x_y_converted[0],x_y_converted[1],font);
        canvas.restore();
        canvas.save();
    }

    private float[] convertXPosRotate(float rotate, float org_x, float org_y){ // cette fonction doit pouvoir convertir la x et y en fonction de la rotation pour que le texte soit tournée sur sa position initiale par rapport au degrès 0
        float[] result = new float[2];
        float offset_X_rotate = (width_screen-height_screen)/2,offset_Y_rotate = width_screen-(width_screen-height_screen)/2;
        System.out.println("width:"+width_screen+ "height:"+height_screen+"  diff:"+(width_screen-height_screen));
        if(rotate == 0){ result[0] = org_x; result[1] = org_y; return result;}
        if(rotate == 90){ result[0] = org_y+ offset_X_rotate; result[1] = offset_Y_rotate - org_x; return result;}
        result[0] = org_x;
        result[1] = org_y;
        return result;
    }

    public void drawGround(float startX, float startY, float endX, float diffY){
        float middle_x = (endX - startX) /2;
        float step_x = (float) ((endX - startX) * 0.1);

        Paint blackTracePaint = new Paint();
        blackTracePaint.setColor(Color.BLACK);
        blackTracePaint.setStrokeWidth(10);
        blackTracePaint.setStyle(Paint.Style.STROKE);

        canvas.drawLine(startX,startY,startX+(step_x*2),startY,blackTracePaint);
        canvas.drawLine(startX+(step_x*2),startY,startX+(step_x*3),diffY+startY,blackTracePaint);
        canvas.drawLine(startX+(step_x*3),diffY+startY,endX,diffY+startY,blackTracePaint);

    }

    private float[][] drawLayer(int index_coucheportante,float heightMax,float startY, float offset_X_rect,float size_X_rect ){
        float[][] tabRecapLayer = new float[index_coucheportante][3];
        float start = startY;
        float height = startY;

        canvas.save();
        for(int i=0; i < index_coucheportante; i++){
            ParamSolData data = paramSolDataList.get(i);
            if(i == 0){
                float HkheightDraw = ((pieuParamManagerData.Hk_val()/1000)*heightMax)/(calculator.getLayerCalculator().accumulation_h_couche_index(calculator.getLayerCalculator().index_couchePortante()-1)/1000);
                float H_pieu = (data.h()*heightMax)/(calculator.getLayerCalculator().accumulation_h_couche_index(index_coucheportante-1) /1000);
                height += ( H_pieu - HkheightDraw );
                //to_str_size_right = (data.h()*1000) - pieuParamManagerData.Hk_val();
            }else {
                height += (data.h()*heightMax)/(calculator.getLayerCalculator().accumulation_h_couche_index(index_coucheportante-1) /1000);
            }

            canvas.drawRect(offset_X_rect,start,size_X_rect+offset_X_rect,height,data.getTypeSol().getTexture().getPaint());

            tabRecapLayer[i][0] = start;
            tabRecapLayer[i][1] = height;


            if(i != index_coucheportante-1){
                sumLayerHeightLegend +=(height-start);
            }

            start = height;

        }
        return tabRecapLayer;
    }

    private float[][] drawLayer_focus(int index_coucheportante,float heightMax,float startY, float offset_X_rect,float size_X_rect ){
        float[][] tabRecapLayer = new float[index_coucheportante][3];
        float start = startY;
        float height = startY;

        canvas.save();
        for(int i=0; i < index_coucheportante; i++){
            ParamSolData data = paramSolDataList.get(i);
            if(i == 0){
                float HkheightDraw = ((pieuParamManagerData.Hk_val()/1000)*heightMax)/(calculator.getLayerCalculator().accumulation_enfoncement_couche_index(index_coucheportante-1)/1000);
                float H_pieu = ((calculator.getLayerCalculator().enfoncement_couche_index(i)/1000)*heightMax)/(calculator.getLayerCalculator().accumulation_enfoncement_couche_index(index_coucheportante-1) /1000);
                height += ( H_pieu - HkheightDraw );
                //to_str_size_right = (data.h()*1000) - pieuParamManagerData.Hk_val();
            }else if(i == index_coucheportante-1){
                height += ((calculator.getLayerCalculator().enfoncement_couche_index(i)/1000)*heightMax)/(calculator.getLayerCalculator().accumulation_enfoncement_couche_index(index_coucheportante-1) /1000);
            }else{
                height += ((calculator.getLayerCalculator().enfoncement_couche_index(i)/1000)*heightMax)/(calculator.getLayerCalculator().accumulation_enfoncement_couche_index(index_coucheportante-1) /1000);
            }

            Log.d("drawLayer_focus",calculator.getLayerCalculator().enfoncement_couche_index(i)/1000 + "   " + calculator.getLayerCalculator().accumulation_enfoncement_couche_index(index_coucheportante-1) /1000);
            canvas.drawRect(offset_X_rect,start,size_X_rect+offset_X_rect,height,data.getTypeSol().getTexture().getPaint());

            tabRecapLayer[i][0] = start;
            tabRecapLayer[i][1] = height;


            if(i != index_coucheportante-1){
                sumLayerHeightLegend +=(height-start);
            }

            start = height;

        }
        return tabRecapLayer;
    }

    private void drawPieu(float HkheightDraw, float START_TOP, float height_screen, float startX){
        pieuDrawHeight = ((pieuParamManagerData.H_val()/1000)*height_screen)/(calculator.getLayerCalculator().accumulation_h_couche_index(calculator.getLayerCalculator().index_couchePortante()-1)/1000);
        diff_outGround = (((pieuParamManagerData.Ip_val() - pieuParamManagerData.H_val())/1000)*height_screen)/(calculator.getLayerCalculator().accumulation_h_couche_index(calculator.getLayerCalculator().index_couchePortante()-1)/1000) ;
        System.out.println(((pieuParamManagerData.H_val()/1000)*height_screen)/(calculator.getLayerCalculator().accumulation_h_couche_index(calculator.getLayerCalculator().index_couchePortante()-1)/1000));
        GraphPieu graphPieu = new GraphPieu(canvas,startX,START_TOP-diff_outGround,pieuDrawHeight,40);
        graphPieu.draw();
    }

    private void drawPieu_focus(float HkheightDraw, float START_TOP, float height_screen, float startX){
        pieuDrawHeight = ((pieuParamManagerData.H_val()/1000)*height_screen)/(calculator.getLayerCalculator().accumulation_enfoncement_couche_index(calculator.getLayerCalculator().index_couchePortante()-1)/1000);
        diff_outGround = (((pieuParamManagerData.Ip_val() - pieuParamManagerData.H_val())/1000)*height_screen)/(calculator.getLayerCalculator().accumulation_enfoncement_couche_index(calculator.getLayerCalculator().index_couchePortante()-1)/1000) ;
        System.out.println(((pieuParamManagerData.H_val()/1000)*height_screen)/(calculator.getLayerCalculator().accumulation_enfoncement_couche_index(calculator.getLayerCalculator().index_couchePortante()-1)/1000));
        GraphPieu graphPieu = new GraphPieu(canvas,startX,START_TOP-diff_outGround,pieuDrawHeight,40);
        graphPieu.draw();
    }

    private void drawLegend(float[][] tabLayer, float width_screen, float height_screen, float offset_X_rect, float size_X_rect, float heigtMaxLayers, int index_couchePortante){
        float offset_X_rotate = 450,offset_Y_rotate = -400;

        canvas.save();
        for(int i=0; i < tabLayer.length; i++){
            float start = tabLayer[i][0], height =tabLayer[i][1];
            String str_left = String.valueOf(calculator.getLayerCalculator().enfoncement_couche_index(i));


            if(i == index_couchePortante-1){
                height = (calculator.getLayerCalculator().accumulation_enfoncement_couche_index(i)/1000 *heigtMaxLayers)/(calculator.getLayerCalculator().accumulation_h_couche_index(index_couchePortante-1) /1000);
            }else if(i == 0){
                //dessine les lignes droite dans le cas de la première ciouche avec d'autres couche après
                canvas.drawLine(offset_X_rect,start,offset_X_rect-50,start,lineColor);
                canvas.drawLine(offset_X_rect,height,offset_X_rect-50f,height,lineColor);
            }

            //dessine les lignes droite
            canvas.drawLine(size_X_rect+offset_X_rect,start,size_X_rect+offset_X_rect+50,start,lineColor);
            canvas.drawLine(size_X_rect+offset_X_rect,height,size_X_rect+offset_X_rect+50f,height,lineColor);

            //valeur gauche
            canvas.rotate(90,width_screen/2, height_screen/2);

            if(i == 0 && i != index_couchePortante-1){
                canvas.drawText("test",offset_X_rotate+((height-start)/2)+start,offset_Y_rotate+(width_screen-offset_X_rect+50f),font); // en rotation le x et le y sont inversé
            }

            canvas.drawText(str_left,offset_X_rotate+((height-start)/2)+start,offset_Y_rotate+(width_screen-offset_X_rect-size_X_rect-50f),font); // en rotation le x et le y sont inversé
            canvas.restore();
            canvas.save();
        }
    }


}
