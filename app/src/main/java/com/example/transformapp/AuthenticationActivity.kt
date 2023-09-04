package com.example.transformapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.transformapp.databinding.ActivityAuthenticationBinding
import com.example.transformapp.viewModel.TransformViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.koin.androidx.viewmodel.ext.android.viewModel

class AuthenticationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthenticationBinding
    private var firebaseAuth = Firebase.auth
    private var handler = Handler()
    private val viewModel by viewModel<TransformViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpApplication()
        binding = ActivityAuthenticationBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setUpApplication() {
        viewModel.users.isInitialized
        handler.postDelayed(
            Runnable
            {
                run() {
                    if (firebaseAuth.currentUser != null) {
                        startActivity(
                            Intent(
                                this@AuthenticationActivity,
                                MainActivity::class.java
                            )
                        )
                    }
                }
            }, 2000)
    }
}