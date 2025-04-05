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
import com.google.firebase.auth.FirebaseAuth
import androidx.appcompat.widget.AppCompatButton
import com.example.mad_collaborative.utils.User
import android.content.Context


class MainActivity : AppCompatActivity() {

    private lateinit var firestoreHelper: FirestoreHelper
    private lateinit var auth: FirebaseAuth

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


        val edtEmail = findViewById<EditText>(R.id.edtEmail)
        val edtPassword = findViewById<EditText>(R.id.edtPassword)
        val privacyAndTermsTextView = findViewById<TextView>(R.id.PrivacyandTerms)

        val regionTextView = findViewById<TextView>(R.id.Region)
        val changeRegionTextView = findViewById<TextView>(R.id.Changeregion)


        setupPrivacyAndTermsLinks(privacyAndTermsTextView)


        changeRegionTextView.setOnClickListener {
            showRegionSelectionDialog(regionTextView)
        }

        //Handle sign-up button click (register user and navigate to signin_page_2)
        findViewById<Button>(R.id.btnSignup).setOnClickListener {
            val email = edtEmail.text.toString().trim()
            val password = edtPassword.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                firestoreHelper.registerUser(email, password) { success ->
                    if (success) {

                        showSigninPage2()
                    } else {
                        Toast.makeText(this, "Registration failed! Try again.", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }

        //Login link click listener
        findViewById<TextView>(R.id.tvLoginLink).setOnClickListener {
            showLoginPage()
        }
    }


    private fun showSigninPage2() {
        setContentView(R.layout.signin_page_2)

        val edtFirstName = findViewById<EditText>(R.id.edtFirstName)
        val edtLastName = findViewById<EditText>(R.id.edtLastName)
        val edtAge = findViewById<EditText>(R.id.edtAge)
        val genderRadioGroup = findViewById<RadioGroup>(R.id.genderRadioGroup)
        val regionTextView = findViewById<TextView>(R.id.Region)
        val changeRegionTextView = findViewById<TextView>(R.id.Changeregion)

        // Handling region change dialog
        changeRegionTextView.setOnClickListener {
            showRegionSelectionDialog(regionTextView)
        }

        // Handle "Next" button click
        findViewById<Button>(R.id.btnNext).setOnClickListener {
            val firstName = edtFirstName.text.toString().trim()
            val lastName = edtLastName.text.toString().trim()
            val age = edtAge.text.toString().trim()
            val genderRadioButtonId = genderRadioGroup.checkedRadioButtonId
            val gender = if (genderRadioButtonId != -1) {
                findViewById<RadioButton>(genderRadioButtonId)?.text.toString()
            } else {
                "" // No gender selected, return empty or handle it
            }
            val region = regionTextView.text.toString().trim()

            // Check if all fields are filled
            if (firstName.isNotEmpty() && lastName.isNotEmpty() && age.isNotEmpty() && gender.isNotEmpty() && region.isNotEmpty()) {
                // Store user data in a temporary model or variable
                val user = User(
                    firstName = firstName,
                    lastName = lastName,
                    age = age,
                    gender = gender,
                    region = region
                )

                // Save user data to a global or shared preference for later use
                val sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putString("firstName", firstName)
                editor.putString("lastName", lastName)
                editor.putString("age", age)
                editor.putString("gender", gender)
                editor.putString("region", region)
                editor.apply()

                // Navigate to next page
                showSignPage3()
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showSignPage3() {
        setContentView(R.layout.sign_page_3)

        val edtWeight: EditText = findViewById(R.id.edtWeight)
        val edtAge: EditText = findViewById(R.id.edtAge)
        val edtHeight: EditText = findViewById(R.id.edtHeight)
        val radioKg: RadioButton = findViewById(R.id.radioKg)
        val radioLbs: RadioButton = findViewById(R.id.radioLbs)
        val radioCm: RadioButton = findViewById(R.id.radioCm)
        val radioFt: RadioButton = findViewById(R.id.radioFt)
        val radioInches: RadioButton = findViewById(R.id.radioInches)
        val workoutRadioGroup: RadioGroup = findViewById(R.id.Workout_ratio_RadioGroup)
        val btnSignup: AppCompatButton = findViewById(R.id.btnSignup)

        val sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val firstName = sharedPreferences.getString("firstName", "")
        val lastName = sharedPreferences.getString("lastName", "")
        val age = sharedPreferences.getString("age", "")
        val gender = sharedPreferences.getString("gender", "")
        val region = sharedPreferences.getString("region", "")

        val userId = FirebaseAuth.getInstance().currentUser?.uid

        btnSignup.setOnClickListener {
            val weight = edtWeight.text.toString().trim()
            val height = edtHeight.text.toString().trim()

            if (weight.isEmpty() || height.isEmpty()) {
                Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Get weight and height units
            val weightUnit = if (radioKg.isChecked) "Kg" else if (radioLbs.isChecked) "Lbs" else "Not Selected"
            val heightUnit = when {
                radioCm.isChecked -> "Cm"
                radioFt.isChecked -> "Ft"
                radioInches.isChecked -> "Inches"
                else -> "Not Selected"
            }

            // Get workout frequency
            val workoutFrequency = when (workoutRadioGroup.checkedRadioButtonId) {
                R.id.radioRegular -> "Regular Workouts (3-5 times a week)"
                R.id.radioOccasional -> "Occasional Workouts (1-2 times a week)"
                R.id.radioActive -> "Active Lifestyle (Every day or most days)"
                else -> "Not Selected"
            }

            // Create a map of user data to store
            val userData: Map<String, Any> = mapOf(
                "firstName" to (firstName ?: ""),
                "lastName" to (lastName ?: ""),
                "age" to (age ?: ""),
                "gender" to (gender ?: ""),
                "region" to (region ?: ""),
                "weight" to weight,
                "weightUnit" to weightUnit,
                "height" to height,
                "heightUnit" to heightUnit,
                "workoutFrequency" to workoutFrequency
            )

            if (userId != null) {
                val firestoreHelper = FirestoreHelper(this)

                // Save the user's data to Firestore
                firestoreHelper.saveUserData(userId, userData) { success ->
                    if (success) {
                        Toast.makeText(this, "Registration Complete!", Toast.LENGTH_SHORT).show()
                        showWelcomePage() // Redirect to welcome page
                    } else {
                        Toast.makeText(this, "Failed to save data! Try again.", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "User not found!", Toast.LENGTH_SHORT).show()
            }
        }
    }





    private fun setupPrivacyAndTermsLinks(privacyAndTermsTextView: TextView) {
        val fullText = "By continuing, I agree to SmartFit's Privacy Policy and Terms of Use"
        val privacyPolicy = "Privacy Policy"
        val termsOfUse = "Terms of Use"

        val spannableString = SpannableString(fullText)

        // Create clickable span for Privacy Policy
        val privacyClick = object : ClickableSpan() {
            override fun onClick(widget: View) {
                showPrivacyPolicyPage()
            }
        }
        // Create clickable span for Terms of Use
        val termsClick = object : ClickableSpan() {
            override fun onClick(widget: View) {
                showTermsOfUsePage()
            }
        }

        // Apply clickable spans to the text
        val privacyStart = fullText.indexOf(privacyPolicy)
        val privacyEnd = privacyStart + privacyPolicy.length
        spannableString.setSpan(privacyClick, privacyStart, privacyEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        val termsStart = fullText.indexOf(termsOfUse)
        val termsEnd = termsStart + termsOfUse.length
        spannableString.setSpan(termsClick, termsStart, termsEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        // Set the spannable text to the TextView
        privacyAndTermsTextView.text = spannableString
        privacyAndTermsTextView.movementMethod = LinkMovementMethod.getInstance()
        privacyAndTermsTextView.highlightColor = Color.TRANSPARENT
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
                        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid
                        if (currentUserId == "O0sBdBVDJYM49d4EMVJTUNjJDnR2") {
                            val intent = Intent(this, AdminDashboardActivity::class.java)
                            startActivity(intent)
                        } else {
                            val intent = Intent(this, MainPageActivity::class.java)
                            startActivity(intent)
                        }
                        finish()
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
