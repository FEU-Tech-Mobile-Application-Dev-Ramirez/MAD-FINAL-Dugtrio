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

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<ImageView>(R.id.Close_1).setOnClickListener {
            Toast.makeText(requireContext(), "Close button clicked", Toast.LENGTH_SHORT).show()
        }

        view.findViewById<ImageView>(R.id.Settings_1).setOnClickListener {
            Toast.makeText(requireContext(), "Settings clicked", Toast.LENGTH_SHORT).show()
        }

        view.findViewById<ImageView>(R.id.qr_code).setOnClickListener {
            Toast.makeText(requireContext(), "QR Code clicked", Toast.LENGTH_SHORT).show()
        }

        view.findViewById<TextView>(R.id.profile_Bio2).setOnClickListener {
            Toast.makeText(requireContext(), "View messages clicked", Toast.LENGTH_SHORT).show()
        }

        view.findViewById<TextView>(R.id.Smartfit_Rewards).setOnClickListener {
            Toast.makeText(requireContext(), "Smartfit Rewards clicked", Toast.LENGTH_SHORT).show()
        }

        view.findViewById<AppCompatButton>(R.id.Edit_Profile_btn).setOnClickListener {
            Toast.makeText(requireContext(), "Edit Profile clicked", Toast.LENGTH_SHORT).show()
        }

        view.findViewById<AppCompatButton>(R.id.Add_Friends_btn).setOnClickListener {
            Toast.makeText(requireContext(), "Add Friends clicked", Toast.LENGTH_SHORT).show()
        }
    }
}
