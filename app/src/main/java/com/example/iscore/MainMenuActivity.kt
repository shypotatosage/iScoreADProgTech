package com.example.iscore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main_menu.*
import kotlinx.android.synthetic.main.card_studentlist.*

class MainMenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        listener()
    }

    private fun listener() {
        updateprofile_button.setOnClickListener {
            val myIntent = Intent(this, UpdateProfileActivity::class.java)

            startActivity(myIntent)
        }

        viewclasslist_button.setOnClickListener {
            val myIntent = Intent(this, ClassListActivity::class.java)

            startActivity(myIntent)
        }

        viewstudentlist_button.setOnClickListener {
            val myIntent = Intent(this, StudentListActivity::class.java)

            startActivity(myIntent)
        }
    }
}