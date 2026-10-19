package com.example.syscm20

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.syscm20.model.ExpedienteRequest
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RegistrarExpedienteEnfActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_expediente_enf)

        val etIdPaciente = findViewById<TextInputEditText>(R.id.etIdPacienteExpEnf)
        val etAntecedentes = findViewById<TextInputEditText>(R.id.etSignosVitalesEnf)
        val etAlergias = findViewById<TextInputEditText>(R.id.etAlergiasExpEnf)
        val btnGuardar = findViewById<Button>(R.id.btnGuardarExpedienteEnf)

        btnGuardar.setOnClickListener {
            val idPacienteStr = etIdPaciente.text.toString()
            val antecedentes = etAntecedentes.text.toString()
            val alergias = etAlergias.text.toString()

            if (idPacienteStr.isEmpty() || antecedentes.isEmpty() || alergias.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Capturar fecha actual
            val fechaActual = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())

            val datos = ExpedienteRequest(
                id_paciente = idPacienteStr.toInt(),
                antecedentes_medicos = antecedentes,
                alergias = alergias,
                fecha_creacion = fechaActual
            )

            // Realizamos la llamada a Retrofit
            RetrofitClient.instance.guardarExpediente(datos).enqueue(object : Callback<Any> {
                override fun onResponse(call: Call<Any>, response: Response<Any>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@RegistrarExpedienteEnfActivity, "Expediente registrado", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this@RegistrarExpedienteEnfActivity, "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<Any>, t: Throwable) {
                    Toast.makeText(this@RegistrarExpedienteEnfActivity, "Fallo: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}