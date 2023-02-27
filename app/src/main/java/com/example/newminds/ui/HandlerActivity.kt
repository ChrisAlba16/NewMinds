package com.example.newminds.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.newminds.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class HandlerActivity() : AppCompatActivity() {

    private lateinit var match: MutableList<Pairs>
    private lateinit var multiple: MutableList<MultipleChoice>
    private lateinit var flash: MutableList<FlashCards>

    //private lateinit var progreso: Progreso
    var contador = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val idMateria = intent.getIntExtra("idMateria", 1)
        val idEstudiante = intent.getIntExtra("idEstudiante", 1)
        GlobalScope.launch(Dispatchers.IO) {
            //progreso = Requests.progreso(idEstudiante, idMateria)
            async {
                match = Requests.pairs(1, 1)
                multiple = Requests.multipleChoice(1, 1)
                flash = Requests.flashcards(1, 1)
            }.await()
            contador += 1
            val i = Intent(this@HandlerActivity, MultipleChoiceUI::class.java)
            i.putExtra("pregunta", multiple[0])
            startActivity(i)

        }
    }

    override fun onResume() {
        super.onResume()
        when (contador) {
            1 -> {
                val i = Intent(this@HandlerActivity, MultipleChoiceUI::class.java)
                i.putExtra("pregunta", multiple[0])
                startActivity(i)
            }
            2 -> {
                val i = Intent(this@HandlerActivity, MultipleChoiceUI::class.java)
                i.putExtra("pregunta", multiple[1])
                startActivity(i)
            }
            3 -> {
                val i = Intent(this@HandlerActivity, MultipleChoiceUI::class.java)
                i.putExtra("pregunta", multiple[2])
                startActivity(i)
            }
            4 -> {
                val i = Intent(this@HandlerActivity, Flashcard::class.java)
                i.putExtra("pregunta", flash[0])
                startActivity(i)
            }
            5 -> {
                val i = Intent(this@HandlerActivity, Flashcard::class.java)
                i.putExtra("pregunta", flash[1])
                startActivity(i)
            }
            6 -> {
                val i = Intent(this@HandlerActivity, Flashcard::class.java)
                i.putExtra("pregunta", flash[2])
                startActivity(i)
            }
            7 -> {
                val i = Intent(this@HandlerActivity, MatchPairs::class.java)
                i.putExtra("pregunta", match[0])
                startActivity(i)
            }
            8 -> {
                val i = Intent(this@HandlerActivity, MatchPairs::class.java)
                i.putExtra("pregunta", match[1])
                startActivity(i)
            }
            9 -> {
                val i = Intent(this@HandlerActivity, MatchPairs::class.java)
                i.putExtra("pregunta", match[2])
                startActivity(i)
            }
            else -> {
                val i = Intent(this@HandlerActivity, LessonList::class.java)
                startActivity(i)
                finish()
            }
        }
    }
}