package com.example.menagmentapp.Activities

import android.app.Dialog
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.menagmentapp.R
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

open class BaseActivity : AppCompatActivity() {

    private var doubleBackToExitPressedOnce = false

    private lateinit var mProgressDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        FirebaseApp.initializeApp(this)


    }


    //showing progress dialog on screen when called
    fun showProgressDialog() {
        mProgressDialog = Dialog(this)
        mProgressDialog.setContentView(R.layout.progress_dialog)
        mProgressDialog.show()
    }

    //Hiding progress dialog on screen when called
    fun hideProgressDialog() {
        mProgressDialog.hide()
        mProgressDialog.dismiss()
    }


    fun getCurentUserID(): String {
        return FirebaseAuth.getInstance().currentUser!!.uid
    }

    fun dubleBackToExit() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(
            this.applicationContext,
            R.string.backButtonPressedOnce_toast,
            Toast.LENGTH_SHORT
        ).show()

        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 200)
    }

    fun showSnackBar(msg: String) {
        val snackBar =
            Snackbar.make(findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG)
        val snackBarView = snackBar.view
        snackBar.show()
    }


    fun showErrorSnackBar(msg: String) {
        val snackBar =
            Snackbar.make(findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG)
        val snackBarView = snackBar.view
        snackBarView.setBackgroundColor(Color.RED)
        snackBar.show()
    }
}