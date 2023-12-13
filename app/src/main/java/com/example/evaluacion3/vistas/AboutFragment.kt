package com.example.evaluacion3.vistas

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.evaluacion3.CambiarContrasenaActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.example.evaluacion3.databinding.FragmentAboutBinding

// TODO: Cambia los nombres de parámetros, elige nombres que coincidan
// con los parámetros de inicialización del fragmento, p.ej. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * Un simple [Fragment] subclass.
 * Utiliza el método de fábrica [AboutFragment.newInstance] para
 * crear una instancia de este fragmento.
 */
class AboutFragment : Fragment() {
    // TODO: Cambia los tipos y nombres de los parámetros
    private lateinit var binding: FragmentAboutBinding

    // Firebase auth
    private lateinit var auth: FirebaseAuth

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el diseño de este fragmento usando ViewBinding
        binding = FragmentAboutBinding.inflate(inflater, container, false)
        val view = binding.root

        // Inicializar Firebase
        auth = Firebase.auth

        // Encontrar el botón para cerrar sesión
        binding.btnLogout.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("¿Quieres salir?")
                .setMessage("Si presionas 'Cerrar sesión', saldrás de la aplicación")
                .setNeutralButton("Cancelar") { dialog, which ->
                    // Responder al botón neutral
                }
                .setPositiveButton("Cerrar sesión") { dialog, which ->
                    // Responder al botón positivo
                    auth.signOut()
                    requireActivity().finish()
                }
                .show()
        }

        // Configuración del evento de clic en el botón "Cambiar Contraseña"
        binding.btnCambiarContrasena.setOnClickListener {
            val intent = Intent(requireContext(), CambiarContrasenaActivity::class.java)
            startActivity(intent)
        }

        return view
    }

    companion object {
        /**
         * Utiliza este método de fábrica para crear una nueva instancia de
         * este fragmento utilizando los parámetros proporcionados.
         *
         * @param param1 Parámetro 1.
         * @param param2 Parámetro 2.
         * @return Una nueva instancia de fragmento AboutFragment.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AboutFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
