package com.example.syscm20

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditarUsuarioActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_usuario)

        val etNombre = findViewById<EditText>(R.id.etNombre)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)

        // Obtenemos el username enviado desde el Adaptador
        val username = intent.getStringExtra("USER_EXTRA")

        btnGuardar.setOnClickListener {
            val nuevoNombre = etNombre.text.toString()
            val nuevaPass = etPassword.text.toString()

            if (username != null) {
                // Creamos el objeto para enviar a la API
                val request = RegistroRequest(nuevoNombre, username, nuevaPass, "")

                RetrofitClient.instance.actualizarUsuario(request).enqueue(object : Callback<RegistroResponse> {
                    override fun onResponse(call: Call<RegistroResponse>, response: Response<RegistroResponse>) {
                        if (response.isSuccessful) {
                            Toast.makeText(this@EditarUsuarioActivity, "Actualizado correctamente", Toast.LENGTH_SHORT).show()
                            finish() // Cerramos la actividad y volvemos a la lista
                        } else {
                            Toast.makeText(this@EditarUsuarioActivity, "Error al actualizar", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<RegistroResponse>, t: Throwable) {
                        Toast.makeText(this@EditarUsuarioActivity, "Error de red: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
    }
}