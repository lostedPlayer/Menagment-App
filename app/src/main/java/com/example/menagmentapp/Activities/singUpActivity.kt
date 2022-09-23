package com.example.menagmentapp.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.menagmentapp.Firebase.FirestoreClass
import com.example.menagmentapp.Models.User
import com.example.menagmentapp.R
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase

class singUpActivity : BaseActivity() {

    private lateinit var et_name: EditText
    private lateinit var et_email: EditText
    private lateinit var et_password: EditText

    private lateinit var btn_SignUp: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sing_up)
        title = "Home"
        getViews()
        FirebaseApp.initializeApp(this)


        btn_SignUp.setOnClickListener {
            registerUser()
        }


    }

    fun getViews() {
        et_name = findViewById(R.id.et_singup_name)
        et_email = findViewById(R.id.et_singup_email)
        et_password = findViewById(R.id.et_singup_password)

        btn_SignUp = findViewById(R.id.btn_signUp_signUpActivity)
    }


    //register new user
    fun registerUser() {

        //TODO: CHECK LENGHT OF PASSWORD BEFORE SENDING TO FIREBASE SERVER
        val name: String = et_name.text.toString().trim { it <= ' ' }
        val email: String = et_email.text.toString().trim { it <= ' ' }
        val password: String = et_password.text.toString().trim { it <= ' ' }

        if (validateForm(name, email, password)) {
            showProgressDialog()
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    hideProgressDialog()
                    if (task.isSuccessful) {
                        val firebaseUser: FirebaseUser = task.result!!.user!!
                        val registerdEmail = firebaseUser.email!!
                        val user = User(firebaseUser.uid, name, registerdEmail)
                        FirestoreClass().registerUser(this, user)


                        Toast.makeText(
                            this,
                            "$name you have successfully registred ",
                            Toast.LENGTH_SHORT
                        ).show()

                        FirebaseAuth.getInstance().signOut()
                        finish()
                    } else {
                        Toast.makeText(
                            this,
                            "Eroor" + task.exception.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.d("Firebase error", "registerUser: " + task.exception.toString())
                    }
                }
        }
    }


    //show errors if fields inside sing up are empty
    private fun validateForm(name: String, email: String, password: String): Boolean {
        return when {
            TextUtils.isEmpty(name) -> {
                showErrorSnackBar("Please enter a name")
                false
            }

            TextUtils.isEmpty(email) -> {
                showErrorSnackBar("Please enter a email")
                false
            }

            TextUtils.isEmpty(password) -> {
                showErrorSnackBar("Please enter a password")
                false
            }
            else -> {
                return true
            }
        }
    }


    fun userRegisterdSucces() {
        Toast.makeText(
            this,
            "You have succesufly registered in firestore database",
            Toast.LENGTH_SHORT
        ).show()
        FirebaseAuth.getInstance().signOut()
        finish()
    }
}