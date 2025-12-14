package com.example.quizapp.data.remote.api

import com.example.quizapp.data.remote.models.CategoriesResponse
import com.example.quizapp.data.remote.models.QuestionResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenTdbApi {
    
    @GET("api.php")
    suspend fun getQuestions(
        @Query("amount") amount: Int,
        @Query("category") category: Int? = null,
        @Query("difficulty") difficulty: String? = null,
        @Query("type") type: String = "multiple"
    ): QuestionResponse

    @GET("api_category.php")
    suspend fun getCategories(): CategoriesResponse
}
