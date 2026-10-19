package com.example.syscm20

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.syscm20.model.Diagnostico

class DiagnosticoAdapter(private val listaDiagnosticos: List<Diagnostico>) :
    RecyclerView.Adapter<DiagnosticoAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvIdDiagnostico: TextView = view.findViewById(R.id.tvIdDiagnostico)
        val tvIdExpediente: TextView = view.findViewById(R.id.tvIdExpedienteDiag)
        val tvFecha: TextView = view.findViewById(R.id.tvFechaDiagnostico)
        val tvDescripcion: TextView = view.findViewById(R.id.tvDescripcionDiagnostico)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_diagnostico, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val diag = listaDiagnosticos[position]

        // Asignamos todos los campos del nuevo diseño
        holder.tvIdDiagnostico.text = "Diag #${diag.id_diagnostico}"
        holder.tvIdExpediente.text = "Exp #${diag.id_expediente}"
        holder.tvFecha.text = "Fecha: ${diag.fecha_diagnostico}"
        holder.tvDescripcion.text = diag.descripcion
    }

    override fun getItemCount(): Int = listaDiagnosticos.size
}