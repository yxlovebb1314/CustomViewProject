package com.example.customviewproject.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.customviewproject.R
import com.example.customviewproject.adapter.HomeRvAdapter

class HomeActivity : AppCompatActivity() {

    private lateinit var recyclerView:RecyclerView
    private lateinit var adapter:HomeRvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initView()
        setListData()
        setListener()
    }

    private fun setListener() {
        adapter.setOnItemClickListener (object : HomeRvAdapter.OnItemClickListener {
            override fun onClick(position: Int) {
                when (position) {
                    0 -> goToActivity(DashBoard_Pie_Activity::class.java)
                    1 -> goToActivity(TextActivity::class.java)
                    2 -> goToActivity(AnimatorActivity::class.java)
                    3 -> goToActivity(MeterialEditTextActivity::class.java)
                    4 -> goToActivity(MeasureLayoutActivity::class.java)
                }
            }
        })
    }

    private fun <T> goToActivity(act: Class<T>) {
        startActivity(Intent(this, act))
    }

    private fun initView() {
        recyclerView = findViewById(R.id.rv_act_home_recyclerView)
    }

    private fun setListData() {
        adapter = HomeRvAdapter(this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }
}