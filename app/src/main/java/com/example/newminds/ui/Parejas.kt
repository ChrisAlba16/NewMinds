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
/*
    onCreate
    Entradas: Ingresan los valores con la información sobre la pregunta a responder como extras al invocar la actividad
    Salidas: Ninguna
    Valor de retorno: Ninguna
    Función: Inicializa la interfaz descomponiendo los datos ingresados, estableciendo las escuchas para los botones y las imágenes
    Variables: 
        binding: Una interfaz gráfica
        pregunta: La información de la pregunta recibida desde la actividad anterior
        contador: Un contador del número de parejas que se han formado correctamente
        texto_boton_uno: El texto del primer botón presionado
        texto_boton_dos: El texto del segundo botón presionado
        estado_boton_uno: El primer botón presionado
        estado_boton_dos: El segundo botón presionado
        parejas: Una matriz de las posibles parejas que solucionan la pregunta
        btn1: El primer botón presionado
        btn2: El segundo botón presionado
        Bundle: Un estado previo guardado (si existe) 
    Fecha: 05/03/2023
    Autor: Gerardo Rodríguez
    Rutinas anexas: finish(), startActivity(), setOnClickListener(), Uri.parse(), arrayOf(), revisar()
 */
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
/*
    revisar()
    Entradas: Dos cadenas de texto y una matriz de cadenas de texto
    Salidas: Saber si las dos cadenas ingresadas forman una pareja
    Valor de retorno: Booleano
    Función: Compara las dos cadenas ingresadas para saber si en la matriz de parejas existe aquel par y actualiza el estado del contador
    Variables: 
        correcto: Evalúa si se ha formado correctamente una pareja
        a: La cadena del primer botón presionado
        b: La cadena del segundo botón presionado
        lista: La matriz de parejas que solucionan el problema
        item: Una pareja de la matriz de posibles soluciones
        contador: El contador de parejas que se han formado correctamente
    Fecha: 05/03/2023
    Autor: Gerardo Rodríguez
    Rutinas anexas: respuestaCorrecta(), respuestaEquivocada()
 */
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
/*
    respuestaCorrecta()
    Entradas: La interfaz gráfica desplegada
    Salidas: Cambio de color, animaciones y mensaje
    Valor de retorno: Ninguno
    Función: Ejecuta las animaciones y el cambio de color en caso de haber completado el ejercicio correctamente
    Variables: 
        colores: Un arreglo de colores para la transición
        transicion: Una animación de transición de colores
        handler: un temporizador para ejecutar un método tras cierto tiempo
    Fecha: 05/03/2023
    Autor: Gerardo Rodríguez
    Rutinas anexas: parseColor(), makeText(), startTransition(), finish(), postDelayed()
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
    respuestaEquivocada()
    Entradas: La interfaz gráfica desplegada
    Salidas: Cambio de color, animaciones y mensaje
    Valor de retorno: Ninguno
    Función: Ejecuta las animaciones y el cambio de color en caso de haber completado el ejercicio de manera equivocada
    Variables: 
        colores: Un arreglo de colores para la transición
        transicion: Una animación de transición de colores
        handler: un temporizador para ejecutar un método tras cierto tiempo
    Fecha: 05/03/2023
    Autor: Gerardo Rodríguez
    Rutinas anexas: parseColor(), makeText(), startTransition()
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