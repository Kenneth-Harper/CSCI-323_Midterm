package com.example.csci_323midterm

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver



class EmptyHighscoreObserver (private val recyclerView: RecyclerView, private val emptyView: View?):
    AdapterDataObserver()
    {
        init
        {
            checkIfEmpty()
        }

        /**
         * <h1> checkIfEmpty </h1>
         *      A function that changes what input displays are visible given if the recyclerView is empty
         */
        private fun checkIfEmpty()
        {
            if (emptyView != null && recyclerView.adapter != null)
            {
                val emptyViewVisible = recyclerView.adapter!!.itemCount == 0
                emptyView.visibility = if (emptyViewVisible) View.VISIBLE else View.GONE
                recyclerView.visibility = if (emptyViewVisible) View.GONE else View.VISIBLE
            }
        }

        /**
         * <h1> onChanged </h1>
         *      Override of an abstract method for AdapterDataObserver, which will simply call checkIfEmpty
         */
        override fun onChanged() { checkIfEmpty() }
        /**
         * <h1> onItemRangeInserted </h1>
         *      Override of an abstract method for AdapterDataObserver, which will simply call checkIfEmpty
         */
        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) { checkIfEmpty() }
        /**
         * <h1> onItemRangeRemoved </h1>
         *      Override of an abstract method for AdapterDataObserver, which will simply call checkIfEmpty
         */
        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) { checkIfEmpty() }

    }