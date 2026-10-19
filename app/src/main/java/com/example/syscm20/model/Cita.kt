package com.example.syscm20.model

data class Cita(
    val id_cita: Int,
    val id_paciente: Int,
    val fecha_cita: String,
    val motivo: String,
    val estado: String
)