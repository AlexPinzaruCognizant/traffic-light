package com.example.trafficlight.secondScreen.data.repository

import com.example.trafficlight.secondScreen.domain.model.TrafficLightState
import com.example.trafficlight.secondScreen.domain.repository.TrafficLightStateRepository
import io.reactivex.rxjava3.subjects.BehaviorSubject
import java.util.Timer
import java.util.TimerTask


class TrafficLightStateRepositoryImpl(
    private val timer: Timer = Timer(),
    private val trafficLightStateSubject: BehaviorSubject<TrafficLightState> = BehaviorSubject.create(),
    private var currentState: TrafficLightState = TrafficLightState.GREEN
) : TrafficLightStateRepository {


    override fun getCurrentTrafficLightState(): BehaviorSubject<TrafficLightState> {
        scheduleNextState(currentState)
        return trafficLightStateSubject
    }

    private fun scheduleNextState(state: TrafficLightState) {
        trafficLightStateSubject.onNext(state)
        timer.schedule(object : TimerTask() {
            override fun run() {
                scheduleNextState(getNextState(state))
            }
        }, getDelayByState(state))
    }

    private fun getDelayByState(state: TrafficLightState): Long {
        return when (state) {
            TrafficLightState.GREEN -> 4000L
            TrafficLightState.ORANGE -> 1000L
            TrafficLightState.RED -> 4000L
        }
    }

    private fun getNextState(state: TrafficLightState): TrafficLightState {
        return when (state) {
            TrafficLightState.GREEN -> TrafficLightState.ORANGE
            TrafficLightState.ORANGE -> TrafficLightState.RED
            TrafficLightState.RED -> TrafficLightState.GREEN
        }
    }
}