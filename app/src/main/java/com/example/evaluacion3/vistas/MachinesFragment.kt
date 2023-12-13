package com.example.evaluacion3.vistas

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.evaluacion3.Adapter.AdapterMaquinas
import com.example.evaluacion3.AgregarMachineActivity
import com.example.evaluacion3.EditarMachineActivity
import com.example.evaluacion3.databinding.FragmentMachinesBinding
import com.example.evaluacion3.models.Machines
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MachinesFragment : Fragment() {

    // Declaración de variables
    private lateinit var binding: FragmentMachinesBinding
    private lateinit var machinesList: ArrayList<Machines>
    private lateinit var machinesRecyclerView: RecyclerView
    private lateinit var database: DatabaseReference
    private lateinit var adapterMachines: AdapterMaquinas

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el diseño de este fragmento usando ViewBinding
        binding = FragmentMachinesBinding.inflate(inflater, container, false)
        val view = binding.root

        // Configuración del RecyclerView
        machinesRecyclerView = binding.rvMachines
        machinesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        machinesRecyclerView.setHasFixedSize(true)

        // Inicializar la lista de máquinas y el adaptador
        machinesList = arrayListOf()
        adapterMachines = AdapterMaquinas(machinesList)

        // Configurar clics en elementos del RecyclerView
        adapterMachines.setOnItemClickListener(object : AdapterMaquinas.OnItemClickListener {
            override fun onDeleteClick(position: Int) {
                val machineToRemove = machinesList[position]
                machineToRemove.id?.let { eliminarDeBaseDeDatos(it) }
            }

            override fun onEditClick(position: Int) {
                val machineToEdit = machinesList[position]
                val intent = Intent(requireContext(), EditarMachineActivity::class.java)
                intent.putExtra("machine", machineToEdit)
                startActivity(intent)
            }
        })

        // Configurar el adaptador en el RecyclerView
        machinesRecyclerView.adapter = adapterMachines

        // Configurar evento de clic en el botón Agregar
        binding.btnAgregar.setOnClickListener {
            try {
                val intent = Intent(requireContext(), AgregarMachineActivity::class.java)
                startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(
                    requireContext(),
                    "Ocurrió un error: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        // Obtener y mostrar las máquinas desde la base de datos
        getMachines()

        return view
    }

    // Función para obtener las máquinas desde la base de datos
    private fun getMachines() {
        // Ruta de Máquinas en la base de datos
        database = FirebaseDatabase.getInstance().getReference("Machines")

        // Escuchar cambios en la base de datos
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    // Limpiar la lista antes de agregar nuevos datos
                    machinesList.clear()
                    // Iterar sobre las máquinas en la base de datos
                    for (machineSnapshot in snapshot.children) {
                        // Obtener el objeto Machine y agregarlo a la lista
                        val machine = machineSnapshot.getValue(Machines::class.java)
                        machinesList.add(machine!!)
                    }
                    // Notificar al adaptador que los datos han cambiado
                    adapterMachines.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Manejar error si es necesario
            }
        })
    }

    // Función para eliminar una máquina de la base de datos
    private fun eliminarDeBaseDeDatos(id: String) {
        // Referencia a la base de datos
        val database = FirebaseDatabase.getInstance().getReference("Machines")

        // Eliminar el elemento de la base de datos
        database.child(id).removeValue()
            .addOnSuccessListener {
                Toast.makeText(
                    requireContext(),
                    "Máquina eliminada, por favor recargue la vista",
                    Toast.LENGTH_SHORT
                ).show()
                // Recargar la lista después de eliminar la máquina
                getMachines()
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Error al eliminar la máquina", Toast.LENGTH_SHORT)
                    .show()
            }
    }

    companion object {
        @JvmStatic
        fun newInstance() = MachinesFragment()
    }
}