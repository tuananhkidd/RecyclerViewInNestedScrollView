package com.example.mykotlinapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.example.kotlinapplication.EndlessLoadingRecyclerViewAdapter
import com.example.kotlinapplication.RecyclerViewAdapter
import com.example.kotlinapplication.Test
import com.example.kotlinapplication.TestAdapter
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.ScrollView


class MainActivity : AppCompatActivity(), EndlessLoadingRecyclerViewAdapter.OnLoadingMoreListener, RecyclerViewAdapter.OnItemClickListener {

    var adapter: TestAdapter? = null
    var isLoading = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        adapter = TestAdapter(this)
        adapter!!.addModels(fakeData(), false)
        adapter!!.addOnItemClickListener(this)
        adapter!!.setLoadingMoreListener(this)
//        val manager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
//        adapter?.setLayoutManagerRecycler(manager)
        rcv_test.adapter = adapter

//        swipe_refresh.setColorSchemeResources(R.color.colorAccent);

//        rcv_test.layoutManager = manager
//        rcv_test.setViewParent(nsv)

//        btn_create.setOnClickListener{
//            adapter!!.addModels(fakeData(), false)
//        }
//
//        btn_clear.setOnClickListener{
//            adapter!!.clear()
//        }

//        nsv.setOnScrollChangeListener(object : NestedScrollView.OnScrollChangeListener {
//            override fun onScrollChange(v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int) {
//                v?.let {
//                    if (v.getChildAt(v.childCount - 1) != null) {
//                        if (scrollY > oldScrollY) {
//                            if (nsv.canScrollVertically(1)) {
//                                onLoadMore()
//                            }
//                        }
//                    }
//                }
//            }
//        })

        nsv.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (v.getChildAt(v.childCount - 1) != null) {
                if (scrollY >= v.getChildAt(v.childCount - 1).measuredHeight - v.measuredHeight && scrollY > oldScrollY) {

                    if (isLoading) {
                        return@OnScrollChangeListener
                    }
                    val visibleItemCount = rcv_test.layoutManager?.childCount!!
                    val totalItemCount = rcv_test.layoutManager?.itemCount!!
                    val pastVisiblesItems = (rcv_test.layoutManager as LinearLayoutManager)
                            .findFirstVisibleItemPosition()

                    if (!isLoading) {
                        isLoading = true
                        if (visibleItemCount + pastVisiblesItems >= totalItemCount) {
                            onLoadMore()
                        }
                    }
                }
            }
        })

    }

    override fun onLoadMore() {
        adapter!!.showLoadingItem(true)
        nsv.smoothScrollTo(0, nsv.bottom)
        nsv.post { nsv.fullScroll(ScrollView.FOCUS_DOWN) }
        Handler().postDelayed(Runnable {
            adapter!!.setIsLoading(true)
            adapter!!.hideLoadingItem()
            adapter!!.addModels(fakeData(), false)
            isLoading = false
        }, 1000)
    }

    fun fakeData(): ArrayList<Test> {
        var data: ArrayList<Test> = ArrayList()
        for (i in 0..15) {
            data.add(Test("Test ${i}"))
        }
        return data
    }

    override fun onItemClick(
            adapter1: RecyclerView.Adapter<*>,
            viewHolder: RecyclerView.ViewHolder?,
            viewType: Int,
            position: Int
    ) {
        val test: Test? = adapter!!.getItem(position, Test::class.java)
        Toast.makeText(applicationContext, test?.getName(), Toast.LENGTH_LONG).show()
    }
}
