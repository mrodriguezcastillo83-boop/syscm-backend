package com.example.syscm20

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.syscm20.model.Expediente

class ExpedienteAdapter(private val lista: List<Expediente>) :
    RecyclerView.Adapter<ExpedienteAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvIdExpediente: TextView = view.findViewById(R.id.tvIdExpediente)
        val tvFecha: TextView = view.findViewById(R.id.tvFechaExpediente)
        val tvIdPaciente: TextView = view.findViewById(R.id.tvIdPacienteExp)
        val tvAntecedentes: TextView = view.findViewById(R.id.tvAntecedentes)
        val tvAlergias: TextView = view.findViewById(R.id.tvAlergias)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_expediente, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = lista[position]

        // Asignamos todos los campos al diseño actualizado
        holder.tvIdExpediente.text = "Expediente #${item.id_expediente}"
        holder.tvFecha.text = "Creado: ${item.fecha_creacion}"
        holder.tvIdPaciente.text = "Paciente ID: ${item.id_paciente}"
        holder.tvAntecedentes.text = "Antecedentes: ${item.antecedentes_medicos}"
        holder.tvAlergias.text = "Alergias: ${item.alergias}"
    }

    override fun getItemCount() = lista.size
}