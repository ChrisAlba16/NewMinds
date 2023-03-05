package com.example.newminds.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.example.newminds.databinding.ActividadMapaBinding
import com.example.newminds.databinding.TemaBinding
import com.example.newminds.databinding.UnidadBinding
import com.example.newminds.utils.Mapa
import com.example.newminds.utils.Requests
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class Mapa : AppCompatActivity() {
    private lateinit var binding: ActividadMapaBinding
    private lateinit var mapa: MutableList<Mapa>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActividadMapaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        GlobalScope.launch(Dispatchers.IO) {
            mapa = async { Requests.mapa(1) }.await()
            runOnUiThread {
                binding.materiaActual.text = "Prototipo"
                var valor_anterior: String? = null
                for ((indice, dato) in mapa.iterator().withIndex()) {
                    if (valor_anterior != null && valor_anterior == dato.nombre_unidad) {
                        val inflar_mapa = LayoutInflater.from(this@Mapa)
                        val segundo_binding = TemaBinding.inflate(inflar_mapa, binding.seccionMapa, true)
                        segundo_binding.circuloProgreso.progress = 0
                        segundo_binding.botonTema.setOnClickListener {
                        }
                        continue
                    }
                    val inflar_mapa = LayoutInflater.from(this@Mapa)
                    val segundo_binding =
                        UnidadBinding.inflate(inflar_mapa, binding.seccionMapa, true)
                    segundo_binding.tituloUnidad.setText("Unidad ${indice + 1}")
                    segundo_binding.subtituloUnidad.setText(dato.nombre_unidad)

                    val tercer_binding = TemaBinding.inflate(inflar_mapa, binding.seccionMapa, true)
                    tercer_binding.circuloProgreso.progress = 33
                    tercer_binding.botonTema.setOnClickListener {
                        val intento_mapa_actividades = Intent(this@Mapa, Actividades::class.java)
                        intento_mapa_actividades.putExtra("idEstudiante", 1)
                        intento_mapa_actividades.putExtra("idMateria", 1)
                        startActivity(intento_mapa_actividades)
                        finish()
                    }
                    valor_anterior = dato.nombre_unidad
                }
            }
        }
    }
}