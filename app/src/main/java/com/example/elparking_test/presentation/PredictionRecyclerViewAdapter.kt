package com.example.elparking_test.presentation


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.elparking_test.models.Prediction
import com.example.elparking_test.models.Response
import com.example.elparking_test.databinding.FragmentPredictionListItemBinding
import java.text.SimpleDateFormat
import java.util.*


class PredictionRecyclerViewAdapter(
    private val layoutId: Int,
    private val callback: ((Prediction) -> Unit)?) : RecyclerView.Adapter<PredictionRecyclerViewAdapter.ViewHolder>() {

    private var predictions: Array<Response>? = null

    inner class ViewHolder(private val binding: FragmentPredictionListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val predictionResponse = predictions?.get(position)!!
            val dateFormat = SimpleDateFormat("EEEE d 'de' MMMM 'a las' hh:mm", Locale("es", "ES"))
            val dateOfPrediction = Date(predictionResponse.risetime.toLong() * 1000)
            val formattedDateOfPrediction = dateFormat.format(dateOfPrediction)
            val currentDate = Calendar.getInstance().time;
            val prediction = Prediction(
                duration = predictionResponse.duration.toInt(),
                durationInMinutes = (predictionResponse.duration / 60).toInt(),
                durationInSeconds = (predictionResponse.duration % 60).toInt(),
                timeLeft = (dateOfPrediction.time - currentDate.time) /1000,
                dateOfPrediction = formattedDateOfPrediction
            )
            binding.prediction = prediction
            binding.executePendingBindings()
            binding.root.setOnClickListener { callback?.invoke(prediction) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: FragmentPredictionListItemBinding =
            DataBindingUtil.inflate(layoutInflater, viewType, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = predictions?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemViewType(position: Int): Int = getLayoutIdForPosition(position)

    private fun getLayoutIdForPosition(position: Int) = layoutId

    /**
     * Called whenever the list is updated.
     * Changes are being listened in the view.
     */
    fun update(newForecastList: Array<Response>) {
        predictions = newForecastList
        notifyDataSetChanged()
    }

}