package com.pursan.dicoding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pursan.dicoding.about.About
import com.pursan.dicoding.academy.Academy
import com.pursan.dicoding.academy.AcademyAdapter
import com.pursan.dicoding.academy.AcademyData

class MainActivity : AppCompatActivity() {
    private lateinit var rvAcademy: RecyclerView
    private var list: ArrayList<Academy> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val actionbar = supportActionBar
        actionbar!!.title= "Academy"

        rvAcademy = findViewById(R.id.rv_academy)
        rvAcademy.setHasFixedSize(true)

        list.addAll(AcademyData.listData)
        showRecyclerList()
    }

    private fun showRecyclerList() {
        rvAcademy.layoutManager = LinearLayoutManager(this)
        val academyAdapter = AcademyAdapter(list)
        rvAcademy.adapter = academyAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        setMode(item.itemId)
        return super.onOptionsItemSelected(item)
    }

    private fun setMode(selectedMode: Int) {
        when (selectedMode) {
            R.id.miCompose -> {
                val iAbout = Intent(this@MainActivity,
                    About::class.java)
                startActivity(iAbout)
            }
        }
    }
}