package com.example.mad_collaborative

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment

class ProgramsFragmentNew : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView = inflater.inflate(R.layout.fragment_programs, container, false)

        // Setting click listeners for image views
        rootView.findViewById<ImageView>(R.id.program_workout_image1)?.setOnClickListener {
            openWebPage("https://www.nerdfitness.com/blog/beginner-body-weight-workout-burn-fat-build-muscle/")
        }

        rootView.findViewById<ImageView>(R.id.program_workout_image2)?.setOnClickListener {
            openWebPage("https://www.nerdfitness.com/blog/strength-training-101/")
        }

        rootView.findViewById<ImageView>(R.id.program_workout_image3)?.setOnClickListener {
            openWebPage("https://www.verywellfit.com/cardio-workout-program-weight-loss-1230810")
        }

        return rootView
    }

    private fun openWebPage(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}
