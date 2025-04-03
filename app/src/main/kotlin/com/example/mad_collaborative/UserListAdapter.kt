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
        holder.bind(user)
    }

    override fun getItemCount(): Int = users.size

    fun submitList(users: List<User>) {
        this.users.clear()
        this.users.addAll(users)
        notifyDataSetChanged()
    }

    fun getSelectedUser(): User? {
        return selectedUser
    }

    inner class UserViewHolder(private val binding: UserItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.userCheckBox.text = "${user.firstName} ${user.lastName}"
            binding.userCheckBox.setOnCheckedChangeListener { _, isChecked ->
                selectedUser = if (isChecked) user else null
            }
        }
    }
}
