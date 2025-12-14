package com.example.quizapp.utils

object Constants {
    // API Configuration
    const val BASE_URL = "https://opentdb.com/"
    const val CONNECT_TIMEOUT = 30L
    const val READ_TIMEOUT = 30L
    const val WRITE_TIMEOUT = 30L

    // Database Configuration
    const val DATABASE_NAME = "quiz_database"

    // Quiz Configuration
    const val MIN_QUESTIONS = 5
    const val MAX_QUESTIONS = 50
    const val DEFAULT_QUESTIONS = 10

    // Categories
    val CATEGORIES = mapOf(
        0 to "All Categories",
        9 to "General Knowledge",
        10 to "Entertainment: Books",
        11 to "Entertainment: Film",
        12 to "Entertainment: Music",
        13 to "Entertainment: Musicals & Theatre",
        14 to "Entertainment: Television",
        15 to "Entertainment: Video Games",
        16 to "Entertainment: Board Games",
        17 to "Science & Nature",
        18 to "Science: Computers",
        19 to "Science: Mathematics",
        20 to "Mythology",
        21 to "Sports",
        22 to "Geography",
        23 to "History",
        24 to "Politics",
        25 to "Art",
        26 to "Celebrities",
        27 to "Animals",
        28 to "Vehicles",
        29 to "Entertainment: Comics",
        30 to "Science: Gadgets",
        31 to "Entertainment: Japanese Anime & Manga",
        32 to "Entertainment: Cartoon & Animations"
    )

    // Difficulty Levels
    const val DIFFICULTY_EASY = "easy"
    const val DIFFICULTY_MEDIUM = "medium"
    const val DIFFICULTY_HARD = "hard"

    // Question Type
    const val QUESTION_TYPE_MULTIPLE = "multiple"
    const val QUESTION_TYPE_BOOLEAN = "boolean"

    // Response Codes
    const val RESPONSE_CODE_SUCCESS = 0
    const val RESPONSE_CODE_NO_RESULTS = 1
    const val RESPONSE_CODE_INVALID_PARAMETER = 2
    const val RESPONSE_CODE_TOKEN_NOT_FOUND = 3
    const val RESPONSE_CODE_TOKEN_RESET = 4
    const val RESPONSE_CODE_TOO_MANY_REQUESTS = 5

    // Score Thresholds
    const val EXCELLENT_SCORE = 80f
    const val GOOD_SCORE = 60f
    const val AVERAGE_SCORE = 40f
}
