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
import com.example.syscm20.model.Diagnostico
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListaDiagnosticosActivity : AppCompatActivity() {

    private lateinit var listaCompleta: List<Diagnostico>
    private lateinit var rvHistorial: RecyclerView
    private lateinit var cardSinRegistros: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_diagnosticos)

        // Inicialización de vistas
        rvHistorial = findViewById(R.id.rvHistorialGeneral)
        cardSinRegistros = findViewById(R.id.cardSinRegistros)
        val etBuscar = findViewById<EditText>(R.id.etBuscarDiagnostico)

        rvHistorial.layoutManager = LinearLayoutManager(this)

        // Inicializar con adaptador vacío para evitar el error "No adapter attached"
        rvHistorial.adapter = DiagnosticoAdapter(emptyList())

        // Lógica de búsqueda
        etBuscar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                filtrarLista(s.toString())
            }
        })
    }

    // Se ejecuta cada vez que regresas a esta pantalla
    override fun onResume() {
        super.onResume()
        obtenerDiagnosticos()
    }

    private fun obtenerDiagnosticos() {
        RetrofitClient.instance.getDiagnosticos().enqueue(object : Callback<List<Diagnostico>> {
            override fun onResponse(call: Call<List<Diagnostico>>, response: Response<List<Diagnostico>>) {
                if (response.isSuccessful && !response.body().isNullOrEmpty()) {
                    listaCompleta = response.body()!!

                    // Asignar los datos recibidos al adaptador
                    rvHistorial.adapter = DiagnosticoAdapter(listaCompleta)

                    rvHistorial.visibility = View.VISIBLE
                    cardSinRegistros.visibility = View.GONE
                } else {
                    rvHistorial.visibility = View.GONE
                    cardSinRegistros.visibility = View.VISIBLE
                }
            }

            override fun onFailure(call: Call<List<Diagnostico>>, t: Throwable) {
                Toast.makeText(this@ListaDiagnosticosActivity, "Error de red: ${t.message}", Toast.LENGTH_SHORT).show()
                rvHistorial.visibility = View.GONE
                cardSinRegistros.visibility = View.VISIBLE
            }
        })
    }

    private fun filtrarLista(texto: String) {
        if (!::listaCompleta.isInitialized) return

        val listaFiltrada = listaCompleta.filter {
            // Asegúrate de que el campo que comparas exista en tu modelo Diagnostico
            it.descripcion.contains(texto, ignoreCase = true)
        }

        if (listaFiltrada.isEmpty()) {
            rvHistorial.visibility = View.GONE
            cardSinRegistros.visibility = View.VISIBLE
        } else {
            rvHistorial.adapter = DiagnosticoAdapter(listaFiltrada)
            rvHistorial.visibility = View.VISIBLE
            cardSinRegistros.visibility = View.GONE
        }
    }
}