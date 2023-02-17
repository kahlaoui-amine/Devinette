package com.example.Devinette

import android.content.Intent

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class SplashScreenActivity : AppCompatActivity() {

    private lateinit var tv_TitleSplach: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splashscreen_activity)


        initSplashScreen()
        initComponents()
        launchApp()
    }
    private fun initComponents() {

        tv_TitleSplach = findViewById(R.id.tv_TitleSplach)
    }
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)


    }

    private fun initSplashScreen() {
        val ivLogo = findViewById<ImageView>(R.id.iv_splash_logo)


    }

    /**
     * Starts the animation of the logo featured on the splash screen.
     */


    /**
     * Launches the activity immediately following the splash screen after a set duration.
     */
    private fun launchApp() {
        Handler(Looper.getMainLooper()).postDelayed({
            val i = Intent(this@SplashScreenActivity, MainActivity::class.java)
            startActivity(i)
            finish()
        }, AnimationUtil.SPLASH_SCREEN_TIMEOUT.toLong())
    }
}