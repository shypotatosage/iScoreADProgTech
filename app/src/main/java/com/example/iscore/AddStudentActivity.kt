package com.example.iscore

import Database.GlobalVar
import Model.Classroom
import Model.Score
import Model.Student
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.core.view.isEmpty
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_add_class.*
import kotlinx.android.synthetic.main.activity_add_student.*
import kotlinx.android.synthetic.main.activity_student_list.*

class AddStudentActivity : AppCompatActivity() {

    private var position: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)

        listener()
GetIntent()
    }

    private fun GetIntent() {
        position  = intent.getIntExtra("Position", -1)
}

    private fun listener() {
        addStudentBackFAB.setOnClickListener {
            finish()
        }

        addStudentBtn.setOnClickListener {
            var studentValid = true
            val user = Firebase.auth.currentUser

            if(addStudentNameTIL.isEmpty()) {
                addStudentNameTIL.error = "Name is required."
                studentValid = false
            } else {
                addStudentNameTIL.error = ""
            }

            if (addStudentAddressTIL.isEmpty()) {
                addStudentAddressTIL.error = "Address is required."
                studentValid = false
            } else {
                addStudentAddressTIL.error = ""
            }

            if (addStudentPhoneNumberTIL.isEmpty()) {
                addStudentPhoneNumberTIL.error = "Phone Number is required"
                studentValid = false
            } else {
                addStudentPhoneNumberTIL.error = ""
            }

            if (studentValid) {
                saveData(user!!.uid, addStudentNameTIL.editText!!.text.toString(), addStudentAddressTIL.editText!!.text.toString(), addStudentPhoneNumberTIL.editText!!.text.toString(), GlobalVar.classArrayList[position].id)
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