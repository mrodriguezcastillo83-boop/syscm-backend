package com.example.syscm20

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.syscm20.model.Receta
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VerRecetasActivity : AppCompatActivity() {

    // Componentes de la vista
    private lateinit var rvRecetas: RecyclerView
    private lateinit var cardSinRegistros: CardView
    private lateinit var etBuscar: EditText

    // Datos
    private var listaCompleta: List<Receta> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_recetas)

        // Inicialización de vistas
        rvRecetas = findViewById(R.id.rvRecetas)
        cardSinRegistros = findViewById(R.id.cardSinRegistros)
        etBuscar = findViewById(R.id.etBuscarReceta)

        // Configuración de RecyclerView
        rvRecetas.layoutManager = LinearLayoutManager(this)

        // Carga inicial
        obtenerRecetas()

        // Configuración del buscador
        etBuscar.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                filtrarLista(s.toString())
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun obtenerRecetas() {
        RetrofitClient.instance.getRecetas().enqueue(object : Callback<List<Receta>> {
            override fun onResponse(call: Call<List<Receta>>, response: Response<List<Receta>>) {
                if (response.isSuccessful && !response.body().isNullOrEmpty()) {
                    listaCompleta = response.body()!!
                    actualizarInterfaz(listaCompleta)
                } else {
                    actualizarInterfaz(emptyList())
                }
            }
            override fun onFailure(call: Call<List<Receta>>, t: Throwable) {
                Toast.makeText(this@VerRecetasActivity, "Error de conexión: ${t.message}", Toast.LENGTH_SHORT).show()
                actualizarInterfaz(emptyList())
            }
        })
    }

    private fun filtrarLista(query: String) {
        val filtrada = listaCompleta.filter {
            it.medicamentos.contains(query, ignoreCase = true) ||
                    it.indicaciones.contains(query, ignoreCase = true)
        }
        actualizarInterfaz(filtrada)
    }

    private fun actualizarInterfaz(lista: List<Receta>) {
        if (lista.isEmpty()) {
            rvRecetas.visibility = View.GONE
            cardSinRegistros.visibility = View.VISIBLE
        } else {
            rvRecetas.adapter = RecetaAdapter(lista)
            rvRecetas.visibility = View.VISIBLE
            cardSinRegistros.visibility = View.GONE
        }
    }
}