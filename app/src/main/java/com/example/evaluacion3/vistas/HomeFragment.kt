package com.example.evaluacion3.vistas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.evaluacion3.databinding.FragmentHomeBinding
import com.google.firebase.database.*
import com.google.firebase.database.DatabaseReference

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        getTemperatureData()

        return view
    }

    private fun getTemperatureData() {
        // Ruta de "Temperatura" en la base de datos
        database = FirebaseDatabase.getInstance().getReference().child("datos").child("Temperatura1").child("Temperatura")

        var tempTextView = binding.textViewTemperature
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val temperatureValue = snapshot.getValue(String::class.java)
                tempTextView.text = temperatureValue
            }
            override fun onCancelled(error: DatabaseError) {
                // Manejar error si es necesario
            }
        })
    }

    companion object {
        // ...
    }
}
