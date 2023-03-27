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
    var id_materia: Int = 0
    var id_estudiante: Int = 0
    var id_tema: Int = 0
    var id_actividad: Int = 0

    var contador = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        id_estudiante = intent.getIntExtra("idEstudiante", 1)
        id_materia = intent.getIntExtra("idMateria", 1)
        id_tema = intent.getIntExtra("idTema", 1)
        id_actividad = intent.getIntExtra("idActividad", 1)

        GlobalScope.launch(Dispatchers.IO) {
            async {
                opciones = Requests.multipleChoice(id_tema, id_actividad)
            }.await()

            val intento_actividades_opciones = Intent(this@Actividades, Opciones::class.java)
            intento_actividades_opciones.putExtra("pregunta", opciones[0])
            startActivity(intento_actividades_opciones)

            async {
                parejas = Requests.pairs(id_tema, id_actividad)
                tarjetas = Requests.flashcards(id_tema, id_actividad)
            }.await()
            contador += 1
        }
    }

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
                    GlobalScope.launch(Dispatchers.IO) {
                        async { Requests.progreso(id_estudiante, id_materia, id_tema, id_actividad)}.await()
                    }
                    finish()
                }
            }
            contador += 1
        }

    }
}