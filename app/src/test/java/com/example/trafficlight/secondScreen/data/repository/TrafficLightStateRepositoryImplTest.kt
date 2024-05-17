package com.example.trafficlight.secondScreen.data.repository

import com.example.trafficlight.secondScreen.data.repository.TrafficLightStateRepositoryImpl.Companion.DELAY_GREEN
import com.example.trafficlight.secondScreen.data.repository.TrafficLightStateRepositoryImpl.Companion.DELAY_ORANGE
import com.example.trafficlight.secondScreen.data.repository.TrafficLightStateRepositoryImpl.Companion.DELAY_RED
import com.example.trafficlight.secondScreen.domain.model.TrafficLightState
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.just
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.util.Timer

class TrafficLightStateRepositoryImplTest {

    private lateinit var repository: TrafficLightStateRepositoryImpl

    @MockK
    private lateinit var timer: Timer

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        repository = TrafficLightStateRepositoryImpl(timer = timer)
    }


    @Test
    fun `GIVEN no time has passed, WHEN current traffic light state is queried THEN green light state is returned`() {
        // given
        every { timer.schedule(any(), DELAY_GREEN) } just Runs

        // when
        repository.getCurrentTrafficLightState()

        // then
        Assert.assertEquals(TrafficLightState.GREEN, repository.trafficLightStateSubject.value)
    }


    @Test
    fun `GIVEN green traffic light state, WHEN delay by state is queried THEN green light state delay is returned`() {
        // given
        val state: TrafficLightState = TrafficLightState.GREEN

        // when
        val result = repository.getDelayByState(state)

        // then
        Assert.assertEquals(DELAY_GREEN, result)
    }

    @Test
    fun `GIVEN orange traffic light state, WHEN delay by state is queried THEN orange light state delay is returned`() {
        // given
        val state: TrafficLightState = TrafficLightState.ORANGE

        // when
        val result = repository.getDelayByState(state)

        // then
        Assert.assertEquals(DELAY_ORANGE, result)
    }

    @Test
    fun `GIVEN red traffic light state, WHEN delay by state is queried THEN red light state delay is returned`() {
        // given
        val state: TrafficLightState = TrafficLightState.RED

        // when
        val result = repository.getDelayByState(state)

        // then
        Assert.assertEquals(DELAY_RED, result)
    }

    @Test
    fun `GIVEN green traffic light state, WHEN next state is queried THEN orange light state is returned`() {
        // given
        val state: TrafficLightState = TrafficLightState.GREEN

        // when
        val result = repository.getNextState(state)

        // then
        Assert.assertEquals(TrafficLightState.ORANGE, result)
    }

    @Test
    fun `GIVEN orange traffic light state, WHEN next state is queried THEN red light state is returned`() {
        // given
        val state: TrafficLightState = TrafficLightState.ORANGE

        // when
        val result = repository.getNextState(state)

        // then
        Assert.assertEquals(TrafficLightState.RED, result)
    }

    @Test
    fun `GIVEN red traffic light state, WHEN next state is queried THEN green light state is returned`() {
        // given
        val state: TrafficLightState = TrafficLightState.RED

        // when
        val result = repository.getNextState(state)

        // then
        Assert.assertEquals(TrafficLightState.GREEN, result)
    }
}