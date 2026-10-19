package com.example.syscm20

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.syscm20.model.CitaRequest
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistrarCitaActivity : AppCompatActivity() {

    private lateinit var spinnerEstado: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_cita)

        // Inicialización de vistas
        val etIdPaciente = findViewById<TextInputEditText>(R.id.etIdPacienteCitaEnf)
        val etFecha = findViewById<TextInputEditText>(R.id.etFechaCitaEnf)
        val etMotivo = findViewById<TextInputEditText>(R.id.etMotivoCitaEnf)
        val btnGuardar = findViewById<Button>(R.id.btnGuardarCitaEnf)
        spinnerEstado = findViewById(R.id.spinnerEstadoCita)

        // Configuración del Spinner
        val opciones = listOf("Pendiente", "Completada", "Cancelada")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, opciones)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerEstado.adapter = adapter

        btnGuardar.setOnClickListener {
            val idPacienteStr = etIdPaciente.text.toString()
            val fecha = etFecha.text.toString()
            val motivo = etMotivo.text.toString()
            val estadoSeleccionado = spinnerEstado.selectedItem.toString()

            if (idPacienteStr.isEmpty() || fecha.isEmpty() || motivo.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Crear el objeto para enviar
            val nuevaCita = CitaRequest(
                id_paciente = idPacienteStr.toInt(),
                fecha_cita = fecha,
                motivo = motivo,
                estado = estadoSeleccionado
            )

            // Enviar a Retrofit
            RetrofitClient.instance.registrarCita(nuevaCita).enqueue(object : Callback<Any> {
                override fun onResponse(call: Call<Any>, response: Response<Any>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@RegistrarCitaActivity, "Cita registrada exitosamente", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this@RegistrarCitaActivity, "Error en servidor: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Any>, t: Throwable) {
                    Toast.makeText(this@RegistrarCitaActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}