package com.example.playgroundforkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: PlaygroundViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(PlaygroundViewModel::class.java)
        viewModel.create(this)

        findViewById<Button>(R.id.loginWithEmail).setOnClickListener { view ->
            viewModel.onLogin(this)
        }
        findViewById<Button>(R.id.createDoc).setOnClickListener { view ->
            viewModel.createDoc(this)
        }
        findViewById<Button>(R.id.loginWithFacebook).setOnClickListener { view ->
            viewModel.onLoginOauth(this,"facebook",this)
        }
        findViewById<Button>(R.id.loginWithGithub).setOnClickListener { view ->
            viewModel.onLoginOauth(this,"github",this)
        }
        findViewById<Button>(R.id.loginWithGoogle).setOnClickListener { view ->
            viewModel.onLoginOauth(this,"google",this)
        }
        findViewById<Button>(R.id.logoutButton).setOnClickListener { view ->
            viewModel.onLogout(this)
        }
        viewModel.user.observe(this, Observer {
            user ->
            if(user!=null)
                findViewById<TextView>(R.id.textView).text = user["name"].toString()
            else
                findViewById<TextView>(R.id.textView).text = "Anonymous"
        } )
    }
}