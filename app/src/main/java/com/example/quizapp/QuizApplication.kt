package com.example.quizapp

import android.app.Application
import android.util.Log
import com.example.quizapp.data.local.QuizDatabase
import com.example.quizapp.data.remote.api.RetrofitClient
import com.example.quizapp.data.repository.QuizRepository

class QuizApplication : Application() {
    
    companion object {
        lateinit var repository: QuizRepository
            private set
        
        private const val TAG = "QuizApplication"
    }

    override fun onCreate() {
        super.onCreate()
        
        try {
            Log.d(TAG, "Initializing QuizApplication...")
            
            // Initialize API client
            val openTdbApi = RetrofitClient.openTdbApi
            Log.d(TAG, "API client initialized")
            
            // Initialize database
            val database = QuizDatabase.getDatabase(this)
            Log.d(TAG, "Database initialized")
            
            // Create repository with both API and database
            repository = QuizRepository(openTdbApi, database.quizHistoryDao())
            Log.d(TAG, "Repository initialized successfully with database")
            
        } catch (e: Exception) {
            Log.e(TAG, "Failed to initialize QuizApplication", e)
            e.printStackTrace()
            throw RuntimeException("Failed to initialize QuizApplication: ${e.message}", e)
        }
    }
}
