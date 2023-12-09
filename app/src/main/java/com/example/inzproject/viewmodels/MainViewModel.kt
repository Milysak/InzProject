package com.example.inzproject.viewmodels

import androidx.lifecycle.ViewModel
import com.example.inzproject.domain.repository.WeatherRepository
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {
    val mapViewModel = MapViewModel(repository = repository)
}