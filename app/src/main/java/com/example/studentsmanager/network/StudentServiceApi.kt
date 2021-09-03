package com.example.studentsmanager.network

import com.example.studentsmanager.Student
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 *  Tạo interface API, Json Converter Moshi, Retrofit
 * */

private const val BASE_URL = "http://192.168.0.104:8000/api/"

val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


private val retrofitBuilder = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface StudentServiceApi {
    @GET("students")
    suspend fun getStudents(): List<Student>

    @POST("students")
    suspend fun createStudent(@Body student: Student): Response<Student>

    @GET("students")
    suspend fun searchStudent(@Query("nameLike") name: String): List<Student>
}

object StudentApi {
    val retrofit: StudentServiceApi by lazy {
        retrofitBuilder.create(StudentServiceApi::class.java)
    }
}