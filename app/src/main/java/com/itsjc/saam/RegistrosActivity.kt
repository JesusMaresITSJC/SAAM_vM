package com.itsjc.saam

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.itsjc.saam.adapters.RegistrosAdapter
import com.itsjc.saam.models.Registro
import com.itsjc.saam.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistrosActivity : AppCompatActivity() {

    private lateinit var recycler: RecyclerView
    private lateinit var adapter: RegistrosAdapter
    private val registrosList = mutableListOf<Registro>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registros)

        // Inicializar RecyclerView
        recycler = findViewById(R.id.recyclerRegistros)
        recycler.layoutManager = LinearLayoutManager(this)

        // Inicializar adapter con lista mutable
        adapter = RegistrosAdapter(registrosList)
        recycler.adapter = adapter

        // Configurar toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbarRegistros)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        toolbar.setTitleTextColor(getColor(android.R.color.white))
        supportActionBar?.title = "Historial de Datos"
        toolbar.setNavigationOnClickListener { finish() }

        // Cargar registros desde servidor
        cargarRegistros()
    }

    private fun cargarRegistros() {
        RetrofitClient.apiService.obtenerRegistros()
            .enqueue(object : Callback<List<Registro>> {
                override fun onResponse(
                    call: Call<List<Registro>>,
                    response: Response<List<Registro>>
                ) {
                    if (response.isSuccessful) {
                        // Limpiar lista y agregar los nuevos registros
                        registrosList.clear()
                        registrosList.addAll(response.body() ?: emptyList())
                        adapter.notifyDataSetChanged()
                    } else {
                        Toast.makeText(
                            this@RegistrosActivity,
                            "Error cargando registros",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onFailure(call: Call<List<Registro>>, t: Throwable) {
                    Toast.makeText(
                        this@RegistrosActivity,
                        "Error cargando registros",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }
}