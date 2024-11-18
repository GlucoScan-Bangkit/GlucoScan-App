package com.dicoding.glucoscan.helper

import android.content.Context
import android.content.Intent
import android.util.Log
import com.dicoding.glucoscan.ui.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class FirebaseHelper(private val context: Context, private val auth: FirebaseAuth) {
    private var user: FirebaseUser? = null
    private var type: String = ""

    fun signUp(email: String, password: String): String {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("Sign up", "createUserWithEmail:success")
                    user = auth.currentUser
                    type = "Success"
                    val intent = Intent(context, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    context.startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("Sign up", "createUserWithEmail:failure", task.exception)
                    type = "Failed"
                }
            }
        Log.d("Sign up", type)
        return type
    }

    fun signIn(email: String, password: String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    Log.d("Sign in", "signInWithEmail:success")
                    user = auth.currentUser
                    val intent = Intent(context, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    context.startActivity(intent)
                } else {
                    Log.w("Sign in", "signInWithEmail:failure", task.exception)
                }
            }
    }
}