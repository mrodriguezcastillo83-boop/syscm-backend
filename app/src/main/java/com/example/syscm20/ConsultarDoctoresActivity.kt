package com.example.syscm20

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConsultarDoctoresActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consultar_doctores)

        val recyclerView = findViewById<RecyclerView>(R.id.rvDoctores)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = UsuarioAdapter(emptyList(), R.layout.item_doctor)

        RetrofitClient.instance.obtenerDoctores().enqueue(object : Callback<List<UsuarioResponse>> {
            override fun onResponse(call: Call<List<UsuarioResponse>>, response: Response<List<UsuarioResponse>>) {
                if (response.isSuccessful) {
                    val lista = response.body() ?: emptyList()
                    recyclerView.adapter = UsuarioAdapter(lista, R.layout.item_doctor)
                } else {
                    Toast.makeText(this@ConsultarDoctoresActivity, "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<List<UsuarioResponse>>, t: Throwable) {
                Toast.makeText(this@ConsultarDoctoresActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}