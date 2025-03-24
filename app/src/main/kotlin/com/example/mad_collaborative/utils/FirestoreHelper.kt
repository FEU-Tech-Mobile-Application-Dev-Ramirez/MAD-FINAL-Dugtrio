package com.example.mad_collaborative.utils

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreHelper(private val context: Context) {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance() // Firebase Authentication

    // 🔐 Register User with Email & Password
    fun registerUser(firstName: String, lastName: String, email: String, password: String, onSuccess: (Boolean) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid ?: return@addOnCompleteListener
                    val user = hashMapOf(
                        "firstName" to firstName,
                        "lastName" to lastName,
                        "email" to email,
                        "userId" to userId
                    )
                    db.collection("users").document(userId)
                        .set(user)
                        .addOnSuccessListener {
                            Log.d("Firestore", "User added with ID: $userId")
                            Toast.makeText(context, "Account Created Successfully!", Toast.LENGTH_SHORT).show()
                            onSuccess(true) // ✅ Call success callback
                        }
                        .addOnFailureListener { e ->
                            Log.e("Firestore", "Error saving user data", e)
                            onSuccess(false) // ❌ Call failure callback
                        }
                } else {
                    Log.e("Auth", "Registration failed", task.exception)
                    Toast.makeText(context, "Registration failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    onSuccess(false) // ❌ Call failure callback
                }
            }
    }

    // 🔑 User Login with Email & Password
    fun loginUser(email: String, password: String, onSuccess: (Boolean) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("Auth", "Login successful!")
                    Toast.makeText(context, "Login Successful!", Toast.LENGTH_SHORT).show()
                    onSuccess(true) // ✅ Call success callback
                } else {
                    Log.e("Auth", "Login failed", task.exception)
                    Toast.makeText(context, "Login failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    onSuccess(false) // ❌ Call failure callback
                }
            }
    }
}
