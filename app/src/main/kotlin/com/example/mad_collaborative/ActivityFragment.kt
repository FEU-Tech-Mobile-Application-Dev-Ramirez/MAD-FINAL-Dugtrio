package com.example.mad_collaborative

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

class ActivityFragmentNew : Fragment() {

    private lateinit var rootView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        rootView = inflater.inflate(R.layout.fragment_activity, container, false)

        // Setting up interactivity
        setupInteractivity()

        return rootView
    }

    private fun setupInteractivity() {
        // Handle the "Add Workout" icon click - Show Toast
        rootView.findViewById<ImageView>(R.id.add_workout_icon_1)?.setOnClickListener {
            Toast.makeText(requireContext(), "Workout Added", Toast.LENGTH_SHORT).show()
        }

        // Handle "Find A Workout" button click - Simulate with Fragment
        rootView.findViewById<AppCompatButton>(R.id.Find_a_Workout_btn_1)?.setOnClickListener {
            showWorkoutListFragment()
        }

        // Handle "History" TextView click - Simulate with Fragment
        rootView.findViewById<TextView>(R.id.Activity_text_1)?.setOnClickListener {
            showHistoryFragment()
        }

        // Handle "Achievements" TextView click - Simulate with Fragment
        rootView.findViewById<TextView>(R.id.Activity_text_2)?.setOnClickListener {
            showAchievementsFragment()
        }

        // Handle "Challenges" TextView click - Simulate with Fragment
        rootView.findViewById<TextView>(R.id.Activity_text_3)?.setOnClickListener {
            showChallengesFragment()
        }

        // Handle "Daily Challenges" TextView click - Simulate with Fragment
        rootView.findViewById<TextView>(R.id.Activity_text_4)?.setOnClickListener {
            showDailyChallengesFragment()
        }
    }

    private fun showWorkoutListFragment() {
        val workoutListFragment = WorkoutListFragment()
        val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_layout_2, workoutListFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun showHistoryFragment() {
        val historyFragment = HistoryFragment()
        val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_layout_2, historyFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun showAchievementsFragment() {
        val achievementsFragment = AchievementsFragment()
        val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_layout_2, achievementsFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun showChallengesFragment() {
        val challengesFragment = ChallengesFragment()
        val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_layout_2, challengesFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun showDailyChallengesFragment() {
        val dailyChallengesFragment = DailyChallengesFragment()
        val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_layout_2, DailyChallengesFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}

private fun FragmentTransaction.replace(
    i: kotlin.Int,
    dailyChallengesFragment: com.example.mad_collaborative.DailyChallengesFragment.Companion
) {
}
