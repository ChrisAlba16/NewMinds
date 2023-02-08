package com.example.newminds

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.newminds.databinding.ActivityLessonListBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLessonListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLessonListBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}