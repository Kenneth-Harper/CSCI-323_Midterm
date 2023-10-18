package com.example.csci_323midterm

import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.csci_323midterm.databinding.FragmentGameBinding
import com.example.csci_323midterm.databinding.FragmentGameInputBinding

class GameInputFragment : Fragment() {
    private var _binding : FragmentGameInputBinding? = null
    private val binding get() = _binding!!

    val viewModel : GameViewModel by activityViewModels()

    private var updateNumber = false

    private lateinit var mediaPlayer : MediaPlayer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Set up the binding and viewModel
        _binding = FragmentGameInputBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        // Instantiate the mediaPlayer
        mediaPlayer = MediaPlayer.create(this.context, R.raw.buzz)

        // Create a secret number
        viewModel.createSecretNumber()

        // Set up the edittext for inputting the player name
        binding.edittextPlayerName.addTextChangedListener { text ->
            if (text.toString() != "")
            {
                viewModel.playerName = text.toString()
            }
        }

        // Set up the edittext for inputting the number guess
        binding.edittextNumberInput.addTextChangedListener { text ->
            if (text.toString() != "")
            {
                val s = text.toString().trim()
                viewModel.setCurrentGuess(s.toInt())
                updateNumber = true
            }
        }

        // Set up the input edittext to update should the user use the increase/decrease buttons
        viewModel.currentGuess.observe(viewLifecycleOwner, Observer {num ->
            if (num != null && updateNumber)
            {
                binding.edittextNumberInput.setText(num.toString())
                updateNumber = false
            }
        })

        // Create toast when there is a guess that isn't equal to secret number
        viewModel.guessLessThanSecret.observe(viewLifecycleOwner, Observer { bool ->
            if (bool != null)
            {
                mediaPlayer.start()
                if (bool)
                {
                    val text = "Your guess was lower than the number"
                    val duration = Toast.LENGTH_SHORT
                    val toast = Toast.makeText(this.context, text, duration)
                    toast.show()
                }
                else
                {
                    val text = "Your guess was higher than the number"
                    val duration = Toast.LENGTH_SHORT
                    val toast = Toast.makeText(this.context, text, duration)
                    toast.show()
                }

            }
        })

        // Setting up the imagebutton for decrementing the player guess
        binding.imagebuttonDecrement.setOnClickListener {
            viewModel.decreaseGuess()
            if (viewModel.currentGuess.value != null)
            {
                updateNumber = true
            }
        }

        // Setting up the imagebutton for incrementing the player guess
        binding.imagebuttonIncrement.setOnClickListener {
            viewModel.increaseGuess()
            if (viewModel.currentGuess.value != null)
            {
                updateNumber = true
            }
        }

        // Setting up submitting guess button
        binding.buttonSubmitGuess.setOnClickListener {
            viewModel.submitGuess()
        }

        return view
    }

    override fun onDestroy()
    {
        super.onDestroy()
        mediaPlayer.release()
        _binding = null
    }
}