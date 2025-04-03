package com.example.mad_collaborative

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton

class HistoryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the fragment layout
        val view = inflater.inflate(R.layout.fragment_history, container, false)

        // Find the button by its ID
        val findWorkoutButton = view.findViewById<AppCompatButton>(R.id.Find_Workout_btn_1)

        // Set a click listener on the button
        findWorkoutButton.setOnClickListener {
            // Create an instance of FragmentChallenges
            val fragment = ChallengesFragment()

            // Begin the fragment transaction
            val transaction = parentFragmentManager.beginTransaction()

            // Replace the current fragment with FragmentChallenges
            transaction.replace(R.id.frame_layout_1, fragment) // Replace with your fragment container ID
            transaction.addToBackStack(null) // Add the transaction to the back stack if you want back navigation
            transaction.commit() // Commit the transaction
        }

        return view
    }
}
