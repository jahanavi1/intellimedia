package com.jahanavi.practical.activities

import android.os.Bundle
import com.jahanavi.practical.BindingActivity
import com.jahanavi.practical.R
import com.jahanavi.practical.databinding.ActivityMainBinding


class MainActivity() : BindingActivity<ActivityMainBinding>() {
    override fun getLayoutResId() = R.layout.activity_main
    lateinit var adapter: SearchAdapter
    var data:MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpRecyclerView()

        mBinding.ivPlus.setOnClickListener {
            adapter.addItem("asbdahsd")
            adapter.addItems(data)
            adapter.notifyDataSetChanged()
        }
    }

    fun setUpRecyclerView(){
        adapter = SearchAdapter(this)
        mBinding.rvListData.adapter = adapter
    }
}


