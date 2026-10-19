package com.example.syscm20

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class PacienteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paciente)

        // Enlazar las tarjetas de la interfaz gráfica
        val cardVerCitas = findViewById<CardView>(R.id.cardVerCitas)
        val cardVerExpedientes = findViewById<CardView>(R.id.cardVerExpedientes)
        val cardVerDiagnosticos = findViewById<CardView>(R.id.cardVerDiagnosticos)
        val cardVerRecetas = findViewById<CardView>(R.id.cardVerRecetas)
        val cardCerrarSesion = findViewById<CardView>(R.id.cardCerrarSesion)

        // Lanzar actividades correspondientes
        cardVerCitas.setOnClickListener {
            startActivity(Intent(this, VerCitasActivity::class.java))
        }

        cardVerExpedientes.setOnClickListener {
            startActivity(Intent(this, VerExpedientesActivity::class.java))
        }

        cardVerDiagnosticos.setOnClickListener {
            startActivity(Intent(this, ListaDiagnosticosActivity::class.java))
        }

        cardVerRecetas.setOnClickListener {
            startActivity(Intent(this, VerRecetasActivity::class.java))
        }

        // Cierre de sesión seguro
        cardCerrarSesion.setOnClickListener {
            cerrarSesion()
        }
    }

    private fun cerrarSesion() {
        // 1. Borramos la sesión de SharedPreferences
        val prefs = getSharedPreferences("SesionPref", Context.MODE_PRIVATE)
        prefs.edit().clear().apply()

        // 2. Volvemos al Login (MainActivity)
        val intent = Intent(this, MainActivity::class.java)

        // 3. Limpiamos la pila de actividades
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        startActivity(intent)
        finish()
    }
}