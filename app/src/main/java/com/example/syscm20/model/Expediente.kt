package com.example.syscm20.model

data class Expediente(
    val id_expediente: Int,
    val id_paciente: Int,
    val antecedentes_medicos: String,
    val alergias: String,
    val fecha_creacion: String
)