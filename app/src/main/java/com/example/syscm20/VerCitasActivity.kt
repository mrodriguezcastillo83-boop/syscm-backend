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
import com.example.syscm20.model.Cita
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VerCitasActivity : AppCompatActivity() {

    private lateinit var rvCitas: RecyclerView
    private lateinit var etBuscar: EditText
    private lateinit var cardSinCitas: CardView
    private var listaCompletaCitas: List<Cita> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_citas)

        // Inicialización de vistas
        rvCitas = findViewById(R.id.rvMonitoreoCitas)
        etBuscar = findViewById(R.id.etBuscarCita)
        cardSinCitas = findViewById(R.id.cardSinCitas)

        rvCitas.layoutManager = LinearLayoutManager(this)

        etBuscar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                filtrarLista(s.toString())
            }
        })

        obtenerCitas()
    }

    private fun obtenerCitas() {
        RetrofitClient.instance.getCitas().enqueue(object : Callback<List<Cita>> {
            override fun onResponse(call: Call<List<Cita>>, response: Response<List<Cita>>) {
                if (response.isSuccessful && !response.body().isNullOrEmpty()) {
                    listaCompletaCitas = response.body()!!
                    actualizarInterfaz(listaCompletaCitas)
                } else {
                    actualizarInterfaz(emptyList())
                }
            }

            override fun onFailure(call: Call<List<Cita>>, t: Throwable) {
                Toast.makeText(this@VerCitasActivity, "Error de red: ${t.message}", Toast.LENGTH_SHORT).show()
                actualizarInterfaz(emptyList())
            }
        })
    }

    private fun filtrarLista(texto: String) {
        val listaFiltrada = listaCompletaCitas.filter {
            it.fecha_cita.contains(texto, ignoreCase = true) ||
                    it.id_paciente.toString().contains(texto) ||
                    it.estado.contains(texto, ignoreCase = true)
        }
        actualizarInterfaz(listaFiltrada)
    }

    private fun actualizarInterfaz(lista: List<Cita>) {
        if (lista.isEmpty()) {
            rvCitas.visibility = View.GONE
            cardSinCitas.visibility = View.VISIBLE
        } else {
            rvCitas.adapter = CitaAdapter(lista)
            rvCitas.visibility = View.VISIBLE
            cardSinCitas.visibility = View.GONE
        }
    }
}