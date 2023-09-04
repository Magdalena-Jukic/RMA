package com.example.transformapp.repository

import android.content.ContentValues
import android.net.Uri
import android.os.Handler
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.transformapp.model.Member
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.util.HashMap

class MemberRepositoryImpl() : MemberRepository {
    private var firebaseStore: FirebaseFirestore = Firebase.firestore
    private var storageReference = FirebaseStorage.getInstance().reference
    private var collectionReference: CollectionReference = firebaseStore.collection("members")
    private var firebaseAuth = Firebase.auth

    private var members: MutableLiveData<MutableList<Member>?> =
        MutableLiveData<MutableList<Member>?>()
    private lateinit var member_picture: Uri

    override fun save(member: Member) {
        val documentRef: DocumentReference = collectionReference.document()
        val imageRef =
            storageReference.child("images/members/${member.picture.lastPathSegment}")
        imageRef.putFile(member.picture).addOnSuccessListener {
            imageRef.downloadUrl.addOnSuccessListener { download ->
                val new_member: MutableMap<String, Any> = HashMap()
                new_member["userID"] = member.userID
                new_member["id"] = documentRef.id
                new_member["name"] = member.name
                new_member["surname"] = member.surname
                new_member["email"] = member.email
                new_member["phoneNumber"] = member.phoneNumber
                new_member["picture"] = member.picture

                documentRef.set(new_member).addOnSuccessListener {
                    Log.d(ContentValues.TAG, "Korisnik je spremljen!")
                }
            }
        }

        val handler = Handler()
        handler.postDelayed(Runnable {
            run() {
                val updated: MutableList<Member>? = members.value
                updated?.add(member)
                if (updated != null) {
                    members.postValue(updated)
                }
            }
        }, 2000)


    }

    override fun delete(member: Member) {
        var documentRef: DocumentReference
        collectionReference.get().addOnSuccessListener { it ->
            for (memb in it) {
                if (memb.data.get("email") == member.email) {//email je jedinstven!!
                    documentRef = collectionReference.document(memb.id)
                    documentRef.delete()
                    Log.d(ContentValues.TAG, "Korisnik je obrisan!")
                }
            }

        }

        val handler = Handler()
        handler.postDelayed(Runnable {

            val updated: MutableList<Member>? = members.value
            updated?.remove(member)
            if (updated != null) {
                members.postValue(updated)
            }

        }, 2000)
    }

    override fun getMemberById(id: String): Member? {

        val members = this.members.value?.iterator()
        members?.forEach {
            if (it.id == id) {
                return it
            }
        }
        return null
    }

    override fun getAllMembers(): MutableLiveData<MutableList<Member>?> {

        members.value?.clear()
        collectionReference.get().addOnSuccessListener { membs ->

            if (membs != null && !membs.isEmpty && firebaseAuth.currentUser != null) {
                val members = mutableListOf<Member>()
                for (member in membs) {
                    member_picture = Uri.parse(member.data.get("picture") as String)
                    members.add(
                        Member(
                            member.data.get("userID") as String,
                            member.data.get("id") as String,
                            member.data.get("name") as String,
                            member.data.get("surname") as String,
                            member.data.get("email") as String,
                            member.data.get("phoneNumber") as String,
                            member_picture
                        )
                    )
                }
                this.members.postValue(members)
            }

        }
        return this.members


    }

}