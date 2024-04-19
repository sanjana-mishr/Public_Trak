package com.example.public_trak

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.nfc.Tag
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var logInButton: Button
    private lateinit var signUpButton: Button
    private lateinit var eyeButton: ImageView
    private var isPasswordVisible = false


    override fun onCreate(savedInstanceState: Bundle?) {

        auth = Firebase.auth

        if (!isInternetAvailable()) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)
            // You can perform actions here if there's no internet connection
            // Create an Intent to start the BusOnline activity
            val intent = Intent(this, BusOffline::class.java)
            // Start the activity
            startActivity(intent)
        }else{
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_login)
            Toast.makeText(this, "Internet connection", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, LoginActivity::class.java)

        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            reload()
        }
    }

    private fun togglePasswordVisibility() {
        if (isPasswordVisible) {
            // Show password
            etPassword.transformationMethod = null
            eyeButton.setImageResource(R.drawable.eye_visible)
        } else {
            // Hide password
            etPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            eyeButton.setImageResource(R.drawable.eye_hidden)
        }
        // Move cursor to the end of the text
        etPassword.setSelection(etPassword.text.length)
    }

    private fun updateUI(user: FirebaseUser?) {
    }

    private fun reload() {
        Log.d(TAG, "Already Logged In")
    }

    companion object {
        private const val TAG = "EmailPassword"
    }

    private fun createAccount(email: String, password: String) {
        // [START create_user_with_email]
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    when (task.exception?.message) {
                        "The email address is already in use by another account." -> {
                            // Handle the case where the email is already in use
                            Toast.makeText(
                                baseContext,
                                "The email address is already in use by another account.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        else -> {
                            // Handle other errors
                            Toast.makeText(
                                baseContext,
                                "Authentication failed.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    updateUI(null)
                    
                }
            }
        // [END create_user_with_email]
    }

    private fun signIn(email: String, password: String) {
        // [START sign_in_with_email]
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                    updateUI(null)
                }
            }
        // [END sign_in_with_email]
    }

    // Method to check if internet is available
    private fun isInternetAvailable(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        connectivityManager?.let {
            val networkInfo = it.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }
        return false
    }
}