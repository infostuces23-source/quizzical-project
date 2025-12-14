package com.example.quizapp.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.quizapp.QuizApplication
import com.example.quizapp.R
import com.example.quizapp.databinding.ActivityResultBinding
import com.example.quizapp.viewmodel.QuizViewModel
import com.example.quizapp.viewmodel.QuizViewModelFactory

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
    private val viewModel: QuizViewModel by viewModels {
        QuizViewModelFactory(QuizApplication.repository)
    }

    private var correctAnswers: Int = 0
    private var totalQuestions: Int = 0
    private var score: Float = 0f
    private var category: String = ""
    private var categoryId: Int = 0
    private var difficulty: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get data from intent
        correctAnswers = intent.getIntExtra("correctAnswers", 0)
        totalQuestions = intent.getIntExtra("totalQuestions", 0)
        score = intent.getFloatExtra("score", 0f)
        category = intent.getStringExtra("category") ?: "General"
        categoryId = intent.getIntExtra("categoryId", 0)
        difficulty = intent.getStringExtra("difficulty") ?: "medium"

        displayResults()
        setupButtons()
        
        // Save quiz history to database with correct data
        viewModel.saveQuizHistory(
            categoryId = categoryId,
            categoryName = category,
            difficulty = difficulty,
            totalQuestions = totalQuestions,
            correctAnswers = correctAnswers,
            scorePercentage = score.toInt()
        )
    }

    private fun displayResults() {
        val wrongAnswers = totalQuestions - correctAnswers

        binding.scorePercentage.text = String.format("%.1f%%", score)
        binding.correctAnswersCount.text = correctAnswers.toString()
        binding.wrongAnswersCount.text = wrongAnswers.toString()
        binding.totalQuestions.text = totalQuestions.toString()
        binding.categoryText.text = category
    }

    private fun setupButtons() {
        binding.backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }

        binding.playAgainButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }

}
