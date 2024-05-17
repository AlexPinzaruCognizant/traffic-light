package com.example.trafficlight.secondScreen.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.trafficlight.common.applySchedulers
import com.example.trafficlight.secondScreen.data.repository.TrafficLightStateRepositoryImpl
import com.example.trafficlight.secondScreen.domain.model.TrafficLightState
import com.example.trafficlight.secondScreen.domain.repository.TrafficLightStateRepository
import com.example.trafficlight.secondScreen.presentation.events.SecondScreenEvent
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo

class SecondScreenViewModel(
    private val repository: TrafficLightStateRepository = TrafficLightStateRepositoryImpl(),
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
) : ViewModel() {

    private val _state = MutableLiveData<SecondScreenState>()
    val state: LiveData<SecondScreenState> get() = _state

    init {
        _state.value = SecondScreenState()
    }

    fun onEvent(event: SecondScreenEvent) {
        when (event) {
            is SecondScreenEvent.TurnOnTrafficLights -> turnOnTrafficLights()
        }
    }

    private fun turnOnTrafficLights() {
        repository.getCurrentTrafficLightState()
            .observeOn(AndroidSchedulers.mainThread())
            .applySchedulers()
            .subscribe(
                { onStateChanged(it) },
                { onError(it) }
            )
            .addTo(compositeDisposable)
    }

    private fun onStateChanged(newState: TrafficLightState) {
        _state.value = state.value!!.copy(
            currentTrafficLightState = newState
        )
    }

    private fun onError(error: Throwable) {
        // Empty
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}