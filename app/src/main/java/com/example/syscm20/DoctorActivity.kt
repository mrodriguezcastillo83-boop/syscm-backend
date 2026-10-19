package com.example.syscm20

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class DoctorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor)

        // 1. Consultar Expedientes
        findViewById<CardView>(R.id.cardConsultarExpedientesActivity).setOnClickListener {
            startActivity(Intent(this, ListaExpedientesActivity::class.java))
        }

        // 2. Consultar Pacientes
        findViewById<CardView>(R.id.cardConsultarPacientesActivity).setOnClickListener {
            startActivity(Intent(this, ConsultarPacientesActivity::class.java))
        }

        // 3. Registrar Receta
        findViewById<CardView>(R.id.cardRegistrarRecetaActivity).setOnClickListener {
            startActivity(Intent(this, RegistrarRecetaActivity::class.java))
        }

        // 4. Registrar Diagnóstico
        findViewById<CardView>(R.id.cardRegistrarDiagnosticoActivity).setOnClickListener {
            startActivity(Intent(this, RegistrarDiagnosticoActivity::class.java))
        }

        // 5. Lista Diagnósticos
        findViewById<CardView>(R.id.cardListaDiagnosticosActivity).setOnClickListener {
            startActivity(Intent(this, ListaDiagnosticosActivity::class.java))
        }

        // 6. Cerrar Sesión
        findViewById<CardView>(R.id.cardCerrarSesion).setOnClickListener {
            cerrarSesion()
        }
    }

    private fun cerrarSesion() {
        val prefs = getSharedPreferences("SesionPref", Context.MODE_PRIVATE)
        prefs.edit().clear().apply()
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}