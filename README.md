# 🎯 Quizzical - Android Quiz Application

A modern, feature-rich Android quiz application built with **Kotlin** and **Material Design 3**. Quizzical allows users to test their knowledge across 30+ categories with customizable difficulty levels and question counts.

## ✨ Features

### 📱 Quiz Selection
- **30+ Quiz Categories**: Science, History, Sports, Entertainment, and more
- **3 Difficulty Levels**: Easy, Medium, Hard
- **Flexible Question Count**: Choose between 5-50 questions per quiz
- **Real-time Statistics**: View your performance metrics instantly

### 🎮 Gameplay Experience
- **Interactive Multiple Choice**: Immediate feedback on answers
- **Progress Tracking**: Real-time progress bar during quiz
- **Dynamic Scoring**: Live score calculation
- **Quick Exit**: Return to main menu anytime
- **Smooth Navigation**: Optimized user experience with Material Design 3

### 📊 Results & Analytics
- **Percentage Score**: Clear performance metrics
- **Detailed Statistics**: Track total quizzes, average scores
- **Quiz History**: Complete history of all completed quizzes
- **Delete History**: Manage your quiz history easily

### 🎨 Modern UI/UX
- **Material Design 3**: Purple and pink color scheme (#FF6B9D, #E91E63, #FFB6C1)
- **Responsive Layout**: Works seamlessly on all screen sizes
- **Custom App Icon**: Branded Quizzical logo
- **Splash Screen**: Beautiful gradient introduction
- **Fixed Headers**: Persistent navigation elements

## 🏗️ Architecture

The application follows the **MVVM (Model-View-ViewModel)** architectural pattern with clean separation of concerns:

```
Quizzical/
├── data/
│   ├── local/                    # Local database layer
│   │   ├── entity/              # Room database entities
│   │   ├── dao/                 # Data access objects
│   │   └── QuizDatabase.kt      # Room database instance
│   ├── remote/                   # Remote API layer
│   │   ├── api/                 # Retrofit API interfaces
│   │   └── models/              # API response models
│   └── repository/               # Repository pattern
│       └── QuizRepository.kt     # Data aggregation layer
├── ui/
│   ├── activities/               # UI Activities
│   │   ├── SplashActivity.kt    # Splash screen
│   │   ├── MainActivity.kt       # Main screen with statistics
│   │   ├── QuizActivity.kt       # Quiz taking screen
│   │   ├── ResultActivity.kt     # Results display
│   │   └── HistoryActivity.kt    # Quiz history
│   ├── adapters/                 # RecyclerView adapters
│   │   └── HistoryAdapter.kt     # History list adapter
│   └── fragments/                # UI Fragments (if any)
├── viewmodel/                    # ViewModel classes
│   └── QuizViewModel.kt          # Shared ViewModel
├── utils/                        # Utility classes
├── QuizApplication.kt            # Application class
└── res/
    ├── layout/                   # XML layout files
    ├── values/                   # Strings, colors, styles
    ├── drawable/                 # Drawables and backgrounds
    └── mipmap-*/                 # App icons (multiple densities)
```

## 🛠️ Technology Stack

### Core Technologies
| Technology | Version | Purpose |
|-----------|---------|---------|
| **Kotlin** | 1.9.22 | Primary programming language |
| **Android SDK** | 34 (Min: 24) | Android framework |
| **Gradle** | 8.1.1 | Build automation |

### Libraries & Frameworks
| Library | Version | Purpose |
|---------|---------|---------|
| **AndroidX Core** | 1.12.0 | Core Android functionality |
| **Material Components** | 1.11.0 | Material Design 3 components |
| **Room Database** | 2.6.1 | Local data persistence |
| **Retrofit** | 2.9.0 | HTTP client for API calls |
| **OkHttp** | 4.12.0 | HTTP interceptor and logging |
| **Gson** | 2.8.9 | JSON serialization |
| **Lifecycle** | 2.7.0 | ViewModel and LiveData |
| **Coroutines** | 1.7.3 | Asynchronous programming |

### External API
- **OpenTDB (Open Trivia Database)**: Free quiz questions API
  - Base URL: `https://opentdb.com/`
  - No API key required
  - Rate limit: 1 request per second

## 📦 Dependencies

```kotlin
// AndroidX
androidx.core:core-ktx:1.12.0
androidx.appcompat:appcompat:1.6.1
androidx.constraintlayout:constraintlayout:2.1.4

// Material Design 3
com.google.android.material:material:1.11.0

// Lifecycle & ViewModel
androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0
androidx.lifecycle:lifecycle-livedata-ktx:2.7.0
androidx.lifecycle:lifecycle-runtime-ktx:2.7.0

// Room Database
androidx.room:room-runtime:2.6.1
androidx.room:room-ktx:2.6.1
androidx.room:room-compiler:2.6.1

// Retrofit & Networking
com.squareup.retrofit2:retrofit:2.9.0
com.squareup.retrofit2:converter-gson:2.9.0
com.squareup.okhttp3:okhttp:4.12.0
com.squareup.okhttp3:logging-interceptor:4.12.0

// Coroutines
org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3
org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3

// Testing
junit:junit:4.13.2
androidx.test.ext:junit:1.1.5
androidx.test.espresso:espresso-core:3.5.1
```

## 🚀 Getting Started

### Prerequisites
- **Android Studio** (Latest version recommended)
- **Java Development Kit (JDK)** 17 or higher
- **Android SDK** API level 24 or higher

### Installation Steps

1. **Clone the Repository**
   ```bash
   git clone https://github.com/infostuces23-source/quizzical-project.git
   cd quizzical-project
   ```

2. **Open in Android Studio**
   - Launch Android Studio
   - Select `File → Open`
   - Navigate to the cloned directory
   - Click `OK`

3. **Sync Gradle**
   - Android Studio will automatically prompt to sync
   - Or manually: `File → Sync Now`
   - Wait for the build to complete

4. **Run the Application**
   - Connect an Android device or start an emulator
   - Click `Run → Run 'app'`
   - Or press `Shift + F10`

### Build from Command Line

```bash
# Build debug APK
./gradlew assembleDebug

# Build release APK
./gradlew assembleRelease

# Run tests
./gradlew test

# Clean build
./gradlew clean
```

## 📱 Usage Guide

### Main Screen
1. **View Statistics**: See your total quizzes and average score
2. **Select Category**: Choose from 30+ quiz categories
3. **Set Difficulty**: Pick Easy, Medium, or Hard
4. **Adjust Questions**: Slider to select 5-50 questions
5. **Start Quiz**: Tap "Start Quiz" button

### During Quiz
1. **Read Question**: Carefully read the question
2. **Select Answer**: Tap one of the four options
3. **Get Feedback**: See if your answer is correct immediately
4. **Track Progress**: Monitor your progress with the progress bar
5. **Next Question**: Tap "Next" to continue
6. **Quit Anytime**: Use "Quit" button to exit

### Results Screen
- **Final Score**: Percentage score achieved
- **Statistics**: Questions answered, correct answers
- **Options**: Return to main menu or view history

### History Screen
- **View All Quizzes**: See all completed quizzes
- **Quiz Details**: Date, category, score, difficulty
- **Delete History**: Clear individual quizzes or all history

## 🔧 Configuration

### API Configuration
The app uses OpenTDB API for quiz questions. Configuration is handled automatically:
- **Base URL**: `https://opentdb.com/`
- **Endpoints**: Used for fetching quiz categories and questions
- **Timeout**: 30 seconds per request
- **Retry Policy**: Automatic retry on network failure

### Database Configuration
- **Database Name**: `quiz_database`
- **Version**: 1
- **Location**: Device internal storage (private)
- **Auto-migration**: Enabled for future schema updates

### Theme Configuration
- **Theme**: Material Design 3 with custom colors
- **Primary Color**: `#FF6B9D` (Pink)
- **Secondary Color**: `#E91E63` (Deep Pink)
- **Tertiary Color**: `#FFB6C1` (Light Pink)
- **Day/Night Mode**: Automatic based on system settings

## 🐛 Troubleshooting

### Common Issues & Solutions

#### Issue: "Gradle sync failed"
**Solution:**
```bash
# Clean and rebuild
./gradlew clean
./gradlew build
```

#### Issue: "Cannot find symbol" errors
**Solution:**
- Invalidate caches: `File → Invalidate Caches → Invalidate and Restart`
- Rebuild project: `Build → Rebuild Project`

#### Issue: "API request timeout"
**Solution:**
- Check internet connection
- Verify OpenTDB API is accessible: `https://opentdb.com/`
- Check network timeout settings in `RetrofitClient`

#### Issue: "Database migration error"
**Solution:**
- Uninstall app completely
- Clear app data: `Settings → Apps → Quizzical → Storage → Clear All Data`
- Reinstall the app

#### Issue: "Run button is grayed out"
**Solution:**
- Ensure a device/emulator is connected
- Sync Gradle: `File → Sync Now`
- Rebuild: `Build → Rebuild Project`

## 📊 Data Models

### QuizHistory Entity
```kotlin
@Entity(tableName = "quiz_history")
data class QuizHistory(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val category: String,
    val difficulty: String,
    val score: Int,
    val totalQuestions: Int,
    val date: Long = System.currentTimeMillis()
)
```

### Quiz API Response
```kotlin
data class QuizResponse(
    val response_code: Int,
    val results: List<QuizQuestion>
)

data class QuizQuestion(
    val category: String,
    val difficulty: String,
    val question: String,
    val correct_answer: String,
    val incorrect_answers: List<String>
)
```

## 🔐 Security Considerations

- **No API Key Required**: OpenTDB API doesn't require authentication
- **Local Storage**: Quiz history stored locally on device
- **Network Security**: Uses HTTPS for all API calls
- **Data Privacy**: No personal data is collected or transmitted

## 📈 Performance Optimization

- **Image Optimization**: App icons are optimized to prevent memory issues
- **Lazy Loading**: Quiz questions loaded on-demand
- **Coroutines**: Asynchronous operations prevent UI blocking
- **Database Indexing**: Optimized queries for history retrieval
- **Memory Management**: Proper lifecycle handling prevents leaks

## 🎨 UI/UX Design

### Color Palette
| Color | Hex Code | Usage |
|-------|----------|-------|
| Primary Pink | `#FF6B9D` | Buttons, highlights |
| Deep Pink | `#E91E63` | Accents, selected states |
| Light Pink | `#FFB6C1` | Backgrounds, subtle elements |
| Dark Gray | `#212121` | Text, primary content |
| Light Gray | `#F5F5F5` | Card backgrounds |

### Typography
- **Headline**: Roboto Bold, 24sp
- **Body**: Roboto Regular, 16sp
- **Caption**: Roboto Regular, 12sp

## 📝 Code Style Guide

### Kotlin Conventions
- Use meaningful variable names
- Follow Kotlin naming conventions (camelCase for variables)
- Use data classes for models
- Prefer immutability with `val`
- Use extension functions for utility operations

### Android Best Practices
- Use ViewModel for state management
- Implement proper lifecycle handling
- Use LiveData for reactive updates
- Follow MVVM architecture
- Use coroutines for async operations

## 🤝 Contributing

While this is a personal project, the codebase follows professional standards:
- Clean architecture principles
- Comprehensive error handling
- Meaningful commit messages
- Well-documented code

## 📄 License

This project is open source and available under the MIT License.

## 👨‍💻 Author

**infostuces23-source**
- GitHub: [@infostuces23-source](https://github.com/infostuces23-source)

## 🙏 Acknowledgments

- **OpenTDB**: For providing the free quiz questions API
- **Google**: For Material Design 3 components and guidelines
- **JetBrains**: For Kotlin and Android development tools
- **Android Community**: For excellent documentation and support

## 📞 Support

For issues, questions, or suggestions:
1. Check the [Troubleshooting](#-troubleshooting) section
2. Review existing issues on GitHub
3. Create a new issue with detailed information

---

**Developed with ❤️ using Kotlin and Material Design 3**

*Last Updated: December 2025*
