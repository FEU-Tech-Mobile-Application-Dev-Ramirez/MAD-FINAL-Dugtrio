package com.example.mad_collaborative

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mad_collaborative.databinding.ChallengeItemViewBinding
import com.example.mad_collaborative.utils.Challenge

class ChallengeListAdapter : RecyclerView.Adapter<ChallengeListAdapter.ChallengeViewHolder>() {

    private val challenges = mutableListOf<Challenge>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChallengeViewHolder {
        val binding = ChallengeItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChallengeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChallengeViewHolder, position: Int) {
        val challenge = challenges[position]
        holder.bind(challenge)
    }

    override fun getItemCount(): Int = challenges.size

    // Submit a list of challenges to the adapter
    fun submitList(challenges: List<Challenge>) {
        this.challenges.clear()
        this.challenges.addAll(challenges)
        notifyDataSetChanged() // Notify once after the list is updated
    }

    inner class ChallengeViewHolder(private val binding: ChallengeItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(challenge: Challenge) {
            // Bind challenge data to the views
            binding.challengeNameTextView.text = challenge.name
            binding.challengeDescriptionTextView.text = challenge.description
            binding.challengeCategoryTextView.text = challenge.category
        }
    }
}
