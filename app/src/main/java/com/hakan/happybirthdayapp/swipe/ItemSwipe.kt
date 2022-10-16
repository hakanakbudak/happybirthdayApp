package com.hakan.happybirthdayapp.swipe

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ItemTouchHelper


class ItemSwipe() :
/**
 * @author Hakan Akbudak
 * @since 26.08.22
 * @param ItemTouchHelper rcyclerview right swipe and left swipe
 *  */
    ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        onSwipeListener?.let {
            it(viewHolder.absoluteAdapterPosition)
        }
    }


    private var onSwipeListener: ((Int) -> Unit)? = null
    fun setOnSwipeListener(f: (Int) -> Unit) {
        onSwipeListener = f
    }

}