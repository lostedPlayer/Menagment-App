package com.example.menagmentapp.Firebase

import com.example.menagmentapp.Activities.singUpActivity
import com.example.menagmentapp.Models.User
import com.example.menagmentapp.Utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class FirestoreClass {

    private val mFirestore = FirebaseFirestore.getInstance()

    fun registerUser(activity: singUpActivity, userInfo: User) {
        mFirestore.collection(Constants.USERS)
            .document(getCurentUserId()).set(userInfo , SetOptions.merge())
            .addOnSuccessListener {
                activity.userRegisterdSucces()
            }
    }


    fun getCurentUserId(): String {
        return FirebaseAuth.getInstance().currentUser!!.uid
    }
}