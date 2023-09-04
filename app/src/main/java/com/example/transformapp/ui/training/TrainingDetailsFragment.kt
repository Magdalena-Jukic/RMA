package com.example.transformapp.ui.training

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.transformapp.R
import com.example.transformapp.databinding.FragmentTrainingDetailsBinding
import com.example.transformapp.model.Training
import com.example.transformapp.viewModel.TransformViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class TrainingDetailsFragment : Fragment() {
    private lateinit var binding: FragmentTrainingDetailsBinding

    private lateinit var viewModel: TransformViewModel
    private val args: TrainingDetailsFragmentArgs by navArgs()
    private lateinit var training : Training

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel= ViewModelProvider(
            requireActivity(),
            defaultViewModelProviderFactory
        )[TransformViewModel::class.java]
        binding = FragmentTrainingDetailsBinding.inflate(layoutInflater)
        binding.toTrainingList.setOnClickListener{
            showListTrainingFragment()
        }
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        training = viewModel.getTrainingById(args.trainingId)!!
        display(training)
    }

    private fun display(training: Training?) {
        training?.let {
            binding.apply {
                tvTrainingNameDetails.text =training.trainingName
                tvTrainingDescriptionDetails.text = training.trainingDescription
            }
        }
    }

    companion object {
        val Tag = "TrainingsDetails"
        val TrainingIdKey = "TrainingId"

        fun create(id: Long): Fragment {
            val fragment = TrainingDetailsFragment()
            return fragment
        }
    }

    private fun showListTrainingFragment(){
        val action = TrainingDetailsFragmentDirections.actionTrainingDetailsFragmentToTrainingListFragment(training.memberID)
        findNavController().navigate(action)
    }

}