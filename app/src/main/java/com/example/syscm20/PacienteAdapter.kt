package com.example.syscm20

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.syscm20.model.Paciente

class PacienteAdapter(private val listaPacientes: List<Paciente>) :
    RecyclerView.Adapter<PacienteAdapter.PacienteViewHolder>() {

    class PacienteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Asegúrate de que estos IDs existan en tu archivo 'item_paciente.xml'
        val tvId = itemView.findViewById<TextView>(R.id.tvIdPaciente)
        val tvNombre: TextView = view.findViewById(R.id.tvNombrePaciente)
        val tvTelefono: TextView = view.findViewById(R.id.tvTelefonoPaciente)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PacienteViewHolder {
        // Asegúrate de tener un layout llamado 'item_paciente'
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_paciente, parent, false)
        return PacienteViewHolder(view)
    }

    override fun onBindViewHolder(holder: PacienteViewHolder, position: Int) {
        val paciente = listaPacientes[position]
        holder.tvId.text = "ID: ${paciente.id_paciente}"
        holder.tvNombre.text = paciente.nombre_completo
        holder.tvTelefono.text = "Tel: ${paciente.telefono}"
    }

    override fun getItemCount() = listaPacientes.size
}