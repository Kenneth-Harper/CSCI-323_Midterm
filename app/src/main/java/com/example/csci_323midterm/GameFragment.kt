package com.example.csci_323midterm

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.csci_323midterm.databinding.FragmentGameBinding

class GameFragment : Fragment() {
    private var _binding : FragmentGameBinding? = null
    private val binding get() = _binding!!

    val viewModel : GameViewModel by activityViewModels()
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

        // Adding navigation back to the home screen based on updating the navigateToHome liveData
        viewModel.navigateToHome.observe(viewLifecycleOwner, Observer { bool ->
            if (bool)
            {
                val action = GameFragmentDirections.actionGameFragmentToMainFragment()
                action.recentScore = "${viewModel.playerName} score: ${viewModel.numberGuesses.value}"
                this.findNavController().navigate(action)
            }
        })


        return view
    }

    override fun onDestroy()
    {
        super.onDestroy()
        _binding = null
    }
}