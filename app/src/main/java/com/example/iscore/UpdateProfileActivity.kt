package com.example.iscore

import Database.GlobalVar
import Model.Score
import Model.Student
import Model.User
import android.app.Activity
import android.app.ProgressDialog
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_main_menu.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_update_profile.*
import java.io.IOException
import java.net.URI
import java.text.SimpleDateFormat
import java.util.*


class UpdateProfileActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var username: String
    private val PICK_IMAGE_REQUEST = 71
    private var filePath: Uri? = null
    private var firebaseStore: FirebaseStorage? = null
    private var storageReference: StorageReference? = null
    lateinit var imagePreview: ImageView
    private var ImageUri : Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_profile)

        auth = Firebase.auth
        listener()

       updateProfileBackFAB.setOnClickListener {
            val intent = Intent(this,MainMenuActivity::class.java)
            startActivity(intent)
        }
    }

    private fun selectImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent,100)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            ImageUri = data?.data!!
            imageView4.setImageURI(ImageUri)
        }
    }

    private fun listener() {
        imageView4.setOnClickListener {
            selectImage()
        }
        updateProfileBtn.setOnClickListener {
            val progressDialog = ProgressDialog(this)
            progressDialog.setMessage("Uploading File...")
            progressDialog.setCancelable(false)
            progressDialog.show()

            val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
            val now = Date()
            val filename = formatter.format(now)
            val storageReference = FirebaseStorage.getInstance().getReference("images/$filename")
            ImageUri?.let {
                storageReference.putFile(it).addOnSuccessListener {
                    imageView4.setImageURI(null)
                    Toast.makeText(this@UpdateProfileActivity, "Succesfuly Uploaded", Toast.LENGTH_SHORT)
                    if(progressDialog.isShowing) progressDialog.dismiss()
                }.addOnFailureListener{
                    if(progressDialog.isShowing) progressDialog.dismiss()
                    Toast.makeText(this@UpdateProfileActivity, "Failed", Toast.LENGTH_SHORT)

                }
            }

            var userValid: Boolean = true
            val uname = unameUpdateTIL.editText?.text.toString().trim();
            val email = emailUpdateTIL.editText?.text.toString().trim();
            val password = passwordUpdateTIL.editText?.text.toString().trim();
            val user = FirebaseAuth.getInstance().currentUser
            user?.let {
                // Name, email address, and profile photo Url
                val uid = user.uid

                    user.updatePassword(password)
                    user.updateEmail(email)
                    val database = Firebase.database
                    val myRef = FirebaseDatabase.getInstance().getReference("users")
                    val user =  mapOf<String,String>(
                        "uid" to uid,
                        "username" to uname,
                        "email" to email,
                    )
                    myRef.child("users").child(uid).child("image").setValue(filename)
                    myRef.child("users").child(uid).child("email").setValue(email)
                    myRef.child("users").child(uid).child("username").setValue(uname).addOnSuccessListener {
                        Toast.makeText(this,"Data Updated",Toast.LENGTH_SHORT).show()
                        Toast.makeText(this,filename,Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }
}





