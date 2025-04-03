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

        // Button to create new challenge
        binding.createChallengeButton.setOnClickListener {
            val challengeName = binding.challengeNameEditText.text.toString()
            val challengeDescription = binding.challengeDescriptionEditText.text.toString()
            val challengePoints = binding.challengePointsEditText.text.toString()

            if (challengeName.isNotEmpty() && challengeDescription.isNotEmpty() && challengePoints.isNotEmpty()) {
                createChallenge(challengeName, challengeDescription, challengePoints)
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }

        // Button for logout
        binding.logoutButton.setOnClickListener {
            // Perform logout operations if needed (like Firebase logout, etc.)

            // Navigate back to the Welcome Page
            val intent = Intent(this, MainActivity::class.java) // Replace with the correct activity name
            startActivity(intent)
            finish()  // Finish AdminDashboardActivity so it can't be accessed via the back button
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
                userListAdapter.submitList(users)
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Error loading users: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }

    // Create a new challenge and store it in Firestore
    private fun createChallenge(name: String, description: String, points: String) {
        val challengeData = hashMapOf(
            "name" to name,
            "description" to description,
            "points" to points
        )

        firestore.collection("challenges")
            .add(challengeData)
            .addOnSuccessListener {
                Toast.makeText(this, "Challenge created successfully", Toast.LENGTH_SHORT).show()

                // After creating the challenge, automatically assign it to the selected user
                val selectedUser = userListAdapter.getSelectedUser()
                if (selectedUser != null) {
                    assignChallengeToUser(selectedUser, name)
                } else {
                    Toast.makeText(this, "No user selected to assign challenge", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Error creating challenge: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }

    // Assign challenge to a specific user
    private fun assignChallengeToUser(user: User, challenge: String) {
        val userId = user.userId
        val challengeData: Map<String, Any> = mapOf(
            "challenge" to challenge
        )

        firestore.collection("users")
            .document(userId)
            .update(challengeData)
            .addOnSuccessListener {
                Toast.makeText(this, "Challenge assigned to ${user.firstName}", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Error assigning challenge: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
