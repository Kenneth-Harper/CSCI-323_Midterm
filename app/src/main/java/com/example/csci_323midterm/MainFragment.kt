package com.example.csci_323midterm

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
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

        return view
    }


}