package com.example.mad_collaborative.utils

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.mad_collaborative.AdminDashboardActivity
import com.example.mad_collaborative.MainPageActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreHelper(private val context: Context) {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance() // Firebase Authentication

    // 🔐 Register User with Email & Password
    fun registerUser(
        email: String,
        password: String,
        firstName: String = "",
        lastName: String = "",
        age: String = "",
        gender: String = "",
        region: String = "",
        onSuccess: (Boolean) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid ?: return@addOnCompleteListener
                    val user: MutableMap<String, Any> = mutableMapOf(
                        "email" to email,
                        "userId" to userId
                    )

                    // Add optional fields if provided
                    if (firstName.isNotEmpty()) user["firstName"] = firstName
                    if (lastName.isNotEmpty()) user["lastName"] = lastName
                    if (age.isNotEmpty()) user["age"] = age
                    if (gender.isNotEmpty()) user["gender"] = gender
                    if (region.isNotEmpty()) user["region"] = region

                    db.collection("users").document(userId)
                        .set(user)
                        .addOnSuccessListener {
                            Log.d("Firestore", "User added with ID: $userId")
                            Toast.makeText(context, "Account Created Successfully!", Toast.LENGTH_SHORT).show()
                            onSuccess(true)
                        }
                        .addOnFailureListener { e ->
                            Log.e("Firestore", "Error saving user data", e)
                            onSuccess(false)
                        }
                } else {
                    Log.e("Auth", "Registration failed", task.exception)
                    Toast.makeText(context, "Registration failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    onSuccess(false)
                }
            }
    }


    fun saveUserData(userId: String, userData: Map<String, Any>, onSuccess: (Boolean) -> Unit) {
        db.collection("users").document(userId)
            .set(userData)
            .addOnSuccessListener {
                Log.d("Firestore", "User data added with ID: $userId")
                onSuccess(true)  // Data saved successfully
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Error saving user data", e)
                onSuccess(false)  // Error saving data
            }
    }



    fun loginUser(email: String, password: String, onSuccess: (Boolean) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("Auth", "Login successful!")
                    Toast.makeText(context, "Login Successful!", Toast.LENGTH_SHORT).show()

                    // Get current user ID
                    val currentUserId = auth.currentUser?.uid
                    Log.d("Auth", "Current User ID: $currentUserId") // Log the current user ID

                    // Check if the user is the admin by UID
                    if (currentUserId == "O0sBdBVDJYM49d4EMVJTUNjJDnR2") {
                        Log.d("Auth", "Admin login successful!") // Log admin login
                        // Admin login - Navigate to AdminDashboardActivity
                        val intent = Intent(context, AdminDashboardActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        context.startActivity(intent)
                    } else {
                        Log.d("Auth", "Regular user login") // Log regular user login
                        // Regular user login - Navigate to MainPageActivity
                        val intent = Intent(context, com.example.mad_collaborative.MainPageActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        context.startActivity(intent)
                    }

                    onSuccess(true) // ✅ Call success callback
                } else {
                    Log.e("Auth", "Login failed", task.exception)
                    Toast.makeText(context, "Login failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    onSuccess(false) // ❌ Call failure callback
                }
            }
    }




    // 🔍 Check if user is an admin
    private fun isAdminUser(email: String): Boolean {
        return email == "jballicud07@gmail.com"
    }
}
