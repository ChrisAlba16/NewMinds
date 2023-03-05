package com.example.newminds.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.newminds.MainActivity
import com.example.newminds.databinding.ActividadLoginBinding
import com.example.newminds.utils.Requests
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class Login : AppCompatActivity() {
    private lateinit var binding: ActividadLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActividadLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.botonIniciarSesion.setOnClickListener {
            try {
                val matricula = Integer.parseInt(binding.textoMatricula.text.toString())
                val contrasena = binding.textoContrasena.text.toString()

                GlobalScope.launch(Dispatchers.IO) {
                    val repuesta_request = async { Requests.login(matricula, contrasena) }
                    if (repuesta_request.await().size == 1) {
                        val intento_login_mapa = Intent(this@Login, Mapa::class.java)
                        startActivity(intento_login_mapa)
                        finish()
                    }
                }

            } catch (e: Exception) {
                println(e.message)
            }

        }

        binding.botonRegreso.setOnClickListener {
            val intento_login_bienvenida = Intent(this@Login, MainActivity::class.java)
            startActivity(intento_login_bienvenida)
            finish()
        }
    }
}