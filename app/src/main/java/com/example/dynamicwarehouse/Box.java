package com.example.dynamicwarehouse;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

public class Box {
    protected float x; // координаты
    protected float y;
    protected float Speed;

    protected boolean fromUp=true;
    protected  int course;

    protected int size; // размер
    protected float speedY; // скорость
    protected float speedX; // скорость
    protected int bitmapId; // id картинки
    protected Bitmap bitmap; // картинка

    void init(Context context) { // сжимаем картинку до нужных размеров
        Bitmap cBitmap = BitmapFactory.decodeResource(context.getResources(), bitmapId);
        bitmap = Bitmap.createScaledBitmap(
                cBitmap, (int)(size * GameView.unitW), (int)(size * GameView.unitH), false);
        cBitmap.recycle();
    }

    public void update(){ // тут будут вычисляться новые координаты

        if (y>GameView.maxY-size){
            speedY*=-1;
        }
        if (y<0 ) {
            speedY*=-1;
        }

        if (x>(GameView.maxX-size)) {
            speedX*=-1;
        }
        if (x<0){
            speedX*=-1;
        }

        y += speedY;
        x += speedX;

    }

    void drow(Paint paint, Canvas canvas){ // рисуем картинку
        canvas.drawBitmap(bitmap, x*GameView.unitW, y*GameView.unitH, paint);
    }
}
