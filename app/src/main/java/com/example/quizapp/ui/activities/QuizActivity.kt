package com.example.quizapp.ui.activities

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.quizapp.QuizApplication
import com.example.quizapp.R
import com.example.quizapp.databinding.ActivityQuizBinding
import com.example.quizapp.viewmodel.QuizState
import com.example.quizapp.viewmodel.QuizViewModel
import com.example.quizapp.viewmodel.QuizViewModelFactory
import com.google.android.material.button.MaterialButton

class QuizActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuizBinding
    private val viewModel: QuizViewModel by viewModels {
        QuizViewModelFactory(QuizApplication.repository)
    }

    private var category: Int? = null
    private var difficulty: String = "medium"
    private var questionCount: Int = 10
    private var selectedAnswers = mutableMapOf<Int, String>()
    private var categoryName: String = "General"
    private var quizStarted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get parameters
        category = intent.getIntExtra("CATEGORY_ID", 0).takeIf { it != 0 }
        difficulty = intent.getStringExtra("DIFFICULTY") ?: "medium"
        questionCount = intent.getIntExtra("QUESTION_COUNT", 10)

        setupUI()
        fetchQuestions()
    }

    private fun setupUI() {
        binding.nextButton.setOnClickListener {
            if (quizStarted) {
                if (viewModel.nextQuestion()) {
                    displayQuestion()
                } else {
                    finishQuiz()
                }
            }
        }

        binding.retryButton.setOnClickListener {
            fetchQuestions()
        }

        binding.quitButton.setOnClickListener {
            finish() // Retourne au menu principal (MainActivity)
        }
    }

    private fun fetchQuestions() {
        binding.loadingContainer.visibility = android.view.View.VISIBLE
        binding.mainContent.visibility = android.view.View.GONE
        binding.errorContainer.visibility = android.view.View.GONE

        viewModel.fetchQuestions(questionCount, category, difficulty).observe(this) { state ->
            when (state) {
                is QuizState.Loading -> {
                    binding.loadingContainer.visibility = android.view.View.VISIBLE
                    binding.mainContent.visibility = android.view.View.GONE
                    binding.errorContainer.visibility = android.view.View.GONE
                }
                is QuizState.Success -> {
                    binding.loadingContainer.visibility = android.view.View.GONE
                    binding.mainContent.visibility = android.view.View.VISIBLE
                    binding.errorContainer.visibility = android.view.View.GONE
                    quizStarted = true
                    displayQuestion()
                }
                is QuizState.Error -> {
                    binding.loadingContainer.visibility = android.view.View.GONE
                    binding.mainContent.visibility = android.view.View.GONE
                    binding.errorContainer.visibility = android.view.View.VISIBLE
                    binding.errorMessage.text = state.message
                    quizStarted = false
                }
                is QuizState.Completed -> {
                    finishQuiz()
                }
            }
        }
    }

    private fun displayQuestion() {
        val question = viewModel.getCurrentQuestion()
        if (question == null) {
            finishQuiz()
            return
        }

        categoryName = question.category
            .replace("&quot;", "\"")
            .replace("&amp;", "&")
            .replace("&#039;", "'")

        // Update progress
        val currentIndex = viewModel.getCurrentQuestionIndex() + 1
        val total = viewModel.getTotalQuestions()
        binding.questionNumberText.text = "Question $currentIndex of $total"
        binding.progressBar.progress = (currentIndex * 100) / total
        binding.scoreText.text = "Score: ${viewModel.getCorrectAnswersCount()}/$total"

        // Display question
        val questionText = Html.fromHtml(question.question, Html.FROM_HTML_MODE_LEGACY).toString()
        binding.questionText.text = questionText

        // Display answers
        binding.answersContainer.removeAllViews()
        val answers = question.getAllAnswers()

        answers.forEach { answer ->
            val answerButton = MaterialButton(this).apply {
                text = Html.fromHtml(answer, Html.FROM_HTML_MODE_LEGACY).toString()
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(0, 8, 0, 8)
                }
                setOnClickListener {
                    selectAnswer(answer, question.correctAnswer)
                }
            }
            binding.answersContainer.addView(answerButton)
        }

        // Update button text
        binding.nextButton.text = if (currentIndex == total) "Finish" else "Next"
        binding.previousButton.isEnabled = currentIndex > 1
    }

    private fun selectAnswer(selectedAnswer: String, correctAnswer: String) {
        val currentIndex = viewModel.getCurrentQuestionIndex()
        selectedAnswers[currentIndex] = selectedAnswer

        val isCorrect = selectedAnswer == correctAnswer
        viewModel.recordAnswer(isCorrect)

        // Disable all buttons and highlight correct/wrong answer
        for (i in 0 until binding.answersContainer.childCount) {
            val button = binding.answersContainer.getChildAt(i) as MaterialButton
            val buttonText = button.text.toString()

            button.isEnabled = false

            val correctAnswerText = Html.fromHtml(correctAnswer, Html.FROM_HTML_MODE_LEGACY).toString()
            val selectedAnswerText = Html.fromHtml(selectedAnswer, Html.FROM_HTML_MODE_LEGACY).toString()

            when {
                buttonText == correctAnswerText -> {
                    button.setBackgroundColor(resources.getColor(R.color.correct_color, null))
                    button.setTextColor(resources.getColor(android.R.color.white, null))
                }
                buttonText == selectedAnswerText && !isCorrect -> {
                    button.setBackgroundColor(resources.getColor(R.color.wrong_color, null))
                    button.setTextColor(resources.getColor(android.R.color.white, null))
                }
            }
        }
    }

    private fun finishQuiz() {
        val intent = Intent(this, ResultActivity::class.java).apply {
            putExtra("correctAnswers", viewModel.getCorrectAnswersCount())
            putExtra("totalQuestions", viewModel.getTotalQuestions())
            putExtra("score", viewModel.getScore())
            putExtra("category", categoryName)
            putExtra("categoryId", category ?: 0)
            putExtra("difficulty", difficulty)
        }
        startActivity(intent)
        finish()
    }
}
