package com.example.syscm20.model

data class Diagnostico(
    val id_diagnostico: Int,
    val id_expediente: Int,
    val descripcion: String,
    val fecha_diagnostico: String
)