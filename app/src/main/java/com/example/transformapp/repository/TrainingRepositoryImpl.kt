package com.example.transformapp.repository

import android.content.ContentValues
import android.os.Handler
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.transformapp.model.Training
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class TrainingRepositoryImpl() : TrainingRepository {


    private var firebaseStore: FirebaseFirestore = Firebase.firestore
    private var collectionReference: CollectionReference = firebaseStore.collection("trainings")

    private var trainings: MutableLiveData<MutableList<Training>?> =
        MutableLiveData<MutableList<Training>?>()

    override fun save(training: Training) {
        val documentRef: DocumentReference = collectionReference.document()

        val new_training: MutableMap<String, Any> = HashMap()
        new_training["memberID"] = training.memberID
        new_training["id"] = documentRef.id
        new_training["name"] = training.trainingName
        new_training["description"] = training.trainingDescription
        documentRef.set(new_training).addOnSuccessListener {
            Log.d(ContentValues.TAG, "Trening je spremljen!")
        }

        val handler = Handler()
        handler.postDelayed(Runnable {
            run() {
                val updated: MutableList<Training>? = trainings.value
                updated?.add(training)
                if (updated != null) {
                    trainings.postValue(updated)
                }
            }
        }, 2000)
    }

    override fun delete(training: Training) {
        var documentRef: DocumentReference
        collectionReference.get().addOnSuccessListener { it ->
            for (memb in it) {
                if (memb.data.get("id") == training.id) {//id je jedinstven!!
                    documentRef = collectionReference.document(memb.id)
                    documentRef.delete()
                    Log.d(ContentValues.TAG, "Trening je obrisan!")
                }
            }

        }

        val handler = Handler()
        handler.postDelayed(Runnable {
            run() {
                val updated: MutableList<Training>? = trainings.value
                updated?.remove(training)
                if (updated != null) {
                    trainings.postValue(updated)
                }
            }
        }, 2000)
    }

    override fun getTrainingById(id: String): Training? {
        val trainings = this.trainings.value?.iterator()
        trainings?.forEach {
            if (it.id == id) {
                return it
            }
        }
        return null
    }

    override fun getAllTrainings(): MutableLiveData<MutableList<Training>?> {
        trainings.value?.clear()
        collectionReference.get().addOnSuccessListener { trains ->
            if (trains != null && !trains.isEmpty) {
                val trainings = mutableListOf<Training>()
                for (training in trains) {
                    trainings.add(
                        Training(
                            training.data.get("memberID") as String,
                            training.id,
                            training.data.get("name") as String,
                            training.data.get("description") as String
                        )
                    )
                }
                this.trainings.postValue(trainings)
            }

        }
        return this.trainings


    }

}