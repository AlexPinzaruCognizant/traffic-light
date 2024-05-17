package com.example.trafficlight.firstScreen.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.trafficlight.firstScreen.presentation.events.FirstScreenEvent
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule


class FirstScreenViewModelTest {

    private lateinit var viewModel: FirstScreenViewModel

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        viewModel = FirstScreenViewModel()
    }

    @Test
    fun `GIVEN car model text input is changed, WHEN input text is empty THEN show inputError`() {
        // given
        val event = FirstScreenEvent.CarModelTextChanged("")

        // when
        viewModel.onEvent(event)

        // then
        Assert.assertEquals("Min character length is 3", viewModel.state.value!!.inputError)
    }

    @Test
    fun `GIVEN car model text input is changed, WHEN input text is less than three characters in length THEN show inputError`() {
        // given
        val event = FirstScreenEvent.CarModelTextChanged("AB")

        // when
        viewModel.onEvent(event)

        // then
        Assert.assertEquals("Min character length is 3", viewModel.state.value!!.inputError)
    }

    @Test
    fun `GIVEN car model text input is changed, WHEN input text is three characters in length THEN hide inputError`() {
        // given
        val event = FirstScreenEvent.CarModelTextChanged("ABC")

        // when
        viewModel.onEvent(event)

        // then
        Assert.assertNull(viewModel.state.value!!.inputError)
    }

    @Test
    fun `GIVEN car model text input is changed, WHEN input text is longer than three characters in length THEN hide inputError`() {
        // given
        val event = FirstScreenEvent.CarModelTextChanged("ABCDE")

        // when
        viewModel.onEvent(event)

        // then
        Assert.assertNull(viewModel.state.value!!.inputError)
    }

    @Test
    fun `GIVEN Start Driving button is clicked, WHEN input text is less than three characters in length THEN show error`() {
        // given
        val eventCarModelTextChanged = FirstScreenEvent.CarModelTextChanged("AB")
        viewModel.onEvent(eventCarModelTextChanged)
        val eventStartDrivingButtonClicked = FirstScreenEvent.StartDrivingButtonClicked

        // when
        viewModel.onEvent(eventStartDrivingButtonClicked)

        // then
        Assert.assertEquals("Min character length is 3", viewModel.state.value!!.error)
    }

    @Test
    fun `GIVEN Start Driving button is clicked, WHEN input text is three characters in length THEN hide error`() {
        // given
        val eventCarModelTextChanged = FirstScreenEvent.CarModelTextChanged("ABC")
        viewModel.onEvent(eventCarModelTextChanged)
        val eventStartDrivingButtonClicked = FirstScreenEvent.StartDrivingButtonClicked

        // when
        viewModel.onEvent(eventStartDrivingButtonClicked)

        // then
        Assert.assertNull(viewModel.state.value!!.error)
    }

    @Test
    fun `GIVEN Start Driving button is clicked, WHEN input text is longer than three characters in length THEN hide error`() {
        // given
        val eventCarModelTextChanged = FirstScreenEvent.CarModelTextChanged("ABCDE")
        viewModel.onEvent(eventCarModelTextChanged)
        val eventStartDrivingButtonClicked = FirstScreenEvent.StartDrivingButtonClicked

        // when
        viewModel.onEvent(eventStartDrivingButtonClicked)

        // then
        Assert.assertNull(viewModel.state.value!!.error)
    }

    @Test
    fun `GIVEN Start Driving button is clicked, WHEN input text is less than three characters in length THEN user does not navigate to second screen`() {
        // given
        val eventCarModelTextChanged = FirstScreenEvent.CarModelTextChanged("AB")
        viewModel.onEvent(eventCarModelTextChanged)
        val eventStartDrivingButtonClicked = FirstScreenEvent.StartDrivingButtonClicked

        // when
        viewModel.onEvent(eventStartDrivingButtonClicked)

        // then
        Assert.assertFalse(viewModel.state.value!!.navigateToSecondScreen)
    }

    @Test
    fun `GIVEN Start Driving button is clicked, WHEN input text is three characters in length THEN user navigates to second screen`() {
        // given
        val eventCarModelTextChanged = FirstScreenEvent.CarModelTextChanged("ABC")
        viewModel.onEvent(eventCarModelTextChanged)
        val eventStartDrivingButtonClicked = FirstScreenEvent.StartDrivingButtonClicked

        // when
        viewModel.onEvent(eventStartDrivingButtonClicked)

        // then
        Assert.assertTrue(viewModel.state.value!!.navigateToSecondScreen)
    }

    @Test
    fun `GIVEN Start Driving button is clicked, WHEN input text is longer than three characters in length THEN user navigates to second screen`() {
        // given
        val eventCarModelTextChanged = FirstScreenEvent.CarModelTextChanged("ABCDE")
        viewModel.onEvent(eventCarModelTextChanged)
        val eventStartDrivingButtonClicked = FirstScreenEvent.StartDrivingButtonClicked

        // when
        viewModel.onEvent(eventStartDrivingButtonClicked)

        // then
        Assert.assertTrue(viewModel.state.value!!.navigateToSecondScreen)
    }
}