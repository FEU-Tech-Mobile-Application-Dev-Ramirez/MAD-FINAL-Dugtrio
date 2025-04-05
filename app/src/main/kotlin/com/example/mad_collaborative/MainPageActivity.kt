package com.example.mad_collaborative

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainPageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_page)

        // Check if the fragment is already added, otherwise add it
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout_1, HomeFragmentNew())
                .commit()
        }

        // Set up Bottom Navigation
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> loadFragment(HomeFragmentNew())  // HomeFragment
                R.id.workouts -> loadFragment(WorkoutsFragmentNew())  // WorkoutsFragment
                R.id.activity -> loadFragment(ActivityFragmentNew())  // ActivityFragment
                R.id.programs -> loadFragment(ProgramsFragmentNew())  // ProfileFragment
                R.id.profile -> loadFragment(ProfileFragmentNew())  // ProfileFragment
                else -> false
            }
            true
        }
    }

    // Function to load a fragment into the FrameLayout
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_layout_1, fragment)  // Replace the FrameLayout with the fragment
            .commit()
    }
}
