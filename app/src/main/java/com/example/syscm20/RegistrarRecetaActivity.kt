package com.example.syscm20

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.syscm20.model.RecetaRequest
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistrarRecetaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_receta)

        // Referencias del XML
        val etIdDiagnostico = findViewById<TextInputEditText>(R.id.etIdDiagnosticoReceta)
        val etMedicamentos = findViewById<TextInputEditText>(R.id.etMedicamentos)
        val etIndicaciones = findViewById<TextInputEditText>(R.id.etIndicaciones)
        val btnGuardar = findViewById<MaterialButton>(R.id.btnGuardarReceta)

        btnGuardar.setOnClickListener {
            val idDiagStr = etIdDiagnostico.text.toString()
            val medicamentos = etMedicamentos.text.toString()
            val indicaciones = etIndicaciones.text.toString()

            // Validación básica
            if (idDiagStr.isEmpty() || medicamentos.isEmpty() || indicaciones.isEmpty()) {
                Toast.makeText(this, "Por favor, llena todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Crear el objeto request
            val request = RecetaRequest(
                id_diagnostico = idDiagStr.toInt(),
                medicamentos = medicamentos,
                indicaciones = indicaciones
            )

            // Llamada a la API
            RetrofitClient.instance.registrarReceta(request).enqueue(object : Callback<Any> {
                override fun onResponse(call: Call<Any>, response: Response<Any>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@RegistrarRecetaActivity, "Receta guardada exitosamente", Toast.LENGTH_SHORT).show()
                        finish() // Cierra la pantalla al terminar
                    } else {
                        Toast.makeText(this@RegistrarRecetaActivity, "Error al guardar: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Any>, t: Throwable) {
                    Toast.makeText(this@RegistrarRecetaActivity, "Error de conexión: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}