package com.example.model

data class UserProfile(
    val name: String = "Awiskar",
    val experienceLevel: String = "Beginner",
    val learningGoals: List<String> = listOf("Learn Backend Development", "Learn MySQL", "Build APIs"),
    val dailyGoalMinutes: Int = 20,
    val minutesSpentToday: Int = 15,
    val streakDays: Int = 14,
    val xpPoints: Int = 850,
    val completedLessonIds: Set<String> = setOf("web-1", "php-1", "syntax-1"),
    val completedProjectIds: Set<String> = emptySet(),
    val completedChallengeIds: Set<String> = setOf("ch-1"),
    val unlockedAchievementIds: Set<String> = setOf("ach-1", "ach-2", "ach-streak"),
    val isOnboarded: Boolean = true,
    val bookmarkedLessonIds: Set<String> = setOf("syntax-1", "oop-1")
)

data class PhpModule(
    val id: String,
    val levelNumber: Int,
    val title: String,
    val subtitle: String,
    val description: String,
    val iconKey: String,
    val lessons: List<PhpLesson>
)

data class PhpLesson(
    val id: String,
    val moduleId: String,
    val title: String,
    val shortSummary: String,
    val readTimeMinutes: Int,
    val conceptOverview: String,
    val realWorldUsage: String,
    val serverFlowDescription: String,
    val syntaxHighlight: String,
    val interactiveCode: String,
    val simulatedOutput: String,
    val securityTip: String,
    val commonMistakes: String,
    val practiceTask: String,
    val practiceSolution: String
)

data class ProjectItem(
    val id: String,
    val title: String,
    val level: String, // Beginner, Intermediate, Advanced
    val description: String,
    val tags: List<String>,
    val fileList: List<ProjectFile>,
    val milestones: List<String>,
    val databaseSchema: String = ""
)

data class ProjectFile(
    val name: String,
    val path: String,
    val content: String
)

data class DatabaseRow(
    val id: Int,
    val name: String,
    val email: String,
    val role: String,
    val status: String
)

data class ApiPlaygroundEndpoint(
    val id: String,
    val method: String, // GET, POST, PUT, DELETE
    val path: String,
    val title: String,
    val description: String,
    val defaultHeaders: Map<String, String>,
    val defaultBody: String,
    val responseStatus: Int,
    val responseHeaders: Map<String, String>,
    val responseBody: String
)

data class SecuritySimTopic(
    val id: String,
    val title: String,
    val type: String, // SQL Injection, XSS, CSRF, File Upload
    val description: String,
    val vulnerableCode: String,
    val vulnerableExplanation: String,
    val attackInputExample: String,
    val attackResult: String,
    val secureCode: String,
    val secureExplanation: String,
    val defensePrinciple: String
)

data class QuizItem(
    val id: String,
    val topic: String,
    val question: String,
    val codeSnippet: String? = null,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String
)

data class InterviewItem(
    val id: String,
    val category: String,
    val question: String,
    val answer: String,
    val codeSample: String = ""
)

data class AchievementBadge(
    val id: String,
    val title: String,
    val description: String,
    val iconKey: String,
    val unlocked: Boolean
)

data class PhpFunctionReference(
    val name: String,
    val category: String,
    val syntax: String,
    val description: String,
    val example: String,
    val output: String
)
