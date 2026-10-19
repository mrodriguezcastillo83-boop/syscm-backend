package com.example.syscm20.model

data class CitaRequest(
    val id_paciente: Int,
    val fecha_cita: String,
    val motivo: String,
    val estado: String
)