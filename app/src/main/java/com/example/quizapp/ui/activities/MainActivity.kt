package com.example.quizapp.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.quizapp.QuizApplication
import com.example.quizapp.R
import com.example.quizapp.databinding.ActivityMainBinding
import com.example.quizapp.data.remote.models.CategoryDto
import com.example.quizapp.viewmodel.QuizViewModel
import com.example.quizapp.viewmodel.QuizViewModelFactory

class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    private val viewModel: QuizViewModel by viewModels {
        try {
            QuizViewModelFactory(QuizApplication.repository)
        } catch (e: Exception) {
            e.printStackTrace()
            throw RuntimeException("Repository not initialized", e)
        }
    }
    
    private var selectedCategoryId: Int = 0
    private var selectedDifficulty: String = "easy"
    private var numberOfQuestions: Int = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        try {
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)

            setupViews()
            observeViewModel()
        } catch (e: Exception) {
            e.printStackTrace()
            // Show error and finish activity
            finish()
        }
    }

    private fun setupViews() {
        // Setup difficulty chip group
        binding.difficultyGroup.setOnCheckedStateChangeListener { _, checkedIds ->
            selectedDifficulty = when (checkedIds.firstOrNull()) {
                R.id.easyChip -> "easy"
                R.id.mediumChip -> "medium"
                R.id.hardChip -> "hard"
                else -> "easy"
            }
        }

        // Setup question count slider
        binding.questionCountSlider.addOnChangeListener { _, value, _ ->
            numberOfQuestions = value.toInt()
            binding.questionCountText.text = "$numberOfQuestions questions"
        }

        // Set initial values
        numberOfQuestions = 10
        binding.questionCountText.text = "$numberOfQuestions questions"

        // Setup history button
        binding.historyButton.setOnClickListener {
            val intent = android.content.Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }

        // Setup start button
        binding.startButton.setOnClickListener {
            startQuiz()
        }
    }

    private fun observeViewModel() {
        // Observe categories
        viewModel.categories.observe(this) { categories: List<CategoryDto> ->
            val categoryNames = mutableListOf("All Categories")
            val categoryIds = mutableListOf(0)

            categories.forEach { category: CategoryDto ->
                categoryNames.add(category.name)
                categoryIds.add(category.id)
            }

            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                categoryNames
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.categorySpinner.adapter = adapter

            // Store category IDs for selection
            binding.categorySpinner.onItemSelectedListener = object : android.widget.AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: android.widget.AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {
                    selectedCategoryId = categoryIds[position]
                }

                override fun onNothingSelected(parent: android.widget.AdapterView<*>?) {
                    selectedCategoryId = 0
                }
            }
        }

        // Observe statistics from database
        viewModel.totalQuizzesCount.observe(this) { count ->
            binding.quizzesCountText.text = count?.toString() ?: "0"
        }
        
        viewModel.averageScore.observe(this) { average ->
            val avgScore = average?.toInt() ?: 0
            binding.averageScoreText.text = "$avgScore%"
        }
    }

    private fun startQuiz() {
        val intent = Intent(this, QuizActivity::class.java).apply {
            putExtra("CATEGORY_ID", selectedCategoryId)
            putExtra("DIFFICULTY", selectedDifficulty)
            putExtra("QUESTION_COUNT", numberOfQuestions)
        }
        startActivity(intent)
    }
}
