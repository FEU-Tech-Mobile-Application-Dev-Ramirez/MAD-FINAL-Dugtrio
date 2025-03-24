package com.example.mad_collaborative

import android.net.Uri
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showWelcomePage()
    }

    private fun showWelcomePage() {
        setContentView(R.layout.welcome_page)

        val videoView = findViewById<VideoView>(R.id.videoView)
        val videoPath = "android.resource://" + packageName + "/raw/welcome_video"
        videoView.setVideoURI(Uri.parse(videoPath))
        videoView.start()
        videoView.setOnPreparedListener { it.isLooping = true }

        val welcomeLayout = findViewById<LinearLayout>(R.id.welcomeLayout)
        applySlideAnimation(welcomeLayout)

        findViewById<Button>(R.id.white_button_joinus).setOnClickListener {
            showSignupPage()
        }
        findViewById<Button>(R.id.white_transparent_button_signin).setOnClickListener {
            showLoginPage()
        }
    }

    private fun showSignupPage() {
        setContentView(R.layout.signin_page)

        val signupLayout = findViewById<LinearLayout>(R.id.signupLayout)
        applySlideAnimation(signupLayout)

        findViewById<Button>(R.id.btnSignup).setOnClickListener {
            showWelcomePage()
        }
        findViewById<TextView>(R.id.tvLoginLink).setOnClickListener {
            showLoginPage()
        }
    }

    private fun showLoginPage() {
        setContentView(R.layout.login_page)

        val loginLayout = findViewById<LinearLayout>(R.id.loginLayout)
        applySlideAnimation(loginLayout)

        findViewById<Button>(R.id.btnLogin).setOnClickListener {
            showWelcomePage()
        }
        findViewById<TextView>(R.id.tvSignUpLink).setOnClickListener {
            showSignupPage()
        }
    }

    private fun applySlideAnimation(view: LinearLayout) {
        val slideAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_in_right)
        view.startAnimation(slideAnimation)
    }
}
