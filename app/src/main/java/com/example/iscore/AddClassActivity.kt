package com.example.iscore

import Model.Classroom
import Model.Student

import android.content.Intent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isEmpty
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_add_class.*

class AddClassActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_class)

        listener()
    }

    private fun listener() {
        addClassBackFAB.setOnClickListener {
            finish()
        }

        addClassBtn.setOnClickListener {
            val user = Firebase.auth.currentUser
            var classValid = true

            if (addClassNameTIL.isEmpty()) {
                addClassNameTIL.error = "Class Name is required."
                classValid = false
            } else {
                addClassDescTIL.error = ""
            }

            if (addClassDescTIL.isEmpty()) {
                addClassDescTIL.error = "Class Description is required."
                classValid = false
            } else {
                addClassDescTIL.error = ""
            }

            if (classValid) {
                saveData(user!!.uid, addClassNameTIL.editText!!.text.toString(), addClassDescTIL.editText!!.text.toString())
            }
        }
    }

    private fun saveData(uid: String, name: String, desc: String) {
        val database = Firebase.database
        val ref = database.getReference("users")
        var students: ArrayList<Student> = ArrayList()

        var idKey = ref.push().key.toString()

        var classroom = Classroom(idKey, name, desc, students)

        ref.child("users").child(uid).child("classes").child(idKey).setValue(classroom).addOnCompleteListener {
            Toast.makeText(applicationContext, "Class successfully added!", Toast.LENGTH_LONG).show()

            finish()
        }
    }
}