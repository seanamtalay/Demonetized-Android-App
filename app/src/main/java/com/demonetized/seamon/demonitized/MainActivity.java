package com.demonetized.seamon.demonitized;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private int screenWidth, screenHeight;
    private ImageView green_s, yellow_s;
    private ImageButton youtube_button;
    private float positionX, positionY;

    private Handler handler = new Handler();
    private long animationDuration = 7500;
    private boolean isRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // remove title(fullscreen mode)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        green_s = (ImageView)findViewById(R.id.green_s);
        yellow_s = (ImageView)findViewById(R.id.yellow_s);
        youtube_button = (ImageButton)findViewById(R.id.youtube_button);




        //get screen size
        WindowManager wm = getWindowManager();
        Display disp = wm.getDefaultDisplay();
        Point size = new Point();
        disp.getSize(size);
        screenHeight = size.y;
        screenWidth = size.x;

        //set positions
        // Move to out screen
        //green_s.setX(screenWidth/2);
        green_s.setY(-1300f);
        yellow_s.setY(-1300f);


        youtube_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePos();
            }
        });

        //
    }

    public void changePos(){
        //to prevent duplicate running animation, sound when spamming the button
        if(isRunning){
            return;
        }
        isRunning = true;

        final boolean isDemonetized = getRandomBoolean();
        MediaPlayer mp_g = MediaPlayer.create(this, R.raw.monitized);
        MediaPlayer mp_y = MediaPlayer.create(this, R.raw.demonitized);

        //reset position when click
        green_s.setY(-1300f);
        yellow_s.setY(-1300f);

        //flash green $
        new CountDownTimer(6000, 300) {

            public void onTick(long millisUntilFinished) {
                if(green_s.getVisibility() == View.GONE)
                    green_s.setVisibility(View.VISIBLE);
                else
                    green_s.setVisibility(View.GONE);
            }

            public void onFinish() {
                if(isDemonetized) {
                    green_s.setVisibility(View.VISIBLE);
                }
                else{
                    green_s.setVisibility(View.GONE);
                }
                isRunning = false;
            }
        }.start();

        //green
        ObjectAnimator animatorY_g = ObjectAnimator.ofFloat(green_s, "y", 500f);
        animatorY_g.setDuration(animationDuration);
        AnimatorSet animatorSet_g = new AnimatorSet();
        animatorSet_g.playTogether(animatorY_g);
        animatorSet_g.start();

        //yellow
        ObjectAnimator animatorY_y = ObjectAnimator.ofFloat(yellow_s, "y", 500f);
        animatorY_y.setDuration(animationDuration);
        AnimatorSet animatorSet_y = new AnimatorSet();
        animatorSet_y.playTogether(animatorY_y);
        animatorSet_y.start();


        if(isDemonetized) {
            green_s.setVisibility(View.VISIBLE);
            mp_g.start();
        }
        else{
            green_s.setVisibility(View.GONE);
            mp_y.start();
        }


    }

    //get true or false randomly
    public boolean getRandomBoolean() {
        Random random = new Random();
        return random.nextBoolean();
    }
}
