package com.example.newminds.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.newminds.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class Actividades() : AppCompatActivity() {

    private lateinit var parejas: MutableList<datosParejas>
    private lateinit var opciones: MutableList<DatosOpciones>
    private lateinit var tarjetas: MutableList<DatosTarjetas>
    var contador = 1
/*
    onCreate()
    Entradas: Una lista de los tres tipos de pregunta
    Salidas: Cambiar la pantalla a los distintos tipos de pregunta
    Valor de retorno: Ninguno
    Función: Recupera de la base de datos una lista de preguntas y las pone en pantalla en secuencia
    Variables: 
        parejas: Una lista de las preguntas de tipo Unir las parejas
        opciones: Una lista de las preguntas de tipo Opción múltiple
        tarjetas: Una lista de las preguntas de tipo Tarjetas
        contador: Un contador de las veces que se ha ingresado a esta actividad
        Bundle: Un estado previo guardado (si existe)
    Fecha: 05/03/2023
    Autor: Gerardo Rodríguez
    Rutinas anexas: putExtra(), startActivity(), launch(), multipleChoice(), pairs(), flashcards(), await()
 */    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        GlobalScope.launch(Dispatchers.IO) {
            async {
                opciones = Requests.multipleChoice(1, 1)
            }.await()

            val intento_actividades_opciones = Intent(this@Actividades, Opciones::class.java)
            intento_actividades_opciones.putExtra("pregunta", opciones[0])
            startActivity(intento_actividades_opciones)

            async {
                parejas = Requests.pairs(1, 1)
                tarjetas = Requests.flashcards(1, 1)
            }.await()
            contador += 1
        }
    }
/*
    onResume()
    Entradas: Una lista de los tres tipos de pregunta
    Salidas: Cambiar la pantalla a los distintos tipos de pregunta
    Valor de retorno: Ninguno
    Función: Continúa la secuencia de actividades cuando el usuario termina cada una
    Variables: 
        parejas: Una lista de las preguntas de tipo Unir las parejas
        opciones: Una lista de las preguntas de tipo Opción múltiple
        tarjetas: Una lista de las preguntas de tipo Tarjetas
        contador: Un contador de las veces que se ha ingresado a esta actividad
        Bundle: Un estado previo guardado (si existe)
    Fecha: 05/03/2023
    Autor: Gerardo Rodríguez
    Rutinas anexas: putExtra(), startActivity(), finish()
 */
    override fun onResume() {
        super.onResume()
        if (::opciones.isInitialized && ::tarjetas.isInitialized && ::parejas.isInitialized) {
            when (contador) {
                1 -> {
                    val intento_actividades_opciones = Intent(this@Actividades, Opciones::class.java)
                    intento_actividades_opciones.putExtra("pregunta", opciones[0])
                    startActivity(intento_actividades_opciones)
                }
                2 -> {
                    val intento_actividades_opciones = Intent(this@Actividades, Opciones::class.java)
                    intento_actividades_opciones.putExtra("pregunta", opciones[1])
                    startActivity(intento_actividades_opciones)
                }
                3 -> {
                    val intento_actividades_opciones = Intent(this@Actividades, Opciones::class.java)
                    intento_actividades_opciones.putExtra("pregunta", opciones[2])
                    startActivity(intento_actividades_opciones)
                }
                4 -> {
                    val intento_actividades_tarjetas = Intent(this@Actividades, Tarjetas::class.java)
                    intento_actividades_tarjetas.putExtra("pregunta", tarjetas[0])
                    startActivity(intento_actividades_tarjetas)
                }
                5 -> {
                    val intento_actividades_tarjetas = Intent(this@Actividades, Tarjetas::class.java)
                    intento_actividades_tarjetas.putExtra("pregunta", tarjetas[1])
                    startActivity(intento_actividades_tarjetas)
                }
                6 -> {
                    val intento_actividades_tarjetas = Intent(this@Actividades, Tarjetas::class.java)
                    intento_actividades_tarjetas.putExtra("pregunta", tarjetas[2])
                    startActivity(intento_actividades_tarjetas)
                }
                7 -> {
                    val intento_actividades_parejas = Intent(this@Actividades, Parejas::class.java)
                    intento_actividades_parejas.putExtra("pregunta", parejas[0])
                    startActivity(intento_actividades_parejas)
                }
                8 -> {
                    val intento_actividades_parejas = Intent(this@Actividades, Parejas::class.java)
                    intento_actividades_parejas.putExtra("pregunta", parejas[1])
                    startActivity(intento_actividades_parejas)
                }
                9 -> {
                    val intento_actividades_parejas = Intent(this@Actividades, Parejas::class.java)
                    intento_actividades_parejas.putExtra("pregunta", parejas[2])
                    startActivity(intento_actividades_parejas)
                }
                else -> {
                    val intento_actividades_mapa = Intent(this@Actividades, Mapa::class.java)
                    startActivity(intento_actividades_mapa)
                    finish()
                }
            }
            contador += 1
        }

    }
}