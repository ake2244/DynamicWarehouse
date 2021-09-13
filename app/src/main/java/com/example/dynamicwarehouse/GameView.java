package com.example.dynamicwarehouse;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.util.ArrayList;


public class GameView extends SurfaceView implements Runnable{
    public static int maxX = 40; // размер по горизонтали
    public static int maxY = 56; // размер по вертикали
    public static float unitW = 0; // пикселей в юните по горизонтали
    public static float unitH = 0; // пикселей в юните по вертикали
    private boolean firstTime = true;
    private boolean gameRunning = true;
    private StrongLoader strongLoader;
    private WeakLoader weakLoader;
    private Thread gameThread = null;
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;
    private ArrayList<BoxMiddle> boxMiddles = new ArrayList<>(); // тут будут харанится коробки
    private ArrayList<BoxLittle> boxLittles = new ArrayList<>();// тут будут харанится коробки
    private ArrayList<BoxBig> boxBigs = new ArrayList<>();// тут будут харанится коробки
    private final int ASTEROID_INTERVAL = 450; // время через которое появляются коробки (в итерациях)
    private int currentTime = 0;

    public GameView(Context context) {
        super(context);
        //инициализируем обьекты для рисования
        surfaceHolder = getHolder();
        paint = new Paint();
        // инициализируем поток
        gameThread = new Thread(this);
        gameThread.start();
    }

        @Override
        public void run () {
            while (gameRunning) {
                update();
                draw();
                try {
                    checkCollision();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                checkIfNewAsteroid();
                control();
            }
        }


    public void checkCollision() throws IOException{ // перебираем все коробки и проверяем не касается ли один из них грузчиков
        for (BoxMiddle boxMiddle : boxMiddles) {
            if (boxMiddle.isCollision(strongLoader.x, strongLoader.y, strongLoader.size)) {
           //     Log.d("BeforeCollis", Integer.toString(boxMiddles.size()));

                try {
                    boxMiddles.remove(boxMiddle);
                    throw new IOException();
                }
                catch (IOException e) {
                    throw new IOException(new IOException(e));
                }
            }
        }

        for (BoxBig boxBig : boxBigs) {
            if (boxBig.isCollision(strongLoader.x, strongLoader.y, strongLoader.size)) {

                try {
                    boxBigs.remove(boxBig);
                    throw new IOException();
                }
                catch (IOException e) {
                    throw new IOException(new IOException(e));
                }
            }
        }

        for (BoxLittle boxLittle : boxLittles) {
            if (boxLittle.isCollision(weakLoader.x, weakLoader.y, weakLoader.size)) {

                try {
                    boxLittles.remove(boxLittle);
                    throw new IOException();
                }
                catch (IOException e) {
                    throw new IOException(new IOException(e));
                }
            }
        }
    }


    private void update() {
        if (!firstTime) {
            strongLoader.update();
            weakLoader.update();

            for (BoxMiddle boxMiddle : boxMiddles) {
              boxMiddle.update();
            }

            for (BoxLittle boxLittle : boxLittles) {
                boxLittle.update();
            }

            for (BoxBig boxBig : boxBigs) {
                boxBig.update();
            }

        }
    }


    private void draw() {
        if (surfaceHolder.getSurface().isValid()) {  //проверяем валидный ли surface

            if(firstTime){ // инициализация при первом запуске
                firstTime = false;
                unitW = surfaceHolder.getSurfaceFrame().width()/maxX; // вычисляем число пикселей в юните
                unitH = surfaceHolder.getSurfaceFrame().height()/maxY;

                strongLoader = new StrongLoader(getContext()); // добавляем погрузчик
                weakLoader = new WeakLoader(getContext()); // добавляем грузчика
                addBox();
            }

            canvas = surfaceHolder.lockCanvas(); // закрываем canvas
            canvas.drawColor(Color.GRAY); // заполняем фон

            strongLoader.drow(paint, canvas); // рисуем погрузчик
            weakLoader.drow(paint, canvas); // рисуем грузчика

            for(BoxMiddle boxMiddle : boxMiddles){ // рисуем средние коробки
                boxMiddle.drow(paint, canvas);
            }

            for(BoxLittle boxLIttle : boxLittles){ // рисуем маленькие коробки
                boxLIttle.drow(paint, canvas);
            }

            for(BoxBig boxBig : boxBigs){ // рисуем большие коробки
                boxBig.drow(paint, canvas);
            }

            surfaceHolder.unlockCanvasAndPost(canvas); // открываем canvas
        }
    }

    private void control() { // пауза
        try {
            gameThread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void addBox(){
        for (int i=0; i<10; i++) {

            BoxMiddle boxMiddle = new BoxMiddle(getContext());
            boxMiddles.add(boxMiddle);

            BoxLittle boxLittle = new BoxLittle(getContext());
            boxLittles.add(boxLittle);

            BoxBig boxBig = new BoxBig(getContext());
            boxBigs.add(boxBig);
        }
    }

    private void checkIfNewAsteroid(){ // каждые ASTEROID_INTERVAL итераций добавляем новые коробки по 10 шт.
        if(currentTime >= ASTEROID_INTERVAL){
            addBox();
            currentTime = 0;
        }else{
            currentTime ++;
        }
    }

}
