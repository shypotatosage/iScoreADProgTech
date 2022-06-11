package com.example.iscore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        splash.animate().setDuration(3000).alpha(1f).withEndAction {
            val a = Intent(this, RegisterActivity::class.java)

            startActivity(a)
            overridePendingTransition(R.anim.none, R.anim.slide_left_out)
            finish()
        }
    }
}