package com.itsjc.saam.models

data class LoginResponse(
    val success: Boolean,
    val message: String?,
    val usuario: Usuario?
)

data class Usuario(
    val id_usuario: Int,
    val nombre: String,
    val email: String
)