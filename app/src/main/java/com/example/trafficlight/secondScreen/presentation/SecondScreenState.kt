package com.example.trafficlight.secondScreen.presentation

import com.example.trafficlight.secondScreen.domain.model.TrafficLightState

data class SecondScreenState(
    val currentTrafficLightState: TrafficLightState? = null
)