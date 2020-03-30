package com.intanmarsjaf.bengongapps.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.user_management.utils.Email
import com.google.android.material.snackbar.Snackbar
import com.intanmarsjaf.bengongapps.R
import com.intanmarsjaf.bengongapps.api.UserAPI
import com.intanmarsjaf.bengongapps.model.ResponseUser
import com.intanmarsjaf.bengongapps.model.User
import com.intanmarsjaf.bengongapps.model.UserRegister
import com.intanmarsjaf.bengongapps.retrofit_client.RetrofitClient
import com.intanmarsjaf.bengongapps.storage.sharedPreferences
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    lateinit var register: Button
    lateinit var name: EditText
    lateinit var username: EditText
    lateinit var email: EditText
    lateinit var isEmailValid: Email
    lateinit var password: EditText

    lateinit var sharedPreferences: sharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        register = findViewById(R.id.btnreg)
        name = findViewById<EditText>(R.id.name_reg)
        username = findViewById<EditText>(R.id.username_reg)
        email = findViewById<EditText>(R.id.email_reg)
        password = findViewById<EditText>(R.id.password_verif)

        isEmailValid = Email()
        sharedPreferences = sharedPreferences(this)

        register.setOnClickListener {
            if (name.text.isEmpty()){
                name.error="Name is required"
                name.requestFocus()
                return@setOnClickListener
            }else if(username.text.isEmpty()){
                username.error="Username is required"
            } else if(email.text.isEmpty()) {
                email.error = "Email is required"
                email.requestFocus()
                return@setOnClickListener
            }else if(!isEmailValid.emailValidation(email.text.toString())){
                email.error="Email invalid"
                email.requestFocus()
                return@setOnClickListener
            }else if(password.text.isEmpty()){
                password.error="Password is required"
                password.requestFocus()
                return@setOnClickListener
            }else{
                Register()
            }
        }
    }

    private fun Register() {
        val inputName = name.text.toString()
        val inputUsername = username.text.toString()
        val inputEmail = email.text.toString()
        val inputPassword = password.text.toString()

        val userAPI = RetrofitClient.createRetrofit().create(UserAPI::class.java)

        userAPI.register(UserRegister(inputName, inputUsername, inputEmail, inputPassword))
            .enqueue(object : Callback<ResponseUser> {
                override fun onFailure(call: Call<ResponseUser>, t: Throwable) {
                    Log.d("FAILED FETCH", t.toString())
                    Toast.makeText(
                        applicationContext,
                        "Error when connect to server",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onResponse(
                    call: Call<ResponseUser>,
                    response: Response<ResponseUser>
                ) {
                    var user = response.body()?.data

                    if (response.isSuccessful) {
                        if (user == null) {
                            Toast.makeText(
                                applicationContext,
                                response.body()?.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                applicationContext,
                                "Register success",
                                Toast.LENGTH_SHORT
                            ).show()
                            goToLogin()
                        }
                    } else {
                        Toast.makeText(
                            applicationContext,
                            response.body()?.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            })
    }

    fun goToLogin() {
        var newIntent = Intent(applicationContext, LoginActivity::class.java)
        newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(newIntent)
    }
}
