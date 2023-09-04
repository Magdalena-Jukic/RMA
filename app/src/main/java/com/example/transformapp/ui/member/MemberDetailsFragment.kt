package com.example.transformapp.ui.member

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.transformapp.R
import com.example.transformapp.databinding.FragmentMemberDetailsBinding
import com.example.transformapp.databinding.ItemMemberBinding
import com.example.transformapp.model.Member
import com.example.transformapp.viewModel.TransformViewModel
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.viewModel

class MemberDetailsFragment : Fragment() {
    private lateinit var binding : FragmentMemberDetailsBinding
    private lateinit var viewModel: TransformViewModel
    private val args: MemberDetailsFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel= ViewModelProvider(
            requireActivity(),
            defaultViewModelProviderFactory
        )[TransformViewModel::class.java]
        binding = FragmentMemberDetailsBinding.inflate(layoutInflater)
        binding.btnTrainings.setOnClickListener{switchToTrainingList()}
        binding.fabBackToMemberList.setOnClickListener{ showMemberList()}
        return binding.root
    }

    private fun showMemberList() {
        val action = MemberDetailsFragmentDirections.actionMemberDetailsFragmentToMemberListFragment()
        findNavController().navigate(action)
    }

    private fun switchToTrainingList() {
        val action = MemberDetailsFragmentDirections.actionMemberDetailsFragmentToTrainingListFragment(args.memberId)
        findNavController().navigate(action)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val member = viewModel.getMemberById(args.memberId)
        display(member)
    }

    private fun display(member: Member?) {
        member?.let {
            binding.apply {

                Glide.with(requireContext()).load(member.picture).error(R.drawable.ic_person).into(binding.ivMemberDetailsImage)
                //Picasso.get().load(member.picture).into(ivMemberDetailsImage)
                tvMemberDetailsName.text = member.name
                tvMemberDetailsSurname.text = member.surname
                tvMemberDetailsEmail.text = member.email
                tvMemberDetailsPhoneNumber.text = member.phoneNumber
            }
        }
    }

    companion object {
        val Tag = "MembersDetails"
        val MemberIdKey = "MemberId"

        fun create(id: Long): Fragment {
            val fragment = MemberDetailsFragment()
            return fragment
        }
    }

}