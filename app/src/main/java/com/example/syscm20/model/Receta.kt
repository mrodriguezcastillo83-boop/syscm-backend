package com.example.syscm20.model

data class Receta(
    val id_receta: Int,
    val id_diagnostico: Int,
    val medicamentos: String,
    val indicaciones: String
)