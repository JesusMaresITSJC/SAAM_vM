package com.itsjc.saam

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.itsjc.saam.models.GenericResponse
import com.itsjc.saam.network.RetrofitClient
import com.itsjc.saam.models.DatosRequest

class MainActivity : AppCompatActivity() {

    private lateinit var txtRPM: TextView
    private lateinit var txtAlert: TextView
    private lateinit var btnGuardar: Button
    private lateinit var btnAlerta: Button
    private lateinit var btnRegistros: Button
    private lateinit var btnCerrarSesion: Button

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Referencias a la vista
        txtRPM = findViewById(R.id.txtRPM)
        txtAlert = findViewById(R.id.txtAlert)
        btnGuardar = findViewById(R.id.btnGuardar)
        btnAlerta = findViewById(R.id.btnAlerta)
        btnRegistros = findViewById(R.id.btnRegistros)
        btnCerrarSesion = findViewById(R.id.btnLogout)

        // Simular RPM en tiempo real
        actualizarRPM()

        //boton guardar registro
        btnGuardar.setOnClickListener {
            //txtAlert.text = "BOTON PRESIONADO"

            // 1️⃣ Primero enviar datos al servidor
            val datos = DatosRequest(
                dispositivo_id = 1,
                latitud = 16.1234,
                longitud = -95.1234,
                bateria = 80,
                alerta = 0
            )

            RetrofitClient.apiService.recibirDatos(datos)
                .enqueue(object : Callback<GenericResponse> {

                    override fun onResponse(
                        call: Call<GenericResponse>,
                        response: Response<GenericResponse>
                    ) {

                        if (response.isSuccessful) {

                            val result = response.body()

                            if (result?.success == true) {

                                // 2️⃣ Después guardar registro
                                guardarRegistro()

                            } else {

                                Toast.makeText(
                                    this@MainActivity,
                                    "No se pudieron enviar los datos",
                                    Toast.LENGTH_LONG
                                ).show()

                            }

                        }

                    }

                    override fun onFailure(call: Call<GenericResponse>, t: Throwable) {

                        Toast.makeText(
                            this@MainActivity,
                            "Error al enviar datos",
                            Toast.LENGTH_LONG
                        ).show()

                    }

                })

        }

        // Botón enviar alerta
        btnAlerta.setOnClickListener {

            Toast.makeText(this, "Alerta enviada", Toast.LENGTH_SHORT).show()

        }

        // Botón Ver Registros
        btnRegistros.setOnClickListener {

            val intent = Intent(this, RegistrosActivity::class.java)
            startActivity(intent)
        }

        // Botón cerrar sesión
        btnCerrarSesion.setOnClickListener {

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()

        }
    }


    // Simulación de RPM en tiempo real
    private fun actualizarRPM() {

        handler.postDelayed(object : Runnable {
            override fun run() {

                val rpm = Random.nextInt(500, 5000)

                txtRPM.text = "$rpm RPM"

                // Verificar alerta
                if (rpm > 3000) {
                    txtAlert.text = "⚠ RPM MUY ALTAS"
                } else {
                    txtAlert.text = "Todo normal"
                }

                handler.postDelayed(this, 3000)
            }
        }, 3000)
    }

    private fun guardarRegistro() {

        RetrofitClient.apiService.guardarRegistro()
            .enqueue(object : Callback<GenericResponse> {

                override fun onResponse(
                    call: Call<GenericResponse>,
                    response: Response<GenericResponse>
                ) {

                    if (response.isSuccessful) {

                        val result = response.body()

                        if (result?.success == true) {

                            Toast.makeText(
                                this@MainActivity,
                                "Registro guardado correctamente",
                                Toast.LENGTH_LONG
                            ).show()

                        } else {

                            Toast.makeText(
                                this@MainActivity,
                                result?.message ?: "No se pudo guardar",
                                Toast.LENGTH_LONG
                            ).show()

                        }

                    }

                }

                override fun onFailure(call: Call<GenericResponse>, t: Throwable) {

                    Toast.makeText(
                        this@MainActivity,
                        "Error de conexión",
                        Toast.LENGTH_LONG
                    ).show()

                }

            })

    }
}