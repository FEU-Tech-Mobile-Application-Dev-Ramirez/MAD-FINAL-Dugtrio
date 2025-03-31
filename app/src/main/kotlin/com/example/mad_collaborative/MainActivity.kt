package com.example.mad_collaborative

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.mad_collaborative.utils.FirestoreHelper

class MainActivity : AppCompatActivity() {

    private lateinit var firestoreHelper: FirestoreHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_page)
        firestoreHelper = FirestoreHelper(this)

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
    }

    private fun showSigninPage() {
        setContentView(R.layout.signin_page)

        val signupLayout = findViewById<LinearLayout>(R.id.signupLayout)
        applySlideAnimation(signupLayout)

        val edtFirstName = findViewById<EditText>(R.id.edtFirstName)
        val edtLastName = findViewById<EditText>(R.id.edtLastName)
        val edtEmail = findViewById<EditText>(R.id.edtEmail)
        val edtPassword = findViewById<EditText>(R.id.edtPassword)
        val privacyAndTermsTextView = findViewById<TextView>(R.id.PrivacyandTerms)

        val regionTextView = findViewById<TextView>(R.id.Region)
        val changeRegionTextView = findViewById<TextView>(R.id.Changeregion)

        val fullText = "By continuing, I agree to SmartFit's Privacy Policy and Terms of Use"
        val privacyPolicy = "Privacy Policy"
        val termsOfUse = "Terms of Use"

        val spannableString = SpannableString(fullText)

        val privacyClick = object : ClickableSpan() {
            override fun onClick(widget: View) {
                showPrivacyPolicyPage()
            }
        }

        val termsClick = object : ClickableSpan() {
            override fun onClick(widget: View) {
                showTermsOfUsePage()
            }
        }

        val privacyStart = fullText.indexOf(privacyPolicy)
        val privacyEnd = privacyStart + privacyPolicy.length
        spannableString.setSpan(privacyClick, privacyStart, privacyEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        val termsStart = fullText.indexOf(termsOfUse)
        val termsEnd = termsStart + termsOfUse.length
        spannableString.setSpan(termsClick, termsStart, termsEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        privacyAndTermsTextView.text = spannableString
        privacyAndTermsTextView.movementMethod = LinkMovementMethod.getInstance()
        privacyAndTermsTextView.highlightColor = Color.TRANSPARENT

        changeRegionTextView.setOnClickListener {
            showRegionSelectionDialog(regionTextView)
        }

        findViewById<Button>(R.id.btnSignup).setOnClickListener {
            val firstName = edtFirstName.text.toString().trim()
            val lastName = edtLastName.text.toString().trim()
            val email = edtEmail.text.toString().trim()
            val password = edtPassword.text.toString().trim()

            if (firstName.isNotEmpty() && lastName.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                firestoreHelper.registerUser(firstName, lastName, email, password) { success ->
                    if (success) {
                        showWelcomePage()
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

    private fun showRegionSelectionDialog(regionTextView: TextView) {
        val regions = arrayOf("Philippines", "US", "Canada", "UK", "Australia", "Japan", "Germany")

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Select Your Region")
        builder.setItems(regions) { _, which ->
            regionTextView.text = regions[which]
        }
        builder.show()
    }

    private fun showPrivacyPolicyPage() {
        setContentView(R.layout.privacy_policy_page)

        val btnAgree = findViewById<Button>(R.id.btnAgree)
        btnAgree.setOnClickListener {
            showSigninPage()
        }
    }

    private fun showTermsOfUsePage() {
        setContentView(R.layout.terms_use_page)

        val btnAgree = findViewById<Button>(R.id.btnAgree)
        btnAgree.setOnClickListener {
            showSigninPage()
        }
    }

    private fun showLoginPage() {
        setContentView(R.layout.login_page)

        val loginLayout = findViewById<LinearLayout>(R.id.loginLayout)
        applySlideAnimation(loginLayout)

        val edtEmail = findViewById<EditText>(R.id.edtEmailLogin)
        val edtPassword = findViewById<EditText>(R.id.edtPasswordLogin)
        val privacyAndTermsTextView = findViewById<TextView>(R.id.PrivacyandTermsLogin)

        val regionTextView = findViewById<TextView>(R.id.Region)
        val changeRegionTextView = findViewById<TextView>(R.id.Changeregion)

        changeRegionTextView.setOnClickListener {
            showRegionSelectionDialog(regionTextView)
        }

        val fullText = "By continuing, I agree to SmartFit's Privacy Policy and Terms of Use"
        val privacyPolicy = "Privacy Policy"
        val termsOfUse = "Terms of Use"
        val spannableString = SpannableString(fullText)

        val privacyClick = object : ClickableSpan() {
            override fun onClick(widget: View) {
                showPrivacyPolicyPage()
            }
        }

        val termsClick = object : ClickableSpan() {
            override fun onClick(widget: View) {
                showTermsOfUsePage()
            }
        }

        spannableString.setSpan(privacyClick, fullText.indexOf(privacyPolicy),
            fullText.indexOf(privacyPolicy) + privacyPolicy.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        spannableString.setSpan(termsClick, fullText.indexOf(termsOfUse),
            fullText.indexOf(termsOfUse) + termsOfUse.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        privacyAndTermsTextView.text = spannableString
        privacyAndTermsTextView.movementMethod = LinkMovementMethod.getInstance()
        privacyAndTermsTextView.highlightColor = Color.TRANSPARENT

        findViewById<Button>(R.id.btnLogin).setOnClickListener {
            val email = edtEmail.text.toString().trim()
            val password = edtPassword.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                firestoreHelper.loginUser(email, password) { success ->
                    if (success) {
                        showMainPage()
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

    private fun showMainPage() {
        val intent = Intent(this, MainPageActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun applySlideAnimation(view: LinearLayout) {
        val slideAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_in_right)
        view.startAnimation(slideAnimation)
    }
}

class MainPage : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.main_page, container, false)

        // Set default fragment only if there's no saved instance
        if (savedInstanceState == null) {
            replaceFragment(HomeFragment())
        }

        // Setup Bottom Navigation Clicks
        setupBottomNavigation(view)

        return view
    }

    private fun setupBottomNavigation(view: View) {
        val bottomNavigationView = view.findViewById<com.google.android.material.bottomnavigation.BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> replaceFragment(HomeFragment())
                R.id.workouts -> replaceFragment(WorkoutsFragment())
                R.id.activity -> replaceFragment(ActivityFragment())
                R.id.programs -> replaceFragment(ProgramsFragment())
                else -> return@setOnItemSelectedListener false
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.frame_layout_1, fragment)
            .commit()
    }
}