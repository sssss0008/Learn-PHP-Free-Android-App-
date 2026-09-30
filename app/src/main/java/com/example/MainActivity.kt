package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.AppTopHeader
import com.example.ui.screens.*
import com.example.ui.theme.*
import com.example.viewmodel.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val viewModel: PhpAppViewModel = viewModel()
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (!uiState.userProfile.isOnboarded) {
                        OnboardingScreen(
                            onComplete = { name, level, goals ->
                                viewModel.completeOnboarding(name, level, goals)
                            }
                        )
                    } else {
                        MainAppContent(viewModel = viewModel, uiState = uiState)
                    }

                    if (uiState.isSearchOpen) {
                        SearchModal(
                            searchQuery = uiState.searchQuery,
                            onQueryChange = { viewModel.updateSearchQuery(it) },
                            onDismiss = { viewModel.setSearchOpen(false) },
                            onSelectLesson = { lessonId ->
                                viewModel.setSearchOpen(false)
                                viewModel.navigateTo(AppDestination.LessonDetail(lessonId))
                            },
                            onNavigateTo = { dest ->
                                viewModel.setSearchOpen(false)
                                viewModel.navigateTo(dest)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MainAppContent(
    viewModel: PhpAppViewModel,
    uiState: AppUiState,
    modifier: Modifier = Modifier
) {
    val activeDestination = uiState.destinationStack.lastOrNull()

    if (activeDestination != null) {
        // Sub-screen destination
        when (activeDestination) {
            is AppDestination.LessonDetail -> {
                LessonDetailScreen(
                    lessonId = activeDestination.lessonId,
                    isCompleted = uiState.userProfile.completedLessonIds.contains(activeDestination.lessonId),
                    isBookmarked = uiState.userProfile.bookmarkedLessonIds.contains(activeDestination.lessonId),
                    onBack = { viewModel.popBack() },
                    onToggleComplete = { viewModel.completeLesson(activeDestination.lessonId) },
                    onToggleBookmark = { viewModel.toggleBookmark(activeDestination.lessonId) },
                    onOpenInCodeLab = { code ->
                        viewModel.updateActiveCode(code)
                        viewModel.popBack()
                        viewModel.setTab(NavigationTab.CODE_LAB)
                    }
                )
            }
            is AppDestination.ServerFlowLab -> {
                ServerFlowScreen(onBack = { viewModel.popBack() })
            }
            is AppDestination.DatabaseLab -> {
                DatabaseLabScreen(
                    databaseRows = uiState.databaseRows,
                    lastExecutedSql = uiState.lastExecutedSql,
                    onAddRecord = { name, email, role -> viewModel.addDatabaseRecord(name, email, role) },
                    onDeleteRecord = { id -> viewModel.deleteDatabaseRecord(id) },
                    onBack = { viewModel.popBack() }
                )
            }
            is AppDestination.ApiLab -> {
                ApiLabScreen(
                    selectedEndpoint = uiState.selectedApiEndpoint,
                    isSending = uiState.isApiSending,
                    apiResponseReceived = uiState.apiResponseReceived,
                    onSelectEndpoint = { viewModel.selectApiEndpoint(it) },
                    onSendRequest = { viewModel.sendApiRequest() },
                    onBack = { viewModel.popBack() }
                )
            }
            is AppDestination.AuthLab -> {
                AuthLabScreen(onBack = { viewModel.popBack() })
            }
            is AppDestination.SecurityAcademy -> {
                SecurityAcademyScreen(
                    selectedTopic = uiState.selectedSecurityTopic,
                    isDefensiveMode = uiState.securitySimDefensiveMode,
                    onSelectTopic = { viewModel.selectSecurityTopic(it) },
                    onToggleDefenseMode = { viewModel.toggleSecurityMode() },
                    onBack = { viewModel.popBack() }
                )
            }
            is AppDestination.OopLab -> {
                OopVisualizerScreen(onBack = { viewModel.popBack() })
            }
            is AppDestination.FrameworksLab -> {
                FrameworksScreen(onBack = { viewModel.popBack() })
            }
            is AppDestination.AiTutor -> {
                AiTutorScreen(
                    messages = uiState.aiMessages,
                    isThinking = uiState.isAiThinking,
                    onSendMessage = { viewModel.sendAiQuestion(it) },
                    onBack = { viewModel.popBack() }
                )
            }
            is AppDestination.QuizCenter -> {
                QuizCenterScreen(onBack = { viewModel.popBack() })
            }
            is AppDestination.Certificate -> {
                CertificateScreen(
                    learnerName = uiState.userProfile.name,
                    onBack = { viewModel.popBack() }
                )
            }
            else -> {
                Box(modifier = Modifier.fillMaxSize())
            }
        }
    } else {
        // Main Tabbed Navigation Screen
        Scaffold(
            modifier = modifier.fillMaxSize(),
            topBar = {
                AppTopHeader(
                    xpPoints = uiState.userProfile.xpPoints,
                    streakDays = uiState.userProfile.streakDays,
                    onSearchClick = { viewModel.setSearchOpen(true) },
                    onAiTutorClick = { viewModel.navigateTo(AppDestination.AiTutor) }
                )
            },
            bottomBar = {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                    tonalElevation = 3.dp,
                    windowInsets = WindowInsets.navigationBars
                ) {
                    val tabs = listOf(
                        NavTabItem(NavigationTab.HOME, "Home", Icons.Default.Home, Icons.Outlined.Home),
                        NavTabItem(NavigationTab.LEARN, "Learn", Icons.Default.School, Icons.Outlined.School),
                        NavTabItem(NavigationTab.CODE_LAB, "Code Lab", Icons.Default.Terminal, Icons.Outlined.Terminal),
                        NavTabItem(NavigationTab.LABS, "Labs", Icons.Default.DeveloperBoard, Icons.Outlined.DeveloperBoard),
                        NavTabItem(NavigationTab.PROFILE, "Profile", Icons.Default.Person, Icons.Outlined.Person)
                    )

                    tabs.forEach { item ->
                        val selected = uiState.currentTab == item.tab
                        NavigationBarItem(
                            selected = selected,
                            onClick = { viewModel.setTab(item.tab) },
                            icon = {
                                Icon(
                                    imageVector = if (selected) item.activeIcon else item.inactiveIcon,
                                    contentDescription = item.label
                                )
                            },
                            label = {
                                Text(
                                    text = item.label,
                                    fontSize = 11.sp,
                                    fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color.White,
                                indicatorColor = PhpIndigoDark
                            ),
                            modifier = Modifier.testTag("nav_tab_${item.label.lowercase().replace(" ", "_")}")
                        )
                    }
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                when (uiState.currentTab) {
                    NavigationTab.HOME -> {
                        HomeScreen(
                            userProfile = uiState.userProfile,
                            onNavigateTo = { dest -> viewModel.navigateTo(dest) },
                            onSwitchToCodeLab = { viewModel.setTab(NavigationTab.CODE_LAB) },
                            onSwitchToLearn = { viewModel.setTab(NavigationTab.LEARN) }
                        )
                    }
                    NavigationTab.LEARN -> {
                        LearnScreen(
                            completedLessonIds = uiState.userProfile.completedLessonIds,
                            onSelectLesson = { lessonId ->
                                viewModel.navigateTo(AppDestination.LessonDetail(lessonId))
                            }
                        )
                    }
                    NavigationTab.CODE_LAB -> {
                        CodeLabScreen(
                            files = uiState.codeFiles,
                            activeFileName = uiState.activeFileName,
                            isExecuting = uiState.isExecutingCode,
                            executionResult = uiState.executionResult,
                            onSelectFile = { viewModel.selectFile(it) },
                            onCodeChange = { viewModel.updateActiveCode(it) },
                            onRunCode = { viewModel.runPhpCode() }
                        )
                    }
                    NavigationTab.LABS -> {
                        LabsAndProjectsScreen(
                            completedProjectIds = uiState.userProfile.completedProjectIds,
                            onNavigateTo = { dest -> viewModel.navigateTo(dest) },
                            onToggleProjectComplete = { viewModel.completeProject(it) },
                            onOpenCodeInLab = { code ->
                                viewModel.updateActiveCode(code)
                                viewModel.setTab(NavigationTab.CODE_LAB)
                            }
                        )
                    }
                    NavigationTab.PROFILE -> {
                        ProfileScreen(
                            userProfile = uiState.userProfile,
                            onNavigateTo = { dest -> viewModel.navigateTo(dest) }
                        )
                    }
                }
            }
        }
    }
}

private data class NavTabItem(
    val tab: NavigationTab,
    val label: String,
    val activeIcon: ImageVector,
    val inactiveIcon: ImageVector
)
