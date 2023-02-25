package com.example.newminds.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.newminds.databinding.ActivityLessonListBinding

class LessonList : AppCompatActivity() {
    private lateinit var binding: ActivityLessonListBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLessonListBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.uno.setOnClickListener {
            val intent = Intent(this@LessonList, MatchPairs::class.java)
            startActivity(intent)
            finish()
        }
    }


}