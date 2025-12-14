package com.example.quizapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quiz_history")
data class QuizHistory(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val categoryId: Int,
    val categoryName: String,
    val difficulty: String,
    val totalQuestions: Int,
    val correctAnswers: Int,
    val score: Int,
    val timestamp: Long = System.currentTimeMillis()
)

