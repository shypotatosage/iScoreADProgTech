package com.example.iscore

import Database.GlobalVar
import Model.Score
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isEmpty
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_add_score.*

class AddScoreActivity : AppCompatActivity() {

    private var studentPosition: Int = -1
    private var classPosition: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_score)

        GetIntent()
        listener()
    }

    private fun GetIntent() {
        studentPosition  = intent.getIntExtra("Student Position", -1)
        classPosition  = intent.getIntExtra("Class Position", -1)
    }

    private fun listener() {
        addScoreBackFAB.setOnClickListener {
            finish()
        }

        addScoreBtn.setOnClickListener {
            val user = Firebase.auth.currentUser
            var scoreValid = true
            var score = addScoreScoreTIL.editText!!.text.toString().toInt()

            if(addScoreScoreTIL.isEmpty()) {
                addScoreScoreTIL.error = "Score is required."
                scoreValid = false
            } else if (score < 0 || score > 100) {
                addScoreScoreTIL.error = "Score must be between 0 - 100"
            } else {
                addScoreScoreTIL.error = ""
            }

            if (addScoreNameTIL.isEmpty()) {
                addScoreNameTIL.error = "Name is required."
                scoreValid = false
            } else {
                addScoreNameTIL.error = ""
            }

            if (scoreValid) {
                saveData(user!!.uid, GlobalVar.classArrayList[classPosition].students[studentPosition].id, addScoreNameTIL.editText!!.text.toString(), addScoreScoreTIL.editText!!.text.toString().toInt(), addScoreNoteTIL.editText!!.text.toString(), GlobalVar.classArrayList[classPosition].id)
            }
        }
    }

    private fun saveData(uid: String, sid: String, name: String, score: Int, note: String, cid: String) {
        val database = Firebase.database
        val ref = database.getReference("users")

        var idKey = ref.push().key.toString()

        var score = Score(idKey, name, score, note)

        ref.child("users").child(uid).child("classes").child(cid).child("students").child(sid).child("scores").child(idKey).setValue(score).addOnCompleteListener {
            Toast.makeText(applicationContext, "Score successfully added!", Toast.LENGTH_LONG).show()

            finish()
        }
    }
}