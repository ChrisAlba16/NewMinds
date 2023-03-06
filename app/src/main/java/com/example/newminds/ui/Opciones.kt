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
import com.example.newminds.databinding.ActividadOpcionesBinding
import com.example.newminds.utils.DatosOpciones





class Opciones : AppCompatActivity() {
    private lateinit var binding: ActividadOpcionesBinding
    private lateinit var pregunta: DatosOpciones

    /*
    onCreate()
    Entradas: Ingresan los valores con la información sobre la pregunta a responder como extras al invocar la actividad
    Salidas: Ninguna
    Valor de retorno: Ninguno
    Función: Inicializa la interfaz descomponiendo los datos ingresados, estableciendo las escuchas para los botones y las imágenes
    Variables: 
        binding: vincula las vistas y elementos de la interfaz gráfica al codigo.
        pregunta: Informacion de la pregunta
        enlace_video: Link del video de la pregunta
        intento_video: Abre una actividad donde se muestra el enlace del video.
        repuesta: Respuesta correcta de la pregunta
        intento_opciones_mapa: Abre una actividad donde se muestra el mapa
    Fecha: 05/03/2023
    Autor: Isaac Mauricio Cambranis Acosta
    Rutinas anexas: Integer.parseInt(), finish(), startActivity(), Intent(), setOnClickListener(), Uri.parse(), revisar(), setContentView(), inflate()

 */


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActividadOpcionesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pregunta = intent.getParcelableExtra<DatosOpciones>("pregunta")!!

        if (pregunta.video != "") {
            binding.botonVideo.visibility = View.VISIBLE
            binding.botonVideo.setOnClickListener {
                val enlace_video = Uri.parse(pregunta.video)
                val intento_video = Intent(Intent.ACTION_VIEW, enlace_video)
                startActivity(intento_video)
            }
        }

        val repuesta = Integer.parseInt(pregunta.respuesta)

        binding.tituloLayout.text = pregunta.texto
        binding.imagen.load(pregunta.url)
        binding.opcionUno.text = pregunta.opciones[0]
        binding.opcionDos.text = pregunta.opciones[1]
        binding.opcionTres.text = pregunta.opciones[2]
        binding.opcionCuatro.text = pregunta.opciones[3]

        binding.opcionUno.setOnClickListener {
            revisar(1, repuesta)
        }
        binding.opcionDos.setOnClickListener {
            revisar(2, repuesta)
        }
        binding.opcionTres.setOnClickListener {
            revisar(3, repuesta)
        }
        binding.opcionCuatro.setOnClickListener {
            revisar(4, repuesta)
        }

        binding.botonRegreso.setOnClickListener {
            val intento_opciones_mapa = Intent(this@Opciones, Mapa::class.java)
            startActivity(intento_opciones_mapa)
            finish()
        }
    }


    /*
    revisar()
    Entradas: dos valores enteros
    Salidas: Identificar si la opcion esocogida es la correcta
    Valor de retorno: Ninguna
    Función: compara los dos valores enteros recibidos para comprobar si la opcion seleccionada es la respuesta correcta
    Variables: 
        opcion: opcion seleccionada  
        respuesta: respuesta correcta de la pregunta 
    Fecha: 05/03/2023
    Autor: Isaac Mauricio Cambranis Acosta
    Rutinas anexas: respuestaCorrecta(),  repuestaEquivocada()
 */

    private fun revisar(opcion: Int, respuesta: Int) {
        if (opcion == respuesta)
            repuestaCorrecta()
        else
            repuestaEquivocada()

    }

    /*
    repuestaCorrecta()
    Entradas: Interfaz grafica desplegada
    Salidas: respuesta visual cuando se proporciona una respuesta correcta a la pregunta
    Valor de retorno: Ninguna
    Función: muestra un mensaje y cambia el fondo de la actividad cuando se responde correctamente la pregunta y finaliza la actividad
    Variables: 
        colores: Determina el conjunto de colores que se utilizaran
        transicion: Cambia el color del fondo de la actividad
        handler: Temporizador que permite retrasar el cierre de la actividad despues de que se realice la animacion
    Fecha: 05/03/2023
    Autor: Isaac Mauricio Cambranis Acosta
    Rutinas anexas: arrayOf(), parseColor(), makeText(), startTransition(), Handler(), postDelayed(), finish(), 
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
    repuestaEquivocada()
    Entradas: Interfaz grafica desplegada
    Salidas: respuesta visual cuando se proporciona una respuesta equivocada a la pregunta
    Valor de retorno: Ninguna
    Función: muestra un mensaje y cambia el fondo de la actividad cuando se responde incorrectamente la pregunta
    Variables: 
        colores: Determina el conjunto de colores que se utilizaran
        transicion: Cambia el color del fondo de la actividad
        handler: Temporizador que permite retrasar el cierre de la actividad despues de que se realice la animacion
    Fecha: 05/03/2023
    Autor: Isaac Mauricio Cambranis Acosta
    Rutinas anexas: arrayOf(), parseColor(), makeText(), startTransition() 
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