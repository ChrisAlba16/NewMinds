package com.example.newminds.utils

import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import kotlinx.parcelize.Parceler
import kotlinx.parcelize.Parcelize
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.ResultSet

data class User(val id: Int, val nombre: String, val contra: String)
data class Mapa(val nombreUnidad: String, val nombreTema: String)
data class Progreso(val idTema: Int, val numero_actividad: Int)
@Parcelize
data class MultipleChoice(
    val id: Int,
    val idTema: Int,
    val texto: String,
    val options: Array<String>,
    val url: String,
    val respuesta: String,
    val video: String,
    val numeroActividad: Int
) : Parcelable {
    companion object : Parceler<MultipleChoice> {
        override fun MultipleChoice.write(parcel: Parcel, flags: Int) {
            parcel.writeInt(id)
            parcel.writeInt(idTema)
            parcel.writeString(texto)
            parcel.writeStringArray(options)
            parcel.writeString(url)
            parcel.writeString(respuesta)
            parcel.writeString(video)
            parcel.writeInt(numeroActividad)
        }

        override fun create(parcel: Parcel): MultipleChoice {
            return MultipleChoice(
                parcel.readInt(),
                parcel.readInt(),
                parcel.readString() ?: "",
                parcel.createStringArray() ?: emptyArray(),
                parcel.readString() ?: "",
                parcel.readString() ?: "",
                parcel.readString() ?: "",
                parcel.readInt()
            )
        }
    }
}

@Parcelize
data class FlashCards(
    val id: Int,
    val idTema: Int,
    val pregunta: String,
    val flashCard: Array<Array<String>>,
    val respuesta: String,
    val video: String,
    val numeroActividad: Int
): Parcelable {
    override fun describeContents(): Int = 0

    companion object : Parceler<FlashCards> {
        override fun FlashCards.write(parcel: Parcel, flags: Int) {
            parcel.writeInt(id)
            parcel.writeInt(idTema)
            parcel.writeString(pregunta)
            parcel.writeArray(flashCard)
            parcel.writeString(respuesta)
            parcel.writeString(video)
            parcel.writeInt(numeroActividad)
        }

        override fun create(parcel: Parcel): FlashCards {
            return FlashCards(
                parcel.readInt(),
                parcel.readInt(),
                parcel.readString() ?: "",
                parcel.readArray(Array<String>::class.java.classLoader) as Array<Array<String>>,
                parcel.readString() ?: "",
                parcel.readString() ?: "",
                parcel.readInt()
            )
        }
    }
}

@Parcelize
data class Pairs(
    val id: Int,
    val idTema: Int,
    val pairs: Array<Array<String>>,
    val video: String,
    val numeroActividad: Int
) : Parcelable {

    override fun describeContents() = 0

    companion object : Parceler<Pairs> {

        override fun Pairs.write(parcel: Parcel, flags: Int) {
            parcel.writeInt(id)
            parcel.writeInt(idTema)
            parcel.writeArray(pairs)
            parcel.writeString(video)
            parcel.writeInt(numeroActividad)
        }

        override fun create(parcel: Parcel): Pairs {
            return Pairs(
                parcel.readInt(),
                parcel.readInt(),
                parcel.readArray(Array<Array<String>>::class.java.classLoader) as Array<Array<String>>,
                parcel.readString() ?: "",
                parcel.readInt()
            )
        }
    }
}

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
                val sql = "CALL preguntas_multiplechoice($idTema, $numeroActividad);"
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
                    val url = resultSet.getString("url_imagen")

                    val respuesta = resultSet.getString("respuesta")
                    val video = resultSet.getString("video")
                    val numeroActividad = resultSet.getInt("numero_actividad")
                    questions.add(
                        MultipleChoice(
                            id,
                            idTema,
                            texto,
                            ops,
                            url,
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

    fun flashcards(idTema: Int, numeroActividad: Int): MutableList<FlashCards> {
        val questions = mutableListOf<FlashCards>()

        try {
            DriverManager.getConnection(url, user, password).use { connection ->
                val sql = "CALL preguntas_flashcards($idTema, $numeroActividad);"
                val statement: PreparedStatement = connection.prepareStatement(sql)
                val resultSet: ResultSet = statement.executeQuery()
                while (resultSet.next()) {
                    val id = resultSet.getInt("id")
                    val idTema = resultSet.getInt("id_tema")
                    val pregunta = resultSet.getString("pregunta")
                    val flashCards = arrayOf(
                        arrayOf<String>(
                            resultSet.getString("texto1"),
                            resultSet.getString("url1")
                        ),
                        arrayOf<String>(
                            resultSet.getString("texto2"),
                            resultSet.getString("url2")
                        ),
                        arrayOf<String>(
                            resultSet.getString("texto3"),
                            resultSet.getString("url3")
                        ),
                        arrayOf<String>(
                            resultSet.getString("texto4"),
                            resultSet.getString("url4")
                        )
                    )

                    val respuesta = resultSet.getString("respuesta")
                    val video = resultSet.getString("video")
                    val numeroActividad = resultSet.getInt("numero_actividad")
                    questions.add(
                        FlashCards(
                            id,
                            idTema,
                            pregunta,
                            flashCards,
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

    fun pairs(idTema: Int, numeroActividad: Int): MutableList<Pairs> {
        val pares = mutableListOf<Pairs>()

        try {
            DriverManager.getConnection(url, user, password).use { connection ->
                val sql = "CALL preguntas_pairs($idTema, $numeroActividad);"
                val statement: PreparedStatement = connection.prepareStatement(sql)
                val resultSet: ResultSet = statement.executeQuery()
                while (resultSet.next()) {
                    val id = resultSet.getInt("id")
                    val idTema = resultSet.getInt("id_tema")
                    val pairs = arrayOf(
                        arrayOf<String>(
                            resultSet.getString("pareja1"),
                            resultSet.getString("respuesta1")
                        ),
                        arrayOf<String>(
                            resultSet.getString("pareja2"),
                            resultSet.getString("respuesta2")
                        ),
                        arrayOf<String>(
                            resultSet.getString("pareja3"),
                            resultSet.getString("respuesta3")
                        ),
                        arrayOf<String>(
                            resultSet.getString("pareja4"),
                            resultSet.getString("respuesta4")
                        )
                    )
                    val video = resultSet.getString("video")
                    val numeroActividad = resultSet.getInt("numero_actividad")
                    pares.add(
                        Pairs(
                            id,
                            idTema,
                            pairs,
                            video,
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

    fun progreso(idEstudiante: Int, idMateria: Int): Progreso {
        lateinit var progreso: Progreso
        try {
            DriverManager.getConnection(url, user, password).use { connection ->
                val sql = "CALL progreso_estudiante($idEstudiante,$idMateria);"
                val statement: PreparedStatement = connection.prepareStatement(sql)
                val resultSet: ResultSet = statement.executeQuery()
                while (resultSet.next()) {
                    val idTema = resultSet.getInt("id_tema")
                    val numeroActividad = resultSet.getInt("numero_actividad")
                    progreso  = Progreso(idTema, numeroActividad)
                }
            }
        } catch (e: Exception) {
            Log.e("Connection", "Error reading database information", e)
        }
        return progreso
    }
}