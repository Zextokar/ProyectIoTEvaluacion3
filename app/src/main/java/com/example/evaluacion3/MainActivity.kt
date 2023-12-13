package com.example.evaluacion3

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.evaluacion3.databinding.ActivityMainBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MainActivity : AppCompatActivity() {

    // Variable Global para binding
    private lateinit var binding: ActivityMainBinding

    //Fireabse auth
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Inicializar firebase con auth
        auth = Firebase.auth

        binding.loginButton.setOnClickListener {
            try {
                val correo = binding.username.editText?.text.toString()
                val contrasena = binding.password.editText?.text.toString()

                if (correo.isEmpty()) {
                    binding.username.error = "Ingrese un correo"
                    return@setOnClickListener
                }
                if (contrasena.isEmpty()) {
                    binding.password.error = "Ingrese una constraseña"
                }

                signIn(correo, contrasena)

            } catch (e: Exception) {
                Toast.makeText(this, "Ocurrió un error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Inicio de sesión correcto", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, Dashboard01::class.java)
                    startActivity(intent)

                } else {
                    Toast.makeText(this, "ERROR", Toast.LENGTH_LONG).show()
                }
            }
    }
}

