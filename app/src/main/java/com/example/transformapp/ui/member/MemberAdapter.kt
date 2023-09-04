package com.example.transformapp.ui.member

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.Placeholder
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.transformapp.R
import com.example.transformapp.databinding.ItemMemberBinding
import com.example.transformapp.model.Member
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MemberAdapter : RecyclerView.Adapter<MemberViewHolder>() {

    private val members = mutableListOf<Member>()
    var onMemberSelectedListener: OnMemberEventListener? = null

    fun setMembers(members: List<Member>) {
        this.members.clear()
        for (member in members){
            this.members.add(member)
            this.notifyDataSetChanged()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_member, parent, false)
        return MemberViewHolder(view)

    }

    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {
        val member = members[position]
        holder.bind(member)
        onMemberSelectedListener?.let { listener ->
            holder.itemView.setOnClickListener{listener.onMemberSelected(member.id)}
            holder.itemView.setOnLongClickListener { listener.onMemberLongPress(member) }
        }
    }

    override fun getItemCount(): Int = members.count()
}


class MemberViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    fun bind(member: Member) {

        val binding = ItemMemberBinding.bind(itemView)
        binding.itemMemberName.text = member.name
        binding.itemMemberSurname.text = member.surname
        Glide.with(itemView.context).load(member.picture).error(R.drawable.ic_person).into(binding.itemMemberPicture)
        //Picasso.get().load(member.picture).into(binding.itemMemberPicture)
    }



}