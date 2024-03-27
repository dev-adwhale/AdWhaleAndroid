package kr.co.adwhalekotlinsampleapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kr.co.adwhale.sdk.core.cauly.AdWhaleInit
import kr.co.adwhale.sdk.core.cauly.AdWhaleOpenAd
import kr.co.adwhale.sdk.core.cauly.AdWhaleOpenAdListener


class SplashActivity : AppCompatActivity() {

    var context: Context? = null
    private var adWhaleOpenAd : AdWhaleOpenAd? = null
    var adwhaleInit: AdWhaleInit? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        context = this
        adWhaleOpenAd = AdWhaleOpenAd()

        adWhaleOpenAd?.init(context)
        adwhaleInit = AdWhaleInit()

        //테스트 Open Ad Unit ID. 마켓 배포 시 발급 받은 구글 애드몹 Open 용 Ad Unit ID으로 교체
        adwhaleInit!!.init(
            context,
            true,
            "앱 오프닝 AD_UNIT_ID 입력",
            object : AdWhaleInit.OnInitializatizedListener {
                override fun onCompleted() {
                }
            })

        val button = findViewById<View>(R.id.splash_btn1) as Button
        button.setOnClickListener {
//            val adWhaleOpenAd = application as? AdWhaleOpenAd

            adWhaleOpenAd?.showAdIfAvailable(
                "ca-app-pub-8713069554470817/3156662323",
                object : AdWhaleOpenAdListener {
                    override fun onAdLoaded(adWhaleOpenAd: AdWhaleOpenAd) {

                    }
                    override fun onAdFailedToLoad(errorCode: Int, errorMessage: String) {

                    }
                    override fun onAdFailedToShowFullScreenContent(
                        errorCode: Int,
                        errorMessage: String
                    ) {
                    }

                    override fun onAdShowedFullScreenContent() {}
                    override fun onAdDismissedFullScreenContent() {}
                    override fun onAdClicked() {}
                    override fun onAdImpression() {}
                })
        }
        val button2 = findViewById<View>(R.id.splash_btn2) as Button
        button2.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
        }
    }
}