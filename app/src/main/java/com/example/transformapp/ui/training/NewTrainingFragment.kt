package com.example.transformapp.ui.training

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.transformapp.R
import com.example.transformapp.databinding.FragmentNewTrainingBinding
import com.example.transformapp.model.Training
import com.example.transformapp.viewModel.TransformViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class NewTrainingFragment : Fragment() {

    private lateinit var viewModel: TransformViewModel

    private lateinit var binding : FragmentNewTrainingBinding
    private val args: NewTrainingFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel= ViewModelProvider(
            requireActivity(),
            defaultViewModelProviderFactory
        )[TransformViewModel::class.java]
        binding = FragmentNewTrainingBinding.inflate(layoutInflater)

        binding.bSaveTraining.setOnClickListener{
            saveTraining()
        }
        return binding.root

    }

    private fun saveTraining(){

        val name = binding.etTrainingNameInput.text.toString()
        val description = binding.etTrainingDescriptionInput.text.toString()
        if(name.isEmpty() || description.isEmpty()){
            Toast.makeText(context, "Some data missing!", Toast.LENGTH_LONG).show()
        }else{
            viewModel.save(Training(args.memberId, "", name, description))
            Toast.makeText(context, "Saved training", Toast.LENGTH_SHORT).show()
        }

        val action = NewTrainingFragmentDirections.actionNewTrainingFragmentToTrainingListFragment(args.memberId)
        findNavController().navigate(action)



    }
}