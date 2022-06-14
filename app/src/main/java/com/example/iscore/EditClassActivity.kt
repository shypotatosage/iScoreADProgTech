package com.example.iscore

import Database.GlobalVar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_edit_class.*
import kotlinx.android.synthetic.main.activity_update_profile.*

class EditClassActivity : AppCompatActivity() {

    private var position: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_class)

        listener()
    }

    private fun listener() {
        editClassBtn.setOnClickListener {
            val name = editClassNameTIL.editText?.text.toString().trim();
            val desc = editClassDescTIL.editText?.text.toString().trim();

            val user = FirebaseAuth.getInstance().currentUser
            user?.let {
                // Name, email address, and profile photo Url
                val uid = user.uid
              val database = Firebase.database
                val cid = GlobalVar.classArrayList[position].id
                val myRef = FirebaseDatabase.getInstance().getReference("users").child(uid).child("classes").child(cid)
                val user =  mapOf<String,String>(
                    "uid" to uid,
                    "desc" to desc,
                    "name" to name,
                )
                myRef.child("users").child(uid).child("desc").setValue(desc)
                myRef.child("users").child(uid).child("name").setValue(name).addOnSuccessListener {
                    Toast.makeText(this,"Data Updated", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }
}