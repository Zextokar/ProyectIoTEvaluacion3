package com.example.evaluacion3

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.evaluacion3.databinding.ActivityCambiarContrasenaBinding
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class CambiarContrasenaActivity : AppCompatActivity() {

    // Activar ViewBinding
    private lateinit var binding: ActivityCambiarContrasenaBinding

    // Activar Firebase
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializar ViewBinding
        binding = ActivityCambiarContrasenaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializar Firebase
        auth = Firebase.auth

        binding.btnCambiarPass.setOnClickListener {

            // Obtener usuario actual
            val user = Firebase.auth.currentUser!!

            // Obtener contraseña actual, nueva y confirmación de nueva contraseña
            val contraActual: String = binding.etOldPass.text.toString()
            val contraNueva: String = binding.etPassword.text.toString()
            val contraNueva2: String = binding.etPassword2.text.toString()

            // Validar campos nulos
            if (contraActual.isEmpty() || contraNueva.isEmpty() || contraNueva2.isEmpty()) {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            // Reconectar al usuario actual mediante autenticación por correo electrónico
            val credential = EmailAuthProvider
                .getCredential(user.email.toString(), contraActual)

            user.reauthenticate(credential)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        // Cambiar Contraseña
                        Toast.makeText(this, "Cambiando contraseña", Toast.LENGTH_SHORT).show()
                        if (contraNueva == contraNueva2) {
                            user.updatePassword(contraNueva)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        Toast.makeText(
                                            this,
                                            "Datos actualizados",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        // Cerrar sesión después de cambiar la contraseña
                                        auth.signOut()
                                        // Redirigir al usuario a la pantalla de inicio de sesión
                                        startActivity(Intent(this, MainActivity::class.java))
                                        finish()
                                    } else {
                                        Toast.makeText(
                                            this,
                                            "Error al cambiar la contraseña",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                        } else {
                            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT)
                                .show()
                        }
                    } else {
                        Toast.makeText(this, "Error de autenticación", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}