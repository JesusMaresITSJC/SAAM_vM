package com.itsjc.saam.models

data class RegisterRequest(
    val nombre: String,
    val email: String,
    val password: String
)