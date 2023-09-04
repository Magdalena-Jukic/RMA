package com.example.transformapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.transformapp.R
import com.example.transformapp.databinding.FragmentWelcomeBinding


class WelcomeFragment : Fragment() {

    private lateinit var binding: FragmentWelcomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentWelcomeBinding.inflate(layoutInflater)
        binding.loginButton.setOnClickListener{showLogIn()}
        binding.registerButton.setOnClickListener{showRegistration()}
        return binding.root
    }

    private fun showRegistration() {
        val action = WelcomeFragmentDirections.actionWelcomeFragmentToRegistrationFragment()
        findNavController().navigate(action)
    }

    private fun showLogIn() {
        val action = WelcomeFragmentDirections.actionWelcomeFragmentToLoginFragment()
        findNavController().navigate(action)
    }

}