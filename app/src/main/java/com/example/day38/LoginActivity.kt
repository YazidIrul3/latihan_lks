package com.example.day38

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.day38.api.ApiClient
import com.example.day38.request.LoginRequest
import com.example.day38.response.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var usernameET : EditText
    private lateinit var pwET : EditText
    private lateinit var btnLogin : Button
    private lateinit var sessionManager: SessionManager

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)


        usernameET = findViewById(R.id.username_login)
        pwET = findViewById(R.id.pw_login)
        btnLogin = findViewById(R.id.btnLogin)
        sessionManager = SessionManager(this)

        btnLogin.setOnClickListener {
            login()
        }
    }

    private fun login() {
        val username = usernameET.text.toString()
        val pw = pwET.text.toString()

        if(username.isEmpty()) {
            Toast.makeText(this@LoginActivity,"isi usernmae input terlebih dahulu" +
                    "", Toast.LENGTH_SHORT).show()
        }

        if(pw.isEmpty()) {
            Toast.makeText(this@LoginActivity,"isi password input terlebih dahulu" +
                    "", Toast.LENGTH_SHORT).show()
        }

        val loginRequest = LoginRequest(username,pw)

        ApiClient.getApiService.login(loginRequest)
            .enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    val user = response.body()

                    if(user != null) {
                        val intent = Intent(this@LoginActivity,MainActivity::class.java)

                        user.accessToken?.let { sessionManager.setToken(it) }

                        startActivity((intent))
                    } else {
                            Toast.makeText(this@LoginActivity,"akun tidak ditemukan" +
                                    "", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Toast.makeText(this@LoginActivity,t.localizedMessage, Toast.LENGTH_SHORT).show()
                }

            })
    }
}