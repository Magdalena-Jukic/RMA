package com.example.transformapp.ui.training

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.transformapp.R
import com.example.transformapp.databinding.ItemTrainingBinding
import com.example.transformapp.model.Training


class TrainingAdapter : RecyclerView.Adapter<TrainingViewHolder>() {

    private val trainings = mutableListOf<Training>()
    var onTrainingSelectedListener: OnTrainingEventListener? = null

    fun setTrainings(trainings: List<Training>) {
        this.trainings.clear()
        for (training in trainings){
            this.trainings.add(training)
            this.notifyDataSetChanged()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_training, parent, false)
        return TrainingViewHolder(view)

    }

    override fun onBindViewHolder(holder: TrainingViewHolder, position: Int) {
        val training = trainings[position]
        holder.bind(training)
        onTrainingSelectedListener?.let { listener ->
            holder.itemView.setOnClickListener{listener.onTrainingSelected(training.id)}
            holder.itemView.setOnLongClickListener { listener.onTrainingLongPress(training) }


        }
    }

    override fun getItemCount(): Int = trainings.count()
}


class TrainingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    fun bind(training: Training) {
        val binding = ItemTrainingBinding.bind(itemView)
        binding.itemTraining.text = training.trainingName
    }


}