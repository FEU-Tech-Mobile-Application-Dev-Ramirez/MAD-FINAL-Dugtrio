package com.example.mad_collaborative

import com.google.android.material.bottomnavigation.BottomNavigationView
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class HomeFragmentNew : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Initialize buttons
        val readNowBtn1: Button = view.findViewById(R.id.Read_Now_btn_1)
        val readNowBtn2: Button = view.findViewById(R.id.read_Now_btn_2)
        val exploreWorkoutBtn: Button = view.findViewById(R.id.Explore_Workout_btn)
        val viewAllText: TextView = view.findViewById(R.id.View_All)

        // Open an article about "Empty Calories"
        readNowBtn1.setOnClickListener {
            openWebPage("https://www.houstonmethodist.org/blog/articles/2021/jan/empty-calories-what-are-they-and-which-foods-are-they-hiding-in/")
        }

        // Open an article about "Sports and Health"
        readNowBtn2.setOnClickListener {
            openWebPage("https://thehealthcareinsights.com/the-importance-of-sports-to-health-and-fitness/")
        }

        exploreWorkoutBtn.setOnClickListener {
            val workoutsFragment = WorkoutsFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.frame_layout_1, workoutsFragment) // Ensure correct container ID
                .addToBackStack(null)
                .commit()

            // Update the Bottom Navigation View selection
            val bottomNav = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
            bottomNav.selectedItemId = R.id.workouts // Ensure this ID matches the one in menu.xml
        }


        // View all articles
        viewAllText.setOnClickListener {
            openWebPage("https://www.sciencedaily.com/news/health_medicine/fitness/") // Replace with actual link
        }

        return view
    }

    // Function to open a webpage
    private fun openWebPage(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}
