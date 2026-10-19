package com.example.syscm20

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.syscm20.model.Cita

class CitaAdapter(private val listaCitas: List<Cita>) :
    RecyclerView.Adapter<CitaAdapter.CitaViewHolder>() {

    class CitaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvIdCita: TextView = view.findViewById(R.id.tvIdCita)
        val tvFecha: TextView = view.findViewById(R.id.tvFechaCita)
        val tvEstado: TextView = view.findViewById(R.id.tvEstadoCita)
        val tvPaciente: TextView = view.findViewById(R.id.tvPacienteCita)
        val tvMotivo: TextView = view.findViewById(R.id.tvMotivoCita)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cita, parent, false)
        return CitaViewHolder(view)
    }

    override fun onBindViewHolder(holder: CitaViewHolder, position: Int) {
        val cita = listaCitas[position]

        // Asignación de datos
        holder.tvIdCita.text = "Cita #${cita.id_cita}"
        holder.tvFecha.text = "Fecha: ${cita.fecha_cita}"
        holder.tvPaciente.text = "Paciente ID: ${cita.id_paciente}"
        holder.tvMotivo.text = "Motivo: ${cita.motivo}"
        holder.tvEstado.text = "Estado: ${cita.estado}"

        // Lógica de colores según el estado
        when (cita.estado.lowercase().trim()) {
            "pendiente" -> holder.tvEstado.setTextColor(Color.parseColor("#D69E2E")) // Amarillo/Naranja
            "completada" -> holder.tvEstado.setTextColor(Color.parseColor("#38A169")) // Verde
            "cancelada" -> holder.tvEstado.setTextColor(Color.parseColor("#E53E3E")) // Rojo
            else -> holder.tvEstado.setTextColor(Color.parseColor("#4A5568")) // Gris
        }
    }

    override fun getItemCount(): Int = listaCitas.size
}