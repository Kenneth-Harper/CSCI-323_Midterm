package com.example.csci_323midterm

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.csci_323midterm.databinding.FragmentGameBinding

class GameFragment : Fragment() {
    private var _binding : FragmentGameBinding? = null
    private val binding get() = _binding!!

    private val viewModel : GameViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Set up the binding and viewModel
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        // Adding the input game fragment to the view
        val inputFragmentTransaction = childFragmentManager.beginTransaction()
        val inputFragment = GameInputFragment()
        inputFragmentTransaction.replace(binding.frameInput.id, inputFragment)
        inputFragmentTransaction.commit()

        // Adding the result game fragment to the view
        val resultFragmentTransaction = childFragmentManager.beginTransaction()
        val resultFragment = GameResultFragment()
        resultFragmentTransaction.replace(binding.frameResult.id, resultFragment)
        resultFragmentTransaction.commit()


        return view
    }
}