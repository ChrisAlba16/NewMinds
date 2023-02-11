package com.example.newminds.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.newminds.MainActivity
import com.example.newminds.databinding.ActivityLessonListBinding
import com.example.newminds.utils.Requests
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class LessonList : AppCompatActivity() {
    private lateinit var binding: ActivityLessonListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLessonListBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}