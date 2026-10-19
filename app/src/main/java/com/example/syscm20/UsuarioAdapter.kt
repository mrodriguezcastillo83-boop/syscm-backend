package com.example.syscm20

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsuarioAdapter(private val lista: List<UsuarioResponse>, private val layoutId: Int) :
    RecyclerView.Adapter<UsuarioAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNombre: TextView = view.findViewById(R.id.tvNombre)
        val btnEditar: Button = view.findViewById(R.id.btnEditar)
        val btnEliminar: Button = view.findViewById(R.id.btnEliminar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val usuario = lista[position]
        holder.tvNombre.text = usuario.nombre_completo

        holder.btnEditar.setOnClickListener {
            val intent = Intent(holder.itemView.context, EditarUsuarioActivity::class.java)
            intent.putExtra("USER_EXTRA", usuario.username)
            intent.putExtra("NOMBRE_EXTRA", usuario.nombre_completo)
            holder.itemView.context.startActivity(intent)
        }

        holder.btnEliminar.setOnClickListener {
            RetrofitClient.instance.eliminarUsuario(usuario.username).enqueue(object : Callback<RegistroResponse> {
                override fun onResponse(call: Call<RegistroResponse>, response: Response<RegistroResponse>) {
                    Toast.makeText(holder.itemView.context, "Usuario eliminado", Toast.LENGTH_SHORT).show()
                }
                override fun onFailure(call: Call<RegistroResponse>, t: Throwable) {}
            })
        }
    }
    override fun getItemCount() = lista.size
}