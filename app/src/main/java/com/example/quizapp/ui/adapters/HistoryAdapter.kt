package com.example.quizapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.R
import com.example.quizapp.data.local.entity.QuizHistory
import com.example.quizapp.databinding.ItemQuizHistoryBinding
import java.text.SimpleDateFormat
import java.util.*

class HistoryAdapter : ListAdapter<QuizHistory, HistoryAdapter.HistoryViewHolder>(HistoryDiffCallback()) {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = ItemQuizHistoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HistoryViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    
    class HistoryViewHolder(
        private val binding: ItemQuizHistoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        
        fun bind(quizHistory: QuizHistory) {
            // Score percentage (score is already a percentage 0-100)
            binding.scoreText.text = "${quizHistory.score}%"
            
            // Category
            binding.categoryText.text = quizHistory.categoryName
            
            // Difficulty and questions
            val difficultyCapitalized = if (quizHistory.difficulty.isNotEmpty()) {
                quizHistory.difficulty.substring(0, 1).uppercase() + quizHistory.difficulty.substring(1)
            } else {
                quizHistory.difficulty
            }
            binding.difficultyText.text = "$difficultyCapitalized • ${quizHistory.totalQuestions} questions"
            
            // Date
            val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
            binding.dateText.text = dateFormat.format(Date(quizHistory.timestamp))
            
            // Correct and wrong answers
            binding.correctAnswersText.text = quizHistory.correctAnswers.toString()
            binding.wrongAnswersText.text = (quizHistory.totalQuestions - quizHistory.correctAnswers).toString()
        }
    }
    
    class HistoryDiffCallback : DiffUtil.ItemCallback<QuizHistory>() {
        override fun areItemsTheSame(oldItem: QuizHistory, newItem: QuizHistory): Boolean {
            return oldItem.id == newItem.id
        }
        
        override fun areContentsTheSame(oldItem: QuizHistory, newItem: QuizHistory): Boolean {
            return oldItem == newItem
        }
    }
}

