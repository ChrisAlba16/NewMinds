package com.example.newminds.utils

import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import kotlinx.parcelize.Parceler
import kotlinx.parcelize.Parcelize
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.ResultSet

data class Usuario(val id: Int, val nombre: String, val contrasena: String)
data class Mapa(val nombre_unidad: String, val nombre_tema: String)
data class MapaProgreso(val id_unidad: Int, val id_tema: Int, val numero_actividad: Int)

@Parcelize
data class DatosOpciones(
    val id: Int,
    val id_tema: Int,
    val texto: String,
    val opciones: Array<String>,
    val url: String,
    val respuesta: String,
    val video: String,
    val numero_actividad: Int
) : Parcelable {
    companion object : Parceler<DatosOpciones> {
        override fun DatosOpciones.write(parcel: Parcel, flags: Int) {
            parcel.writeInt(id)
            parcel.writeInt(id_tema)
            parcel.writeString(texto)
            parcel.writeStringArray(opciones)
            parcel.writeString(url)
            parcel.writeString(respuesta)
            parcel.writeString(video)
            parcel.writeInt(numero_actividad)
        }

        override fun create(parcel: Parcel): DatosOpciones {
            return DatosOpciones(
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
data class DatosTarjetas(
    val id: Int,
    val id_tema: Int,
    val pregunta: String,
    val textos: Array<String?>,
    val urls: Array<String?>,
    val respuesta: String,
    val video: String,
    val numero_actividad: Int
) : Parcelable {
    override fun describeContents(): Int = 0

    companion object : Parceler<DatosTarjetas> {
        override fun DatosTarjetas.write(parcel: Parcel, flags: Int) {
            parcel.writeInt(id)
            parcel.writeInt(id_tema)
            parcel.writeString(pregunta)
            parcel.writeStringArray(textos)
            parcel.writeStringArray(urls)
            parcel.writeString(respuesta)
            parcel.writeString(video)
            parcel.writeInt(numero_actividad)
        }

        override fun create(parcel: Parcel): DatosTarjetas {
            return DatosTarjetas(
                parcel.readInt(),
                parcel.readInt(),
                parcel.readString() ?: "",
                parcel.createStringArray() ?: emptyArray(),
                parcel.createStringArray() ?: emptyArray(),
                parcel.readString() ?: "",
                parcel.readString() ?: "",
                parcel.readInt()
            )
        }
    }
}

@Parcelize
data class datosParejas(
    val id: Int,
    val id_tema: Int,
    val parejas: Array<String>,
    val respuestas: Array<String>,
    val video: String,
    val numero_actividad: Int
) : Parcelable {

    override fun describeContents() = 0

    companion object : Parceler<datosParejas> {

        override fun datosParejas.write(parcel: Parcel, flags: Int) {
            parcel.writeInt(id)
            parcel.writeInt(id_tema)
            parcel.writeStringArray(parejas)
            parcel.writeStringArray(respuestas)
            parcel.writeString(video)
            parcel.writeInt(numero_actividad)
        }

        override fun create(parcel: Parcel): datosParejas {
            return datosParejas(
                parcel.readInt(),
                parcel.readInt(),
                parcel.createStringArray() ?: emptyArray(),
                parcel.createStringArray() ?: emptyArray(),
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

    fun login(matricula: Int, contrasena: String): MutableList<Usuario> {
        val usuarios = mutableListOf<Usuario>()

        try {
            DriverManager.getConnection(url, user, password).use { conexion ->
                val sql = "CALL login($matricula, $contrasena);"
                val sentencia: PreparedStatement = conexion.prepareStatement(sql)
                val resultado: ResultSet = sentencia.executeQuery()
                while (resultado.next()) {
                    val id = resultado.getInt("id")
                    val nombre = resultado.getString("nombre")
                    val contrasena = resultado.getString("contra")
                    usuarios.add(Usuario(id, nombre, contrasena))
                }
            }
        } catch (e: Exception) {
            Log.e("Conexion", "Error al leer la base de datos", e)
        }
        return usuarios
    }


    fun multipleChoice(id_tema: Int, numero_actividad: Int): MutableList<DatosOpciones> {
        val preguntas = mutableListOf<DatosOpciones>()

        try {
            DriverManager.getConnection(url, user, password).use { conexion ->
                val sql = "CALL preguntas_opciones($id_tema, $numero_actividad);"
                val sentencia: PreparedStatement = conexion.prepareStatement(sql)
                val resultado: ResultSet = sentencia.executeQuery()
                while (resultado.next()) {
                    val id = resultado.getInt("id")
                    val id_tema = resultado.getInt("id_tema")
                    val texto = resultado.getString("texto")
                    val ops = arrayOf<String>(
                        resultado.getString("op1"),
                        resultado.getString("op2"),
                        resultado.getString("op3"),
                        resultado.getString("op4")
                    )
                    val url = resultado.getString("url_imagen")

                    val respuesta = resultado.getString("respuesta")
                    val video = resultado.getString("video")
                    val numero_actividad = resultado.getInt("numero_actividad")
                    preguntas.add(
                        DatosOpciones(
                            id,
                            id_tema,
                            texto,
                            ops,
                            url,
                            respuesta,
                            video,
                            numero_actividad
                        )
                    )
                }
            }
        } catch (e: Exception) {
            Log.e("Conexion", "Error al leer la base de datos", e)
        }
        return preguntas
    }

    fun flashcards(id_tema: Int, numero_actividad: Int): MutableList<DatosTarjetas> {
        val questions = mutableListOf<DatosTarjetas>()

        try {
            DriverManager.getConnection(url, user, password).use { conexion ->
                val sql = "CALL preguntas_tarjetas($id_tema, $numero_actividad);"
                val sentencia: PreparedStatement = conexion.prepareStatement(sql)
                val resultado: ResultSet = sentencia.executeQuery()
                while (resultado.next()) {
                    val id = resultado.getInt("id")
                    val id_tema = resultado.getInt("id_tema")
                    val pregunta = resultado.getString("pregunta")

                    //
                    val t1 = resultado.getString("texto1")
                    val u1 = resultado.getString("url1")

                    val t2 = resultado.getString("texto2")
                    val u2 = resultado.getString("url2")

                    val t3 = resultado.getString("texto3")
                    val u3 = resultado.getString("url3")

                    val t4 = resultado.getString("texto4")
                    val u4 = resultado.getString("url4")


                    val textos = arrayOf(t1, t2, t3, t4)
                    val urls = arrayOf(u1, u2, u3, u4)

                    val respuesta = resultado.getString("respuesta")
                    val video = resultado.getString("video")
                    val numero_actividad = resultado.getInt("numero_actividad")
                    questions.add(
                        DatosTarjetas(
                            id,
                            id_tema,
                            pregunta,
                            textos,
                            urls,
                            respuesta,
                            video,
                            numero_actividad
                        )
                    )
                }
            }
        } catch (e: Exception) {
            Log.e("Conexion", "Error al leer la base de datos", e)
        }
        return questions
    }

    fun pairs(id_tema: Int, numero_actividad: Int): MutableList<datosParejas> {
        val pares = mutableListOf<datosParejas>()

        try {
            DriverManager.getConnection(url, user, password).use { conexion ->
                val sql = "CALL preguntas_parejas($id_tema, $numero_actividad);"
                val sentencia: PreparedStatement = conexion.prepareStatement(sql)
                val resultado: ResultSet = sentencia.executeQuery()
                while (resultado.next()) {
                    val id = resultado.getInt("id")
                    val id_tema = resultado.getInt("id_tema")
                    val parejas = arrayOf(
                        resultado.getString("pareja1"),
                        resultado.getString("pareja2"),
                        resultado.getString("pareja3"),
                        resultado.getString("pareja4")
                    )
                    val respuestas = arrayOf(
                        resultado.getString("respuesta1"),
                        resultado.getString("respuesta2"),
                        resultado.getString("respuesta3"),
                        resultado.getString("respuesta4")
                    )

                    val video = resultado.getString("video")
                    val numero_actividad = resultado.getInt("numero_actividad")
                    pares.add(
                        datosParejas(
                            id,
                            id_tema,
                            parejas,
                            respuestas,
                            video,
                            numero_actividad
                        )
                    )
                }
            }
        } catch (e: Exception) {
            Log.e("Conexion", "Error al leer la base de datos", e)
        }
        return pares
    }

    fun mapa(): MutableList<Mapa> {
        val mapas = mutableListOf<Mapa>()

        try {
            DriverManager.getConnection(url, user, password).use { conexion ->
                val sql = "CALL mapa();"
                val sentencia: PreparedStatement = conexion.prepareStatement(sql)
                val resultado: ResultSet = sentencia.executeQuery()
                while (resultado.next()) {
                    val nombre_unidad = resultado.getString("nombre_Unidad")
                    val nombre_tema = resultado.getString("nombre_Tema")
                    mapas.add(Mapa(nombre_unidad, nombre_tema))
                }
            }
        } catch (e: Exception) {
            Log.e("Conexion", "Error al leer la base de datos", e)
        }
        return mapas
    }

    fun mapa_progreso(id_estudiante: Int, id_materia: Int): MapaProgreso {
        lateinit var progreso: MapaProgreso
        try {
            DriverManager.getConnection(url, user, password).use { conexion ->
                val sql = "CALL mapa_progreso($id_estudiante,$id_materia);"
                val sentencia: PreparedStatement = conexion.prepareStatement(sql)
                val resultado: ResultSet = sentencia.executeQuery()
                while (resultado.next()) {
                    val id_unidad = resultado.getInt("id_unidad")
                    val id_tema = resultado.getInt("id_tema")
                    val numero_actividad = resultado.getInt("numero_actividad")
                    progreso = MapaProgreso(id_unidad, id_tema, numero_actividad)
                }
            }
        } catch (e: Exception) {
            Log.e("Conexion", "Error al leer la base de datos", e)
        }
        return progreso
    }

    fun estudia(id_estudiante: Int): Int {
        var id_materia: Int = 0
        try {
            DriverManager.getConnection(url, user, password).use { conexion ->
                val sql = "CALL estudia($id_estudiante);"
                val sentencia: PreparedStatement = conexion.prepareStatement(sql)
                val resultado: ResultSet = sentencia.executeQuery()
                while (resultado.next()) {
                    id_materia = resultado.getInt("id_materia")
                    return id_materia
                }
            }
        } catch (e: Exception) {
            Log.e("Conexion", "Error al leer la base de datos", e)
        }
        return id_materia
    }

    fun temas_materia(id_materia: Int): MutableList<Int> {
        var temas: MutableList<Int> = arrayListOf()
        try {
            DriverManager.getConnection(url, user, password).use { conexion ->
                val sql = "CALL temas($id_materia);"
                val sentencia: PreparedStatement = conexion.prepareStatement(sql)
                val resultado: ResultSet = sentencia.executeQuery()
                while (resultado.next()) {
                    val tema = resultado.getInt("id_tema")
                    temas.add(tema)
                }
            }
        } catch (e: Exception) {
            Log.e("Conexion", "Error al leer la base de datos", e)
        }
        return temas
    }

    fun progreso(id_estudiante: Int, id_materia: Int, id_tema: Int, numero_actividad: Int) {
        try {
            DriverManager.getConnection(url, user, password).use { conexion ->
                val sql = "CALL progreso($id_estudiante, $id_materia, $id_tema, $numero_actividad);"
                val sentencia: PreparedStatement = conexion.prepareStatement(sql)
                val resultado: ResultSet = sentencia.executeQuery()
            }
        } catch (e: Exception) {
            Log.e("Conexion", "Error al leer la base de datos", e)
        }
    }

    fun materias(id_materia: Int): String {
        var nombre: String = " "
        try {
            DriverManager.getConnection(url, user, password).use { conexion ->
                val sql = "CALL materias($id_materia);"
                val sentencia: PreparedStatement = conexion.prepareStatement(sql)
                val resultado: ResultSet = sentencia.executeQuery()
                while (resultado.next()) {
                    nombre = resultado.getString("nombre")
                }
            }
        } catch (e: Exception) {
            Log.e("Conexion", "Error al leer la base de datos", e)
        }
        return nombre
    }
}