package com.example.playgroundforkotlin

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.material.snackbar.Snackbar
import io.appwrite.AppwriteClient
import io.appwrite.exceptions.AppwriteException
import io.appwrite.services.AccountService
import kotlinx.coroutines.launch
import org.json.JSONObject

class PlaygroundViewModel : ViewModel() {
    val client by lazy {
        AppwriteClient()
            .setEndpoint("https://demo.appwrite.io/v1")
            .setProject("6062f9c2c09ce")
    }
    val account by lazy {
        AccountService(client)
    }

    fun onLogin(context : Context) {
        viewModelScope.launch {

            try {
                var response = account.createSession("user@appwrite.io","password")
                var json = response.body?.string() ?: ""
                json = JSONObject(json).toString(8)
            } catch( e: AppwriteException) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}