package com.example.quizapp.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.quizapp.data.local.entity.QuizHistory

@Dao
interface QuizHistoryDao {
    
    @Insert
    suspend fun insertQuizHistory(quizHistory: QuizHistory)
    
    @Query("SELECT * FROM quiz_history ORDER BY timestamp DESC")
    fun getAllQuizHistory(): LiveData<List<QuizHistory>>
    
    @Query("SELECT * FROM quiz_history ORDER BY timestamp DESC LIMIT 10")
    fun getRecentQuizHistory(): LiveData<List<QuizHistory>>
    
    @Query("SELECT COUNT(*) FROM quiz_history")
    fun getTotalQuizzesCount(): LiveData<Int>
    
    @Query("SELECT COALESCE(AVG(score), 0.0) FROM quiz_history")
    fun getAverageScore(): LiveData<Double>
    
    @Query("SELECT * FROM quiz_history WHERE categoryId = :categoryId ORDER BY timestamp DESC")
    fun getQuizHistoryByCategory(categoryId: Int): LiveData<List<QuizHistory>>
    
    @Query("DELETE FROM quiz_history")
    suspend fun deleteAllHistory()
    
    @Query("DELETE FROM quiz_history WHERE id = :id")
    suspend fun deleteQuizHistory(id: Long)
}

