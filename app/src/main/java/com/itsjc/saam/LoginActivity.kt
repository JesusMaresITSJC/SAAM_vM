package com.itsjc.saam

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.itsjc.saam.models.LoginRequest
import com.itsjc.saam.models.LoginResponse
import com.itsjc.saam.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.content.Intent

class LoginActivity : AppCompatActivity() {

    lateinit var email: EditText
    lateinit var password: EditText
    lateinit var btnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        email = findViewById(R.id.Email)
        password = findViewById(R.id.Password)
        btnLogin = findViewById(R.id.btnLogin)

        btnLogin.setOnClickListener {

            val emailText = email.text.toString()
            val passwordText = password.text.toString()

            val request = LoginRequest(emailText, passwordText)

            RetrofitClient.apiService.login(request)
                .enqueue(object : Callback<LoginResponse> {

                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {

                        if (response.isSuccessful) {

                            val result = response.body()

                            if (result?.success == true) {

                                Toast.makeText(
                                    this@LoginActivity,
                                    "Bienvenido ${result.usuario?.nombre}",
                                    Toast.LENGTH_LONG
                                ).show()

                                val intent = Intent(this@LoginActivity, MainActivity::class.java)

                                startActivity(intent)
                                finish()

                            } else {

                                Toast.makeText(
                                    this@LoginActivity,
                                    "Credenciales incorrectas",
                                    Toast.LENGTH_LONG
                                ).show()

                            }

                        }

                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {

                        Toast.makeText(
                            this@LoginActivity,
                            "Error de conexión",
                            Toast.LENGTH_LONG
                        ).show()

                    }

                })

        }
        val btnRegister = findViewById<Button>(R.id.btnRegister)

        btnRegister.setOnClickListener {

            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)

        }

    }

}