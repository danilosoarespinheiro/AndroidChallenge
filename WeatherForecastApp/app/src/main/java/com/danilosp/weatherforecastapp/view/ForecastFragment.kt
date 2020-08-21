package com.danilosp.weatherforecastapp.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.danilosp.weatherforecastapp.R
import com.danilosp.weatherforecastapp.viewmodel.ForecastViewModel
import kotlinx.android.synthetic.main.fragment_forecast.*
import java.util.*


class ForecastFragment : Fragment() {

    private val weatherAdapter: WeatherAdapter = WeatherAdapter(ArrayList())
    private lateinit var viewModel :ForecastViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.fragment_forecast, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this)[ForecastViewModel::class.java]

        forecastRecyclerView.layoutManager = LinearLayoutManager(context)
        forecastRecyclerView.adapter = weatherAdapter

        btnSearch.setOnClickListener {
            clickSearch()
        }

        observeViewModel()

    }

    private fun observeViewModel(){
        viewModel.weathers.observe(this, androidx.lifecycle.Observer {
            if(it !=null ){
                forecastRecyclerView.visibility = View.VISIBLE
                weatherAdapter.updateWeatherList(it)
            }
        })

        viewModel.weatherLoadError.observe(this, androidx.lifecycle.Observer {
            if(it != null){
                listEmptyMsg.visibility = if (it) View.VISIBLE else View.GONE
            }
        })

        viewModel.loading.observe(this, androidx.lifecycle.Observer {
            if(it != null){
                loadingView.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    listEmptyMsg.visibility = View.GONE
                    forecastRecyclerView.visibility = View.GONE
                }
            }
        })
    }

    fun clickSearch(){
        Log.d("Click","Click")
        if(edtCity.text.isEmpty()){
            edtCity.error = "That field must be filled"
        }else{
            viewModel.fetchFromServer(edtCity.text.toString())
        }
    }

}