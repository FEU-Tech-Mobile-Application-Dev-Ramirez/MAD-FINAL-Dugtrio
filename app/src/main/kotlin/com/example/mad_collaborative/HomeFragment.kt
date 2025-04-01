package com.example.mad_collaborative

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment

class HomeFragmentNew : Fragment(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Example: Interacting with TextViews and Buttons
        val homeTitleTextView: TextView = view.findViewById(R.id.Home_1)
        val whatsNewTextView: TextView = view.findViewById(R.id.Whats_New)
        val viewAllTextView: TextView = view.findViewById(R.id.View_All)
        val readNowButton1: Button = view.findViewById(R.id.Read_Now_btn_1)
        val exploreWorkoutButton: Button = view.findViewById(R.id.Explore_Workout_btn)

        // Ensure all elements are clickable
        homeTitleTextView.setOnClickListener {
            // You can add some logic here for when the Home Title is clicked
        }

        // Example Logic: Set dynamic text to what's new and Home title
        homeTitleTextView.text = "Welcome to Smartfit"
        whatsNewTextView.text = "Check out the latest news from Smartfit!"

        // Example: View All Button Logic
        viewAllTextView.setOnClickListener {
            // Maybe open a detailed view when the "View All" button is clicked
        }

        // Button Click Logic
        readNowButton1.setOnClickListener {
            // For instance, navigate to a detailed page or show a new message
        }

        exploreWorkoutButton.setOnClickListener {
            // Navigate to the workout section or open a new fragment
            // You can also use Navigation or Fragment Transaction here if needed
        }

        // Example: Interacting with ImageViews
        val foodCaloriesImageView: ImageView = view.findViewById(R.id.Food_Calories_1)
        foodCaloriesImageView.setImageResource(R.drawable.food_calories)  // Set an image dynamically if needed

        // Example: Interact with another image view or text views
        val sportsArtImageView: ImageView = view.findViewById(R.id.sports_art_1)
        sportsArtImageView.setImageResource(R.drawable.sports_art)  // Dynamically set an image for the "sports_art_1"

        // Set interactivity to buttons or other interactive UI components if needed.
    }
}
