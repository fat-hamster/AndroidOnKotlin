package com.dmgpersonal.androidonkotlin.viewmodel

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.dmgpersonal.androidonkotlin.R
import com.dmgpersonal.androidonkotlin.model.Weather

class CitiesFragmentAdapter :
    RecyclerView.Adapter<CitiesFragmentAdapter.CitiesViewHolder>() {

    private var weatherData: List<Weather> = listOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setWeather(data: List<Weather>) {
        weatherData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) : CitiesViewHolder {
        return CitiesViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_cities_list_recycler_item, parent, false)
        as View) }

    override fun onBindViewHolder(holder: CitiesViewHolder, position: Int) {
        holder.bind(weatherData[position])
    }

    override fun getItemCount(): Int { return weatherData.size }

    inner class CitiesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(weather: Weather) {
            itemView.findViewById<TextView>(R.id.mainFragmentRecyclerItemTextView).text =
                weather.city.name
            itemView.setOnClickListener {
                Toast.makeText(itemView.context, weather.city.name, Toast.LENGTH_LONG).show()
            }
        }
    }
}