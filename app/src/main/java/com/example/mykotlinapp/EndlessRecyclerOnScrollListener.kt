package com.example.mykotlinapp

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView


internal abstract class EndlessRecyclerOnScrollListener(private val mLayoutManager: RecyclerView.LayoutManager) : RecyclerView.OnScrollListener() {
    private var previousTotal = 0 // The total number of items in the data set after the last load
    private var loading = true // True if we are still waiting for the last set of data to load.
    private var firstVisibleItem: Int = 0
    private var visibleItemCount: Int = 0
    private var totalItemCount: Int = 0
    private var mDisable = false

    abstract fun onLoadMore()

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        visibleItemCount = recyclerView!!.childCount
        totalItemCount = mLayoutManager.itemCount
        if (mLayoutManager is LinearLayoutManager) {
            firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition()
        } else if (mLayoutManager is GridLayoutManager) {
            firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition()
        }

        if (loading) {
            if (totalItemCount > previousTotal + 1) {
                loading = false
                previousTotal = totalItemCount
            }
        }
        if (!mDisable && !loading && totalItemCount - visibleItemCount <= firstVisibleItem + VISIBLE_THRESHOLD) {
            // End has been reached. Do something
            onLoadMore()
            loading = true
        }
    }

    fun reset() {
        previousTotal = 0
        loading = true
        firstVisibleItem = 0
        visibleItemCount = 0
        totalItemCount = 0
        mDisable = false
    }

    fun disable() {
        mDisable = true
    }

    companion object {
        private val VISIBLE_THRESHOLD = 0 // The minimum amount of items to have below your current scroll position before loading more.
    }
}