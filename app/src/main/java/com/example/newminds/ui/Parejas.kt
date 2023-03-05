package com.example.newminds.ui

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.TransitionDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.newminds.databinding.ActividadParejasBinding
import com.example.newminds.utils.datosParejas

class Parejas : AppCompatActivity() {
    private lateinit var binding: ActividadParejasBinding
    private lateinit var pregunta: datosParejas
    private var contador = 0
    private var texto_boton_uno = ""
    private var texto_boton_dos = ""
    private var estado_boton_uno = 0
    private var estado_boton_dos = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActividadParejasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pregunta = intent.getParcelableExtra<datosParejas>("pregunta")!!
        val parejas = arrayOf(
            arrayOf(pregunta.parejas[0], pregunta.respuestas[0]),
            arrayOf(pregunta.parejas[1], pregunta.respuestas[1]),
            arrayOf(pregunta.parejas[2], pregunta.respuestas[2]),
            arrayOf(pregunta.parejas[3], pregunta.respuestas[3])
        )

        if (pregunta.video != "") {
            binding.botonVideo.visibility = View.VISIBLE
            binding.botonVideo.setOnClickListener {
                val enlace_video = Uri.parse(pregunta.video)
                val intento_video = Intent(Intent.ACTION_VIEW, enlace_video)
                startActivity(intento_video)
            }
        }

        binding.botonRegreso.setOnClickListener {
            val intento_opciones_mapa = Intent(this@Parejas, Mapa::class.java)
            startActivity(intento_opciones_mapa)
            finish()
        }

        binding.botonIzquierdaUno.text = parejas[0][0]
        binding.botonIzquierdaDos.text = parejas[1][0]
        binding.botonDerechaUno.text = parejas[0][1]
        binding.botonDerechaDos.text = parejas[1][1]

        binding.botonIzquierdaUno.setOnClickListener {
            if (texto_boton_uno != "") {
                texto_boton_dos = binding.botonIzquierdaUno.text.toString()
                estado_boton_dos = binding.botonIzquierdaUno.id
                if (revisar(texto_boton_uno, texto_boton_dos, parejas)) {
                    val btn1 = findViewById<Button>(estado_boton_uno)
                    val btn2 = findViewById<Button>(estado_boton_dos)
                    btn1.isClickable = false
                    btn1.setBackgroundColor(Color.parseColor("#004000"))
                    btn2.isClickable = false
                    btn2.setBackgroundColor(Color.parseColor("#004000"))
                }
                estado_boton_uno = 0
                estado_boton_dos = 0
                texto_boton_uno = ""
                texto_boton_dos = ""
            } else {
                texto_boton_uno = binding.botonIzquierdaUno.text.toString()
                estado_boton_uno = binding.botonIzquierdaUno.id
            }
        }

        binding.botonIzquierdaDos.setOnClickListener {
            if (texto_boton_uno != "") {
                texto_boton_dos = binding.botonIzquierdaDos.text.toString()
                estado_boton_dos = binding.botonIzquierdaDos.id
                if (revisar(texto_boton_uno, texto_boton_dos, parejas)) {
                    val btn1 = findViewById<Button>(estado_boton_uno)
                    val btn2 = findViewById<Button>(estado_boton_dos)
                    btn1.isClickable = false
                    btn1.setBackgroundColor(Color.parseColor("#004000"))
                    btn2.isClickable = false
                    btn2.setBackgroundColor(Color.parseColor("#004000"))
                }
                estado_boton_uno = 0
                estado_boton_dos = 0
                texto_boton_uno = ""
                texto_boton_dos = ""
            } else {
                texto_boton_uno = binding.botonIzquierdaDos.text.toString()
                estado_boton_uno = binding.botonIzquierdaDos.id
            }
        }

        binding.botonDerechaUno.setOnClickListener {
            if (texto_boton_uno != "") {
                texto_boton_dos = binding.botonDerechaUno.text.toString()
                estado_boton_dos = binding.botonDerechaUno.id
                if (revisar(texto_boton_uno, texto_boton_dos, parejas)) {
                    val btn1 = findViewById<Button>(estado_boton_uno)
                    val btn2 = findViewById<Button>(estado_boton_dos)
                    btn1.isClickable = false
                    btn1.setBackgroundColor(Color.parseColor("#004000"))
                    btn2.isClickable = false
                    btn2.setBackgroundColor(Color.parseColor("#004000"))
                }
                estado_boton_uno = 0
                estado_boton_dos = 0
                texto_boton_uno = ""
                texto_boton_dos = ""
            } else {
                texto_boton_uno = binding.botonDerechaUno.text.toString()
                estado_boton_uno = binding.botonDerechaUno.id
            }
        }

        binding.botonDerechaDos.setOnClickListener {
            if (texto_boton_uno != "") {
                texto_boton_dos = binding.botonDerechaDos.text.toString()
                estado_boton_dos = binding.botonDerechaDos.id
                if (revisar(texto_boton_uno, texto_boton_dos, parejas)) {
                    val btn1 = findViewById<Button>(estado_boton_uno)
                    val btn2 = findViewById<Button>(estado_boton_dos)
                    btn1.isClickable = false
                    btn1.setBackgroundColor(Color.parseColor("#004000"))
                    btn2.isClickable = false
                    btn2.setBackgroundColor(Color.parseColor("#004000"))
                }
                estado_boton_uno = 0
                estado_boton_dos = 0
                texto_boton_uno = ""
                texto_boton_dos = ""
            } else {
                texto_boton_uno = binding.botonDerechaDos.text.toString()
                estado_boton_uno = binding.botonDerechaDos.id
            }
        }
    }

    private fun revisar(a: String, b: String, lista: Array<Array<String>>): Boolean {
        var correcto = false
        for (item in lista) {
            correcto =
                ((a == item[0] && b == item[1]) || (a == item[1] && b == item[0])) || correcto
        }
        if (!correcto) {
            repuestaEquivocada()
            return false
        }
        contador += 1
        Toast.makeText(
            binding.root.context,
            "Bien, sólo te falta una más",
            Toast.LENGTH_SHORT
        ).show()
        if (contador >= 2) {
            repuestaCorrecta()
        }
        return true
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