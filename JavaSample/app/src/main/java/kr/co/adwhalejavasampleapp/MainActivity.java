package kr.co.adwhalejavasampleapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import kr.co.adwhale.sdk.core.cauly.AdWhaleAdView;
import kr.co.adwhale.sdk.core.cauly.AdWhaleBannerAdListener;
import kr.co.adwhale.sdk.core.cauly.AdWhaleInterstitialAd;
import kr.co.adwhale.sdk.core.cauly.AdWhaleInterstitialAdListener;
import kr.co.adwhale.sdk.core.cauly.AdWhaleRewardedAd;
import kr.co.adwhale.sdk.core.cauly.AdWhaleRewardedAdListener;
import kr.co.adwhale.sdk.core.cauly.AdWhaleInit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Context context = null;
    Button bannerBtn = null;

    Button interstitialBtn = null;
    Button rewardedBtn = null;
    Button testBtn = null;
    Button nativeBtn = null;
    Button gdprresetBtn;
    Button gdprcheckBtn;
    AdWhaleAdView adwhaleAdView;
    RelativeLayout rlRoot;
    Spinner spinner;
    String bannerType;

    String [] spinnerItems = {"BANNER_320x50", "BANNER_320x100", "BANNER_300x250"};

    EditText edit1;
    EditText edit2;
    AdWhaleInit adwhaleInit;
    public static boolean _DEBUG = true;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        adwhaleInit = new AdWhaleInit(); // AdWhale SDK 초기화
        _DEBUG = true;
        try{
            Log.e(this.getClass().getSimpleName(),"_DEBUG Value : " + _DEBUG);
            adwhaleInit.init(context, _DEBUG, "테스트 기기 문자열 입력", new AdWhaleInit.OnInitializatizedListener() {
                @Override
                public void onCompleted() {
                    Log.i(this.getClass().getName(), "GDPR 테스트 SampleApp Main isGDPR value : " + adwhaleInit.isGDPR());
                    Log.i(this.getClass().getName(),"GDPR 테스트 SampleApp Main canShowAds value : " + adwhaleInit.canShowAds());

                    if(!adwhaleInit.canShowAds()){
                        adwhaleInit.gdpr(context, new AdWhaleInit.OnGDPRInitializedListener() {
                            @Override
                            public void onCompleted() {
                                Log.i(this.getClass().getName(),"SampleApp Main isGDPR value : " + adwhaleInit.isGDPR());
                                Log.i(this.getClass().getName(),"SampleApp Main canShowAds value : " + adwhaleInit.canShowAds());
                            }
                            @Override
                            public void onFailed(String error) {
                                Log.e(this.getClass().getName(),"SampleApp Main GDPR Error Message : " + error);
                            }
                        });
                    }
                }
            });

        }catch (Exception e){
            Log.e(this.getClass().getName(),"SampleApp Main onCreate Init, GDPR Error : " + e.getMessage());
        }
        rlRoot = (RelativeLayout) findViewById(R.id.root);
        edit1 = (EditText)findViewById(R.id.edit1);
        edit2 = (EditText) findViewById(R.id.edit2);

        spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,spinnerItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        bannerType = spinner.getSelectedItem().toString();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                bannerType = spinner.getSelectedItem().toString();
                adwhaleAdView = null;
                adwhaleAdView = new AdWhaleAdView(context);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        adwhaleAdView = new AdWhaleAdView(context);

        gdprresetBtn = (Button)findViewById(R.id.gdpr_reset);
        gdprresetBtn.setOnClickListener(this);
        gdprcheckBtn = (Button)findViewById(R.id.gdpr_check);
        gdprcheckBtn.setOnClickListener(this);

        bannerBtn = (Button) findViewById(R.id.banner_btn1);
        bannerBtn.setOnClickListener(this);

        interstitialBtn = (Button) findViewById(R.id.interstitial_btn1);
        interstitialBtn.setOnClickListener(this);

        rewardedBtn = (Button) findViewById(R.id.rewarded_btn1);
        rewardedBtn.setOnClickListener(this);

        testBtn = (Button) findViewById(R.id.test_btn1);
        testBtn.setOnClickListener(this);

        nativeBtn = (Button) findViewById(R.id.native_btn1);
        nativeBtn.setOnClickListener(this);

        edit1.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(edit1.getWindowToken(), 0);    //hide keyboard
                    return true;
                }
                return false;
            }
        });
        edit2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(edit2.getWindowToken(), 0);    //hide keyboard
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle bundle){
        super.onSaveInstanceState(bundle);
    }

    @Override
    public void onClick(View view) {
        if(view == bannerBtn){

            rlRoot.removeAllViews();
            rlRoot.addView(adwhaleAdView); // AdWhaleAdView 를 루트 뷰에 붙인다.
            adwhaleAdView.loadAd(context, bannerType, "배너 광고 AD_UNIT_ID 입력" , new AdWhaleBannerAdListener() {
                @Override
                public void onAdLoaded() {
                    Log.d(this.getClass().getSimpleName(),".onAdLoaded() Success.");
                }

                @Override
                public void onAdFailedToLoad(int errorCode, String errorMessage) {
//                    Log.d(this.getClass().getSimpleName(), this.getClass().getSimpleName() + ".onAdFailedToLoad(" + errorCode + ", " + errorMessage + ").");
                    Log.e(this.getClass().getName(),".onAdFailedToLoad(" + errorCode + ", " + errorMessage + ").");
                }

                @Override
                public void onAdImpression() {
                    Log.i(this.getClass().getName(),".onAdImpression().");

                }

                @Override
                public void onAdClicked() {
                    Log.i(this.getClass().getSimpleName(), ".onAdClicked().");

                }

                @Override
                public void onAdOpened() {
                    Log.i(this.getClass().getSimpleName(), ".onAdOpened().");

                }

                @Override
                public void onAdClosed() {
                    Log.i(this.getClass().getSimpleName(), ".onAdClosed().");

                }
            });

        }else if(view == interstitialBtn) {

            AdWhaleInterstitialAd adWhaleInterstitialAd = new AdWhaleInterstitialAd();
            adWhaleInterstitialAd.loadAd(this, "발급 받은 전면 광고 AD_UNIT_ID 입력", new AdWhaleInterstitialAdListener() {
                @Override
                public void onAdLoaded(AdWhaleInterstitialAd adWhaleInterstitialAd) {
                    if(adWhaleInterstitialAd != null) {
                        adWhaleInterstitialAd.show(MainActivity.this);
                    }
                    Log.d(this.getClass().getSimpleName(), ". AdWhaleInterstitialAd onAdLoaded() Success.");
                }

                @Override
                public void onAdFailedToLoad(int errorCode, String errorMessage) {

                    Log.e(this.getClass().getSimpleName(), ". AdWhaleInterstitialAd onAdFailedToLoad(" + errorCode + ", " + errorMessage + ").");
                }

                @Override
                public void onAdClicked() {
                    Log.i(this.getClass().getSimpleName(), ". AdWhaleInterstitialAd onAdClicked().");

                }

                @Override
                public void onAdDismissedFullScreenContent() {
                    Log.i(this.getClass().getSimpleName(), ". AdWhaleInterstitialAd onAdDismissedFullScreenContent().");

                }

                @Override
                public void onAdFailedToShowFullScreenContent(int errorCode, String errorMessage) {
                    Log.e(this.getClass().getSimpleName(), ". AdWhaleInterstitialAd onAdFailedToShowFullScreenContent(" + errorCode + ", " + errorMessage + ").");

                }

                @Override
                public void onAdImpression() {
                    Log.i(this.getClass().getSimpleName(), ". AdWhaleInterstitialAd onAdImpression().");

                }

                @Override
                public void onAdShowedFullScreenContent() {
                    Log.i(this.getClass().getSimpleName(), ". AdWhaleInterstitialAd onAdShowedFullScreenContent().");

                }
            });
        }
        else if(view == rewardedBtn){

            AdWhaleRewardedAd adWhaleRewardedAd = new AdWhaleRewardedAd();
            adWhaleRewardedAd.loadAd(this, "발급 받은 리워드 광고 AD_UNIT_ID 입력", new AdWhaleRewardedAdListener() {
                @Override
                public void onAdLoaded(AdWhaleRewardedAd adWhaleRewardedAd) {
                    if(adWhaleRewardedAd != null) {
                        Log.i(this.getClass().getSimpleName(), ". AdWhaleRewardedAd AdLoaded() Success");
                        adWhaleRewardedAd.show(MainActivity.this);
                    }
                }

                @Override
                public void onAdFailedToLoad(int errorCode, String errorMessage) {
                    Log.e(this.getClass().getSimpleName(), ". AdWhaleRewardedAd onAdFailedToLoad(" + errorCode + ", " + errorMessage + ").");
                }

                @Override
                public void onAdDismissedFullScreenContent() {
                    Log.i(this.getClass().getSimpleName(),  ". AdWhaleRewardedAd onAdDismissedFullScreenContent().");

                }

                @Override
                public void onAdFailedToShowFullScreenContent(int errorCode, String errorMessage) {
                    Log.e(this.getClass().getSimpleName(), ". AdWhaleRewardedAd onAdFailedToShowFullScreenContent(" + errorCode + ", " + errorMessage + ").");

                }

                @Override
                public void onAdShowedFullScreenContent() {
                    Log.i(this.getClass().getSimpleName(), ". AdWhaleRewardedAd onAdShowedFullScreenContent().");

                }

                @Override
                public void onUserEarnedReward(int rewardAmount, String rewardType) {
                    Log.i(this.getClass().getSimpleName(), ". AdWhaleRewardedAd onUserEarnedReward() rewardAmount : " + rewardAmount);
                    Log.i(this.getClass().getSimpleName(), ". AdWhaleRewardedAd onUserEarnedReward() rewardType : " + rewardType);
                }
            });
        }

        else if(view == nativeBtn){

            Intent intent = new Intent(MainActivity.this, NativeActivity.class);
            intent.putExtra("width",edit1.getText().toString());
            intent.putExtra("height",edit2.getText().toString());
            startActivity(intent);

        } else if(view == testBtn){
            adwhaleInit.TestAdInspector(context);
        }

        else if(view == gdprcheckBtn){
            Log.d(this.getClass().getSimpleName(), this.getClass().getSimpleName() + " 설명 isGDPR() : 동의 혹은 비동의를 누르기만해도 true 또는 1");
            Log.d(this.getClass().getSimpleName(), this.getClass().getSimpleName() + ". isGDPR() : " + adwhaleInit.isGDPR());
            Log.d(this.getClass().getSimpleName(), this.getClass().getSimpleName() + " 설명 canShowAds() : 동의 = true, 비동의 = false ");
            Log.d(this.getClass().getSimpleName(), this.getClass().getSimpleName() + ". canShowAds() : " + adwhaleInit.canShowAds());
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (adwhaleAdView != null) {
            adwhaleAdView = null;
        }
    }

}