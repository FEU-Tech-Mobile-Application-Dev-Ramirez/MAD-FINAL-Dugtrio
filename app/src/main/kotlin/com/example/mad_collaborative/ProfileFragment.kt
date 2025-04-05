package com.example.mad_collaborative

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProfileFragmentNew : Fragment() {

    private lateinit var tvUserName: TextView
    private lateinit var btnLogout: ImageView
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        tvUserName = view.findViewById(R.id.tvUserName)
        btnLogout = view.findViewById(R.id.LogoutButton)

        loadUserName()
        setupLogoutButton()

        return view
    }

    private fun loadUserName() {
        val userId = auth.currentUser?.uid

        if (userId != null) {
            db.collection("users").document(userId).get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        val firstName = document.getString("firstName") ?: ""
                        val lastName = document.getString("lastName") ?: ""
                        val fullName = "$firstName $lastName".trim()
                        tvUserName.text = if (fullName.isNotBlank()) fullName else "Welcome!"
                    } else {
                        tvUserName.text = "User not found"
                    }
                }
                .addOnFailureListener {
                    tvUserName.text = "Failed to load user"
                }
        } else {
            tvUserName.text = "Not logged in"
        }
    }

    private fun setupLogoutButton() {
        btnLogout.setOnClickListener {
            auth.signOut()
            val intent = Intent(requireContext(), MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}
