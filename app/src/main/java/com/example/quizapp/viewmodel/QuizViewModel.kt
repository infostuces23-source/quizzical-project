package com.example.quizapp.viewmodel

import androidx.lifecycle.*
import com.example.quizapp.data.local.entity.QuizHistory
import com.example.quizapp.data.remote.models.QuestionDto
import com.example.quizapp.data.repository.QuizRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QuizViewModel(private val repository: QuizRepository) : ViewModel() {

    private val _quizState = MutableLiveData<QuizState>()
    val quizState: LiveData<QuizState> = _quizState

    private var currentQuestions: List<QuestionDto> = emptyList()
    private var currentQuestionIndex = 0
    private var correctAnswersCount = 0
    private var userAnswers = mutableListOf<Boolean>()

    private var selectedCategory: Int = 0
    private var selectedCategoryName: String = "All Categories"
    private var selectedDifficulty: String? = null

    val categories = liveData(Dispatchers.IO) {
        try {
            val result = repository.fetchCategories()
            when {
                result.isSuccess -> emit(result.getOrNull() ?: emptyList())
                result.isFailure -> emit(emptyList())
            }
        } catch (e: Exception) {
            emit(emptyList())
        }
    }
    
    // Statistics LiveData - lazy initialization to avoid crash
    val totalQuizzesCount: LiveData<Int> by lazy {
        repository.getTotalQuizzesCount()
    }
    
    val averageScore: LiveData<Double> by lazy {
        repository.getAverageScore()
    }
    
    val quizHistory: LiveData<List<QuizHistory>> by lazy {
        repository.getRecentQuizHistory()
    }
    
    val allQuizHistory: LiveData<List<QuizHistory>> by lazy {
        repository.getAllQuizHistory()
    }

    fun fetchQuestions(amount: Int, category: Int?, difficulty: String): LiveData<QuizState> {
        selectedCategory = category ?: 0
        selectedDifficulty = difficulty
        
        _quizState.value = QuizState.Loading
        
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = repository.fetchQuestions(selectedCategory, difficulty, amount)
                result.onSuccess { questions ->
                    currentQuestions = questions
                    currentQuestionIndex = 0
                    correctAnswersCount = 0
                    userAnswers = MutableList(questions.size) { false }
                    _quizState.postValue(QuizState.Success(questions[0], 0, questions.size))
                }
                result.onFailure { exception ->
                    _quizState.postValue(QuizState.Error(exception.message ?: "Unknown error"))
                }
            } catch (e: Exception) {
                _quizState.postValue(QuizState.Error(e.message ?: "Unknown error"))
            }
        }
        
        return quizState
    }

    fun getCurrentQuestion(): QuestionDto? {
        return if (currentQuestionIndex < currentQuestions.size) {
            currentQuestions[currentQuestionIndex]
        } else {
            null
        }
    }

    fun getCurrentQuestionIndex(): Int = currentQuestionIndex

    fun getTotalQuestions(): Int = currentQuestions.size

    fun getCorrectAnswersCount(): Int = correctAnswersCount

    fun getScore(): Float {
        return if (currentQuestions.isEmpty()) {
            0f
        } else {
            (correctAnswersCount.toFloat() / currentQuestions.size.toFloat()) * 100f
        }
    }

    fun recordAnswer(isCorrect: Boolean) {
        if (currentQuestionIndex < userAnswers.size) {
            userAnswers[currentQuestionIndex] = isCorrect
            if (isCorrect) {
                correctAnswersCount++
            }
        }
    }

    fun nextQuestion(): Boolean {
        currentQuestionIndex++
        return currentQuestionIndex < currentQuestions.size
    }

    fun previousQuestion() {
        if (currentQuestionIndex > 0) {
            currentQuestionIndex--
            _quizState.value = QuizState.Success(
                currentQuestions[currentQuestionIndex],
                currentQuestionIndex,
                currentQuestions.size
            )
        }
    }
    
    fun saveQuizHistory(
        categoryId: Int,
        categoryName: String,
        difficulty: String,
        totalQuestions: Int,
        correctAnswers: Int,
        scorePercentage: Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val quizHistory = QuizHistory(
                categoryId = categoryId,
                categoryName = categoryName,
                difficulty = difficulty,
                totalQuestions = totalQuestions,
                correctAnswers = correctAnswers,
                score = scorePercentage
            )
            repository.saveQuizHistory(quizHistory)
        }
    }
    
    fun deleteAllHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllHistory()
        }
    }

}

sealed class QuizState {
    object Loading : QuizState()
    data class Success(
        val question: QuestionDto,
        val currentIndex: Int,
        val totalQuestions: Int
    ) : QuizState()
    data class Error(val message: String) : QuizState()
    data class Completed(val correctAnswers: Int, val totalQuestions: Int) : QuizState()
}

class QuizViewModelFactory(private val repository: QuizRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuizViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return QuizViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
