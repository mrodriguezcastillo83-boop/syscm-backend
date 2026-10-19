package com.example.syscm20.model

data class DiagnosticoRequest(
    val id_expediente: Int,
    val descripcion: String,
    val fecha_diagnostico: String
)