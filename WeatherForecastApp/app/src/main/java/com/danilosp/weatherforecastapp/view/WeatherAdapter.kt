package com.danilosp.weatherforecastapp.view

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.danilosp.weatherforecastapp.R
import com.danilosp.weatherforecastapp.databinding.ItemWeatherBinding
import com.danilosp.weatherforecastapp.model.WeatherDetail
import java.util.*

class WeatherAdapter(todoList: ArrayList<WeatherDetail>) : RecyclerView.Adapter<WeatherAdapter.TodoViewHolder>() {

    private val todoList: ArrayList<WeatherDetail> = todoList

    fun updateWeatherList(newWeatherList: List<WeatherDetail>) {
        todoList.clear()
        todoList.addAll(newWeatherList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TodoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: ItemWeatherBinding =
            DataBindingUtil.inflate(inflater, R.layout.item_weather, parent, false)
        return TodoViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: TodoViewHolder, position: Int ) {
        holder.itemview.weather = todoList[position]
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    class TodoViewHolder(
        itemView: ItemWeatherBinding
    ) :
        RecyclerView.ViewHolder(itemView.root) {
        var itemview: ItemWeatherBinding = itemView

    }

}

