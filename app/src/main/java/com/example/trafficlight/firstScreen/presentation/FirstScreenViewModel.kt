package com.example.trafficlight.firstScreen.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.trafficlight.firstScreen.presentation.events.FirstScreenEvent

class FirstScreenViewModel : ViewModel() {

    private val _state = MutableLiveData<FirstScreenState>()
    val state: LiveData<FirstScreenState> get() = _state

    init {
        _state.value = FirstScreenState()
    }

    fun onEvent(event: FirstScreenEvent) {
        when (event) {
            is FirstScreenEvent.CarModelTextChanged -> onCarModelTextChanged(event.model)
            is FirstScreenEvent.StartDrivingButtonClicked -> onStartDrivingButtonClicked()
        }
    }

    private fun onCarModelTextChanged(model: String) {
        if (model.trim().length < 3) {
            _state.value = state.value!!.copy(
                carModel = model,
                inputError = "Min character length is 3",
                error = null,
                navigateToSecondScreen = false
            )
        } else {
            _state.value = state.value!!.copy(
                carModel = model,
                inputError = null,
                error = null,
                navigateToSecondScreen = false
            )
        }
    }

    private fun onStartDrivingButtonClicked() {
        val carModel = state.value?.carModel ?: ""
        if (carModel.trim().length < 3) {
            _state.value = state.value!!.copy(
                error = "Min character length is 3"
            )
        } else {
            _state.value = state.value!!.copy(
                navigateToSecondScreen = true
            )
        }
    }
}