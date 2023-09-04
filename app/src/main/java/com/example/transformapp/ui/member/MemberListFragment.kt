package com.example.transformapp.ui.member

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.transformapp.AuthenticationActivity
import com.example.transformapp.databinding.FragmentMemberListBinding
import com.example.transformapp.model.Member
import com.example.transformapp.viewModel.TransformViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MemberListFragment : Fragment(), OnMemberEventListener  {
    private lateinit var binding: FragmentMemberListBinding
    private lateinit var adapter: MemberAdapter

    private lateinit var viewModel: TransformViewModel
    private var firebaseAuth = Firebase.auth
    private var handler = Handler()

    private var userMembers = mutableListOf<Member>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel= ViewModelProvider(
            requireActivity(),
            defaultViewModelProviderFactory
        )[TransformViewModel::class.java]
        binding = FragmentMemberListBinding.inflate(layoutInflater)
        setupRecyclerView()
        binding.fabAddMember.setOnClickListener { showCreateNewMemberFragment() }
        binding.fabLogout.setOnClickListener{Logout()}
        return binding.root
    }

    private fun Logout() {
        firebaseAuth.signOut()
        handler.postDelayed(
            Runnable
            {
                run() {

                        startActivity(
                            Intent(
                                context,
                                AuthenticationActivity::class.java
                            )
                        )

                }
            }, 2000)
    }


    private fun setupRecyclerView() {
        binding.memberListRvMembers.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
        adapter = MemberAdapter()
        adapter.onMemberSelectedListener = this
        binding.memberListRvMembers.adapter = adapter

    }

    override fun onResume() {
        super.onResume()
        updateData()
    }

    private fun updateData() {

        userMembers.clear()
        viewModel.members.observe(viewLifecycleOwner){
                members->
            if(members?.isNotEmpty() == true) {

                members?.forEach {
                    if (it.userID == firebaseAuth.currentUser!!.uid) {
                        userMembers.add(it)
                    }
                    Log.d("Test", it.phoneNumber)
                }

                adapter.setMembers(userMembers)



            }

        }
    }

    companion object {
        val Tag = "MembersList"

        fun create(): Fragment {
            return MemberListFragment()
        }

    }

    override fun onMemberSelected(id: String?) {
        val action =
            MemberListFragmentDirections.actionMemberListFragmentToMemberDetailsFragment(id ?: "")
        findNavController().navigate(action)

    }

    override fun onMemberLongPress(member: Member?): Boolean {

        member?.let { it ->
            viewModel.delete(it)
            updateData()
        }
        Toast.makeText(context, "Member is deleted!", Toast.LENGTH_SHORT).show()
        return true
    }

    private fun showCreateNewMemberFragment() {
        val action = MemberListFragmentDirections.actionMemberListFragmentToNewMemberFragment()
        findNavController().navigate(action)
    }

}