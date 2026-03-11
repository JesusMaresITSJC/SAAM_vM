package com.itsjc.saam.models

data class DatosRequest(
    val dispositivo_id: Int,
    val latitud: Double,
    val longitud: Double,
    val bateria: Int,
    val alerta: Int
)