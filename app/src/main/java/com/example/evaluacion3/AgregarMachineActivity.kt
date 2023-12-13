package com.example.evaluacion3

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.evaluacion3.databinding.ActivityAgregarMachineBinding
import com.example.evaluacion3.models.Machines
import com.example.evaluacion3.vistas.MachinesFragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AgregarMachineActivity : AppCompatActivity() {

    // Declaración de la referencia a la base de datos Firebase
    private lateinit var database: DatabaseReference

    // Declaración de la variable de ViewBinding
    private lateinit var binding: ActivityAgregarMachineBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflar el diseño de la interfaz de usuario utilizando ViewBinding
        binding = ActivityAgregarMachineBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializar la referencia a la base de datos Firebase con el nombre "Machines"
        database = FirebaseDatabase.getInstance().getReference("Machines")

        // Configuración del evento de clic en el botón "Guardar"
        binding.btnGuardar.setOnClickListener {
            // Obtener la información de las vistas de la interfaz de usuario
            val nombre = binding.etNombreMachine.text.toString()
            val producto = binding.etProductsMachine.text.toString()
            val temperatura = binding.etIdealTemperature.text.toString()

            // Generar un ID único para la máquina
            val id = database.child("Machines").push().key

            // Verificar la validez de los datos ingresados
            if (nombre.isEmpty()) {
                binding.etNombreMachine.error = "Por favor ingresar nombre"
                return@setOnClickListener
            }
            if (producto.isEmpty()) {
                binding.etProductsMachine.error = "Por favor ingresar producto"
                return@setOnClickListener
            }
            if (temperatura.isEmpty()) {
                binding.etIdealTemperature.error = "Por favor ingresar temperatura"
                return@setOnClickListener
            }

            // Crear un objeto Machines con la información
            val machines = Machines(id, nombre, producto, temperatura)

            // Guardar la información en la base de datos Firebase
            database.child(id!!).setValue(machines)
                .addOnSuccessListener {
                    // Mostrar un mensaje de éxito
                    Snackbar.make(binding.root, "Máquina Agregada", Snackbar.LENGTH_SHORT).show()
                    finish()
                }
                .addOnFailureListener {
                    // Mostrar un mensaje de error en caso de falla
                    Snackbar.make(binding.root, "ERROR", Snackbar.LENGTH_SHORT).show()
                }
        }

        // Configuración del evento de clic en el botón "Ver"
        binding.btnVer.setOnClickListener {
            // Crear un intent para abrir la actividad MachinesFragment
            val intent = Intent(this, MachinesFragment::class.java)
            startActivity(intent)
        }
    }
}
