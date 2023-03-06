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
/*
    onCreate
    Entradas: dos cadenas de texto, matricula y contrasena
    Salidas: Obtener acceso para poder pasar a otra seccion de la aplicacion 
    Valor de retorno: Ninguno
    Función: La clase "Login", se extiende de la clase AppCompatActivity. 
             Podemos observar que dentro del metodo Oncreate, se agregaron dos escuchas
             para los botones de inicio y de regreso.
             El boton de inicio de sesion intenta iniciar sesion en la 
             aplicacion mediante la funcion requests.login
             Si todo es correcto pasamos a la actividad Mapa.

    Variables: binding: Interfaz grafica
               matricula: variable para guardar la matricula y poder usarlo en los otros apartados
               contrasena: variable para guardar la contrasena y poder usarlo en los otros apartados
               intento_login_mapa: Variable para guardar la actividad siguiente
               intento_login_bienvenida: Variable para cargar la actividad en startActivity

    Fecha: 05-03-23
    Autor: Leonardo del Jesus Salazar Chuc
    Rutinas anexas: AppCompatActivity(), startActivity(), Intent(), finish()
 */

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