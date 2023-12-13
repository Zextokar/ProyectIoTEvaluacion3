package com.example.evaluacion3

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.evaluacion3.databinding.ActivityEditarMachineBinding
import com.example.evaluacion3.models.Machines
import com.example.evaluacion3.vistas.MachinesFragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.FirebaseDatabase

class EditarMachineActivity : AppCompatActivity() {

    // Declaración de variables
    private lateinit var binding: ActivityEditarMachineBinding
    private var machineToEdit: Machines? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflar el diseño de la interfaz de usuario utilizando ViewBinding
        binding = ActivityEditarMachineBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtener la máquina a editar desde el intent
        machineToEdit = intent.getParcelableExtra("machine")

        // Mostrar los detalles de la máquina en la interfaz de usuario
        machineToEdit?.let { showMachineDetails(it) }

        // Configuración del evento de clic en el botón "Guardar"
        binding.btnGuardar.setOnClickListener {
            // Obtener los nuevos valores ingresados por el usuario
            val nuevoNombre = binding.etNombreMachine.text.toString()
            val nuevosProductos = binding.etProductsMachine.text.toString()
            val nuevaTemperatura = binding.etIdealTemperature.text.toString()

            // Actualizar los valores de la máquinaToEdit con los nuevos valores
            machineToEdit?.apply {
                name = nuevoNombre
                product = nuevosProductos
                temperature = nuevaTemperatura
            }

            // Actualizar los datos en la base de datos Firebase
            machineToEdit?.id?.let { id ->
                val databaseReference = FirebaseDatabase.getInstance().getReference("Machines")
                databaseReference.child(id).setValue(machineToEdit)
                    .addOnSuccessListener {
                        // Mostrar un mensaje de éxito
                        Snackbar.make(binding.root, "Máquina Editada", Snackbar.LENGTH_SHORT).show()
                        finish()
                    }
                    .addOnFailureListener {
                        // Mostrar un mensaje de error en caso de falla
                        Snackbar.make(binding.root, "ERROR", Snackbar.LENGTH_SHORT).show()
                    }
            }
        }

        // Configuración del evento de clic en el botón "Ver"
        binding.btnVer.setOnClickListener {
            // Crear un intent para abrir la actividad MachinesFragment
            val intent = Intent(this, MachinesFragment::class.java)
            startActivity(intent)
        }
    }

    // Función para mostrar los detalles de la máquina en la interfaz de usuario
    private fun showMachineDetails(machine: Machines) {
        binding.etNombreMachine.setText(machine.name)
        binding.etProductsMachine.setText(machine.product)
        binding.etIdealTemperature.setText(machine.temperature)
    }
}
