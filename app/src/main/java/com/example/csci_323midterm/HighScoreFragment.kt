package com.example.csci_323midterm

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.csci_323midterm.databinding.FragmentHighScoreBinding
import com.example.csci_323midterm.databinding.FragmentMainBinding


class HighScoreFragment : Fragment() {

    private var _binding : FragmentHighScoreBinding? = null
    private val binding get() = _binding!!
    val viewModel : GameViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        _binding = FragmentHighScoreBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.getScores()

        fun yesPressed(noteId : Long)
        {
            binding.viewModel?.deleteScore(noteId)
        }

        fun deleteClicked(noteId: Long)
        {
            ConfirmDeleteDialogFragment(noteId,::yesPressed).show(childFragmentManager, ConfirmDeleteDialogFragment.TAG)
        }

        val adapter = HighscoreItemAdapter(::deleteClicked)
        binding.recyclerviewHighscoreList.adapter = adapter

        viewModel.scores.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        binding.buttonStartScreen.setOnClickListener {
            val action = HighScoreFragmentDirections.actionHighScoreFragmentToMainFragment()
            action.recentScore = "null"
            this.findNavController().navigate(action)
        }

        return view
    }

    override fun onDestroy()
    {
        super.onDestroy()
        _binding = null
    }
}