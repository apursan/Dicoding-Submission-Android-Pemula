package com.pursan.dicoding

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        val background = object : Thread(){
            override fun run(){
                try {
                    Thread.sleep(3000)

                    val intent =Intent(baseContext, MainActivity::class.java)
                    startActivity(intent)
                } catch (e: Exception){
                    e.printStackTrace()
                }
            }
        }
        background.start()
    }
}