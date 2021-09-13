package com.example.dynamicwarehouse;

import android.content.Context;

import java.util.Random;

public class BoxMiddle extends Box {

    public BoxMiddle(Context context) {
        Speed=0.3F;
        Random random = new Random();
        bitmapId = R.drawable.box;
        size = 4;
        y=random.nextInt(GameView.maxY-size*2)+5;
        x = random.nextInt(GameView.maxX-size*2)+5;


        course=random.nextInt(2);

        if (course==0) {
            speedX=-Speed;
            speedY=-Speed;
            fromUp=false;
        }
        else {
            speedX= Speed;
            speedY= Speed;
            fromUp=true;
        }

        init(context);
    }

    public void update() {
        super.update();
    }

    public boolean isCollision(float shipX, float shipY, float shipSize) {
        return !(((x+size) < shipX)||(x > (shipX+shipSize))||((y+size) < shipY)||(y > (shipY+shipSize)));
    }

}
