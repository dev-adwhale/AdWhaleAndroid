package kr.co.adwhalekotlinsampleapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kr.co.adwhale.sdk.core.cauly.*
import kr.co.adwhale.sdk.core.cauly.AdWhaleInit.OnGDPRInitializedListener
import kr.co.adwhale.sdk.core.cauly.AdWhaleInit.OnInitializatizedListener


class MainActivity : AppCompatActivity(), View.OnClickListener {
    var context: Context? = null
    var bannerBtn: Button? = null
    var interstitialBtn: Button? = null
    var rewardedBtn: Button? = null
    var testBtn: Button? = null
    var nativeBtn: Button? = null
    var bannerloopTestBtn: Button? = null
    var gdprresetBtn: Button? = null
    var gdprcheckBtn: Button? = null
    var adwhaleAdView: AdWhaleAdView? = null
    var rlRoot: RelativeLayout? = null
    var spinner: Spinner? = null
    var bannerType: String? = null
    var spinnerItems = arrayOf("BANNER_320x50", "BANNER_320x100", "BANNER_300x250")
    var edit1: EditText? = null
    var edit2: EditText? = null
    var adwhaleInit: AdWhaleInit? = null
    companion object {
        var _DEBUG = true
    }
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        context = this
        adwhaleInit = AdWhaleInit() // AdWhale SDK 초기화
        //        _DEBUG = (0 != (getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE));
        _DEBUG = true
        try {
            Log.e(this.javaClass.simpleName, "_DEBUG Value : " + _DEBUG)
            adwhaleInit!!.init(
                context,
                _DEBUG,
                "테스트 기기 문자열 입력",
                object : OnInitializatizedListener {
                    override fun onCompleted() {
                        Log.i(
                            this.javaClass.name,
                            "GDPR 테스트 SampleApp Main isGDPR value : " + adwhaleInit!!.isGDPR
                        )
                        //                    adwhaleInit.adWhaleCoppa(AdWhaleInit.CoppaValue.TAG_FOR_CHILD_DIRECTED_TREATMENT_UNSPECIFIED);
                        Log.i(
                            this.javaClass.name,
                            "GDPR 테스트 SampleApp Main canShowAds value : " + adwhaleInit!!.canShowAds()
                        )
                        if (!adwhaleInit!!.canShowAds()) {
                            adwhaleInit!!.gdpr(context, object : OnGDPRInitializedListener {
                                override fun onCompleted() {
                                    Log.i(
                                        this.javaClass.name,
                                        "SampleApp Main isGDPR value : " + adwhaleInit!!.isGDPR
                                    )
                                    Log.i(
                                        this.javaClass.name,
                                        "SampleApp Main canShowAds value : " + adwhaleInit!!.canShowAds()
                                    )
                                }

                                override fun onFailed(error: String) {
                                    Log.e(
                                        this.javaClass.name,
                                        "SampleApp Main GDPR Error Message : $error"
                                    )
                                }
                            })
                        }
                    }
                })
        } catch (e: Exception) {
            Log.e(this.javaClass.name, "SampleApp Main onCreate Init, GDPR Error : " + e.message)
        }
        rlRoot = findViewById<View>(R.id.root) as RelativeLayout
        edit1 = findViewById<View>(R.id.edit1) as EditText
        edit2 = findViewById<View>(R.id.edit2) as EditText
        spinner = findViewById(R.id.spinner)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, spinnerItems)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner!!.setAdapter(adapter)
        bannerType = spinner!!.getSelectedItem().toString()
        spinner!!.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View, i: Int, l: Long) {
                bannerType = spinner!!.getSelectedItem().toString()
                adwhaleAdView = null
                adwhaleAdView = AdWhaleAdView(context)
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        })
        adwhaleAdView = AdWhaleAdView(context)
        gdprresetBtn = findViewById<View>(R.id.gdpr_reset) as Button
        gdprresetBtn!!.setOnClickListener(this)
        gdprcheckBtn = findViewById<View>(R.id.gdpr_check) as Button
        gdprcheckBtn!!.setOnClickListener(this)
        bannerBtn = findViewById<View>(R.id.banner_btn1) as Button
        bannerBtn!!.setOnClickListener(this)
        interstitialBtn = findViewById<View>(R.id.interstitial_btn1) as Button
        interstitialBtn!!.setOnClickListener(this)
        rewardedBtn = findViewById<View>(R.id.rewarded_btn1) as Button
        rewardedBtn!!.setOnClickListener(this)
        testBtn = findViewById<View>(R.id.test_btn1) as Button
        testBtn!!.setOnClickListener(this)
        nativeBtn = findViewById<View>(R.id.native_btn1) as Button
        nativeBtn!!.setOnClickListener(this)
        edit1!!.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(edit1!!.windowToken, 0) //hide keyboard
                return@OnKeyListener true
            }
            false
        })
        edit2!!.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(edit2!!.windowToken, 0) //hide keyboard
                return@OnKeyListener true
            }
            false
        })
    }

    override fun onSaveInstanceState(bundle: Bundle) {
        super.onSaveInstanceState(bundle)
    }

    override fun onClick(view: View) {
        if (view === bannerBtn) {
            rlRoot!!.removeAllViews()
            rlRoot!!.addView(adwhaleAdView) // AdWhaleAdView 를 루트 뷰에 붙인다.
            // 테스트 Banner Ad Unit ID. 마켓 배포 시 발급 받은 구글 애드몹 Banner 용 Ad Unit ID으로 교체
            adwhaleAdView!!.loadAd(
                context,
                bannerType,
                "배너 광고 AD_UNIT_ID 입력",
                object : AdWhaleBannerAdListener {
                    override fun onAdLoaded() {
                        Log.d(this.javaClass.simpleName, ".onAdLoaded() Success.")
                    }

                    override fun onAdFailedToLoad(errorCode: Int, errorMessage: String) {
                        Log.e(
                            this.javaClass.name,
                            ".onAdFailedToLoad($errorCode, $errorMessage)."
                        )
                    }
                    override fun onAdImpression() {
                        Log.i(this.javaClass.name, ".onAdImpression().")
                    }

                    override fun onAdClicked() {
                        Log.i(this.javaClass.simpleName, ".onAdClicked().")
                    }

                    override fun onAdOpened() {
                        Log.i(this.javaClass.simpleName, ".onAdOpened().")
                    }

                    override fun onAdClosed() {
                        Log.i(this.javaClass.simpleName, ".onAdClosed().")
                    }
                }) // AdWhaleAdView 배너를 로드.
        } else if (view === interstitialBtn) {
            val adWhaleInterstitialAd = AdWhaleInterstitialAd()
            // 테스트 Interstitial Ad Unit ID. 마켓 배포 시 발급 받은 구글 애드몹 Interstitial 용 Ad Unit ID으로 교체
            adWhaleInterstitialAd.loadAd(
                this,
                "발급 받은 전면 광고 AD_UNIT_ID 입력",
                object : AdWhaleInterstitialAdListener {
                    override fun onAdLoaded(adWhaleInterstitialAd: AdWhaleInterstitialAd) {
                        if (adWhaleInterstitialAd != null) {
                            adWhaleInterstitialAd.show(this@MainActivity)
                        }
                        Log.d(
                            this.javaClass.simpleName,
                            ". AdWhaleInterstitialAd onAdLoaded() Success."
                        )
                    }

                    override fun onAdFailedToLoad(errorCode: Int, errorMessage: String) {
                        Log.e(
                            this.javaClass.simpleName,
                            ". AdWhaleInterstitialAd onAdFailedToLoad($errorCode, $errorMessage)."
                        )
                    }

                    override fun onAdClicked() {
                        Log.i(this.javaClass.simpleName, ". AdWhaleInterstitialAd onAdClicked().")
                    }

                    override fun onAdDismissedFullScreenContent() {
                        Log.i(
                            this.javaClass.simpleName,
                            ". AdWhaleInterstitialAd onAdDismissedFullScreenContent()."
                        )
                    }

                    override fun onAdFailedToShowFullScreenContent(
                        errorCode: Int,
                        errorMessage: String
                    ) {
                        Log.e(
                            this.javaClass.simpleName,
                            ". AdWhaleInterstitialAd onAdFailedToShowFullScreenContent($errorCode, $errorMessage)."
                        )
                    }

                    override fun onAdImpression() {
                        Log.i(
                            this.javaClass.simpleName,
                            ". AdWhaleInterstitialAd onAdImpression()."
                        )
                    }

                    override fun onAdShowedFullScreenContent() {
                        Log.i(
                            this.javaClass.simpleName,
                            ". AdWhaleInterstitialAd onAdShowedFullScreenContent()."
                        )
                    }
                })
        } else if (view === rewardedBtn) {

            val adWhaleRewardedAd = AdWhaleRewardedAd()
            // 테스트 Reward Ad Unit ID. 마켓 배포 시 발급 받은 구글 애드몹 Reward 용 Ad Unit ID으로 교체
            adWhaleRewardedAd.loadAd(
                this,
                "발급 받은 리워드 광고 AD_UNIT_ID 입력",
                object : AdWhaleRewardedAdListener {
                    override fun onAdLoaded(adWhaleRewardedAd: AdWhaleRewardedAd) {
                        if (adWhaleRewardedAd != null) {
                            Log.i(
                                this.javaClass.simpleName,
                                ". AdWhaleRewardedAd AdLoaded() Success"
                            )
                            //                        Log.e("테스트","리워드 onLoaded L.160");
                            adWhaleRewardedAd.show(this@MainActivity)
                        }
                    }

                    override fun onAdFailedToLoad(errorCode: Int, errorMessage: String) {
                        Log.e(
                            this.javaClass.simpleName,
                            ". AdWhaleRewardedAd onAdFailedToLoad($errorCode, $errorMessage)."
                        )
                    }

                    override fun onAdDismissedFullScreenContent() {
                        Log.i(
                            this.javaClass.simpleName,
                            ". AdWhaleRewardedAd onAdDismissedFullScreenContent()."
                        )
                    }

                    override fun onAdFailedToShowFullScreenContent(
                        errorCode: Int,
                        errorMessage: String
                    ) {
                        Log.e(
                            this.javaClass.simpleName,
                            ". AdWhaleRewardedAd onAdFailedToShowFullScreenContent($errorCode, $errorMessage)."
                        )
                    }

                    override fun onAdShowedFullScreenContent() {
                        Log.i(
                            this.javaClass.simpleName,
                            ". AdWhaleRewardedAd onAdShowedFullScreenContent()."
                        )
                    }

                    override fun onUserEarnedReward(rewardAmount: Int, rewardType: String) {
                        Log.i(
                            this.javaClass.simpleName,
                            ". AdWhaleRewardedAd onUserEarnedReward() rewardAmount : $rewardAmount"
                        )
                        Log.i(
                            this.javaClass.simpleName,
                            ". AdWhaleRewardedAd onUserEarnedReward() rewardType : $rewardType"
                        )
                    }
                })
        } else if (view === nativeBtn) {

            val intent = Intent(this@MainActivity, NativeActivity::class.java)
            intent.putExtra("width", edit1!!.text.toString())
            intent.putExtra("height", edit2!!.text.toString())
            startActivity(intent)
        } else if (view === testBtn) {
            AdWhaleInit.TestAdInspector(context)
        } else if (view === gdprcheckBtn) {
            Log.d(
                this.javaClass.simpleName,
                this.javaClass.simpleName + " 설명 isGDPR() : 동의 혹은 비동의를 누르기만해도 true 또는 1"
            )
            Log.d(
                this.javaClass.simpleName,
                this.javaClass.simpleName + ". isGDPR() : " + adwhaleInit!!.isGDPR
            )
            Log.d(
                this.javaClass.simpleName,
                this.javaClass.simpleName + " 설명 canShowAds() : 동의 = true, 비동의 = false "
            )
            Log.d(
                this.javaClass.simpleName,
                this.javaClass.simpleName + ". canShowAds() : " + adwhaleInit!!.canShowAds()
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (adwhaleAdView != null) {
            adwhaleAdView = null
        }
    }


}