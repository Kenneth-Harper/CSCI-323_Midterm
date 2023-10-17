package com.example.csci_323midterm

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.csci_323midterm.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private var _binding : FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Set up the binding and viewModel
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val view = binding.root
        val viewModelFactory = MainFragmentViewModelFactory()
        val viewModel = ViewModelProvider(this, viewModelFactory).get(MainFragmentViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        // Sets the lastScore of the viewModel based on arguments received through SafeArgs
        viewModel.setLastScore(MainFragmentArgs.fromBundle(requireArguments()).recentScore)

        // Sets the viewModel to observe the viewModels lastScore variable
        viewModel.lastScore.observe(viewLifecycleOwner, Observer{score ->
            val newText = "$score Wanna play again?"
            binding.textviewMain.text = newText
        })

        // Set the play game button to navigate to the game fragment
        binding.buttonPlayGame.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToGameFragment()
            this.findNavController().navigate(action)
        }
        // Set the highscore button to navigate to the highscore fragment
        binding.buttonViewHighScores.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToHighScoreFragment()
            this.findNavController().navigate(action)
        }

        return view
    }


}