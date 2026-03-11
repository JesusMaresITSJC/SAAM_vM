package com.itsjc.saam.models

data class Registro(
    val id_registro: Int,
    val dispositivo_id: Int,
    val latitud: Double,
    val longitud: Double,
    val bateria: Int,
    val fecha: String

)