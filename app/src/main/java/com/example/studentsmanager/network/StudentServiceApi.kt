package com.example.studentsmanager.network

import com.example.studentsmanager.Student
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "http://192.168.0.104:8000"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


private val retrofitBuilder = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface StudentServiceApi {
    @GET("/api/students")
    suspend fun getStudents(): List<Student>
}

object StudentApi {
    val retrofit: StudentServiceApi by lazy {
        retrofitBuilder.create(StudentServiceApi::class.java)
    }
}