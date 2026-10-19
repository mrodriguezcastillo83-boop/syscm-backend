package com.example.syscm20

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.syscm20.model.Receta

class RecetaAdapter(private val listaRecetas: List<Receta>) :
    RecyclerView.Adapter<RecetaAdapter.RecetaViewHolder>() {

    class RecetaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvId: TextView = view.findViewById(R.id.tvIdReceta)
        // Agregamos este TextView que falta
        val tvIdDiagnostico: TextView = view.findViewById(R.id.tvIdDiagnosticoReceta)
        val tvMedicamentos: TextView = view.findViewById(R.id.tvMedicamentosReceta)
        val tvIndicaciones: TextView = view.findViewById(R.id.tvIndicacionesReceta)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecetaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_receta, parent, false)
        return RecetaViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecetaViewHolder, position: Int) {
        val receta = listaRecetas[position]

        holder.tvId.text = "Receta #${receta.id_receta}"
        // Asignamos el valor del diagnóstico
        holder.tvIdDiagnostico.text = "Diagnóstico ID: ${receta.id_diagnostico}"
        holder.tvMedicamentos.text = "Medicamentos: ${receta.medicamentos}"
        holder.tvIndicaciones.text = "Indicaciones: ${receta.indicaciones}"
    }

    override fun getItemCount(): Int = listaRecetas.size
}