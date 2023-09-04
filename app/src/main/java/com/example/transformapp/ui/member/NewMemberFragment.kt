package com.example.transformapp.ui.member

import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.transformapp.databinding.FragmentNewMemberBinding
import com.example.transformapp.model.Member
import com.example.transformapp.viewModel.TransformViewModel


class NewMemberFragment : Fragment() {
    private lateinit var viewModel: TransformViewModel
    lateinit var binding : FragmentNewMemberBinding

    private lateinit var picture: Uri

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel= ViewModelProvider(
            requireActivity(),
            defaultViewModelProviderFactory
        )[TransformViewModel::class.java]
        binding = FragmentNewMemberBinding.inflate(layoutInflater)
        binding.buttonLoadPicture.setOnClickListener{
            Log.d(ContentValues.TAG, "clickinng")
            getPicture()
        }
        binding.bSaveMember.setOnClickListener { saveMember() }

        return binding.root
    }

    private fun saveMember(){
        val name = binding.etMemberNameInput.text.toString()
        val surname = binding.etMemberSurnameInput.text.toString()
        val email = binding.etMemberEmailInput.text.toString()
        val phoneNumber = binding.etMemberPhoneNumberInput.text.toString()

        if(name.isEmpty() || surname.isEmpty() || email.isEmpty() || phoneNumber.isEmpty()){
            Toast.makeText(context, "Some data is missing! ", Toast.LENGTH_LONG).show()
        }else{
            viewModel.save(Member("","",  name, surname, email, phoneNumber, picture ))
            Toast.makeText(context, "Save member", Toast.LENGTH_SHORT).show()
            val handler = Handler()
            handler.postDelayed({ val action = NewMemberFragmentDirections.actionNewMemberFragmentToMemberListFragment()
                findNavController().navigate(action) }, 5000)
        }



    }

    private  fun  getPicture(){

        getIconPicture.launch("image/*")

    }

    private val getIconPicture =
        registerForActivityResult(
            ActivityResultContracts.GetContent()
        ){
                picture ->
            if(picture != null){
                binding.profileImage.setImageURI(picture)
                this.picture = picture
            }
        }



    companion object {
        val Tag = "NewMember"

        fun create(): Fragment {
            return NewMemberFragment()
        }
    }

}