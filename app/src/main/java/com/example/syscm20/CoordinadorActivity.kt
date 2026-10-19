package com.example.syscm20

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class CoordinadorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coordinador)

        findViewById<CardView>(R.id.cardRegDoctor).setOnClickListener {
            startActivity(Intent(this, RegistrarDoctorActivity::class.java))
        }

        findViewById<CardView>(R.id.cardRegEnfermero).setOnClickListener {
            startActivity(Intent(this, RegistrarEnfermeroActivity::class.java))
        }

        findViewById<CardView>(R.id.cardRegCoordinador).setOnClickListener {
            startActivity(Intent(this, RegistrarCoordinadorActivity::class.java))
        }

        findViewById<CardView>(R.id.cardConsDoctores).setOnClickListener {
            startActivity(Intent(this, ConsultarDoctoresActivity::class.java))
        }

        findViewById<CardView>(R.id.cardConsEnfermeros).setOnClickListener {
            startActivity(Intent(this, ConsultarEnfermerosActivity::class.java))
        }

        findViewById<CardView>(R.id.cardConsCoordinadores).setOnClickListener {
            startActivity(Intent(this, ConsultarCoordinadoresActivity::class.java))
        }

        findViewById<CardView>(R.id.cardConsExpedientes).setOnClickListener {
            startActivity(Intent(this, ListaExpedientesActivity::class.java))
        }

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