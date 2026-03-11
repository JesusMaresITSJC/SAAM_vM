package com.itsjc.saam.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.itsjc.saam.R
import com.itsjc.saam.models.Registro
import android.content.Intent
import android.net.Uri

class RegistrosAdapter(private val lista: List<Registro>) :
    RecyclerView.Adapter<RegistrosAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val imgMapa: ImageView = view.findViewById(R.id.imgMapa)
        val txtDispositivo: TextView = view.findViewById(R.id.txtDispositivo)
        val txtFecha: TextView = view.findViewById(R.id.txtFecha)
        val txtCoordenadas: TextView = view.findViewById(R.id.txtCoordenadas)
        val txtBateria: TextView = view.findViewById(R.id.txtBateria)
        val btnMapa: Button = view.findViewById(R.id.btnMapa)

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

        holder.btnMapa.setOnClickListener {

            val uri = Uri.parse(
                "https://www.google.com/maps?q=${registro.latitud},${registro.longitud}"
            )

            val intent = Intent(Intent.ACTION_VIEW, uri)
            holder.itemView.context.startActivity(intent)

        }

    }

    override fun getItemCount(): Int {
        return lista.size
    }

}