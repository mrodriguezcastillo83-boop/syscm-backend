package com.example.syscm20

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.syscm20.model.Paciente
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListaPacientesEnfActivity : AppCompatActivity() {

    private lateinit var rvLista: RecyclerView
    private lateinit var etBuscar: EditText
    private lateinit var cardSinRegistros: View

    // Lista original que viene de la API
    private var listaCompleta: List<Paciente> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_pacientes_enf)

        rvLista = findViewById(R.id.rvListaPacientes)
        etBuscar = findViewById(R.id.etBuscarPacienteEnf)
        cardSinRegistros = findViewById(R.id.cardSinRegistros)

        rvLista.layoutManager = LinearLayoutManager(this)

        // Lógica del buscador
        etBuscar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                filtrarLista(s.toString())
            }
        })

        obtenerPacientes()
    }

    private fun obtenerPacientes() {
        RetrofitClient.instance.getPacientes().enqueue(object : Callback<List<Paciente>> {
            override fun onResponse(call: Call<List<Paciente>>, response: Response<List<Paciente>>) {
                if (response.isSuccessful && !response.body().isNullOrEmpty()) {
                    listaCompleta = response.body()!!
                    actualizarLista(listaCompleta)
                } else {
                    cardSinRegistros.visibility = View.VISIBLE
                    rvLista.visibility = View.GONE
                }
            }

            override fun onFailure(call: Call<List<Paciente>>, t: Throwable) {
                Toast.makeText(this@ListaPacientesEnfActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun filtrarLista(texto: String) {
        val listaFiltrada = listaCompleta.filter {
            it.nombre_completo.contains(texto, ignoreCase = true)
        }
        actualizarLista(listaFiltrada)
    }

    private fun actualizarLista(lista: List<Paciente>) {
        if (lista.isEmpty()) {
            cardSinRegistros.visibility = View.VISIBLE
            rvLista.visibility = View.GONE
        } else {
            cardSinRegistros.visibility = View.GONE
            rvLista.visibility = View.VISIBLE
            rvLista.adapter = PacienteAdapter(lista)
        }
    }
}