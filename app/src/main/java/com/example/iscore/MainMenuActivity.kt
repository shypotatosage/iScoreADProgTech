package com.example.iscore

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main_menu.*
import kotlinx.android.synthetic.main.activity_student_list.*


class MainMenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            // Name, email address, and profile photo Url
            val uid = user.uid
            val database = Firebase.database
            val myRef = database.getReference("users")

            val ordersRef = myRef.child("users").child(uid)
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val username = dataSnapshot.child("username").getValue()
//                untuk mengambil data didalam data
                val classcount = dataSnapshot.child("classes").childrenCount
                val studentcount = dataSnapshot.child("student").childrenCount
                   Log.d("DataBaseGetName", username.toString())

                    hellomainmenu_textView.setText("Hello, " + username)
                    classavailable_textView.setText(classcount.toString() + " Class Available")
                    studentavailable_textView.setText(studentcount.toString()+ " Students Available")
            }
            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("Data", databaseError.getMessage()) //Don't ignore errors!
            }
        }
   ordersRef.addValueEventListener(valueEventListener)
        }

        Listener()
    }

    private fun Listener(){
        mainMenuFAB.setOnClickListener {
            val myIntent = Intent(this, LoginActivity::class.java)
            startActivity(myIntent)
        }
        updateprofile_button.setOnClickListener {
            val myIntent = Intent(this, UpdateProfileActivity::class.java)

            startActivity(myIntent)
        }

        viewclasslist_button.setOnClickListener {
            val myIntent = Intent(this, ClassListActivity::class.java)

            startActivity(myIntent)
        }

        studentlist_button.setOnClickListener {
            val myIntent = Intent(this, StudentListActivity::class.java)

            startActivity(myIntent)
        }
    }
}