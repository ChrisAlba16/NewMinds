package com.example.newminds.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.newminds.databinding.ActivityMatchPairsBinding
import java.lang.Thread.sleep

class MatchPairs : AppCompatActivity() {
    private lateinit var binding: ActivityMatchPairsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMatchPairsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var leftOne = 2
        var leftTwo = 2
        var rightOne = 2
        var rightTwo = 2
        var pairOne = 2
        var pairTwo = 2

        binding.leftOne.setOnClickListener {
            if ((leftOne == 1 && rightTwo == 0) || (leftOne == 0 && rightTwo == 1)) {
                binding.fondo.setBackgroundColor(Color.RED)
                sleep(1000)
                val intent = Intent(this@MatchPairs, MatchPairs::class.java)
                startActivity(intent)
                finish()
            }
            leftOne = 1
            leftTwo = 0
            rightOne = 0
            rightTwo = 0
            binding.leftOne.setTextColor(Color.BLUE)
            binding.leftTwo.setTextColor(Color.BLACK)
            if (leftOne == 1 && rightTwo == 1) {
                pairOne = 1
                binding.leftOne.isClickable = false
                binding.rightTwo.isClickable = false
                binding.leftOne.setTextColor(Color.GREEN)
                binding.rightTwo.setTextColor(Color.GREEN)
            }

            if (pairOne == 1 && pairTwo == 1) {
                val intent = Intent(this@MatchPairs, LessonList::class.java)
                startActivity(intent)
                finish()
            }
        }
        binding.leftTwo.setOnClickListener {
            leftOne = 0
            leftTwo = 1
            rightOne = 0
            rightTwo = 0
            binding.leftTwo.setTextColor(Color.BLUE)
            binding.leftOne.setTextColor(Color.BLACK)
            if (leftTwo == 1 && rightOne == 1) {
                pairTwo = 1
                binding.leftTwo.isClickable = false
                binding.rightOne.isClickable = false
                binding.leftTwo.setTextColor(Color.GREEN)
                binding.rightOne.setTextColor(Color.GREEN)
            }

            if (pairOne == 1 && pairTwo == 1) {
                val intent = Intent(this@MatchPairs, LessonList::class.java)
                startActivity(intent)
                finish()
            }
        }

        binding.rightOne.setOnClickListener {
            leftOne = 0
            leftTwo = 0
            rightOne = 1
            rightTwo = 0
            binding.rightOne.setTextColor(Color.BLUE)
            binding.rightTwo.setTextColor(Color.BLACK)
            if (leftTwo == 1 && rightOne == 1) {
                pairTwo = 1
                binding.leftTwo.isClickable = false
                binding.rightOne.isClickable = false
                binding.leftTwo.setTextColor(Color.GREEN)
                binding.rightOne.setTextColor(Color.GREEN)
            }

            if (pairOne == 1 && pairTwo == 1) {
                val intent = Intent(this@MatchPairs, LessonList::class.java)
                startActivity(intent)
                finish()
            }
        }
        binding.rightTwo.setOnClickListener {
            leftOne = 0
            leftTwo = 0
            rightOne = 0
            rightTwo = 1
            binding.rightTwo.setTextColor(Color.BLUE)
            binding.rightOne.setTextColor(Color.BLACK)
            if (leftOne == 1 && rightTwo == 1) {
                pairOne = 1
                binding.leftOne.isClickable = false
                binding.rightTwo.isClickable = false
                binding.leftOne.setTextColor(Color.GREEN)
                binding.rightTwo.setTextColor(Color.GREEN)
            }

            if (pairOne == 1 && pairTwo == 1) {
                val intent = Intent(this@MatchPairs, LessonList::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}