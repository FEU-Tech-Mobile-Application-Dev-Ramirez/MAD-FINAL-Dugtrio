package com.example.mad_collaborative

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView

class WorkoutsFragmentNew : Fragment() {

    private lateinit var rootView: View
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        rootView = inflater.inflate(R.layout.fragment_workouts, container, false)

        sharedPreferences = requireActivity().getSharedPreferences("Bookmarks", Context.MODE_PRIVATE)

        setupCardClickListeners()

        return rootView
    }

    private fun setupCardClickListeners() {
        val cardIds = listOf(R.id.card2, R.id.card3, R.id.card4, R.id.card5, R.id.card6)

        cardIds.forEach { cardId ->
            rootView.findViewById<CardView>(cardId)?.setOnClickListener {
                // Redirect to YouTube video when a card is clicked
                val youtubeUrl = when (cardId) {
                    R.id.card2 -> "https://www.youtube.com/watch?v=uAqRC7gA4Ok"
                    R.id.card3 -> "https://www.youtube.com/watch?v=edIK5SZYMZo"
                    R.id.card4 -> "https://www.youtube.com/watch?v=PwXUHMKamP8"
                    R.id.card5 -> "https://www.youtube.com/watch?v=3WUtJxLv-wI"
                    R.id.card6 -> "https://www.youtube.com/watch?v=DsqS-yuthWk"
                    else -> ""
                }
                openUrl(youtubeUrl)
            }
        }

        // Set up the "View All" button click listener
        rootView.findViewById<View>(R.id.Workouts_4)?.setOnClickListener {
            // Redirect to a webpage when the "View All" button is clicked
            val webpageUrl = "https://darebee.com/" // Replace with your desired webpage URL
            openUrl(webpageUrl)
        }
        // Workouts_7 TextView click listener (View All link)
        rootView.findViewById<View>(R.id.Workouts_7)?.setOnClickListener {
            val webpageUrl = "https://www.healthline.com/health/fitness-exercise/essential-runner-stretches#10-helpful-post-run-stretches" // Replace with your desired webpage URL
            openUrl(webpageUrl)
        }
    }

    private fun openUrl(url: String) {
        if (url.isNotEmpty()) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }
    }
}
