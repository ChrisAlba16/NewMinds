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
import com.example.newminds.databinding.ActivityMatchPairsBinding
import com.example.newminds.utils.Pairs
data class m(
    val id: Int,
    val idTema: Int,
    val pairs: Array<Array<String>>,
    val video: String,
    val numeroActividad: Int
)

class MatchPairs : AppCompatActivity() {
    private lateinit var binding: ActivityMatchPairsBinding
    private lateinit var pregunta: m
    private var contador = 0
    private var b1 = ""
    private var b2 = ""
    private var buttonClicked1 = 0
    private var buttonClicked2 = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMatchPairsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val p = intent.getParcelableExtra<Pairs>("pregunta")!!
        val pair = arrayOf(arrayOf(p.parejas[0], p.respuestas[0]),arrayOf(p.parejas[1], p.respuestas[1]),arrayOf(p.parejas[2], p.respuestas[2]),arrayOf(p.parejas[3], p.respuestas[3]))
        pregunta = m(p.id, p.idTema, pair, p.video, p.numeroActividad)
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
        binding.cancelar.setOnClickListener {
            val intent = Intent(this@MatchPairs, LessonList::class.java)
            startActivity(intent)
            finish()
        }
        binding.leftOne.text = pregunta.pairs[0][0]
        binding.leftOne.setOnClickListener {
            if(b1 != ""){
                b2 = binding.leftOne.text.toString()
                buttonClicked2 = binding.leftOne.id
                val mensaje = Toast.makeText(binding.root.context,b2,Toast.LENGTH_LONG).show()
                if(checkCorrecto(b1,b2,pregunta.pairs)){
                    val btn1 = findViewById<Button>(buttonClicked1)
                    val btn2 = findViewById<Button>(buttonClicked2)
                    btn1.isClickable = false
                    btn1.setBackgroundColor(Color.parseColor("#004000"))
                    btn2.isClickable = false
                    btn1.setBackgroundColor(Color.parseColor("#004000"))
                }
                buttonClicked1 = 0
                buttonClicked2 = 0
                b1 = ""
                b2 = ""
            }
            else{
                b1 = binding.leftOne.text.toString()
                buttonClicked1 = binding.leftOne.id
                val mensaje = Toast.makeText(binding.root.context,b1,Toast.LENGTH_LONG).show()
            }
        }

        binding.leftTwo.text = pregunta.pairs[1][0]
        binding.leftOne.setOnClickListener {
            if(b1 != ""){
                b2 = binding.leftTwo.text.toString()
                buttonClicked2 = binding.leftOne.id
                val mensaje = Toast.makeText(binding.root.context,b2,Toast.LENGTH_LONG).show()
                if(checkCorrecto(b1,b2,pregunta.pairs)){
                    val btn1 = findViewById<Button>(buttonClicked1)
                    val btn2 = findViewById<Button>(buttonClicked2)
                    btn1.isClickable = false
                    btn1.setBackgroundColor(Color.parseColor("#004000"))
                    btn2.isClickable = false
                    btn1.setBackgroundColor(Color.parseColor("#004000"))
                }
                buttonClicked1 = 0
                buttonClicked2 = 0
                b1 = ""
                b2 = ""
            }
            else{
                b1 = binding.leftTwo.text.toString()
                buttonClicked1 = binding.leftOne.id
                val mensaje = Toast.makeText(binding.root.context,b1,Toast.LENGTH_LONG).show()
            }
        }

        binding.rightOne.text = pregunta.pairs[0][1]
        binding.rightOne.setOnClickListener {
            if(b1 != ""){
                b2 = binding.rightOne.text.toString()
                buttonClicked2 = binding.leftOne.id
                val mensaje = Toast.makeText(binding.root.context,b2,Toast.LENGTH_LONG).show()
                if(checkCorrecto(b1,b2,pregunta.pairs)){
                    val btn1 = findViewById<Button>(buttonClicked1)
                    val btn2 = findViewById<Button>(buttonClicked2)
                    btn1.isClickable = false
                    btn1.setBackgroundColor(Color.parseColor("#004000"))
                    btn2.isClickable = false
                    btn1.setBackgroundColor(Color.parseColor("#004000"))
                }
                buttonClicked1 = 0
                buttonClicked2 = 0
                b1 = ""
                b2 = ""
            }
            else{
                b1 = binding.rightOne.text.toString()
                buttonClicked1 = binding.leftOne.id
                val mensaje = Toast.makeText(binding.root.context,b1,Toast.LENGTH_LONG).show()
            }
        }

        binding.rightTwo.text = pregunta.pairs[1][1]
        binding.rightTwo.setOnClickListener {
            if(b1 != ""){
                b2 = binding.rightTwo.text.toString()
                buttonClicked2 = binding.leftOne.id
                val mensaje = Toast.makeText(binding.root.context,b2,Toast.LENGTH_LONG).show()
                if(checkCorrecto(b1,b2,pregunta.pairs)){
                    val btn1 = findViewById<Button>(buttonClicked1)
                    val btn2 = findViewById<Button>(buttonClicked2)
                    btn1.isClickable = false
                    btn1.setBackgroundColor(Color.parseColor("#004000"))
                    btn2.isClickable = false
                    btn1.setBackgroundColor(Color.parseColor("#004000"))
                }
                buttonClicked1 = 0
                buttonClicked2 = 0
                b1 = ""
                b2 = ""
            }
            else{
                b1 = binding.rightTwo.text.toString()
                buttonClicked1 = binding.leftOne.id
                val mensaje = Toast.makeText(binding.root.context,b1,Toast.LENGTH_LONG).show()
            }
        }
    }
    private fun checkCorrecto(a: String, b: String, lista: Array<Array<String>>): Boolean{
        var correcto = false
        for(item in lista){
            correcto = ((a == item[0] && b == item[1]) || (a == item[1] && b == item[0])) || correcto
        }
        if(correcto){
            contador += 1
            val mensaje = Toast.makeText(binding.root.context,"Bien, sólo te falta una más",Toast.LENGTH_LONG).show()
            if (contador >= 2){
                correcto()
            }
            return true
        }
        else{
            equivocado()
            return false
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