package com.example.trafficlight.firstScreen.presentation.events

sealed class FirstScreenEvent {

    class CarModelTextChanged(val model: String) : FirstScreenEvent()
    data object StartDrivingButtonClicked : FirstScreenEvent()
}