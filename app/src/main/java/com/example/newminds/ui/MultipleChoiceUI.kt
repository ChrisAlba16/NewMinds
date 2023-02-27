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
import com.example.newminds.databinding.ActivityMultiplechoiceBinding
import com.example.newminds.utils.MultipleChoice


class MultipleChoiceUI:AppCompatActivity() {
    private lateinit var binding: ActivityMultiplechoiceBinding
    private lateinit var pregunta: MultipleChoice
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityMultiplechoiceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        pregunta = intent.getParcelableExtra<MultipleChoice>("pregunta")!!
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
        val answer = Integer.parseInt(pregunta.respuesta)
        binding.textView.text = pregunta.texto
        binding.option1.text = pregunta.options[0]
        binding.option2.text = pregunta.options[1]
        binding.option3.text = pregunta.options[2]
        binding.option4.text = pregunta.options[3]
        binding.option1.setOnClickListener {
            checkCorrecto(1,answer)
        }
        binding.option2.setOnClickListener {
            checkCorrecto(2,answer)
        }
        binding.option3.setOnClickListener {
            checkCorrecto(3,answer)
        }
        binding.option4.setOnClickListener {
            checkCorrecto(4,answer)
        }
        binding.cancelar.setOnClickListener {
            val intent = Intent(this@MultipleChoiceUI, LessonList::class.java)
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
        val colorDrawables = arrayOf(ColorDrawable(Color.parseColor("#007f00")),ColorDrawable(Color.WHITE))
        val transitionDrawable = TransitionDrawable(colorDrawables)
        binding.fondo.background = transitionDrawable
        val mensaje = Toast.makeText(binding.root.context,"¡Muy bien hecho!",Toast.LENGTH_LONG).show()
        transitionDrawable.startTransition(2500)
        val handler = Handler()
        handler.postDelayed({
            finish()
        }, 2500)
    }
    private fun equivocado(){
        val colorDrawables = arrayOf(ColorDrawable(Color.parseColor("#7f0000")),ColorDrawable(Color.WHITE))
        val transitionDrawable = TransitionDrawable(colorDrawables)
        binding.fondo.background = transitionDrawable
        val mensaje = Toast.makeText(binding.root.context,"Parece que esa no es la respuesta, vuelve a intentar",Toast.LENGTH_LONG).show()
        transitionDrawable.startTransition(1000)
    }
}