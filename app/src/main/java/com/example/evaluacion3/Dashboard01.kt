package com.example.evaluacion3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.evaluacion3.databinding.ActivityDashboard01Binding
import com.example.evaluacion3.vistas.AboutFragment
import com.example.evaluacion3.vistas.ConfigFragment
import com.example.evaluacion3.vistas.HomeFragment
import com.example.evaluacion3.vistas.MachinesFragment

class Dashboard01 : AppCompatActivity() {
    private lateinit var binding: ActivityDashboard01Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboard01Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // Cargar Fragment, cuando inicie la app

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout, HomeFragment()).commit()
        }

        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, HomeFragment()).commit()
                    true
                }

                R.id.nav_machine -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, MachinesFragment()).commit()
                    true
                }

                R.id.nav_about -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, AboutFragment()).commit()
                    true
                }

                R.id.nav_settings -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, ConfigFragment()).commit()
                    true
                }

                else -> false
            }
        }
        binding.bottomNav.setOnItemReselectedListener {
            when(it.itemId){

                R.id.nav_home -> Toast.makeText(this, "Ya estás en la vista home", Toast.LENGTH_SHORT).show()
                R.id.nav_machine -> Toast.makeText(this, "Ya estás en la vista machines", Toast.LENGTH_SHORT).show()
                R.id.nav_about -> Toast.makeText(this, "Ya estás en la vista about", Toast.LENGTH_SHORT).show()
                R.id.nav_settings -> Toast.makeText(this, "Ya estás en la vista settings", Toast.LENGTH_SHORT).show()
                else -> false
            }
        }
    }
}

