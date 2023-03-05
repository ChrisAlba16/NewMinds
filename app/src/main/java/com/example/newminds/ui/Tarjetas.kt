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