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

    private fun revisar(opcion: Int, respuesta: Int) {
        if (opcion == respuesta)
            repuestaCorrecta()
        else
            repuestaEquivocada()

    }

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