package com.example.mad_collaborative

import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.mad_collaborative.utils.FirestoreHelper

class MainActivity : AppCompatActivity() {

    private lateinit var firestoreHelper: FirestoreHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.welcome_page) // Ensure layout is set
        firestoreHelper = FirestoreHelper(this) // Initialize Firestore Helper

        // Delay to ensure UI is fully rendered before setting up views
        Handler(Looper.getMainLooper()).postDelayed({
            showWelcomePage()
        }, 100)
    }

    private fun showWelcomePage() {
        setContentView(R.layout.welcome_page)

        val videoView = findViewById<VideoView>(R.id.videoView)
        val videoPath = "android.resource://$packageName/raw/working_out_video"
        videoView.setVideoURI(Uri.parse(videoPath))
        videoView.start()
        videoView.setOnPreparedListener { it.isLooping = true }

        val welcomeLayout = findViewById<LinearLayout>(R.id.welcomeLayout)
        applySlideAnimation(welcomeLayout)

        findViewById<Button>(R.id.white_button_joinus).setOnClickListener {
            showSigninPage()
        }
        findViewById<Button>(R.id.white_transparent_button_signin).setOnClickListener {
            showLoginPage()
        }

        // New Button to show Privacy and Terms
        findViewById<TextView>(R.id.tvPrivacyTermsLink).setOnClickListener {
            showPrivacyTermsPage()
        }
    }

    private fun showSigninPage() {
        setContentView(R.layout.signin_page)

        val signupLayout = findViewById<LinearLayout>(R.id.signupLayout)
        applySlideAnimation(signupLayout)

        val edtFirstName = findViewById<EditText>(R.id.edtFirstName)
        val edtLastName = findViewById<EditText>(R.id.edtLastName)
        val edtEmail = findViewById<EditText>(R.id.edtEmail)
        val edtPassword = findViewById<EditText>(R.id.edtPassword)

        findViewById<Button>(R.id.btnSignup).setOnClickListener {
            val firstName = edtFirstName.text.toString().trim()
            val lastName = edtLastName.text.toString().trim()
            val email = edtEmail.text.toString().trim()
            val password = edtPassword.text.toString().trim()

            if (firstName.isNotEmpty() && lastName.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                firestoreHelper.registerUser(firstName, lastName, email, password) { success ->
                    if (success) {
                        showWelcomePage() // Navigate back to welcome page after successful signup
                    } else {
                        Toast.makeText(this, "Registration failed! Try again.", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }

        findViewById<TextView>(R.id.tvLoginLink).setOnClickListener {
            showLoginPage()
        }
    }

    private fun showLoginPage() {
        setContentView(R.layout.login_page)

        val loginLayout = findViewById<LinearLayout>(R.id.loginLayout)
        applySlideAnimation(loginLayout)

        val edtEmail = findViewById<EditText>(R.id.edtEmailLogin)
        val edtPassword = findViewById<EditText>(R.id.edtPasswordLogin)

        findViewById<Button>(R.id.btnLogin).setOnClickListener {
            val email = edtEmail.text.toString().trim()
            val password = edtPassword.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                firestoreHelper.loginUser(email, password) { success ->
                    if (success) {
                        showWelcomePage() // Navigate after successful login
                    } else {
                        Toast.makeText(this, "Login failed! Check your credentials.", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Please enter your email and password", Toast.LENGTH_SHORT).show()
            }
        }

        findViewById<TextView>(R.id.tvSignUpLink).setOnClickListener {
            showSigninPage()
        }
    }

    private fun showPrivacyTermsPage() {
        setContentView(R.layout.privacy_terms_page)

        val privacyLayout = findViewById<LinearLayout>(R.id.privacyLayout)
        applySlideAnimation(privacyLayout)

        findViewById<ImageView>(R.id.ivBack).setOnClickListener {
            showWelcomePage()
        }
    }

    private fun applySlideAnimation(view: LinearLayout) {
        val slideAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_in_right)
        view.startAnimation(slideAnimation)
    }
}
