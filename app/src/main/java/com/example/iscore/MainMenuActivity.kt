package com.example.iscore

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_main_menu.*
import java.io.File
import java.io.IOException
import java.util.*


class MainMenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        Listener()
        check()
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

        deleteaccount_button.setOnClickListener {
            val user = FirebaseAuth.getInstance().currentUser
            user?.let {
                // Name, email address, and profile photo Url
                val uid = user.uid
                val database = Firebase.database
                val myRef = database.getReference("users")
                myRef.child("users").child(uid).removeValue()
                user.delete()
            }
            val myIntent = Intent(this, LoginActivity::class.java)

            startActivity(myIntent)
        }
    }



    private fun check(){
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
                    Log.d("DataBaseGetName", username.toString())

                    hellomainmenu_textView.setText("Hello, " + username)
                    classavailable_textView.setText(classcount.toString() + " Class Available")
//                    myRef.child("users").child(uid).child("image").setValue(filename)
                    val imageName = dataSnapshot.child("image").getValue()
                    val storageRef = FirebaseStorage.getInstance().reference.child("images/$imageName")
                    val localfile = File.createTempFile("tempImage", "jpg")
                    storageRef.getFile(localfile).addOnSuccessListener {
                        val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
                        mainmenu_imageView.setImageBitmap(bitmap)
                    }.addOnFailureListener{
//                        Toast.makeText(this,"Failed to retrieve the image",Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onCancelled(databaseError: DatabaseError) {
                    Log.d("Data", databaseError.getMessage()) //Don't ignore errors!
                }
            }
            ordersRef.addValueEventListener(valueEventListener)
        }
    }
}

