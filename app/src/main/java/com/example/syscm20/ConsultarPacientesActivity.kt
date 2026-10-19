package com.example.syscm20

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.syscm20.model.Paciente

class ConsultarPacientesActivity : AppCompatActivity() {

    private lateinit var rvPacientes: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consultar_pacientes)

        rvPacientes = findViewById(R.id.rvPacientes)
        rvPacientes.layoutManager = LinearLayoutManager(this)

        // Llamamos a la API para cargar los datos
        cargarPacientes()
    }

    private fun cargarPacientes() {
        // Asumiendo que 'ApiService' es tu interfaz y 'instance' es tu cliente configurado
        val call = RetrofitClient.instance.getPacientes()

        call.enqueue(object : Callback<List<Paciente>> {
            override fun onResponse(call: Call<List<Paciente>>, response: Response<List<Paciente>>) {
                if (response.isSuccessful) {
                    val listaPacientes = response.body()
                    if (listaPacientes != null) {
                        // Aquí conectamos el adaptador que acabamos de crear
                        val adapter = PacienteAdapter(listaPacientes)
                        rvPacientes.adapter = adapter
                    }
                } else {
                    Toast.makeText(this@ConsultarPacientesActivity, "Error al cargar datos", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Paciente>>, t: Throwable) {
                Toast.makeText(this@ConsultarPacientesActivity, "Fallo de conexión: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}