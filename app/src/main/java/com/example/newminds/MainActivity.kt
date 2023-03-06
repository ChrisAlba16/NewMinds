package com.example.newminds

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.newminds.databinding.ActividadBienvenidaBinding
import com.example.newminds.ui.Login

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActividadBienvenidaBinding
    /*
        onCreate()
        Entradas: El estado de alguna instancia previa si existe
        Salidas: Ninguna
        Valor de retorno: Ninguna
        Función: Inicializa la interfaz de la página de inicio previa a haber iniciado sesión
        Variables:
            binding: Vincular la interfaz gráfica
            intento_video: Abrir otra actividad donde se muestre la pantalla de inicio de sesión
        Fecha: 05/03/2023
        Autor: Gerardo Rodríguez
        Rutinas anexas: startActivity(), setOnClickListener(), finish()
    */
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