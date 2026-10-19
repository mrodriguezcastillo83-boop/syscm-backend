package com.example.syscm20

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs = getSharedPreferences("SesionPref", Context.MODE_PRIVATE)
        if (prefs.contains("rol")) {
            redirigirSegunRol(prefs.getString("rol", "") ?: "")
            return
        }

        setContentView(R.layout.activity_main)

        val etUsuario = findViewById<EditText>(R.id.etUsuario)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnIngresar = findViewById<Button>(R.id.btnIngresar)

        btnIngresar.setOnClickListener {
            val usuario = etUsuario.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (usuario.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor, llena todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val request = LoginRequest(usuario, password)

            RetrofitClient.instance.login(request).enqueue(object : Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    if (response.isSuccessful) {
                        val loginResponse = response.body()
                        val rol = loginResponse?.rol ?: ""
                        val nombreCompleto = loginResponse?.nombre_completo ?: ""

                        prefs.edit().putString("rol", rol).apply()

                        Toast.makeText(this@MainActivity, "¡Bienvenido/a $nombreCompleto!", Toast.LENGTH_SHORT).show()

                        redirigirSegunRol(rol)

                    } else {
                        Toast.makeText(this@MainActivity, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "Error de comunicación: ${t.message}", Toast.LENGTH_LONG).show()
                }
            })
        }
    }

    private fun redirigirSegunRol(rol: String) {
        val intent = when (rol) {
            "COORDINADOR" -> Intent(this, CoordinadorActivity::class.java)
            "DOCTOR" -> Intent(this, DoctorActivity::class.java)
            "ENFERMERO" -> Intent(this, EnfermeriaActivity::class.java)
            "PACIENTE" -> Intent(this, PacienteActivity::class.java)
            else -> null
        }

        if (intent != null) {
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(this, "Rol no reconocido", Toast.LENGTH_SHORT).show()
        }
    }
}