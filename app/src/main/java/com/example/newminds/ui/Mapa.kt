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
    private lateinit var lista: MutableList<Mapa>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActividadMapaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        lista = mutableListOf<Mapa>()
        GlobalScope.launch(Dispatchers.IO) {
            val mapa = async { Requests.mapa(1) }
            if (mapa.await().size != 0) {
                var previousValue: String? = null
                for ((index, dato) in mapa.await().iterator().withIndex()) {
                    if (previousValue == null || previousValue != dato.nombreUnidad) {
                        runOnUiThread {
                            val inflater = LayoutInflater.from(this@Mapa)
                            val subLayoutBinding =
                                UnidadBinding.inflate(inflater, binding.lienzo, true)
                            subLayoutBinding.titulo.setText("Unidad ${index + 1}")
                            subLayoutBinding.subtitulo.setText(dato.nombreUnidad)
                            val infla = LayoutInflater.from(this@Mapa)
                            val sub = TemaBinding.inflate(infla, binding.lienzo, true)
                            sub.progreso.progress = 33
                            sub.myButton.setOnClickListener {
                                val intent = Intent(this@Mapa, Actividades::class.java)
                                intent.putExtra("idEstudiante", 1)
                                intent.putExtra("idMateria", 1)
                                startActivity(intent)
                                finish()
                            }
                        }
                        previousValue = dato.nombreUnidad
                    } else {
                        runOnUiThread {
                            val infla = LayoutInflater.from(this@Mapa)
                            val sub = TemaBinding.inflate(infla, binding.lienzo, true)
                            sub.progreso.progress = 0
                            sub.myButton.setOnClickListener {
                            }
                        }
                    }
                }
            }
        }
    }
}