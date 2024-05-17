package com.example.trafficlight.secondScreen.domain.repository

import com.example.trafficlight.secondScreen.domain.model.TrafficLightState
import io.reactivex.rxjava3.subjects.BehaviorSubject

interface TrafficLightStateRepository {

    fun getCurrentTrafficLightState(): BehaviorSubject<TrafficLightState>
}