package com.example.quizapp.data.remote.models

import com.google.gson.annotations.SerializedName

data class QuestionResponse(
    @SerializedName("response_code")
    val responseCode: Int,
    @SerializedName("results")
    val results: List<QuestionDto>
)

data class QuestionDto(
    @SerializedName("category")
    val category: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("difficulty")
    val difficulty: String,
    @SerializedName("question")
    val question: String,
    @SerializedName("correct_answer")
    val correctAnswer: String,
    @SerializedName("incorrect_answers")
    val incorrectAnswers: List<String>
) {
    fun getAllAnswers(): List<String> {
        return (listOf(correctAnswer) + incorrectAnswers).shuffled()
    }
}

data class CategoryDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)

data class CategoriesResponse(
    @SerializedName("trivia_categories")
    val categories: List<CategoryDto>
)
