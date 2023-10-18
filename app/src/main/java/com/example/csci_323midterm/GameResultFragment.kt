package com.example.csci_323midterm

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.csci_323midterm.databinding.FragmentGameBinding
import com.example.csci_323midterm.databinding.FragmentGameResultBinding

class GameResultFragment : Fragment() {
    private var _binding : FragmentGameResultBinding? = null
    private val binding get() = _binding!!

    val viewModel : GameViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Set up the binding and viewModel
        _binding = FragmentGameResultBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        // Set textview to observe the viewModel's LiveData variable numberGuesses, to show how many guesses have been made
        viewModel.numberGuesses.observe(viewLifecycleOwner, Observer { num ->
            binding.textviewNumberGuesses.text = num.toString()
        })

        return view
    }

    override fun onDestroy()
    {
        super.onDestroy()
        _binding = null
    }
}