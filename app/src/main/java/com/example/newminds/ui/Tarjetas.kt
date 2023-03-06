package com.example.newminds.ui

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.TransitionDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.newminds.databinding.ActividadTarjetasBinding
import com.example.newminds.utils.DatosTarjetas



class Tarjetas : AppCompatActivity() {
    lateinit var binding: ActividadTarjetasBinding
    lateinit var pregunta: DatosTarjetas

/*
    onCreate
    Entradas: Ingresan los valores con la información sobre la pregunta a responder como extras al invocar la actividad
    Salidas: Ninguna
    Valor de retorno: Ninguna
    Función: Inicializa la interfaz descomponiendo los datos ingresados, estableciendo las escuchas para los botones y las imágenes
    Variables: 
        binding: Vincular la interfaz gráfica
        pregunta: Información de la pregunta 
        enlace_video: Link del video de la pregunta
        intento_video: Abrir otra actividad donde se muestre la información del enlace del video.
        intento_tarjetas_mapa: Abrir otra actividad donde se muestre el mapa
    Fecha: 05/03/2023
    Autor: Edward Tadeo Dueñas Dzib
    Rutinas anexas: startActivity(), revisar(), finish()
*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActividadTarjetasBinding.inflate(layoutInflater)
        setContentView(binding.root)
        pregunta = intent.getParcelableExtra<DatosTarjetas>("pregunta")!!
        binding.tituloLayout.text = pregunta.pregunta

        if (pregunta.video != "") {
            binding.botonVideo.visibility = View.VISIBLE
            binding.botonVideo.setOnClickListener {
                val enlace_video = Uri.parse(pregunta.video)
                val intento_video = Intent(Intent.ACTION_VIEW, enlace_video)
                startActivity(intento_video)
            }
        }

        if (pregunta.urls[0] != "" || pregunta.urls[0] != null)
            binding.imagenUno.load(pregunta.urls[0])

        if (pregunta.urls[1] != "" || pregunta.urls[1] != null)
            binding.imagenDos.load(pregunta.urls[1])

        if (pregunta.urls[2] != "" || pregunta.urls[2] != null)
            binding.imagenTres.load(pregunta.urls[2])

        if (pregunta.urls[3] != "" || pregunta.urls[3] != null)
            binding.imagenCuatro.load(pregunta.urls[3])

        binding.textoUno.text = pregunta.textos[0]
        binding.textoDos.text = pregunta.textos[1]
        binding.textoTres.text = pregunta.textos[2]
        binding.textoCuatro.text = pregunta.textos[3]

        binding.imagenUno.setOnClickListener {
            revisar(1, Integer.parseInt(pregunta.respuesta))
        }

        binding.imagenDos.setOnClickListener {
            revisar(2, Integer.parseInt(pregunta.respuesta))
        }

        binding.imagenTres.setOnClickListener {
            revisar(3, Integer.parseInt(pregunta.respuesta))
        }

        binding.imagenCuatro.setOnClickListener {
            revisar(4, Integer.parseInt(pregunta.respuesta))
        }

        binding.botonRegreso.setOnClickListener {
            val intento_tarjetas_mapa = Intent(this@Tarjetas, Mapa::class.java)
            startActivity(intento_tarjetas_mapa)
            finish()
        }
    }


/*  
    revisar
    Entradas: dos variables de tipo entero
    Salidas: identificar si la opción escogida es la correcta
    Valor de retorno: Ninguna
    Función: comparar si los dos valores enteros recibidos son equivalentes para comprobar si la opcion seleccionada es la respuesta correcta
    Variables:
        opcion: opcion seleccionada 
        respuesta: respuesta correcta de la pregunta 
    Fecha: 05/03/2023
    Autor: Edward Tadeo Dueñas Dzib
    Rutinas anexas: respuestaCorrecta(), respuestaEquivocada()
*/
    private fun revisar(opcion: Int, respuesta: Int) {
        if (opcion == respuesta)
            repuestaCorrecta()
        else
            repuestaEquivocada()
    }


/*
    respuestaCorrecta
    Entradas: Interfaz grafica desplegada
    Salidas: Cambio de color, animaciones y mensaje
    Valor de retorno: Ninguno
    Función: Muestra un mensaje y anima con un color el fondo de la actividad al responder correctamente la pregunta y posteriormente finaliza la actividad
    Variables: 
        colores: Determina el conjunto de colores que se utilizaran
        transicion: Animación para el fondo de la actividad con el conjunto de colores determinado 
        handler: Temporizador que permite retrasar el cierre de la actividad despues de que se realice la animacion
    Fecha: 05/03/2023
    Autor: Edward Tadeo Dueñas Dzib
    Rutinas anexas: arrayOf(), TransitionDrawable(), Handler(), finish()
*/
    private fun repuestaCorrecta() {
        val colores =
            arrayOf(ColorDrawable(Color.parseColor("#007f00")), ColorDrawable(Color.WHITE))
        val transicion = TransitionDrawable(colores)
        binding.fondo.background = transicion
        Toast.makeText(binding.root.context, "¡Muy bien hecho!", Toast.LENGTH_SHORT).show()
        transicion.startTransition(1000)
        val handler = Handler()
        handler.postDelayed({
            finish()
        }, 1000)
    }


/*
    respuestaEquivocada
    Entradas: Interfaz grafica desplegada
    Salidas: Cambio de color, animaciones y mensaje
    Valor de retorno: Ninguna
    Función: Muestra un mensaje y anima con un color el fondo de la actividad al responder incorrectamente la pregunta 
    Variables: 
        colores: Determina el conjunto de colores que se utilizaran
        transicion: Animación para el fondo de la actividad con el conjunto de colores determinado
    Fecha: 05/03/2023
    Autor: Edward Tadeo Dueñas Dzib
    Rutinas anexas: arrayOf(), TransitionDrawable(), show()
*/
    private fun repuestaEquivocada() {
        val colores =
            arrayOf(ColorDrawable(Color.parseColor("#7f0000")), ColorDrawable(Color.WHITE))
        val transicion = TransitionDrawable(colores)
        binding.fondo.background = transicion
        Toast.makeText(
            binding.root.context,
            "Parece que esa no es la respuesta, vuelve a intentar",
            Toast.LENGTH_SHORT
        ).show()
        transicion.startTransition(1000)
    }
}