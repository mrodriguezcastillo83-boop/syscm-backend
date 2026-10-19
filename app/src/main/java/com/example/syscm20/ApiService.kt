package com.example.syscm20

import com.example.syscm20.model.*
import retrofit2.Call
import retrofit2.http.*

data class LoginRequest(val username: String, val password: String)
data class LoginResponse(val status: String, val nombre_completo: String, val rol: String)
data class RegistroRequest(val nombre_completo: String, val username: String, val password: String, val rol: String)
data class RegistroResponse(val status: String, val message: String)
data class UsuarioResponse(val username: String, val nombre_completo: String)

interface ApiService {
    @POST("login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @POST("registro")
    fun registrarUsuario(@Body request: RegistroRequest): Call<RegistroResponse>

    @GET("doctores")
    fun obtenerDoctores(): Call<List<UsuarioResponse>>

    @GET("coordinadores")
    fun obtenerCoordinadores(): Call<List<UsuarioResponse>>

    @GET("enfermeros")
    fun obtenerEnfermeros(): Call<List<UsuarioResponse>>

    @GET("pacientes")
    fun getPacientes(): Call<List<Paciente>>

    @POST("pacientes")
    fun guardarPaciente(@Body paciente: Paciente): Call<RegistroResponse>

    @GET("citas")
    fun getCitas(): Call<List<Cita>>

    @POST("citas")
    fun registrarCita(@Body cita: CitaRequest): Call<Any>

    @GET("expedientes")
    fun getExpedientes(): Call<List<Expediente>>

    @POST("guardar_expediente")
    fun guardarExpediente(@Body expediente: ExpedienteRequest): Call<Any>

    @GET("diagnosticos")
    fun getDiagnosticos(): Call<List<Diagnostico>>

    @POST("diagnosticos")
    fun registrarDiagnostico(@Body diagnostico: DiagnosticoRequest): Call<Any>

    @GET("recetas")
    fun getRecetas(): Call<List<Receta>>

    @POST("recetas")
    fun registrarReceta(@Body request: RecetaRequest): Call<Any>

    @DELETE("eliminar/{username}")
    fun eliminarUsuario(@Path("username") username: String): Call<RegistroResponse>

    @PUT("actualizar")
    fun actualizarUsuario(@Body request: RegistroRequest): Call<RegistroResponse>
}