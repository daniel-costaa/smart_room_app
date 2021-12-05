package com.example.smartroom.ui

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.smartroom.R
import com.example.smartroom.common.Resource
import com.example.smartroom.databinding.MainFragmentBinding
import com.example.smartroom.viewmodel.MainViewModel
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class MainFragment : Fragment(R.layout.main_fragment) {
    private lateinit var binding: MainFragmentBinding
    private val mainViewModel = MainViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = MainFragmentBinding.bind(view)
        binding.lineChartTemperature.setTouchEnabled(true)
        binding.lineChartTemperature.setPinchZoom(true)

        initiaLizeLineChart(binding.lineChartLuminosity)
        initiaLizeLineChart(binding.lineChartTemperature, true)
        initiaLizeLineChart(binding.lineChartUmidity)

        observeData()
    }

    private fun initiaLizeLineChart(chart: LineChart, isTemperatureChart: Boolean = false) {
        val xAxis = chart.xAxis
        val yAxis = chart.axisLeft
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)

        yAxis.axisMaximum = if (isTemperatureChart) {
            40.toFloat()
        } else {
            100.toFloat()
        }

        yAxis.axisMinimum = 0.toFloat()


        xAxis.position = XAxis.XAxisPosition.BOTTOM_INSIDE
        chart.axisLeft.setDrawGridLines(false)
        chart.axisRight.isEnabled = false
        chart.legend.isEnabled = false
        chart.description.isEnabled = false
    }

    private fun observeData() {
        observeTemperature()
        observeLuminosity()
        observeUmidity()
        observeUmidityGraph()
        observeTemperatureGraph()
        observeLuminosityGraph()
    }

    private fun observeUmidityGraph() {
        lifecycleScope.launch {
            mainViewModel.umidityDataSet.collect { dataset ->
                initializeDataset(dataset)
                binding.lineChartUmidity.data = LineData(dataset)
                binding.lineChartUmidity.invalidate()
            }
        }
    }

    private fun initializeDataset(dataset: LineDataSet) {
        dataset.setDrawCircleHole(true)
        dataset.setCircleColor(Color.BLACK)
        dataset.color = Color.BLACK
        dataset.valueTextColor = Color.GRAY
        dataset.highLightColor = Color.RED
        dataset.setDrawValues(false)
        dataset.lineWidth = 1.5f
        dataset.isHighlightEnabled = true
        dataset.setDrawHighlightIndicators(false)
    }

    private fun observeTemperatureGraph() {
        lifecycleScope.launch {
            mainViewModel.temperatureDataSet.collect { dataset ->
                initializeDataset(dataset)
                binding.lineChartTemperature.data = LineData(dataset)
                binding.lineChartTemperature.invalidate()
            }
        }
    }

    private fun observeLuminosityGraph() {
        lifecycleScope.launch {
            mainViewModel.luminosityDataSet.collect { dataset ->
                initializeDataset(dataset)
                binding.lineChartLuminosity.data = LineData(dataset)
                binding.lineChartLuminosity.invalidate()
            }
        }
    }


    private fun observeUmidity() {
        lifecycleScope.launch {
            mainViewModel.umidityData.collect { state ->
                when (state) {
                    is Resource.Success -> {
                        binding.umidityData.text = state.data.toString()
                    }
                    is Resource.Loading -> binding.umidityData.text = "..."
                    is Resource.Failed -> showErrorMsg(state)
                }
            }
        }
    }

    private fun observeLuminosity() {
        lifecycleScope.launch {
            mainViewModel.luminosityData.collect { state ->
                when (state) {
                    is Resource.Success -> {
                        binding.luminosityData.text = state.data.toString()
                    }
                    is Resource.Loading -> binding.luminosityData.text = "..."
                    is Resource.Failed -> showErrorMsg(state)
                }
            }
        }
    }

    private fun observeTemperature() {
        lifecycleScope.launch {
            mainViewModel.temperatureData.collect { state ->
                when (state) {
                    is Resource.Success -> {
                        binding.temperatureData.text = state.data.toString()
                    }
                    is Resource.Loading -> binding.temperatureData.text = "..."
                    is Resource.Failed -> showErrorMsg(state)
                }
            }
        }
    }

    private fun showErrorMsg(state: Resource.Failed<Double>) {
        Toast.makeText(context, state.message, Toast.LENGTH_LONG).show()
    }
}