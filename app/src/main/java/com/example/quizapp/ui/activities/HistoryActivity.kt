package com.example.quizapp.ui.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizapp.QuizApplication
import com.example.quizapp.databinding.ActivityHistoryBinding
import com.example.quizapp.ui.adapters.HistoryAdapter
import com.example.quizapp.viewmodel.QuizViewModel
import com.example.quizapp.viewmodel.QuizViewModelFactory

class HistoryActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityHistoryBinding
    private lateinit var viewModel: QuizViewModel
    private lateinit var historyAdapter: HistoryAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupViewModel()
        setupRecyclerView()
        setupClickListeners()
        observeHistory()
    }
    
    private fun setupViewModel() {
        val repository = QuizApplication.repository
        val factory = QuizViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[QuizViewModel::class.java]
    }
    
    private fun setupRecyclerView() {
        historyAdapter = HistoryAdapter()
        binding.historyRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@HistoryActivity)
            adapter = historyAdapter
        }
    }
    
    private fun setupClickListeners() {
        binding.backButton.setOnClickListener {
            finish()
        }
        
        binding.deleteAllButton.setOnClickListener {
            showDeleteConfirmationDialog()
        }
    }
    
    private fun showDeleteConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle("Delete All History")
            .setMessage("Are you sure you want to delete all quiz history? This action cannot be undone.")
            .setPositiveButton("Delete") { _, _ ->
                viewModel.deleteAllHistory()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    private fun observeHistory() {
        viewModel.allQuizHistory.observe(this) { history ->
            if (history.isEmpty()) {
                binding.emptyStateLayout.visibility = View.VISIBLE
                binding.historyRecyclerView.visibility = View.GONE
            } else {
                binding.emptyStateLayout.visibility = View.GONE
                binding.historyRecyclerView.visibility = View.VISIBLE
                historyAdapter.submitList(history)
            }
        }
    }
}

