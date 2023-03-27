package com.example.newminds.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.example.newminds.databinding.ActividadMapaBinding
import com.example.newminds.databinding.TemaBinding
import com.example.newminds.databinding.UnidadBinding
import com.example.newminds.utils.Mapa
import com.example.newminds.utils.MapaProgreso
import com.example.newminds.utils.Requests
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlin.math.ceil

class Mapa : AppCompatActivity() {
    private lateinit var binding: ActividadMapaBinding
    private lateinit var mapa: MutableList<Mapa>
    private lateinit var progreso: MapaProgreso
    private lateinit var temas: MutableList<Int>
    private lateinit var materia: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActividadMapaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val id_estudiante = intent.getIntExtra("idEstudiante", 1)
        val id_materia = intent.getIntExtra("idMateria", 1)


        GlobalScope.launch(Dispatchers.IO) {
            mapa = async { Requests.mapa() }.await()
            progreso = async { Requests.mapa_progreso(id_estudiante, id_materia) }.await()
            temas = async { Requests.temas_materia(id_materia) }.await()
            materia = async { Requests.materias(id_materia) }.await()
            runOnUiThread {
                binding.materiaActual.text = materia
                binding.materiaActual.setOnClickListener() {
                    getIntent().putExtra("idMateria", (id_materia % 3) + 1);
                    recreate()
                }
                for ((indice, dato) in mapa.iterator().withIndex()) {
                    if (indice % 5 == 0) {
                        val inflar_mapa = LayoutInflater.from(this@Mapa)
                        val segundo_binding =
                            UnidadBinding.inflate(inflar_mapa, binding.seccionMapa, true)
                        segundo_binding.tituloUnidad.setText("Unidad ${((indice + 1) / 5) + 1}")
                        segundo_binding.subtituloUnidad.setText(dato.nombre_unidad)
                    }
                    val inflar_mapa = LayoutInflater.from(this@Mapa)
                    val segundo_binding =
                        TemaBinding.inflate(inflar_mapa, binding.seccionMapa, true)
                    if (progreso.id_unidad < (indice / 5) + 1) {
                        segundo_binding.circuloProgreso.progress = 0
                    } else if (progreso.id_unidad > (indice / 5) + 1) {
                        segundo_binding.circuloProgreso.progress = 100
                        segundo_binding.botonTema.setOnClickListener {
                            val intento_mapa_actividades =
                                Intent(this@Mapa, Actividades::class.java)
                            intento_mapa_actividades.putExtra("idEstudiante", id_estudiante)
                            intento_mapa_actividades.putExtra("idMateria", id_materia)
                            intento_mapa_actividades.putExtra("idTema", temas[indice])
                            intento_mapa_actividades.putExtra("idActividad", 1)
                            startActivity(intento_mapa_actividades)
                            finish()
                        }
                    } else if (progreso.id_unidad == (indice / 5) + 1) {
                        if (progreso.id_tema == indice % 5 + 1) {
                            segundo_binding.circuloProgreso.progress =
                                (ceil((progreso.numero_actividad - 1) * 33.333)).toInt()
                            segundo_binding.botonTema.setOnClickListener {
                                val intento_mapa_actividades =
                                    Intent(this@Mapa, Actividades::class.java)
                                intento_mapa_actividades.putExtra("idEstudiante", id_estudiante)
                                intento_mapa_actividades.putExtra("idMateria", id_materia)
                                intento_mapa_actividades.putExtra("idTema", progreso.id_tema)
                                intento_mapa_actividades.putExtra(
                                    "idActividad",
                                    progreso.numero_actividad
                                )
                                startActivity(intento_mapa_actividades)
                                finish()
                            }
                        } else if (progreso.id_tema < indice % 5 + 1) {
                            segundo_binding.circuloProgreso.progress = 0
                        } else {
                            segundo_binding.circuloProgreso.progress = 100
                            segundo_binding.botonTema.setOnClickListener {
                                val intento_mapa_actividades =
                                    Intent(this@Mapa, Actividades::class.java)
                                intento_mapa_actividades.putExtra("idEstudiante", id_estudiante)
                                intento_mapa_actividades.putExtra("idMateria", id_materia)
                                intento_mapa_actividades.putExtra("idTema", temas[indice])
                                intento_mapa_actividades.putExtra("idActividad", 1)
                                startActivity(intento_mapa_actividades)
                                finish()
                            }
                        }
                    }
                }
            }
        }
    }
}