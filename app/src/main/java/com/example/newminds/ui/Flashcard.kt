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
import com.example.newminds.databinding.ActivityFlashcardsBinding
import com.example.newminds.utils.FlashCards
data class f(
    val id: Int,
    val idTema: Int,
    val pregunta: String,
    val flashCard: Array<Array<String>>,
    val respuesta: String,
    val video: String,
    val numeroActividad: Int
)

class Flashcard:AppCompatActivity() {
    lateinit var binding: ActivityFlashcardsBinding
    lateinit var pregunta: f
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFlashcardsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val p = intent.getParcelableExtra<FlashCards>("pregunta")!!
        val pair = arrayOf(arrayOf(p.textos[0]?:"", p.urls[0]?:""),arrayOf(p.textos[1]?:"", p.urls[1]?:""),arrayOf(p.textos[2]?:"", p.urls[2]?:""),arrayOf(p.textos[3]?:"", p.urls[3]?:""))
        pregunta = f(p.id, p.idTema, p.pregunta, pair, p. respuesta, p.video, p.numeroActividad)
        binding.textView.text = pregunta.pregunta
        if(pregunta.video == "" || pregunta.video == null){
            binding.duda.visibility = View.INVISIBLE
        }
        else{
            binding.duda.setOnClickListener {
                val link = Uri.parse(pregunta.video)
                val i = Intent(Intent.ACTION_VIEW,link)
                startActivity(i)
            }
        }
        binding.SupIzq.setImageURI(Uri.parse(pregunta.flashCard[0][1]))
        binding.SupIzq.setOnClickListener {
            checkCorrecto(1,Integer.parseInt(pregunta.respuesta))
        }
        binding.txt1.text = pregunta.flashCard[0][0]
        binding.SupDer.setImageURI(Uri.parse(pregunta.flashCard[1][1]))
        binding.SupDer.setOnClickListener {
            checkCorrecto(2,Integer.parseInt(pregunta.respuesta))
        }
        binding.txt2.text = pregunta.flashCard[1][0]
        binding.InfIzq.setImageURI(Uri.parse(pregunta.flashCard[2][1]))
        binding.InfIzq.setOnClickListener {
            checkCorrecto(3,Integer.parseInt(pregunta.respuesta))
        }
        binding.txt3.text = pregunta.flashCard[2][0]
        binding.InfDer.setImageURI(Uri.parse(pregunta.flashCard[3][1]))
        binding.InfDer.setOnClickListener {
            checkCorrecto(4,Integer.parseInt(pregunta.respuesta))
        }
        binding.txt4.text = pregunta.flashCard[3][0]
        binding.cancelar.setOnClickListener {
            val intent = Intent(this@Flashcard, LessonList::class.java)
            startActivity(intent)
            finish()
        }
    }
    private fun checkCorrecto(opcion: Int, respuesta: Int){
        if(opcion == respuesta){
            correcto()
        }
        else{
            equivocado()
        }
    }
    private fun correcto(){
        val colorDrawables = arrayOf(
            ColorDrawable(Color.parseColor("#007f00")),
            ColorDrawable(Color.WHITE)
        )
        val transitionDrawable = TransitionDrawable(colorDrawables)
        binding.fondo.background = transitionDrawable
        val mensaje = Toast.makeText(binding.root.context,"¡Muy bien hecho!", Toast.LENGTH_LONG).show()
        transitionDrawable.startTransition(2500)
        val handler = Handler()
        handler.postDelayed({
            finish()
        }, 2500)
    }
    private fun equivocado(){
        val colorDrawables = arrayOf(
            ColorDrawable(Color.parseColor("#7f0000")),
            ColorDrawable(Color.WHITE)
        )
        val transitionDrawable = TransitionDrawable(colorDrawables)
        binding.fondo.background = transitionDrawable
        val mensaje = Toast.makeText(binding.root.context,"Parece que esa no es la respuesta, vuelve a intentar",
            Toast.LENGTH_LONG).show()
        transitionDrawable.startTransition(1000)
    }
}