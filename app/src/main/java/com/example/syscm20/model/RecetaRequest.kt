package com.example.syscm20.model

data class RecetaRequest(
    val id_diagnostico: Int,
    val medicamentos: String,
    val indicaciones: String
)