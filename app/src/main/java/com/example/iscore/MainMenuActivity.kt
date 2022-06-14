package com.example.iscore

import android.app.Activity
import android.content.Intent
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
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_main_menu.*
import java.io.IOException
import java.util.*


class MainMenuActivity : AppCompatActivity() {
    private val PICK_IMAGE_REQUEST = 71
    private var filePath: Uri? = null
    private var firebaseStore: FirebaseStorage? = null
    private var storageReference: StorageReference? = null
    lateinit var imagePreview: ImageView
    lateinit var btn_choose_image: Button
    lateinit var btn_upload_image: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        Listener()
        check()
    }

    private fun Listener(){
        mainmenu_imageView.setOnClickListener{
            launchGallery()
            uploadImage()
        }
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

    private fun launchGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            if(data == null || data.data == null){
                return
            }

            filePath = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
                mainmenu_imageView.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun uploadImage(){
//        Toast.makeText(this, filePath.toString(), Toast.LENGTH_SHORT).show()

        if(filePath != null){
//            Toast.makeText(this, filePath.toString(), Toast.LENGTH_SHORT).show()
//            Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show()
            val ref = storageReference?.child("myImages/" + UUID.randomUUID().toString())
            val uploadTask = ref?.putFile(filePath!!)

        }else{
            Toast.makeText(this, "Please Upload an Image", Toast.LENGTH_SHORT).show()
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
                }
                override fun onCancelled(databaseError: DatabaseError) {
                    Log.d("Data", databaseError.getMessage()) //Don't ignore errors!
                }
            }
            ordersRef.addValueEventListener(valueEventListener)
        }
    }
}