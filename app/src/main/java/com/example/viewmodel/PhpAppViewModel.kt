package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.PhpCurriculum
import com.example.data.PhpProjectsAndLabsData
import com.example.model.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed class AppDestination {
    object Main : AppDestination()
    data class LessonDetail(val lessonId: String) : AppDestination()
    object ServerFlowLab : AppDestination()
    object DatabaseLab : AppDestination()
    object ApiLab : AppDestination()
    object AuthLab : AppDestination()
    object SecurityAcademy : AppDestination()
    object OopLab : AppDestination()
    object FrameworksLab : AppDestination()
    object AiTutor : AppDestination()
    object QuizCenter : AppDestination()
    object Certificate : AppDestination()
    object ProjectsList : AppDestination()
    data class ProjectDetail(val projectId: String) : AppDestination()
}

enum class NavigationTab {
    HOME, LEARN, CODE_LAB, LABS, PROFILE
}

data class ExecutionResult(
    val output: String = "",
    val htmlPreview: String = "",
    val executionTimeMs: Double = 0.0,
    val memoryUsageMb: Double = 0.0,
    val logs: List<String> = emptyList(),
    val variables: Map<String, String> = emptyMap(),
    val hasError: Boolean = false,
    val errorMessage: String = ""
)

data class AiMessage(
    val id: String,
    val isUser: Boolean,
    val text: String,
    val timestamp: String = "Just now"
)

data class AppUiState(
    val userProfile: UserProfile = UserProfile(),
    val currentTab: NavigationTab = NavigationTab.HOME,
    val destinationStack: List<AppDestination> = emptyList(),
    val isSearchOpen: Boolean = false,
    val searchQuery: String = "",
    
    // Code Lab State
    val codeFiles: Map<String, String> = mapOf(
        "index.php" to "<?php\n// Learn PHP - Interactive IDE\n// Curated by Awiskar Acharya\n\n\$title = \"Modern PHP 8.3\";\n\$features = [\"Strict Types\", \"JIT Compiler\", \"Match Expressions\"];\n\necho \"<h1>Welcome to \" . \$title . \"!</h1>\\n\";\necho \"<h3>Server Execution Pipeline:</h3>\\n\";\n\nforeach (\$features as \$index => \$feature) {\n    \$num = \$index + 1;\n    echo \"[\$num] Feature: \$feature\\n\";\n}\n\n\$status = match(\$title) {\n    \"Modern PHP 8.3\" => \"Fast, Reliable, Enterprise-Ready!\",\n    default => \"Standard PHP\"\n};\n\necho \"\\nResult: \$status\\n\";\n?>",
        "config.php" to "<?php\ndefine('DB_HOST', 'localhost');\ndefine('DB_NAME', 'learn_php_db');\ndefine('DB_USER', 'awiskar_dev');\ndefine('APP_ENV', 'development');\n?>",
        "db.php" to "<?php\n// PDO Database Connector Simulation\ntry {\n    \$pdo = new PDO('mysql:host=localhost;dbname=learn_php_db', 'root', '');\n    \$pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);\n    echo \"[Database] Connected to MySQL successfully!\\n\";\n} catch (PDOException \$e) {\n    echo \"[Database Error] \" . \$e->getMessage();\n}\n?>",
        "api.php" to "<?php\nheader('Content-Type: application/json');\n\$data = [\n    'status' => 'success',\n    'author' => 'Awiskar Acharya',\n    'platform' => 'Learn PHP',\n    'timestamp' => time()\n];\necho json_encode(\$data, JSON_PRETTY_PRINT);\n?>"
    ),
    val activeFileName: String = "index.php",
    val isExecutingCode: Boolean = false,
    val executionResult: ExecutionResult = ExecutionResult(
        output = "<h1>Welcome to Modern PHP 8.3!</h1>\n<h3>Server Execution Pipeline:</h3>\n[1] Feature: Strict Types\n[2] Feature: JIT Compiler\n[3] Feature: Match Expressions\n\nResult: Fast, Reliable, Enterprise-Ready!",
        htmlPreview = "<div style='font-family:sans-serif;'><h2>Welcome to Modern PHP 8.3!</h2><ul><li>Strict Types</li><li>JIT Compiler</li><li>Match Expressions</li></ul><p><b>Status:</b> Fast, Reliable, Enterprise-Ready!</p></div>",
        executionTimeMs = 4.2,
        memoryUsageMb = 2.15,
        logs = listOf(
            "[Zend Engine] Tokenized 48 AST nodes",
            "[OPcache] Cache hit - 0 compile delays",
            "[Output Buffer] Flushed 264 bytes to HTTP body"
        ),
        variables = mapOf(
            "\$title" to "\"Modern PHP 8.3\" (string)",
            "\$features" to "array(3)",
            "\$status" to "\"Fast, Reliable, Enterprise-Ready!\""
        )
    ),

    // Database Lab State
    val databaseRows: List<DatabaseRow> = PhpProjectsAndLabsData.sampleDatabaseRows,
    val lastExecutedSql: String = "SELECT * FROM students ORDER BY id ASC LIMIT 5;",

    // API Lab State
    val selectedApiEndpoint: ApiPlaygroundEndpoint = PhpProjectsAndLabsData.apiEndpoints.first(),
    val isApiSending: Boolean = false,
    val apiResponseReceived: String? = null,

    // Security Lab State
    val selectedSecurityTopic: SecuritySimTopic = PhpProjectsAndLabsData.securityTopics.first(),
    val securitySimDefensiveMode: Boolean = false,

    // AI Tutor State
    val aiMessages: List<AiMessage> = listOf(
        AiMessage("1", false, "Hello! I am your AI PHP Tutor created for Awiskar Acharya's Learn PHP. Ask me anything about PHP syntax, PDO database connections, REST APIs, or debugging errors!"),
        AiMessage("2", false, "Try tapping one of the quick question chips below or paste your code snippet!")
    ),
    val isAiThinking: Boolean = false
)

class PhpAppViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(AppUiState())
    val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()

    fun setTab(tab: NavigationTab) {
        _uiState.update { it.copy(currentTab = tab, destinationStack = emptyList()) }
    }

    fun navigateTo(destination: AppDestination) {
        _uiState.update { it.copy(destinationStack = it.destinationStack + destination) }
    }

    fun popBack(): Boolean {
        if (_uiState.value.destinationStack.isNotEmpty()) {
            _uiState.update {
                it.copy(destinationStack = it.destinationStack.dropLast(1))
            }
            return true
        }
        return false
    }

    fun completeOnboarding(name: String, level: String, goals: List<String>) {
        _uiState.update {
            it.copy(
                userProfile = it.userProfile.copy(
                    name = name.ifBlank { "Awiskar" },
                    experienceLevel = level,
                    learningGoals = if (goals.isEmpty()) listOf("Learn Backend Development") else goals,
                    isOnboarded = true
                )
            )
        }
    }

    fun completeLesson(lessonId: String) {
        _uiState.update {
            val newCompleted = it.userProfile.completedLessonIds + lessonId
            val newXp = it.userProfile.xpPoints + 50
            it.copy(
                userProfile = it.userProfile.copy(
                    completedLessonIds = newCompleted,
                    xpPoints = newXp
                )
            )
        }
    }

    fun toggleBookmark(lessonId: String) {
        _uiState.update {
            val bookmarks = it.userProfile.bookmarkedLessonIds.toMutableSet()
            if (bookmarks.contains(lessonId)) bookmarks.remove(lessonId)
            else bookmarks.add(lessonId)
            it.copy(userProfile = it.userProfile.copy(bookmarkedLessonIds = bookmarks))
        }
    }

    fun completeProject(projectId: String) {
        _uiState.update {
            it.copy(
                userProfile = it.userProfile.copy(
                    completedProjectIds = it.userProfile.completedProjectIds + projectId,
                    xpPoints = it.userProfile.xpPoints + 150
                )
            )
        }
    }

    fun selectFile(fileName: String) {
        _uiState.update { it.copy(activeFileName = fileName) }
    }

    fun updateActiveCode(newContent: String) {
        val currentFile = _uiState.value.activeFileName
        val updated = _uiState.value.codeFiles.toMutableMap()
        updated[currentFile] = newContent
        _uiState.update { it.copy(codeFiles = updated) }
    }

    fun runPhpCode() {
        _uiState.update { it.copy(isExecutingCode = true) }
        viewModelScope.launch {
            delay(500)
            val currentFile = _uiState.value.activeFileName
            val code = _uiState.value.codeFiles[currentFile] ?: ""
            
            val executionResult = simulatePhpInterpreter(code, currentFile)
            _uiState.update {
                it.copy(
                    isExecutingCode = false,
                    executionResult = executionResult,
                    userProfile = it.userProfile.copy(
                        xpPoints = it.userProfile.xpPoints + 15,
                        minutesSpentToday = (it.userProfile.minutesSpentToday + 1).coerceAtMost(60)
                    )
                )
            }
        }
    }

    private fun simulatePhpInterpreter(code: String, fileName: String): ExecutionResult {
        // Quick syntax check simulation
        if (code.contains("syntax_error") || (code.contains("<?php") && !code.contains(";") && code.lines().size > 3)) {
            return ExecutionResult(
                output = "Parse error: syntax error, unexpected end of file in $fileName on line 5",
                htmlPreview = "<div style='color:red;'><b>Parse error:</b> syntax error</div>",
                executionTimeMs = 1.1,
                memoryUsageMb = 1.2,
                logs = listOf("[Zend Lexer] Syntax Error at line 5", "[Process] Exited with status code 255"),
                hasError = true,
                errorMessage = "Parse error: syntax error, missing semicolon or bracket"
            )
        }

        // Clean simulated stdout
        val stdoutBuilder = StringBuilder()
        val varMap = mutableMapOf<String, String>()
        
        // Extract basic simulated echo or print lines
        val lines = code.lines()
        for (line in lines) {
            val trimmed = line.trim()
            if (trimmed.startsWith("echo ") || trimmed.startsWith("print ")) {
                val content = trimmed.removePrefix("echo ").removePrefix("print ")
                    .removeSuffix(";").trim()
                val clean = content.replace("\"", "").replace("'", "")
                    .replace("\\n", "\n")
                    .replace(". \"\\n\"", "")
                stdoutBuilder.append(clean).append("\n")
            }
            if (trimmed.startsWith("$") && trimmed.contains("=")) {
                val parts = trimmed.split("=", limit = 2)
                if (parts.size == 2) {
                    val varName = parts[0].trim()
                    val varVal = parts[1].removeSuffix(";").trim()
                    varMap[varName] = varVal
                }
            }
        }

        if (stdoutBuilder.isBlank()) {
            stdoutBuilder.append("Script executed successfully. (No output was generated to STDOUT).")
        }

        return ExecutionResult(
            output = stdoutBuilder.toString().trim(),
            htmlPreview = "<div style='font-family:sans-serif;'>${stdoutBuilder.toString().replace("\n", "<br/>")}</div>",
            executionTimeMs = (2.0 + (Math.random() * 3.5)),
            memoryUsageMb = 2.14,
            logs = listOf(
                "[Zend Engine] Tokenized ${code.length / 5} tokens",
                "[OPcache] Compiled memory cached in pool",
                "[Output Buffer] Sent HTTP 200 OK headers"
            ),
            variables = if (varMap.isEmpty()) mapOf("\$_SERVER" to "array(32)", "\$_GET" to "array(0)") else varMap
        )
    }

    // Database Actions
    fun addDatabaseRecord(name: String, email: String, role: String) {
        val newId = (_uiState.value.databaseRows.maxOfOrNull { it.id } ?: 0) + 1
        val newRecord = DatabaseRow(newId, name, email, role, "Active")
        val sql = "INSERT INTO students (name, email, role, status) VALUES ('$name', '$email', '$role', 'Active');"
        _uiState.update {
            it.copy(
                databaseRows = it.databaseRows + newRecord,
                lastExecutedSql = sql
            )
        }
    }

    fun deleteDatabaseRecord(id: Int) {
        val sql = "DELETE FROM students WHERE id = $id;"
        _uiState.update {
            it.copy(
                databaseRows = it.databaseRows.filterNot { row -> row.id == id },
                lastExecutedSql = sql
            )
        }
    }

    // API Lab Actions
    fun selectApiEndpoint(endpoint: ApiPlaygroundEndpoint) {
        _uiState.update { it.copy(selectedApiEndpoint = endpoint, apiResponseReceived = null) }
    }

    fun sendApiRequest() {
        _uiState.update { it.copy(isApiSending = true) }
        viewModelScope.launch {
            delay(400)
            _uiState.update {
                it.copy(
                    isApiSending = false,
                    apiResponseReceived = it.selectedApiEndpoint.responseBody
                )
            }
        }
    }

    // Security Lab Actions
    fun selectSecurityTopic(topic: SecuritySimTopic) {
        _uiState.update { it.copy(selectedSecurityTopic = topic, securitySimDefensiveMode = false) }
    }

    fun toggleSecurityMode() {
        _uiState.update { it.copy(securitySimDefensiveMode = !it.securitySimDefensiveMode) }
    }

    // AI Tutor Actions
    fun sendAiQuestion(question: String) {
        val userMsg = AiMessage(System.currentTimeMillis().toString(), true, question)
        _uiState.update {
            it.copy(
                aiMessages = it.aiMessages + userMsg,
                isAiThinking = true
            )
        }

        viewModelScope.launch {
            delay(700)
            val answer = generateAiAnswer(question)
            val botMsg = AiMessage((System.currentTimeMillis() + 1).toString(), false, answer)
            _uiState.update {
                it.copy(
                    aiMessages = it.aiMessages + botMsg,
                    isAiThinking = false
                )
            }
        }
    }

    private fun generateAiAnswer(prompt: String): String {
        val p = prompt.lowercase()
        return when {
            p.contains("pdo") || p.contains("mysqli") ->
                "**PDO (PHP Data Objects)** is the modern standard because:\n1. It supports 12 different database drivers (MySQL, PostgreSQL, SQLite, etc.) with a single unified interface.\n2. Parameterized prepared statements completely eliminate SQL Injection vulnerabilities.\n3. It uses object-oriented fetch modes (like FETCH_ASSOC or FETCH_CLASS).\n\nIn contrast, MySQLi is restricted to MySQL/MariaDB only."
            p.contains("session") || p.contains("cookie") ->
                "**Sessions vs Cookies in PHP:**\n- **Cookies** are stored in the user's browser (client-side) and sent with every HTTP request.\n- **Sessions** store the data on the server (in `/tmp` or Redis), while only a small `PHPSESSID` cookie is sent to the client.\n- Always call `session_regenerate_id(true)` upon successful login to prevent Session Fixation attacks!"
            p.contains("match") ->
                "**PHP 8 match expression:**\n- Evaluates strictly using `===` (identity comparison).\n- Returns a value directly, eliminating repetitive `break;` statements.\n- Throws an `UnhandledMatchError` if no case matches and no `default` is specified, preventing silent logic bugs!"
            p.contains("xss") || p.contains("security") ->
                "**Preventing XSS (Cross-Site Scripting):**\nNever output raw user variables directly in HTML. Always escape using:\n`htmlspecialchars(\$input, ENT_QUOTES, 'UTF-8');`\nThis converts characters like `<`, `>`, `&`, `'` and `\"` into harmless HTML entities so browsers display them safely."
            else ->
                "Great question! In PHP development, always follow the principle of least privilege, sanitize inputs with `filter_var()`, use PDO prepared statements for database queries, and leverage strict typing with `declare(strict_types=1);`. Check out our interactive Code Lab to test this concept right now!"
        }
    }

    // Search Actions
    fun setSearchOpen(isOpen: Boolean) {
        _uiState.update { it.copy(isSearchOpen = isOpen, searchQuery = if (!isOpen) "" else it.searchQuery) }
    }

    fun updateSearchQuery(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }
}
