package com.example.newminds

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.newminds.databinding.ActividadBienvenidaBinding
import com.example.newminds.ui.Login

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActividadBienvenidaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActividadBienvenidaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.botonEmpecemos.setOnClickListener {
            val intento_bienvenida_login = Intent(this@MainActivity, Login::class.java)
            startActivity(intento_bienvenida_login)
            finish()
        }
    }
}