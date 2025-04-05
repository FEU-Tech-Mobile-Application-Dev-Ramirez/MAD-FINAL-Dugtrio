package com.example.mad_collaborative

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mad_collaborative.utils.FirestoreHelper
import com.example.mad_collaborative.utils.User
import com.google.firebase.firestore.FirebaseFirestore
import com.example.mad_collaborative.databinding.AdminDashboardBinding
import com.google.firebase.firestore.FieldValue
import com.example.mad_collaborative.utils.Challenge
import com.google.firebase.auth.FirebaseAuth
import android.util.Log

class AdminDashboardActivity : AppCompatActivity() {

    private lateinit var firestoreHelper: FirestoreHelper
    private lateinit var firestore: FirebaseFirestore
    private lateinit var binding: AdminDashboardBinding

    private lateinit var userListAdapter: UserListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize ViewBinding
        binding = AdminDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firestoreHelper = FirestoreHelper(this)
        firestore = FirebaseFirestore.getInstance()

        // Set up RecyclerView
        binding.userRecyclerView.layoutManager = LinearLayoutManager(this)
        userListAdapter = UserListAdapter()
        binding.userRecyclerView.adapter = userListAdapter

        // Load users from Firestore
        loadUsers()

        // Button to create new challenges
        binding.createChallengeButton.setOnClickListener {
            val challengeName = binding.challengeNameEditText.text.toString()
            val challengeDescription = binding.challengeDescriptionEditText.text.toString()
            val challengeCategory = binding.challengeCategoryEditText.text.toString()

            if (challengeName.isNotEmpty() && challengeDescription.isNotEmpty() && challengeCategory.isNotEmpty()) {
                createChallenges(challengeName, challengeDescription, challengeCategory)
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }

        // Button for logout
        binding.logoutButton.setOnClickListener {
            // Perform logout operations if needed (like Firebase logout, etc.)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    // Load users from Firestore and display them in RecyclerView
    private fun loadUsers() {
        firestore.collection("users")
            .get()
            .addOnSuccessListener { result ->
                val users = mutableListOf<User>()
                for (document in result) {
                    val user = document.toObject(User::class.java)
                    users.add(user)
                }
                userListAdapter.submitList(users)  // Ensure data is properly passed to the adapter
            }
            .addOnFailureListener { exception ->
                Toast.makeText(
                    this,
                    "Error loading users: ${exception.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun createChallenges(name: String, description: String, category: String) {
        val challenges = mutableListOf<Challenge>()

        // Create 3 challenges with the provided information
        for (i in 1..3) {
            val challenge = Challenge("${name} $i", "$description $i", "$category $i")
            challenges.add(challenge)
        }

        // Log to verify Challenge data
        Log.d("AdminDashboardActivity", "Created challenges: $challenges")

        // Store each challenge in Firestore
        val challengeCollectionRef = firestore.collection("challenges")
        for (challenge in challenges) {
            challengeCollectionRef.add(challenge)
                .addOnSuccessListener {
                    Log.d("AdminDashboardActivity", "Challenge stored successfully")
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(
                        this,
                        "Error storing challenge: ${exception.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }

        // After creating and storing challenges, assign them to the selected user
        val selectedUser = userListAdapter.getSelectedUser()
        if (selectedUser != null) {
            Log.d("AdminDashboardActivity", "Selected User: ${selectedUser.firstName}")
            assignChallengesToUser(selectedUser, challenges)
        } else {
            Log.e("AdminDashboardActivity", "No user selected!")
            Toast.makeText(this, "No user selected to assign challenges", Toast.LENGTH_SHORT).show()
        }

        // Pass the list of challenges to the fragment
        val fragment = DailyChallengesFragmentNew.newInstance(challenges)

        // Ensure the fragment transaction is correct
        try {
            supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout_3, fragment)
                .commit()
        } catch (e: Exception) {
            Log.e("AdminDashboardActivity", "Fragment transaction error: ${e.message}")
            e.printStackTrace()
        }
    }

    private fun assignChallengesToUser(user: User, challenges: List<Challenge>) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid

        if (userId != null) {
            val challengeDataList = challenges.map { challenge ->
                hashMapOf(
                    "name" to challenge.name,
                    "description" to challenge.description,
                    "category" to challenge.category
                )
            }

            // Update the user's assigned challenges in Firestore
            firestore.collection("users")
                .document(userId)
                .update("assignedChallenges", FieldValue.arrayUnion(*challengeDataList.toTypedArray()))
                .addOnSuccessListener {
                    Toast.makeText(
                        this,
                        "Challenges assigned to ${user.firstName}",
                        Toast.LENGTH_SHORT
                    ).show()

                    // Pass the list of challenges to the fragment
                    val fragment = DailyChallengesFragmentNew.newInstance(challenges)

                    // Replace the fragment with the updated data
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_layout_3, fragment)
                        .commit()
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(
                        this,
                        "Error assigning challenges: ${exception.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        } else {
            // Handle the case when user is not logged in
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
        }
    }

}

