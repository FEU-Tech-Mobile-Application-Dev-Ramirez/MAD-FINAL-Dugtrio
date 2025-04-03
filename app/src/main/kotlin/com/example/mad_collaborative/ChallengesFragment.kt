package com.example.mad_collaborative

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RadioButton
import androidx.fragment.app.Fragment


class ChallengesFragment : Fragment(R.layout.fragment_challenges) {

    private lateinit var progressBar1: ProgressBar
    private lateinit var progressBar2: ProgressBar
    private lateinit var progressBar3: ProgressBar
    private lateinit var progressBar4: ProgressBar
    private lateinit var progressBar5: ProgressBar
    private lateinit var progressBar6: ProgressBar

    private lateinit var challengeAcceptedButton1: RadioButton
    private lateinit var challengeAcceptedButton2: RadioButton
    private lateinit var challengeAcceptedButton3: RadioButton
    private lateinit var challengeAcceptedButton4: RadioButton
    private lateinit var challengeAcceptedButton5: RadioButton
    private lateinit var challengeAcceptedButton6: RadioButton

    private lateinit var bookmark1: ImageView
    private lateinit var bookmark2: ImageView
    private lateinit var bookmark3: ImageView
    private lateinit var bookmark4: ImageView
    private lateinit var bookmark5: ImageView
    private lateinit var bookmark6: ImageView

    private var progress1 = 0
    private var progress2 = 0
    private var progress3 = 0
    private var progress4 = 0
    private var progress5 = 0
    private var progress6 = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize views
        progressBar1 = view.findViewById(R.id.Challenge_bar_1)
        progressBar2 = view.findViewById(R.id.challenge_bar_2)
        progressBar3 = view.findViewById(R.id.challenge_bar_3)
        progressBar4 = view.findViewById(R.id.Challenge_bar_4)
        progressBar5 = view.findViewById(R.id.challenge_bar_5)
        progressBar6 = view.findViewById(R.id.challenge_bar_6)

        challengeAcceptedButton1 = view.findViewById(R.id.Challenge_Accepted_Button_1)
        challengeAcceptedButton2 = view.findViewById(R.id.Challenge_Accepted_Button_2)
        challengeAcceptedButton3 = view.findViewById(R.id.Challenge_Accepted_Button_3)
        challengeAcceptedButton4 = view.findViewById(R.id.Challenge_Accepted_Button_4)
        challengeAcceptedButton5 = view.findViewById(R.id.Challenge_Accepted_Button_5)
        challengeAcceptedButton6 = view.findViewById(R.id.Challenge_Accepted_Button_6)

        bookmark1 = view.findViewById(R.id.bookmark1)
        bookmark2 = view.findViewById(R.id.bookmark2)
        bookmark3 = view.findViewById(R.id.bookmark3)
        bookmark4 = view.findViewById(R.id.bookmark4)
        bookmark5 = view.findViewById(R.id.bookmark5)
        bookmark6 = view.findViewById(R.id.bookmark6)

        // Set listeners for RadioButtons
        challengeAcceptedButton1.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                progress1 += 10  // Example progress increment
                progressBar1.progress = progress1
                bookmark1.setImageResource(R.drawable.bookmark_filled_icon) // Update bookmark icon
            }
        }

        challengeAcceptedButton2.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                progress2 += 10
                progressBar2.progress = progress2
                bookmark2.setImageResource(R.drawable.bookmark_filled_icon)
            }
        }

        challengeAcceptedButton3.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                progress3 += 10
                progressBar3.progress = progress3
                bookmark3.setImageResource(R.drawable.bookmark_filled_icon)
            }
        }

        challengeAcceptedButton4.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                progress4 += 10
                progressBar4.progress = progress4
                bookmark4.setImageResource(R.drawable.bookmark_filled_icon)
            }
        }

        challengeAcceptedButton5.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                progress5 += 10
                progressBar5.progress = progress5
                bookmark5.setImageResource(R.drawable.bookmark_filled_icon)
            }
        }

        challengeAcceptedButton6.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                progress6 += 10
                progressBar6.progress = progress6
                bookmark6.setImageResource(R.drawable.bookmark_filled_icon)
            }
        }
    }
}
