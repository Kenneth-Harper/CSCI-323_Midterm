package com.example.csci_323midterm

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.csci_323midterm.databinding.FragmentGameBinding
import com.example.csci_323midterm.databinding.FragmentGameResultBinding

class GameResultFragment : Fragment() {
    private var _binding : FragmentGameResultBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel : GameViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Set up the binding and viewModel
        _binding = FragmentGameResultBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel = ViewModelProvider(requireActivity()).get(GameViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return view
    }
}