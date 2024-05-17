package com.example.trafficlight.firstScreen.presentation

data class FirstScreenState(
    val carModel: String = "",
    val inputError: String? = null,
    val error: String? = null,
    val navigateToSecondScreen: Boolean = false,
)