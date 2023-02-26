package com.example.newminds.utils

import android.util.Log
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.ResultSet

data class User(val id: Int, val nombre: String, val contra: String)
data class Mapa(val nombreUnidad: String, val nombreTema: String)
data class MultipleChoice(
    val id: Int,
    val idTema: Int,
    val texto: String,
    val options: Array<String>,
    val urls: Array<String>,
    val respuesta: String,
    val video: String,
    val numeroActividad: Int
)

data class Parejas(
    val id: Int,
    val idTema: Int,
    val parejas: Array<String>,
    val respuestas: Array<String>,
    val videos: Array<String>,
    val numeroActividad: Int
)

object Requests {
    private const val url = "jdbc:mysql://sql.freedb.tech:3306/freedb_newminds"
    private const val user = "freedb_newminds"
    private const val password = "4ZJ7JuGNjY4TG&z"

    fun login(matricula: Int, contra: String): MutableList<User> {
        val users = mutableListOf<User>()

        try {
            DriverManager.getConnection(url, user, password).use { connection ->
                val sql = "CALL login($matricula, $contra);"
                val statement: PreparedStatement = connection.prepareStatement(sql)
                val resultSet: ResultSet = statement.executeQuery()
                while (resultSet.next()) {
                    val id = resultSet.getInt("id")
                    val nombre = resultSet.getString("nombre")
                    val contra = resultSet.getString("contra")
                    users.add(User(id, nombre, contra))
                }
            }
        } catch (e: Exception) {
            Log.e("Connection", "Error reading database information", e)
        }
        return users
    }


    fun multipleChoice(idTema: Int, numeroActividad: Int): MutableList<MultipleChoice> {
        val questions = mutableListOf<MultipleChoice>()

        try {
            DriverManager.getConnection(url, user, password).use { connection ->
                val sql = "CALL preguntas($idTema, $numeroActividad);"
                val statement: PreparedStatement = connection.prepareStatement(sql)
                val resultSet: ResultSet = statement.executeQuery()
                while (resultSet.next()) {
                    val id = resultSet.getInt("id")
                    val idTema = resultSet.getInt("id_tema")
                    val texto = resultSet.getString("texto")
                    val ops = arrayOf<String>(
                        resultSet.getString("op1"),
                        resultSet.getString("op2"),
                        resultSet.getString("op3"),
                        resultSet.getString("op4")
                    )
                    val urls = arrayOf<String>(
                        resultSet.getString("url1"),
                        resultSet.getString("url2"),
                        resultSet.getString("url3"),
                        resultSet.getString("url4")
                    )
                    val respuesta = resultSet.getString("respuesta")
                    val video = resultSet.getString("video")
                    val numeroActividad = resultSet.getInt("numero_actividad")
                    questions.add(
                        MultipleChoice(
                            id,
                            idTema,
                            texto,
                            ops,
                            urls,
                            respuesta,
                            video,
                            numeroActividad
                        )
                    )
                }
            }
        } catch (e: Exception) {
            Log.e("Connection", "Error reading database information", e)
        }
        return questions
    }

    fun parejas(idTema: Int, numeroActividad: Int): MutableList<Parejas> {
        val pares = mutableListOf<Parejas>()

        try {
            DriverManager.getConnection(url, user, password).use { connection ->
                val sql = "CALL parejas($idTema, $numeroActividad);"
                val statement: PreparedStatement = connection.prepareStatement(sql)
                val resultSet: ResultSet = statement.executeQuery()
                while (resultSet.next()) {
                    val id = resultSet.getInt("id")
                    val idTema = resultSet.getInt("id_tema")
                    val parejas = arrayOf<String>(
                        resultSet.getString("pareja1"),
                        resultSet.getString("pareja2"),
                        resultSet.getString("pareja3"),
                        resultSet.getString("pareja4")
                    )
                    val respuestas = arrayOf<String>(
                        resultSet.getString("respuesta1"),
                        resultSet.getString("respuesta2"),
                        resultSet.getString("respuesta3"),
                        resultSet.getString("respuesta4")
                    )
                    val videos = arrayOf<String>(
                        resultSet.getString("video1"),
                        resultSet.getString("video1"),
                        resultSet.getString("video1"),
                        resultSet.getString("video1")
                    )
                    val numeroActividad = resultSet.getInt("numero_actividad")
                    pares.add(
                        Parejas(
                            id,
                            idTema,
                            parejas,
                            respuestas,
                            videos,
                            numeroActividad
                        )
                    )
                }
            }
        } catch (e: Exception) {
            Log.e("Connection", "Error reading database information", e)
        }
        return pares
    }

    fun mapa(idMateria: Int): MutableList<Mapa> {
        val mapas = mutableListOf<Mapa>()

        try {
            DriverManager.getConnection(url, user, password).use { connection ->
                val sql = "CALL mapa($idMateria);"
                val statement: PreparedStatement = connection.prepareStatement(sql)
                val resultSet: ResultSet = statement.executeQuery()
                while (resultSet.next()) {
                    val nombreUnidad = resultSet.getString("nombre_Unidad")
                    val nombreTema = resultSet.getString("nombre_Tema")
                    mapas.add(Mapa(nombreUnidad, nombreTema))
                }
            }
        } catch (e: Exception) {
            Log.e("Connection", "Error reading database information", e)
        }
        return mapas
    }
}