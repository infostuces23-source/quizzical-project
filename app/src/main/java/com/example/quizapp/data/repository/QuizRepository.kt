package com.example.quizapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.quizapp.data.local.dao.QuizHistoryDao
import com.example.quizapp.data.local.entity.QuizHistory
import com.example.quizapp.data.remote.api.OpenTdbApi
import com.example.quizapp.data.remote.models.QuestionDto
import java.io.IOException

class QuizRepository(
    private val openTdbApi: OpenTdbApi,
    private val quizHistoryDao: QuizHistoryDao? = null  // Make DAO nullable
) {

    // Remote Operations
    suspend fun fetchQuestions(
        category: Int,
        difficulty: String,
        amount: Int
    ): Result<List<QuestionDto>> = try {
        val response = openTdbApi.getQuestions(
            amount = amount,
            category = if (category == 0) null else category,
            difficulty = difficulty
        )
        if (response.responseCode == 0) {
            Result.success(response.results)
        } else {
            Result.failure(Exception("API Error: ${response.responseCode}"))
        }
    } catch (e: IOException) {
        Result.failure(e)
    } catch (e: Exception) {
        Result.failure(e)
    }

    suspend fun fetchCategories(): Result<List<com.example.quizapp.data.remote.models.CategoryDto>> = try {
        val response = openTdbApi.getCategories()
        Result.success(response.categories)
    } catch (e: IOException) {
        Result.failure(e)
    } catch (e: Exception) {
        Result.failure(e)
    }
    
    // Local Database Operations - return dummy data if DAO is null
    suspend fun saveQuizHistory(quizHistory: QuizHistory) {
        quizHistoryDao?.insertQuizHistory(quizHistory)
    }
    
    fun getAllQuizHistory(): LiveData<List<QuizHistory>> {
        return quizHistoryDao?.getAllQuizHistory() ?: MutableLiveData(emptyList())
    }
    
    fun getRecentQuizHistory(): LiveData<List<QuizHistory>> {
        return quizHistoryDao?.getRecentQuizHistory() ?: MutableLiveData(emptyList())
    }
    
    fun getTotalQuizzesCount(): LiveData<Int> {
        return quizHistoryDao?.getTotalQuizzesCount() ?: MutableLiveData(0)
    }
    
    fun getAverageScore(): LiveData<Double> {
        return quizHistoryDao?.getAverageScore() ?: MutableLiveData(0.0)
    }
    
    suspend fun deleteAllHistory() {
        quizHistoryDao?.deleteAllHistory()
    }
}
