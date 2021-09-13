package com.example.dynamicwarehouse;

import android.content.Context;

import java.util.Random;

public class WeakLoader extends Box {

    public WeakLoader(Context context) {
        Speed=0.2F;
        Random random = new Random();
        bitmapId = R.drawable.loader2;
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


}
