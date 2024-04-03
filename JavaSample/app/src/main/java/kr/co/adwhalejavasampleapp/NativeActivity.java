package kr.co.adwhalejavasampleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import kr.co.adwhale.sdk.core.cauly.AdWhaleNativeAdListener;
import kr.co.adwhale.sdk.core.cauly.AdWhaleNativeAdView;

public class NativeActivity extends AppCompatActivity {

    Context context = null;
    String type;
    String width;
    String height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native);

        context = this;
        AdWhaleNativeAdView adWhaleNativeAdView = new AdWhaleNativeAdView(context);

        Intent intent = getIntent();
        width = intent.getStringExtra("width");
        height = intent.getStringExtra("height");

        Log.d("AdWhaleSampleApp", this.getClass().getSimpleName() + " Native Ad Size : " + width + "x" + height);
        adWhaleNativeAdView.setNativeAdSize(width,height);

        RelativeLayout relativeLayout = findViewById(R.id.mainnativeroot);
        relativeLayout.removeAllViews();
        relativeLayout.addView(adWhaleNativeAdView);

        adWhaleNativeAdView.loadAd(context, "발급 받은 네이티브 광고 AD_UNIT_ID 입력", new AdWhaleNativeAdListener() {
            @Override
            public void onNativeAdLoaded() {
                Log.d("["+ this.getClass().getSimpleName()+"]", this.getClass().getSimpleName() + ". AdWhaleNativeAdView Native AdLoaded Success");
            }

            @Override
            public void onAdFailedToLoad(int errorCode, String errorMessage) {
                Log.d("["+ this.getClass().getSimpleName()+"]", this.getClass().getSimpleName() + ". AdWhaleNativeAdView Native AdLoaded Fail(" + errorCode + ", " + errorMessage + ").");
                Log.e("["+ this.getClass().getSimpleName()+"]", this.getClass().getSimpleName() + ". AdWhaleNativeAdView Native AdLoaded Fail(" + errorCode + ", " + errorMessage + ").");
            }
        });
    }


}