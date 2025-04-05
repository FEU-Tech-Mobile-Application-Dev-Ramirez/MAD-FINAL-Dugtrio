package com.example.mad_collaborative

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.example.mad_collaborative.databinding.UserItemViewBinding
import com.example.mad_collaborative.utils.User

class UserListAdapter : RecyclerView.Adapter<UserListAdapter.UserViewHolder>() {

    private val users = mutableListOf<User>()
    private var selectedUser: User? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = UserItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]
        holder.bind(user, position)
    }

    override fun getItemCount(): Int = users.size

    // Submit a list of users to the adapter
    fun submitList(users: List<User>) {
        this.users.clear()
        this.users.addAll(users)
        notifyDataSetChanged()  // Notify once after the list is updated
    }

    // Get the selected user (or null if none is selected)
    fun getSelectedUser(): User? {
        return selectedUser
    }

    inner class UserViewHolder(private val binding: UserItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User, position: Int) {
            // Display user's name
            binding.userCheckBox.text = "${user.firstName} ${user.lastName}"

            // Ensure checkbox reflects the selection state
            binding.userCheckBox.isChecked = selectedUser == user

            // Set up the checkbox listener to manage selection/deselection
            binding.userCheckBox.setOnCheckedChangeListener { _, isChecked ->

                // Use post to update the RecyclerView after layout pass
                binding.root.post {
                    if (isChecked) {
                        // If a user is selected, deselect the previous selection
                        if (selectedUser != user) {
                            val previousSelectedUser = selectedUser
                            selectedUser = user
                            // Notify the previous item to change its state
                            previousSelectedUser?.let { notifyItemChanged(users.indexOf(it)) }
                        }
                    } else {
                        // Deselect user
                        if (selectedUser == user) {
                            selectedUser = null
                        }
                    }

                    // Update the current user position
                    notifyItemChanged(position) // Only update the clicked item
                }
            }
        }
    }
}
