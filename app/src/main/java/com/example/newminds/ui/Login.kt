package com.example.newminds.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.newminds.MainActivity
import com.example.newminds.databinding.ActividadLoginBinding
import com.example.newminds.utils.Requests
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class Login : AppCompatActivity() {
    private lateinit var binding: ActividadLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActividadLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signInButton.setOnClickListener {
            try {
                val matricula = Integer.parseInt(binding.matricula.text.toString())
                val contra = binding.password.text.toString()

                GlobalScope.launch(Dispatchers.IO) {
                    val answer = async { Requests.login(matricula, contra) }
                    if (answer.await().size == 1) {
                        val intent = Intent(this@Login, Mapa::class.java)
                        startActivity(intent)
                        finish()
                    }
                }

            } catch (e: Exception) {
                println(e.message)
            }

        }

        binding.backButton.setOnClickListener {
            val intent = Intent(this@Login, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}