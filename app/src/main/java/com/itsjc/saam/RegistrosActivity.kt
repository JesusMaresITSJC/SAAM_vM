package com.itsjc.saam

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.itsjc.saam.network.RetrofitClient
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.itsjc.saam.adapters.RegistrosAdapter
import com.itsjc.saam.models.Registro

class RegistrosActivity : AppCompatActivity() {

    private lateinit var recycler: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registros)

        recycler = findViewById(R.id.recyclerRegistros)

        recycler.layoutManager = LinearLayoutManager(this)

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

                        val lista = response.body() ?: emptyList()

                        recycler.adapter = RegistrosAdapter(lista)

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
