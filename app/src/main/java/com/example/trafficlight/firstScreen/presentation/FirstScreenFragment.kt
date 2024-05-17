package com.example.trafficlight.firstScreen.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.trafficlight.databinding.FragmentFirstScreenBinding
import com.example.trafficlight.firstScreen.presentation.events.FirstScreenEvent


class FirstScreenFragment : Fragment() {

    private var _binding: FragmentFirstScreenBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FirstScreenViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        setupLayout()
    }

    private fun setupLayout() {
        binding.carModelEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                viewModel.onEvent(FirstScreenEvent.CarModelTextChanged(s.toString()))
            }
        })
        binding.startDrivingButton.setOnClickListener {
            viewModel.onEvent(FirstScreenEvent.StartDrivingButtonClicked)
        }

        viewModel.state.observe(viewLifecycleOwner) {
            updateScreenState(it)
        }
    }

    private fun updateScreenState(state: FirstScreenState) {
        state.inputError.let {
            binding.carModelInputLayout.error = it
        }
        state.error?.let {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
        if (state.navigateToSecondScreen) goToNextScreen(state.carModel)
    }

    private fun goToNextScreen(carModel: String) {
        val action = FirstScreenFragmentDirections.navigateToSecondScreen(carModel)
        findNavController().navigate(action)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}