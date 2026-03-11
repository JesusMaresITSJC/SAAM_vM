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

class MainActivity : AppCompatActivity() {

    private lateinit var txtRPM: TextView
    private lateinit var txtAlert: TextView
    private lateinit var btnGuardar: Button
    private lateinit var btnAlerta: Button
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
        btnCerrarSesion = findViewById(R.id.btnLogout)

        // Simular RPM en tiempo real
        actualizarRPM()

        // Botón guardar datos
        btnGuardar.setOnClickListener {

            Toast.makeText(this, "Datos guardados correctamente", Toast.LENGTH_SHORT).show()

        }

        // Botón enviar alerta
        btnAlerta.setOnClickListener {

            Toast.makeText(this, "Alerta enviada", Toast.LENGTH_SHORT).show()

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
}