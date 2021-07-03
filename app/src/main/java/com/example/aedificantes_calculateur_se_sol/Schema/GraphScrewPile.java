package com.example.aedificantes_calculateur_se_sol.Schema;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;

/**
 * this class is specially create to draw Piles in ground in drawing activity
 */
public class GraphScrewPile {
    float cX=100;
    float  cY=100;
    float Xlenght = 10;
    float Ylenght = 500;
    int leftP=25;
    float percentHead = 0.9f;
    private Canvas canva;

    public GraphScrewPile(Canvas canva, float x, float y, float height, float width ) {
        this.canva = canva;
        this.cX = x;
        this.cY = y;
        this.Ylenght = height - width*3;
        this.Xlenght = width;
    }

    public void draw(){
        int head = (int) (Xlenght/3);
        leftP = head*5;

        Path path = new Path();
        Paint paint =new Paint();
        paint.setColor(Color.parseColor("#2D59B2"));
        paint.setStrokeWidth(3);
        path.moveTo(cX+Xlenght, (float) (cY+Ylenght*percentHead));
        path.cubicTo(cX+Xlenght+leftP,(float) (cY+head+Ylenght*percentHead),cX+Xlenght+leftP,(float) (cY+5+Ylenght*percentHead), (float) (cX+(Xlenght/2)),(float) (cY+head*3+Ylenght*percentHead));//право
        path.cubicTo((float)(cX-leftP*0.85),(float) (cY+head*4+Ylenght*percentHead),(float)(cX-leftP*0.85),(float) (cY+head*5+Ylenght*percentHead),cX,(float) (cY+head*6+Ylenght*percentHead));//лево
        path.moveTo(cX+Xlenght,(float) (cY+(head*6+head/2)+Ylenght*percentHead));
        path.cubicTo((float)(cX+Xlenght+leftP*0.60),(float) (cY+head*7+Ylenght*percentHead),(float)(cX+Xlenght+leftP*0.60),(float) (cY+head*7+Ylenght*percentHead),(float)(cX+(Xlenght/2)),(float) (cY+head*9+Ylenght*percentHead));
        path.cubicTo((float)(cX-leftP*0.45),(float) (cY+head*10+Ylenght*percentHead),(float)(cX-leftP*0.45),(float) (cY+head*10+Ylenght*percentHead),cX+Xlenght/2,(float) (cY+head*11+Ylenght*percentHead));//лево
        canva.drawPath(path, paint);

        Paint paint2 = new Paint();
        paint2.setShader(new LinearGradient(0,0,Xlenght,0,Color.parseColor("#9BC6E7"),Color.parseColor("#2D59B2"), Shader.TileMode.MIRROR));
        Path path2 = new Path();
        path2.moveTo(cX,cY);
        path2.lineTo(cX,cY+Ylenght);
        path2.lineTo((float) (cX+(Xlenght/2)),cY+Ylenght+Xlenght*3);
        path2.lineTo(cX+Xlenght,cY+Ylenght);
        path2.lineTo(cX+Xlenght,cY);
        canva.drawPath(path2,paint2);
    }

}
