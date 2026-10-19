package com.example.syscm20

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.syscm20.model.DiagnosticoRequest
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistrarDiagnosticoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_diagnostico)

        val etIdExpediente = findViewById<TextInputEditText>(R.id.etIdExpediente)
        val etFecha = findViewById<TextInputEditText>(R.id.etFechaDiagnostico)
        val etDetalles = findViewById<TextInputEditText>(R.id.etDetallesDiagnostico)
        val btnGuardar = findViewById<Button>(R.id.btnGuardarDiagnostico)

        btnGuardar.setOnClickListener {
            val idExpedienteStr = etIdExpediente.text.toString()
            val fecha = etFecha.text.toString()
            val detalles = etDetalles.text.toString()

            if (idExpedienteStr.isEmpty() || fecha.isEmpty() || detalles.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validar que el ID sea número
            val idExpediente = idExpedienteStr.toIntOrNull() ?: 0
            if (idExpediente == 0) {
                Toast.makeText(this, "ID Inválido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val request = DiagnosticoRequest(idExpediente, detalles, fecha)

            Log.d("DEBUG_APP", "Enviando: $request")

            RetrofitClient.instance.registrarDiagnostico(request).enqueue(object : Callback<Any> {
                override fun onResponse(call: Call<Any>, response: Response<Any>) {
                    if (response.isSuccessful) {
                        Log.d("DEBUG_APP", "Exito: ${response.code()}")
                        Toast.makeText(this@RegistrarDiagnosticoActivity, "Guardado con éxito", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        val errorBody = response.errorBody()?.string()
                        Log.e("ERROR_DEBUG", "Error del servidor (${response.code()}): $errorBody")
                        Toast.makeText(this@RegistrarDiagnosticoActivity, "Error ${response.code()}", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<Any>, t: Throwable) {
                    Log.e("ERROR_DEBUG", "Error de conexión: ", t)
                    Toast.makeText(this@RegistrarDiagnosticoActivity, "No se pudo conectar: ${t.message}", Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}