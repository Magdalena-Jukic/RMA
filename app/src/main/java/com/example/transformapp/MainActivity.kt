package com.example.transformapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.transformapp.databinding.ActivityMainBinding
import com.example.transformapp.viewModel.TransformViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val viewModel by viewModel<TransformViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        viewModel.users.isInitialized
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }

}