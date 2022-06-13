package com.example.iscore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_add_score.*
import kotlinx.android.synthetic.main.activity_individual_student.*
import kotlinx.android.synthetic.main.activity_register.*

class AddScoreActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_score)

        listener()
    }

    private fun listener() {
        addScoreBackFAB.setOnClickListener {
            finish()
        }

        addScoreBtn.setOnClickListener {

        }
    }
}