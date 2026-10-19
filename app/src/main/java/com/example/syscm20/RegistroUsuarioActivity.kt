package com.example.syscm20

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegistroUsuarioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_usuario) // Asegúrate que tu archivo XML se llame así

        // Enlazamos solo con los IDs que existen en tu nuevo XML
        val etNombre = findViewById<EditText>(R.id.etNombre)
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)

        btnGuardar.setOnClickListener {
            val nombre = etNombre.text.toString()

            if (nombre.isNotEmpty()) {
                // Aquí iría tu lógica de guardado
                Toast.makeText(this, "Guardando a: $nombre", Toast.LENGTH_SHORT).show()
                finish() // Cierra la actividad tras guardar
            } else {
                etNombre.error = "Este campo es obligatorio"
            }
        }
    }
}