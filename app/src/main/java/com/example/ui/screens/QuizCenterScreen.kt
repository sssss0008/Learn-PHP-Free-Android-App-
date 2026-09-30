package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.PhpProjectsAndLabsData
import com.example.model.InterviewItem
import com.example.model.QuizItem
import com.example.ui.components.CodeSnippetCard
import com.example.ui.theme.*

@Composable
fun QuizCenterScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler { onBack() }

    var selectedModeTab by remember { mutableStateOf(0) } // 0: Quiz Practice, 1: Interview Question Bank
    val quizzes = PhpProjectsAndLabsData.quizQuestions
    val interviewQuestions = PhpProjectsAndLabsData.interviewQuestions

    // Quiz State
    var currentQuizIndex by remember { mutableStateOf(0) }
    var selectedOptionIndex by remember { mutableStateOf<Int?>(null) }
    var score by remember { mutableStateOf(0) }
    var quizFinished by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            Surface(color = MaterialTheme.colorScheme.surface, tonalElevation = 2.dp) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(horizontal = 8.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                    Text(
                        text = "Quiz & Interview Preparation Center",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(top = 12.dp, bottom = 48.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                TabRow(
                    selectedTabIndex = selectedModeTab,
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    modifier = Modifier.clip(RoundedCornerShape(10.dp)),
                    divider = {}
                ) {
                    Tab(
                        selected = selectedModeTab == 0,
                        onClick = { selectedModeTab = 0 },
                        text = { Text("Interactive Quiz", fontWeight = FontWeight.Bold, fontSize = 12.sp) }
                    )
                    Tab(
                        selected = selectedModeTab == 1,
                        onClick = { selectedModeTab = 1 },
                        text = { Text("Interview Questions", fontWeight = FontWeight.Bold, fontSize = 12.sp) }
                    )
                }
            }

            if (selectedModeTab == 0) {
                // Interactive Quiz Flow
                if (!quizFinished && currentQuizIndex < quizzes.size) {
                    val currentQuestion = quizzes[currentQuizIndex]

                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                        ) {
                            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "Question ${currentQuizIndex + 1} of ${quizzes.size}",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = PhpIndigo
                                    )
                                    Text(
                                        text = "Topic: ${currentQuestion.topic}",
                                        fontSize = 12.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }

                                Text(
                                    text = currentQuestion.question,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )

                                Spacer(modifier = Modifier.height(4.dp))

                                currentQuestion.options.forEachIndexed { optIndex, optionText ->
                                    val isSelected = selectedOptionIndex == optIndex
                                    val isAnswered = selectedOptionIndex != null
                                    val isCorrect = optIndex == currentQuestion.correctIndex

                                    val cardColor = when {
                                        isAnswered && isCorrect -> PhpEmerald.copy(alpha = 0.2f)
                                        isAnswered && isSelected -> PhpRose.copy(alpha = 0.2f)
                                        isSelected -> PhpIndigo.copy(alpha = 0.2f)
                                        else -> MaterialTheme.colorScheme.surfaceVariant
                                    }

                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable(enabled = !isAnswered) {
                                                selectedOptionIndex = optIndex
                                                if (isCorrect) score++
                                            }
                                            .testTag("quiz_opt_$optIndex"),
                                        shape = RoundedCornerShape(12.dp),
                                        colors = CardDefaults.cardColors(containerColor = cardColor)
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(14.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                                        ) {
                                            Text(
                                                text = "${('A' + optIndex)}.",
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 13.sp,
                                                color = MaterialTheme.colorScheme.primary
                                            )
                                            Text(
                                                text = optionText,
                                                fontSize = 13.sp,
                                                color = MaterialTheme.colorScheme.onSurface,
                                                modifier = Modifier.weight(1f)
                                            )
                                            if (isAnswered) {
                                                if (isCorrect) {
                                                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = PhpEmerald)
                                                } else if (isSelected) {
                                                    Icon(Icons.Default.Cancel, contentDescription = null, tint = PhpRose)
                                                }
                                            }
                                        }
                                    }
                                }

                                if (selectedOptionIndex != null) {
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Card(
                                        modifier = Modifier.fillMaxWidth(),
                                        shape = RoundedCornerShape(10.dp),
                                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                                    ) {
                                        Column(modifier = Modifier.padding(12.dp)) {
                                            Text("Explanation:", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = MaterialTheme.colorScheme.primary)
                                            Spacer(modifier = Modifier.height(4.dp))
                                            Text(currentQuestion.explanation, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface)
                                        }
                                    }

                                    Button(
                                        onClick = {
                                            if (currentQuizIndex < quizzes.size - 1) {
                                                currentQuizIndex++
                                                selectedOptionIndex = null
                                            } else {
                                                quizFinished = true
                                            }
                                        },
                                        modifier = Modifier.fillMaxWidth(),
                                        colors = ButtonDefaults.buttonColors(containerColor = PhpIndigoDark)
                                    ) {
                                        Text(if (currentQuizIndex < quizzes.size - 1) "Next Question" else "View Final Score")
                                    }
                                }
                            }
                        }
                    }
                } else {
                    // Quiz Result Card
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(24.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(64.dp)
                                        .clip(CircleShape)
                                        .background(PhpEmerald.copy(alpha = 0.2f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(Icons.Default.EmojiEvents, contentDescription = null, tint = PhpEmerald, modifier = Modifier.size(36.dp))
                                }

                                Text("Quiz Completed!", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                                Text("You scored $score out of ${quizzes.size} questions correctly.", fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)

                                Button(
                                    onClick = {
                                        currentQuizIndex = 0
                                        selectedOptionIndex = null
                                        score = 0
                                        quizFinished = false
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = PhpIndigoDark)
                                ) {
                                    Text("Retake Quiz")
                                }
                            }
                        }
                    }
                }
            } else {
                // Interview Questions Bank
                items(interviewQuestions) { item ->
                    InterviewCard(item = item)
                }
            }
        }
    }
}

@Composable
private fun InterviewCard(item: InterviewItem) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(PhpIndigo.copy(alpha = 0.15f))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(item.category.uppercase(), fontSize = 10.sp, fontWeight = FontWeight.Bold, color = PhpIndigo)
                }
                Icon(
                    imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Text(item.question, fontWeight = FontWeight.Bold, fontSize = 14.sp)

            AnimatedVisibility(visible = expanded) {
                Column(modifier = Modifier.padding(top = 8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = item.answer,
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = 20.sp
                    )

                    if (item.codeSample.isNotBlank()) {
                        CodeSnippetCard(code = item.codeSample)
                    }
                }
            }
        }
    }
}
