package com.example.syscm20

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.syscm20.model.Expediente
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VerExpedientesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_expedientes)

        val rv = findViewById<RecyclerView>(R.id.rvExpedientes)
        rv.layoutManager = LinearLayoutManager(this)

        obtenerExpedientes(rv)
    }

    private fun obtenerExpedientes(rv: RecyclerView) {
        RetrofitClient.instance.getExpedientes().enqueue(object : Callback<List<Expediente>> {
            override fun onResponse(call: Call<List<Expediente>>, response: Response<List<Expediente>>) {
                if (response.isSuccessful && response.body() != null) {
                    rv.adapter = ExpedienteAdapter(response.body()!!)
                }
            }
            override fun onFailure(call: Call<List<Expediente>>, t: Throwable) {
                Toast.makeText(this@VerExpedientesActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}