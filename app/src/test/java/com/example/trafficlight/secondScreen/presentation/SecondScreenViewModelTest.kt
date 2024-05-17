package com.example.trafficlight.secondScreen.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.trafficlight.common.RxImmediateSchedulerRule
import com.example.trafficlight.secondScreen.domain.model.TrafficLightState
import com.example.trafficlight.secondScreen.domain.repository.TrafficLightStateRepository
import com.example.trafficlight.secondScreen.presentation.events.SecondScreenEvent
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.reactivex.rxjava3.subjects.BehaviorSubject
import org.junit.Assert
import org.junit.Before
import org.junit.ClassRule
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class SecondScreenViewModelTest {

    private lateinit var viewModel: SecondScreenViewModel

    @MockK
    private lateinit var repository: TrafficLightStateRepository

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = SecondScreenViewModel(repository = repository)
    }


    @Test
    fun `GIVEN screen event is received, WHEN traffic lights are turned on THEN traffic light state changes`() {
        // given
        every { repository.getCurrentTrafficLightState() } returns
                BehaviorSubject.createDefault(TrafficLightState.GREEN)
        val event = SecondScreenEvent.TurnOnTrafficLights

        // when
        viewModel.onEvent(event)

        // then
        Assert.assertEquals(
            TrafficLightState.GREEN,
            viewModel.state.value!!.currentTrafficLightState
        )
    }

    companion object {
        @ClassRule
        @JvmField
        val schedulers = RxImmediateSchedulerRule()
    }
}