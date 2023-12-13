package com.example.evaluacion3.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.evaluacion3.R
import com.example.evaluacion3.models.Machines

class AdapterMaquinas(private val maquinas: ArrayList<Machines>) :
    RecyclerView.Adapter<AdapterMaquinas.ViewHolder>() {

    // Interfaz para manejar clics en los elementos del RecyclerView
    interface OnItemClickListener {
        fun onDeleteClick(position: Int)
        fun onEditClick(position: Int)
    }

    private var mListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    // Clase interna ViewHolder que representa la vista de cada elemento en el RecyclerView
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombre: TextView = itemView.findViewById(R.id.tvNombre)
        val producto: TextView = itemView.findViewById(R.id.tvProducto)
        val temperature: TextView = itemView.findViewById(R.id.tvTemperature)
        val btnEliminaritem: Button = itemView.findViewById(R.id.btnEliminaritem)
        val btnEditarItem: Button = itemView.findViewById(R.id.btnEditarItem)
    }

    // Método que se llama cuando se crea un nuevo ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.items_machines, parent, false)
        return ViewHolder(view)
    }

    // Método que devuelve la cantidad de elementos en la lista
    override fun getItemCount(): Int {
        return maquinas.size
    }

    // Método que se llama para mostrar los datos en un ViewHolder específico
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val machines = maquinas[position]

        // Establecer los datos en los elementos de la vista
        holder.nombre.text = machines.name
        holder.producto.text = machines.product
        holder.temperature.text = machines.temperature

        // Configurar el listener para el botón eliminar
        holder.btnEliminaritem.setOnClickListener {
            mListener?.onDeleteClick(position)
        }

        // Configurar el listener para el botón editar
        holder.btnEditarItem.setOnClickListener {
            // Lógica para editar la máquina (puedes abrir una nueva actividad para la edición)
            mListener?.onEditClick(position)
        }
    }
}
