package com.example.iscore

import Database.GlobalVar
import Model.Score
import Model.Student
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isEmpty
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_add_score.*
import kotlinx.android.synthetic.main.activity_add_student.*
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
            var scoreValid = true
            var score = addScoreScoreTIL.editText!!.text.toString().toInt()
            val user = Firebase.auth.currentUser

            if(addScoreScoreTIL.isEmpty()) {
                addScoreScoreTIL.error = "Name is required."
                scoreValid = false
            } else if (score < 0 || score > 100) {
                addScoreScoreTIL.error = ""
            }

            if (addScoreNameTIL.isEmpty()) {
                addStudentAddressTIL.error = "Address is required."
                scoreValid = false
            } else {
                addStudentAddressTIL.error = ""
            }

            if (addStudentPhoneNumberTIL.isEmpty()) {
                addStudentPhoneNumberTIL.error = "Phone Number is required"
                scoreValid = false
            } else {
                addStudentPhoneNumberTIL.error = ""
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