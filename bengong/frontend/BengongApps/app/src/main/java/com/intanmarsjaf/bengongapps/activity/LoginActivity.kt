package com.intanmarsjaf.bengongapps.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.user_management.utils.Email
import com.google.android.material.snackbar.Snackbar
import com.intanmarsjaf.bengongapps.R
import com.intanmarsjaf.bengongapps.api.UserAPI
import com.intanmarsjaf.bengongapps.retrofit_client.RetrofitClient
import com.intanmarsjaf.bengongapps.model.ResponseUser
import com.intanmarsjaf.bengongapps.model.User
import com.intanmarsjaf.bengongapps.model.UserLogin
import com.intanmarsjaf.bengongapps.storage.sharedPreferences
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.Serializable

class LoginActivity : AppCompatActivity() {

    lateinit var sharedPreferences: sharedPreferences

    lateinit var btnLogin: TextView
    lateinit var btnRegister: TextView
    lateinit var email: EditText
    lateinit var isEmailValid: Email
    lateinit var password: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        btnLogin = findViewById(R.id.btnlogin)
        btnRegister = findViewById(R.id.btnreg)
        email = findViewById(R.id.email_login)
        password = findViewById(R.id.password_login)

        isEmailValid = Email()
        sharedPreferences = sharedPreferences(this)

        btnLogin.setOnClickListener {
            if (email.text.isEmpty()) {
                email.error = "Email is required"
                email.requestFocus()
                return@setOnClickListener
            } else if (!isEmailValid.emailValidation(email.text.toString())) {
                email.error = "Email invalid"
                email.requestFocus()
                return@setOnClickListener
            } else if (password.text.isEmpty()) {
                password.error = "Password is required"
                password.requestFocus()
                return@setOnClickListener
            } else {
                Login()
            }
        }

        btnRegister.setOnClickListener {
            val intent = Intent(applicationContext, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    fun Login() {
        val inputEmail = email.text.toString()
        val inputPassword = password.text.toString()

        val userAPI = RetrofitClient.createRetrofit().create(UserAPI::class.java)

        userAPI.login(UserLogin(inputEmail, inputPassword)).enqueue(object : Callback<ResponseUser> {
            override fun onFailure(call: Call<ResponseUser>, t: Throwable) {
                Toast.makeText(
                    applicationContext,
                    "Error when connect to server",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onResponse(call: Call<ResponseUser>, response: Response<ResponseUser>) {
                val data=JSONObject()
                data.put("email",email)

                var user = response.body()?.data

                if (response.isSuccessful) {
                    if (user == null) {
                        Toast.makeText(
                            applicationContext,
                            response.body()?.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        sharedPreferences.saveString(sharedPreferences._email, response.body()?.data?.email.toString())
                        sharedPreferences.saveUser(response.body()?.data!!)
                        Toast.makeText(applicationContext, "Login success", Toast.LENGTH_SHORT)
                            .show()
                        goToHome()
                    }
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Invalid Email or Password",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }

    fun goToHome() {
        val intent = Intent(this@LoginActivity, HomeActivity::class.java)
        intent.flags =
            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()
        if (sharedPreferences.isLoggedIn) {
            val intent = Intent(applicationContext, HomeActivity::class.java)

            intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}

