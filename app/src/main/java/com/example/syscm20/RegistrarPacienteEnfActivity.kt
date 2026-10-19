package com.example.syscm20

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.syscm20.model.Paciente
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistrarPacienteEnfActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_paciente_enf)

        val btnGuardar = findViewById<Button>(R.id.btnGuardarPacienteEnf)

        btnGuardar.setOnClickListener {
            val nombre = findViewById<EditText>(R.id.etNombre).text.toString()
            val fecha = findViewById<EditText>(R.id.etFecha).text.toString()
            val genero = findViewById<EditText>(R.id.etGenero).text.toString()
            val telefono = findViewById<EditText>(R.id.etTelefono).text.toString()

            if (nombre.isEmpty() || fecha.isEmpty()) {
                Toast.makeText(this, "Nombre y Fecha son obligatorios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val nuevoPaciente = Paciente(
                nombre_completo = nombre,
                fecha_nacimiento = fecha,
                genero = genero,
                telefono = telefono
            )

            RetrofitClient.instance.guardarPaciente(nuevoPaciente).enqueue(object : Callback<RegistroResponse> {
                override fun onResponse(call: Call<RegistroResponse>, response: Response<RegistroResponse>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@RegistrarPacienteEnfActivity, "Guardado con éxito", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this@RegistrarPacienteEnfActivity, "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<RegistroResponse>, t: Throwable) {
                    Toast.makeText(this@RegistrarPacienteEnfActivity, "Fallo: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}