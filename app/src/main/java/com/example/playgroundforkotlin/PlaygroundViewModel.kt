package com.example.playgroundforkotlin

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.appwrite.AppwriteClient
import io.appwrite.exceptions.AppwriteException
import io.appwrite.services.AccountService
import io.appwrite.services.DatabaseService
import kotlinx.coroutines.launch
import org.json.JSONObject

class PlaygroundViewModel : ViewModel() {
    private val collectionId = "608faab562521"
    lateinit var client : AppwriteClient
    fun create(context: Context) {
        client = AppwriteClient(context)
            .setEndpoint("https://demo.appwrite.io/v1")
            .setProject("608fa1dd20ef0")
    }
    private val account by lazy {
        AccountService(client)
    }
    private val db by lazy {
        DatabaseService(client)
    }

    private val _user = MutableLiveData<JSONObject>().apply {
        value = null
    }

    val user: LiveData<JSONObject> = _user

    fun onLogin(context : Context) {
        viewModelScope.launch {
            try {
                var response = account.createSession("user@appwrite.io","password")
                _getAccount()
                var json = response.body?.string() ?: ""
                json = JSONObject(json).toString(8)
            } catch( e: AppwriteException) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun _getAccount() {
        viewModelScope.launch {
            try {
                var response = account.get()
                var json = response.body?.string() ?: ""
                var user = JSONObject(json)
                _user.postValue(user)
            } catch( e: AppwriteException) {
                Log.d("Get Account", e.message.toString())
            }
        }
    }

    fun onLoginOauth(activity: ComponentActivity, provider: String, context : Context) {
        viewModelScope.launch {
            try {
                var response = account.createOAuth2Session(activity,
                    provider,
                    "appwrite-callback-6070749e6acd4://demo.appwrite.io/auth/oauth2/success",
                    "appwrite-callback-6070749e6acd4://demo.appwrite.io/auth/oauth2/failur"
                )
            } catch( e: AppwriteException) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun onLogout(context : Context) {
        viewModelScope.launch {
            try {
                var response = account.deleteSession("current")
                _user.postValue(null)
                Toast.makeText(context, "Logged out", Toast.LENGTH_LONG).show()
            } catch( e: AppwriteException) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun createDoc(context: Context) {
        viewModelScope.launch {
            try {
                var response = db.createDocument(collectionId,mapOf("username" to "Android"), listOf("*"),
                    listOf("*"))
                var json = response.body?.string() ?: ""
                json = JSONObject(json).toString(8)
                Toast.makeText(context, json, Toast.LENGTH_LONG).show()
            } catch (e: AppwriteException) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}