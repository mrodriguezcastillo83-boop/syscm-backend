package com.example.syscm20

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.syscm20.model.Expediente
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListaExpedientesActivity : AppCompatActivity() {

    private lateinit var rvExpedientes: RecyclerView
    private lateinit var etBuscar: EditText
    private lateinit var cardSinRegistros: CardView
    private var listaCompleta: List<Expediente> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_expedientes_enf)

        rvExpedientes = findViewById(R.id.rvExpedientesEnf)
        etBuscar = findViewById(R.id.etBuscarExpedienteEnf)
        cardSinRegistros = findViewById(R.id.cardSinRegistros)

        rvExpedientes.layoutManager = LinearLayoutManager(this)

        etBuscar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                filtrar(s.toString())
            }
        })

        cargarExpedientes()
    }

    private fun cargarExpedientes() {
        RetrofitClient.instance.getExpedientes().enqueue(object : Callback<List<Expediente>> {
            override fun onResponse(call: Call<List<Expediente>>, response: Response<List<Expediente>>) {
                if (response.isSuccessful) {
                    val lista = response.body()
                    if (lista != null) {
                        Log.d("API_DEBUG", "Expedientes recibidos: ${lista.size}")
                        listaCompleta = lista
                        actualizarLista(listaCompleta)
                    } else {
                        Log.d("API_DEBUG", "La respuesta fue exitosa pero el cuerpo está vacío")
                        actualizarLista(emptyList())
                    }
                } else {
                    Log.e("API_DEBUG", "Error del servidor: ${response.code()} - ${response.errorBody()?.string()}")
                    Toast.makeText(this@ListaExpedientesActivity, "Error al cargar datos", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Expediente>>, t: Throwable) {
                Log.e("API_DEBUG", "Error de conexión: ${t.message}")
                Toast.makeText(this@ListaExpedientesActivity, "Fallo de conexión", Toast.LENGTH_SHORT).show()
                actualizarLista(emptyList())
            }
        })
    }

    private fun filtrar(texto: String) {
        val textoBusqueda = texto.lowercase()
        val filtrada = listaCompleta.filter {
            it.id_paciente.toString().contains(textoBusqueda) ||
                    it.antecedentes_medicos.lowercase().contains(textoBusqueda) ||
                    it.alergias.lowercase().contains(textoBusqueda)
        }
        actualizarLista(filtrada)
    }

    private fun actualizarLista(lista: List<Expediente>) {
        if (lista.isEmpty()) {
            rvExpedientes.visibility = View.GONE
            cardSinRegistros.visibility = View.VISIBLE
        } else {
            rvExpedientes.adapter = ExpedienteAdapter(lista)
            rvExpedientes.visibility = View.VISIBLE
            cardSinRegistros.visibility = View.GONE
        }
    }
}