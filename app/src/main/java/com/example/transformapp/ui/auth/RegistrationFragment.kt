package com.example.transformapp.ui.auth

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.transformapp.MainActivity
import com.example.transformapp.R
import com.example.transformapp.databinding.FragmentRegistrationBinding
import com.example.transformapp.model.User
import com.example.transformapp.viewModel.TransformViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegistrationFragment : Fragment() {
    private lateinit var binding: FragmentRegistrationBinding
    private var firebaseAuth: FirebaseAuth = Firebase.auth
    private var handler: Handler = Handler()
    private lateinit var viewModel: TransformViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel= ViewModelProvider(
            requireActivity(),
            defaultViewModelProviderFactory
        )[TransformViewModel::class.java]
        binding = FragmentRegistrationBinding.inflate(layoutInflater)
        binding.backButton.setOnClickListener { backToWelcomeFragment() }
        binding.registerButton.setOnClickListener { proceedToApplication() }
        binding.etPassword.doOnTextChanged { text, start, before, count ->
            if(text!!.length < 8){
                binding.tilPassword.error = "Too short Password!"
            }
            else{
                binding.tilPassword.error = null
            }
        }
        return binding.root
    }

    private fun proceedToApplication() {
        val name = binding.etName.text.toString()
        val surname = binding.etSurname.text.toString()
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()

        if(TextUtils.isEmpty(name) ||  TextUtils.isEmpty(surname) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) ) {
            Toast.makeText(
                context,
                "Personal data missing! Please check your input and try again!",
                Toast.LENGTH_LONG
            ).show()
        }else{
            viewModel.saveUser(
                User(
                    "",
                    name,
                    surname,
                    email,
                    password,

                )
            )
            handler.postDelayed(Runnable
            {
                run() {
                    if (firebaseAuth.currentUser != null) {
                        Toast.makeText(context, "Sign in successful", Toast.LENGTH_LONG).show()
                        startActivity(
                            Intent(
                                context,
                                MainActivity::class.java
                            )
                        )
                    } else {
                        Toast.makeText(context, "Unsuccessful sign in", Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }, 2000
            )
        }
    }

    private fun backToWelcomeFragment() {
        val action = RegistrationFragmentDirections.actionRegistrationFragmentToWelcomeFragment()
        findNavController().navigate(action)
    }


}