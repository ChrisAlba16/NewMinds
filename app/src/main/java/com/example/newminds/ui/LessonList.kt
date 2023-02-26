package com.example.newminds.ui

import android.content.Intent
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import com.example.newminds.databinding.ActivityLessonListBinding
import com.example.newminds.utils.Mapa
import com.example.newminds.utils.Requests
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class LessonList : AppCompatActivity() {
    private lateinit var binding: ActivityLessonListBinding
    private lateinit var lista: MutableList<Mapa>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLessonListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        lista = mutableListOf<Mapa>()
        GlobalScope.launch(Dispatchers.IO){
            val mapa = async { Requests.mapa(1)}
            if (mapa.await().size != 0 ){
                for(dato in mapa.await().iterator()){
                    println(dato.nombreTema + " " + dato.nombreUnidad)
                    val leccion = RelativeLayout(binding.lienzo.context)
                    leccion.addView(ProgressBar(leccion.context))
                    val barra = leccion.children.elementAt(0) as ProgressBar
                    barra.setProgress(0,false)
                    barra.setOnClickListener {
                        val intent = Intent(this@LessonList, MultipleChoiceUI::class.java)
                        startActivity(intent)
                        finish()
                    }
                    leccion.addView(TextView(leccion.context))
                    val texto = leccion.children.elementAt(1) as TextView
                    texto.text = dato.nombreUnidad
                    lista.add(dato)
                }
            }
        }
    }
}