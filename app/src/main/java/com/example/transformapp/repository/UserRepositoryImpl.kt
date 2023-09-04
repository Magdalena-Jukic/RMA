package com.example.transformapp.repository

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.transformapp.model.Member
import com.example.transformapp.model.Training
import com.example.transformapp.model.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.util.HashMap

class UserRepositoryImpl() : UserRepository{
    private var firebaseAuth = Firebase.auth
    private var firebaseStore: FirebaseFirestore = Firebase.firestore
    private var storageReference = FirebaseStorage.getInstance().reference
    private var collectionReference: CollectionReference = firebaseStore.collection("users")

    private var liveData: MutableLiveData<MutableList<User>> = MutableLiveData<MutableList<User>>()

    override fun save(user: User) {
        firebaseAuth.createUserWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener {
                    task ->
                if (task.isSuccessful){
                    user.id = firebaseAuth.currentUser!!.uid
                    val documentReference: DocumentReference = collectionReference.document(user.id)
                    val newUser: MutableMap<String, Any> = HashMap()
                    newUser["id"] = user.id
                    newUser["name"] = user.name
                    newUser["surname"] = user.surname
                    newUser["email"] = user.email
                    newUser["password"] = user.password

                    documentReference.set(user).addOnSuccessListener {
                        Log.d(ContentValues.TAG, "OnSuccess: user profile created for ${user.email}")
                        return@addOnSuccessListener
                    }
                        .addOnFailureListener{
                            Log.d(ContentValues.TAG, "OnFailure: ${it.message}")
                            return@addOnFailureListener
                        }
                }
            }
    }

    override fun isRegistered(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener {
                    task ->
                task.isSuccessful

            }
    }

    override fun getAllUsers(): MutableLiveData<MutableList<User>> {
        collectionReference.get().addOnSuccessListener { snapshots ->
            if(snapshots != null && !snapshots.isEmpty){
                val users = mutableListOf<User>()
                for(user in snapshots){
                    users.add(User(
                        user.data["id"] as String,
                        user.data["name"] as String,
                        user.data["surname"] as String,
                        user.data["email"] as String,
                        user.data["password"] as String,
                    ))
                }
                this.liveData.postValue(users)
            }else{
                Log.d(ContentValues.TAG, "No data")
            }
        }
        return liveData
    }

}