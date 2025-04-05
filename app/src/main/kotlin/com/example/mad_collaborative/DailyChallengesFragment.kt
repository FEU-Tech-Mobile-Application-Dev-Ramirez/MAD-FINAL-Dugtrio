package com.example.mad_collaborative

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mad_collaborative.databinding.FragmentDailyChallengesBinding
import com.example.mad_collaborative.utils.Challenge
import androidx.recyclerview.widget.LinearLayoutManager


class DailyChallengesFragmentNew : Fragment() {

    private lateinit var binding: FragmentDailyChallengesBinding
    private lateinit var challengeListAdapter: ChallengeListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDailyChallengesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the adapter and RecyclerView
        challengeListAdapter = ChallengeListAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = challengeListAdapter

        // Retrieve the list of Challenge objects passed to the fragment
        val challenges = arguments?.getParcelableArrayList<Challenge>("CHALLENGE_LIST")

        // If challenges are not null, submit them to the adapter
        if (challenges != null && challenges.isNotEmpty()) {
            challengeListAdapter.submitList(challenges)
        } else {
            // Handle empty state if necessary
        }
    }

    companion object {
        // Helper function to create a new instance of the fragment with a list of Challenge objects
        fun newInstance(challenges: List<Challenge>): DailyChallengesFragmentNew {
            val fragment = DailyChallengesFragmentNew()
            val args = Bundle().apply {
                putParcelableArrayList("CHALLENGE_LIST", ArrayList(challenges))  // Pass the list of challenges as Parcelable
            }
            fragment.arguments = args
            return fragment
        }
    }
}

