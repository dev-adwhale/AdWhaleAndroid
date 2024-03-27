package kr.co.adwhalekotlinsampleapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import kr.co.adwhale.sdk.core.cauly.AdWhaleNativeAdListener
import kr.co.adwhale.sdk.core.cauly.AdWhaleNativeAdView

class NativeActivity : AppCompatActivity() {
    var context: Context? = null
    var type: String? = null
    var width: String? = null
    var height: String? = null

    /**
     * NATIVE_MEDIA_ASPECT_RATIO_LANDSCAPE
     * NATIVE_MEDIA_ASPECT_RATIO_PORTRAIT
     * NATIVE_MEDIA_ASPECT_RATIO_SQUARE
     * NATIVE_MEDIA_ASPECT_RATIO_ANY
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_native)
        context = this
        val adWhaleNativeAdView = AdWhaleNativeAdView(context)
        val intent = intent
        width = intent.getStringExtra("width")
        height = intent.getStringExtra("height")
        Log.d(
            "[" + this.javaClass.simpleName + "]",
            this.javaClass.simpleName + " Native Ad Size : " + width + "x" + height
        )
        adWhaleNativeAdView.setNativeAdSize(width, height)
        val relativeLayout = findViewById<RelativeLayout>(R.id.mainnativeroot)
        relativeLayout.removeAllViews()
        relativeLayout.addView(adWhaleNativeAdView)

        // 테스트 Native Ad Unit ID. 마켓 배포 시 발급 받은 구글 애드몹 Native 용 Ad Unit ID으로 교체
        adWhaleNativeAdView.loadAd(
            context,
            "발급 받은 네이티브 광고 AD_UNIT_ID 입력",
            object : AdWhaleNativeAdListener {
                override fun onNativeAdLoaded() {
                    Log.d(
                        "[" + this.javaClass.simpleName + "]",
                        this.javaClass.simpleName + ". AdWhaleNativeAdView Native AdLoaded Success"
                    )
                }

                override fun onAdFailedToLoad(errorCode: Int, errorMessage: String) {
                    Log.d(
                        "[" + this.javaClass.simpleName + "]",
                        this.javaClass.simpleName + ". AdWhaleNativeAdView Native AdLoaded Fail(" + errorCode + ", " + errorMessage + ")."
                    )
                    Log.e(
                        "[" + this.javaClass.simpleName + "]",
                        this.javaClass.simpleName + ". AdWhaleNativeAdView Native AdLoaded Fail(" + errorCode + ", " + errorMessage + ")."
                    )
                }
            })
    }
}