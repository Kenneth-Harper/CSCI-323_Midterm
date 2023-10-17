package com.example.csci_323midterm

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.csci_323midterm.databinding.HighscoreItemBinding

class HighscoreItemAdapter (val deleteClickListener: (scoreId: Long) -> Unit)
    : ListAdapter<Highscore, HighscoreItemAdapter.HighscoreItemViewHolder>(HighscoreDiffItemCallback())
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : HighscoreItemViewHolder = HighscoreItemViewHolder.inflateFrom(parent)
    override fun onBindViewHolder(holder: HighscoreItemViewHolder, position: Int)
    {
        val item = getItem(position)
        holder.bind(item, deleteClickListener)
    }

    class HighscoreItemViewHolder(val binding: HighscoreItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
        companion object{
            fun inflateFrom(parent: ViewGroup) : HighscoreItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = HighscoreItemBinding.inflate(layoutInflater, parent, false)
                return HighscoreItemViewHolder(binding)
            }
        }

        fun bind(item: Highscore, deleteClickListener: (noteId: Long) -> Unit) {
            binding.highscore = item
            binding.imagebuttonDeletescore.setOnClickListener {deleteClickListener(item.scoreId)}
        }
    }
}