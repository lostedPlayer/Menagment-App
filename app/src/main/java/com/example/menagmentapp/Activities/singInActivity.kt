package com.example.menagmentapp.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.example.menagmentapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class singInActivity : BaseActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var et_email: EditText
    private lateinit var et_password: EditText
    private lateinit var bt_signIn: Button

    override fun onStart() {
        super.onStart()
        //TODO: CHECK IF USER IS ALREADY SIGNED IN
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sing_in)
        title = "Home"
        auth = Firebase.auth
        initialiseViews()

        bt_signIn.setOnClickListener {
            val email: String = et_email.text.toString().trim { it <= ' ' }
            val password: String = et_password.text.toString().trim { it <= ' ' }
            signInUser(email, password)

        }
    }


    //gett Views here
    fun initialiseViews() {
        et_email = findViewById(R.id.et_singin_email)
        et_password = findViewById(R.id.et_singin_password)
        bt_signIn = findViewById(R.id.bt_signIn_SignInActivity)
    }


    fun signInUser(email: String, password: String) {

        showProgressDialog()
        if (validateForm(email, password)) {

            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    hideProgressDialog()
                    //user is signed in successfully
                    // TODO: DO SOMETHING IF USER IS SIGNED IN
                    Log.d("DEBUG", "singInUser: user successfully signed in ")
                    val user = auth.currentUser
                    showSnackBar("Welcome + ${email}")
                    startActivity(Intent(this, MainActivity::class.java))
                } else {
                    hideProgressDialog()
                    Log.d("DEBUG", "singInUser: failed to sign in user  " + task.exception)
                    showErrorSnackBar("Failed to sign in")
                }
            }
        } else {
            showErrorSnackBar("Fields should not be empty")
        }
    }

    //show errors if fields inside sing up are empty
    private fun validateForm(email: String, password: String): Boolean {
        return when {

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

}