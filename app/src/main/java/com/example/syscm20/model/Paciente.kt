package com.example.syscm20.model

data class Paciente(
    val id_paciente: Int = 0,
    val nombre_completo: String,
    val fecha_nacimiento: String,
    val genero: String,
    val telefono: String
)