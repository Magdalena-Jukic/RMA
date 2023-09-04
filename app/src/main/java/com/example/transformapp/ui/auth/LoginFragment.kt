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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.transformapp.MainActivity
import com.example.transformapp.R
import com.example.transformapp.databinding.FragmentLoginBinding
import com.example.transformapp.databinding.FragmentWelcomeBinding
import com.example.transformapp.viewModel.TransformViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: TransformViewModel
    private var firebaseAuth: FirebaseAuth = Firebase.auth
    private var handler: Handler = Handler()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel= ViewModelProvider(
            requireActivity(),
            defaultViewModelProviderFactory
        )[TransformViewModel::class.java]
        binding = FragmentLoginBinding.inflate(layoutInflater)
        binding.loginButton.setOnClickListener{proccedToApplication()}
        binding.backButton.setOnClickListener { backToWelcomeFragment() }
        return binding.root
    }

    private fun backToWelcomeFragment() {
        val action = LoginFragmentDirections.actionLoginFragmentToWelcomeFragment()
        findNavController().navigate(action)
    }

    private fun proccedToApplication() {
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()

        if (TextUtils.isEmpty(email)) {
            binding.etEmail.setError("Missing e-mail!")
        } else if (TextUtils.isEmpty(password)) {
            binding.etPassword.setError("Missing password!")
        } else {
            viewModel.loginUser(email, password)
            handler.postDelayed(Runnable {
                run(){
                    if(firebaseAuth.currentUser != null){
                        Toast.makeText(context, "Logged in successfully!", Toast.LENGTH_SHORT).show()
                        startActivity(
                            Intent(
                                context,
                                MainActivity::class.java
                            )
                        )
                    }else
                        Toast.makeText(context, "Error! Login unsuccessful!", Toast.LENGTH_LONG).show()
                }
            }, 2000)
        }

    }


}