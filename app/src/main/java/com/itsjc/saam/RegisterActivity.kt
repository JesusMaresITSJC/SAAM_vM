package com.itsjc.saam

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.itsjc.saam.models.RegisterRequest
import com.itsjc.saam.models.RegisterResponse
import com.itsjc.saam.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    private lateinit var nombre: EditText
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var confirmPassword: EditText
    private lateinit var btnRegistrar: Button
    private lateinit var btnBack: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        nombre = findViewById(R.id.nombre)
        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        confirmPassword = findViewById(R.id.confirmPassword)
        btnRegistrar = findViewById(R.id.btnRegistrar)
        btnBack = findViewById(R.id.btnBack)

        btnBack.setOnClickListener {
            finish()
        }

        btnRegistrar.setOnClickListener {
            registrarUsuario()
        }
        val txtLogin = findViewById<TextView>(R.id.volverLogin)

        txtLogin.setOnClickListener {

            finish()

        }
    }

    private fun registrarUsuario() {

        val nombreText = nombre.text.toString().trim()
        val emailText = email.text.toString().trim()
        val passwordText = password.text.toString().trim()
        val confirmPasswordText = confirmPassword.text.toString().trim()

        // Validaciones básicas
        if (nombreText.isEmpty() || emailText.isEmpty() || passwordText.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_LONG).show()
            return
        }

        if (passwordText != confirmPasswordText) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_LONG).show()
            return
        }

        val request = RegisterRequest(
            nombre = nombreText,
            email = emailText,
            password = passwordText
        )

        RetrofitClient.apiService.register(request)
            .enqueue(object : Callback<RegisterResponse> {

                override fun onResponse(
                    call: Call<RegisterResponse>,
                    response: Response<RegisterResponse>
                ) {

                    if (response.isSuccessful) {

                        val result = response.body()

                        if (result?.success == true) {

                            Toast.makeText(
                                this@RegisterActivity,
                                "Usuario registrado correctamente",
                                Toast.LENGTH_LONG
                            ).show()

                            finish()

                        } else {

                            Toast.makeText(
                                this@RegisterActivity,
                                result?.message ?: "Error al registrar",
                                Toast.LENGTH_LONG
                            ).show()

                        }

                    }

                }

                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {

                    Toast.makeText(
                        this@RegisterActivity,
                        "Error de conexión con el servidor",
                        Toast.LENGTH_LONG
                    ).show()

                }

            })

    }

}