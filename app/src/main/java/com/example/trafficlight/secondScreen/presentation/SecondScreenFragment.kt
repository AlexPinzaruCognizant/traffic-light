package com.example.trafficlight.secondScreen.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.trafficlight.R
import com.example.trafficlight.common.hideKeyboard
import com.example.trafficlight.databinding.FragmentSecondScreenBinding
import com.example.trafficlight.secondScreen.domain.model.TrafficLightState
import com.example.trafficlight.secondScreen.presentation.events.SecondScreenEvent

class SecondScreenFragment : Fragment() {

    private var _binding: FragmentSecondScreenBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SecondScreenViewModel by viewModels()
    private val args: SecondScreenFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.carModelText.text = getString(R.string.text_car_model, args.carModel)
        viewModel.state.observe(viewLifecycleOwner) {
            updateScreenState(it)
        }
        viewModel.onEvent(SecondScreenEvent.TurnOnTrafficLights)
    }

    private fun updateScreenState(state: SecondScreenState) {
        state.currentTrafficLightState?.let {
            when(it) {
                TrafficLightState.RED -> binding.redLight.requestFocus()
                TrafficLightState.ORANGE -> binding.orangeLight.requestFocus()
                TrafficLightState.GREEN -> binding.greenLight.requestFocus()
            }
            hideKeyboard()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}