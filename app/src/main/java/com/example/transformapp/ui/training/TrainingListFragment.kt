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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.transformapp.R
import com.example.transformapp.databinding.FragmentTrainingListBinding
import com.example.transformapp.model.Training
import com.example.transformapp.ui.member.MemberDetailsFragmentArgs
import com.example.transformapp.viewModel.TransformViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class TrainingListFragment : Fragment() , OnTrainingEventListener{

    private lateinit var binding: FragmentTrainingListBinding
    private lateinit var adapter: TrainingAdapter
    private var memberTrainings = mutableListOf<Training>()

    private lateinit var viewModel: TransformViewModel
    private val args: TrainingListFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {viewModel= ViewModelProvider(
        requireActivity(),
        defaultViewModelProviderFactory
    )[TransformViewModel::class.java]
        binding = FragmentTrainingListBinding.inflate(layoutInflater)
        setupRecyclerView()
        binding.fabAddTraining.setOnClickListener { showCreateNewTrainingFragment() }
        binding.fabBackToMembers.setOnClickListener{ showDetailsMemberFragment()}

        return binding.root
    }


    private fun setupRecyclerView() {
        binding.trainingListRvTrainings.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
        adapter = TrainingAdapter()
        adapter.onTrainingSelectedListener = this
        binding.trainingListRvTrainings.adapter = adapter

    }

    override fun onResume() {
        super.onResume()
        updateData()
    }

    private fun updateData() {

        memberTrainings.clear()
        viewModel.trainings.observe(viewLifecycleOwner) {tranings->
            if(tranings?.isNotEmpty() == true) {

                tranings?.forEach {
                    if (it.memberID == args.memberId) {
                        memberTrainings.add(it)
                    }
                }
                if (memberTrainings.isNotEmpty()) {
                    adapter.setTrainings(memberTrainings)
                }
            }

        }

    }

    companion object {
        val Tag = "TrainingList"

        fun create(): Fragment {
            return TrainingListFragment()
        }

    }

    private fun showCreateNewTrainingFragment() {
        val action = TrainingListFragmentDirections.actionTrainingListFragmentToNewTrainingFragment(args.memberId)
        findNavController().navigate(action)
    }

    private fun showDetailsMemberFragment(){
        val action = TrainingListFragmentDirections.actionTrainingListFragmentToMemberDetailsFragment(args.memberId)
        findNavController().navigate(action)

    }

    override fun onTrainingSelected(id: String?) {
        val action =
            TrainingListFragmentDirections.actionTrainingListFragmentToTrainingDetailsFragment(id ?: "")
        findNavController().navigate(action)
    }

    override fun onTrainingLongPress(training: Training?): Boolean {
        training?.let{it->
            viewModel.delete(it)
            updateData()
        }
        Toast.makeText(context, "Training is deleted!", Toast.LENGTH_SHORT).show()
        return true
    }


}