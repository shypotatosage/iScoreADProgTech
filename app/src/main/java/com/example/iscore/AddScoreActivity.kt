package com.example.iscore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_add_score.*

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
//                saveData(user!!.uid, addStudentNameTIL.editText!!.text.toString(), addStudentAddressTIL.editText!!.text.toString(), addStudentPhoneNumberTIL.editText!!.text.toString(), GlobalVar.classArrayList[position].id)
            }
        }
    }

    private fun saveData(uid: String, name: String, address: String, phoneNum: String, cid: String) {
        val database = Firebase.database
        val ref = database.getReference("users")
        var scores: ArrayList<Score> = ArrayList()

        var idKey = ref.push().key.toString()

        var student = Student(idKey, name, scores, address, phoneNum)

        ref.child("users").child(uid).child("classes").child(cid).child("students").child(idKey).setValue(student).addOnCompleteListener {
            Toast.makeText(applicationContext, "Student successfully added!", Toast.LENGTH_LONG).show()

            finish()
        }
    }
}