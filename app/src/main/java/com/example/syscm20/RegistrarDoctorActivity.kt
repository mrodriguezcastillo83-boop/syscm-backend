package com.example.syscm20

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistrarDoctorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_doctor)

        val etNombre = findViewById<EditText>(R.id.etNombre)
        val etUsuario = findViewById<EditText>(R.id.etUsuario)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)

        btnGuardar.setOnClickListener {
            val nombre = etNombre.text.toString()
            val user = etUsuario.text.toString()
            val pass = etPassword.text.toString()

            if (nombre.isEmpty() || user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val datos = RegistroRequest(nombre, user, pass, "DOCTOR")

            RetrofitClient.instance.registrarUsuario(datos).enqueue(object : Callback<RegistroResponse> {
                override fun onResponse(call: Call<RegistroResponse>, response: Response<RegistroResponse>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@RegistrarDoctorActivity, "Registrado con éxito", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this@RegistrarDoctorActivity, "Error: " + response.code(), Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<RegistroResponse>, t: Throwable) {
                    Toast.makeText(this@RegistrarDoctorActivity, "Fallo de conexión: ${t.message}", Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}