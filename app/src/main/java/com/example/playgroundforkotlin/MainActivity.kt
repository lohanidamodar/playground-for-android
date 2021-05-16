package com.example.playgroundforkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: PlaygroundViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(PlaygroundViewModel::class.java)
        findViewById<Button>(R.id.loginWithEmail).setOnClickListener { view ->
            viewModel.onLogin(this)
        }
        findViewById<Button>(R.id.createDoc).setOnClickListener { view ->
            Snackbar.make(view, "Create Document", Snackbar.LENGTH_SHORT).show()
        }
        findViewById<Button>(R.id.loginWithFacebook).setOnClickListener { view ->
            Snackbar.make(view, "Login with Facebook", Snackbar.LENGTH_SHORT).show()
        }
        findViewById<Button>(R.id.loginWithGithub).setOnClickListener { view ->
            Snackbar.make(view, "Login with Github", Snackbar.LENGTH_SHORT).show()
        }
        findViewById<Button>(R.id.loginWithGoogle).setOnClickListener { view ->
            Snackbar.make(view, "Login with Google", Snackbar.LENGTH_SHORT).show()
        }
        findViewById<Button>(R.id.logoutButton).setOnClickListener { view ->
            Snackbar.make(view, "Logout", Snackbar.LENGTH_SHORT).show()
        }
    }
}