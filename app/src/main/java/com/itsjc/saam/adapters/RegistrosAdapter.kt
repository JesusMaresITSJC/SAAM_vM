package com.itsjc.saam.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.itsjc.saam.R
import com.itsjc.saam.models.Registro
import com.itsjc.saam.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistrosAdapter(private val lista: MutableList<Registro>) :
    RecyclerView.Adapter<RegistrosAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgMapa: ImageView = view.findViewById(R.id.imgMapa)
        val txtDispositivo: TextView = view.findViewById(R.id.txtDispositivo)
        val txtFecha: TextView = view.findViewById(R.id.txtFecha)
        val txtCoordenadas: TextView = view.findViewById(R.id.txtCoordenadas)
        val txtBateria: TextView = view.findViewById(R.id.txtBateria)
        val btnEliminar: Button = view.findViewById(R.id.btnEliminar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_registro, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val registro = lista[position]

        holder.txtDispositivo.text = "Dispositivo: ${registro.dispositivo_id}"
        holder.txtFecha.text = "Fecha: ${registro.fecha}"
        holder.txtCoordenadas.text = "Lat: ${registro.latitud}  Lon: ${registro.longitud}"
        holder.txtBateria.text = "Batería: ${registro.bateria}%"

        holder.btnEliminar.setOnClickListener {
            val adapterPos = holder.adapterPosition
            if (adapterPos != RecyclerView.NO_POSITION) {

                RetrofitClient.apiService.eliminarRegistro(registro.id_registro)
                    .enqueue(object : Callback<Void> {

                        override fun onResponse(call: Call<Void>, response: Response<Void>) {
                            if (response.isSuccessful) {
                                // Eliminar item de la lista y actualizar RecyclerView
                                lista.removeAt(adapterPos)
                                notifyItemRemoved(adapterPos)
                                Toast.makeText(
                                    holder.itemView.context,
                                    "Registro eliminado",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                Toast.makeText(
                                    holder.itemView.context,
                                    "Error eliminando registro: ${response.code()}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                        override fun onFailure(call: Call<Void>, t: Throwable) {
                            Toast.makeText(
                                holder.itemView.context,
                                "Error eliminando registro: ${t.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })
            }
        }
    }

    override fun getItemCount(): Int = lista.size
}