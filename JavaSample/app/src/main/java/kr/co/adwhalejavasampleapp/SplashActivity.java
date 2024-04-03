package kr.co.adwhalejavasampleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import kr.co.adwhale.sdk.core.cauly.AdWhaleInit;
import kr.co.adwhale.sdk.core.cauly.AdWhaleOpenAd;
import kr.co.adwhale.sdk.core.cauly.AdWhaleOpenAdListener;


public class SplashActivity extends AppCompatActivity{

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        context = this;

        Button button = (Button) findViewById(R.id.splash_btn1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdWhaleOpenAd adWhaleOpenAd = (AdWhaleOpenAd) getApplication();
                adWhaleOpenAd.init(context);
                adWhaleOpenAd.showAdIfAvailable("ca-app-pub-8713069554470817/3156662323", new AdWhaleOpenAdListener() {
                    @Override
                    public void onAdLoaded(AdWhaleOpenAd adWhaleOpenAd) {

                    }

                    @Override
                    public void onAdFailedToLoad(int errorCode, String errorMessage) {

                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(int errorCode, String errorMessage) {

                    }

                    @Override
                    public void onAdShowedFullScreenContent() {

                    }

                    @Override
                    public void onAdDismissedFullScreenContent() {

                    }

                    @Override
                    public void onAdClicked() {

                    }

                    @Override
                    public void onAdImpression() {

                    }
                });
            }
        });

        Button button2 = (Button) findViewById(R.id.splash_btn2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
            }
        });
    }

}