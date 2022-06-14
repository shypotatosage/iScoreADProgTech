package com.example.iscore

import Database.GlobalVar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_edit_score.*
import kotlinx.android.synthetic.main.activity_edit_student.*
import kotlinx.android.synthetic.main.activity_individual_student.*

class EditStudentActivity : AppCompatActivity() {

    private var classPosition: Int = -1
    private var studentPosition: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_student)

        GetIntent()
        listener()

    }
    private fun GetIntent() {
        classPosition  = intent.getIntExtra("Class Position", -1)
        studentPosition  = intent.getIntExtra("Student Position", -1)
    }

    private fun listener() {
        editStudentBackFAB.setOnClickListener {
            finish()
        }
        editStudentbutton.setOnClickListener {
            val name = editStudentNameTIL.editText?.text.toString().trim();
            val address = editStudentAddressTIL.editText?.text.toString().trim();
            val phone = editStudentNoTelpTIL.editText?.text.toString().trim();

            val user = FirebaseAuth.getInstance().currentUser
            user?.let {
                // Name, email address, and profile photo Url
                val uid = user.uid
                //Tunggu mimi confirm
                val cid = GlobalVar.classArrayList[classPosition].id
                val sid = GlobalVar.classArrayList[classPosition].students[studentPosition].id
//                Toast.makeText(this,cid, Toast.LENGTH_SHORT).show()
                val myRef = FirebaseDatabase.getInstance().getReference("users")
                val student =  mapOf<String,String>(
                    "id" to sid,
                    "address" to address,
                    "name" to name,
                    "phoneNumber" to phone,
                )
                myRef.child("users").child(uid).child("classes").child(cid).child("students").child(sid).child("address").setValue(address)
                myRef.child("users").child(uid).child("classes").child(cid).child("students").child(sid).child("phoneNumber").setValue(phone)
                myRef.child("users").child(uid).child("classes").child(cid).child("students").child(sid).child("name").setValue(name).addOnSuccessListener {
                    Toast.makeText(this,"Data Updated", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }
}