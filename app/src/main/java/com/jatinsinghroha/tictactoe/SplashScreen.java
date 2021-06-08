package com.jatinsinghroha.tictactoe;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;

import com.airbnb.lottie.LottieAnimationView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    LottieAnimationView mSplashAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        mSplashAnimation = findViewById(R.id.splashAnimation);

        mSplashAnimation.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Intent myIntent = new Intent(SplashScreen.this, IndianStatesAPIExample.class);
                myIntent.putExtra("isComingFromSplash", true);
                myIntent.putExtra("myAge", 23);
                myIntent.putExtra("myName", "Jatin");
                startActivity(myIntent);
                finish();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

    }
}