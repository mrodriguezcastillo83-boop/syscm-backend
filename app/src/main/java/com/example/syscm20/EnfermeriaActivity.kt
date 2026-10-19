package com.example.syscm20

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class EnfermeriaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enfermeria)

        // Configuración de botones de navegación
        findViewById<CardView>(R.id.cardRegPaciente).setOnClickListener {
            startActivity(Intent(this, RegistrarPacienteEnfActivity::class.java))
        }

        findViewById<CardView>(R.id.cardListaPacientes).setOnClickListener {
            startActivity(Intent(this, ListaPacientesEnfActivity::class.java))
        }

        findViewById<CardView>(R.id.cardRegCita).setOnClickListener {
            startActivity(Intent(this, RegistrarCitaActivity::class.java))
        }

        findViewById<CardView>(R.id.cardVerCitas).setOnClickListener {
            startActivity(Intent(this, VerCitasActivity::class.java))
        }

        findViewById<CardView>(R.id.cardRegExpediente).setOnClickListener {
            startActivity(Intent(this, RegistrarExpedienteEnfActivity::class.java))
        }

        findViewById<CardView>(R.id.cardListaExpedientes).setOnClickListener {
            startActivity(Intent(this, ListaExpedientesActivity::class.java))
        }

        findViewById<CardView>(R.id.cardVerDiagnosticos).setOnClickListener {
            startActivity(Intent(this, ListaDiagnosticosActivity::class.java))
        }

        findViewById<CardView>(R.id.cardVerRecetas).setOnClickListener {
            startActivity(Intent(this, VerRecetasActivity::class.java))
        }

        // Configuración del botón Cerrar Sesión
        findViewById<CardView>(R.id.cardCerrarSesion).setOnClickListener {
            cerrarSesion()
        }
    }

    // Método privado fuera de onCreate para evitar errores de compilación
    private fun cerrarSesion() {
        val prefs = getSharedPreferences("SesionPref", Context.MODE_PRIVATE)
        prefs.edit().clear().apply()

        // Cambia 'MainActivity::class.java' por tu pantalla de Login inicial
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}