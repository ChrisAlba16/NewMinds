package com.example.newminds.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.newminds.databinding.ActivityMultiplechoiceBinding
import com.example.newminds.utils.MultipleChoice

class MultipleChoiceUI:AppCompatActivity() {
    lateinit var binding: ActivityMultiplechoiceBinding
    lateinit var pregunta: MultipleChoice
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = com.example.newminds.databinding.ActivityMultiplechoiceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val respuestas = arrayOf("Una ideología","Una política","Un instituto","Un país")
        val enlaces = arrayOf("","","","")
        pregunta = MultipleChoice(1,1,"¿Qué es el comunismo?",respuestas,enlaces,"1","",1)
        binding.textView.text = pregunta.texto
        binding.option1.text = pregunta.options[0]
        binding.option2.text = pregunta.options[1]
        binding.option3.text = pregunta.options[2]
        binding.option4.text = pregunta.options[3]
        binding.option1.setOnClickListener {
            checkCorrecto(1,pregunta.respuesta.toInt())
        }
        binding.option2.setOnClickListener {
            checkCorrecto(2,pregunta.respuesta.toInt())
        }
        binding.option3.setOnClickListener {
            checkCorrecto(3,pregunta.respuesta.toInt())
        }
        binding.option4.setOnClickListener {
            checkCorrecto(4,pregunta.respuesta.toInt())
        }
    }
    fun checkCorrecto(opcion: Int, respuesta: Int){
        if(opcion == respuesta){
            correcto()
        }
        else{
            equivocado()
        }
    }
    fun correcto(){

    }
    fun equivocado(){
        
    }
}