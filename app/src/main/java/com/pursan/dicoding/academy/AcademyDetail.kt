package com.pursan.dicoding.academy

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.pursan.dicoding.R

class AcademyDetail : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_detail_academy)
        val actionbar = supportActionBar
        actionbar!!.title = "Detail Academy"
        actionbar.setDisplayHomeAsUpEnabled(true)

        val tvSetName: TextView = findViewById(R.id.tv_set_name)
        val imgSetPhoto: ImageView = findViewById(R.id.img_item_photo)
        val tvSetIden: TextView = findViewById(R.id.tv_set_detail)
        val tvOverview: TextView = findViewById(R.id.tv_set_overview)
        val tvRate: TextView = findViewById(R.id.tv_item_rate)

        val tName  = intent.getStringExtra(EXTRA_NAME)
        val tImg = intent.getStringExtra(EXTRA_PHOTO)
        val tIDetail = intent.getStringExtra(EXTRA_DETAIL)
        val tOverview = intent.getStringExtra(EXTRA_OVERVIEW)
        val tRate = intent.getStringExtra(EXTRA_RATE)



        tvSetName.text = tName
        Glide.with(this)
            .load(tImg)
            .apply(RequestOptions())
            .into(imgSetPhoto)
        tvSetIden.text = tIDetail
        tvOverview.text = tOverview
        tvRate.text = tRate
    }

    companion object {
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_PHOTO = "extra_photo"
        const val EXTRA_DETAIL = "extra_detail"
        const val EXTRA_OVERVIEW = "extra_overview"
        const val EXTRA_RATE = "extra_rate"
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}