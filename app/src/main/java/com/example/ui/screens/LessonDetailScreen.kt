package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.PhpCurriculum
import com.example.model.PhpLesson
import com.example.ui.components.CodeSnippetCard
import com.example.ui.components.CreatorCreditCard
import com.example.ui.components.FreeBadge
import com.example.ui.theme.*

@Composable
fun LessonDetailScreen(
    lessonId: String,
    isCompleted: Boolean,
    isBookmarked: Boolean,
    onBack: () -> Unit,
    onToggleComplete: () -> Unit,
    onToggleBookmark: () -> Unit,
    onOpenInCodeLab: (code: String) -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler { onBack() }

    val lesson = PhpCurriculum.getLessonById(lessonId) ?: return
    var showSolution by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            Surface(
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 2.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(horizontal = 8.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        IconButton(onClick = onToggleBookmark) {
                            Icon(
                                imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Outlined.BookmarkBorder,
                                contentDescription = "Bookmark",
                                tint = if (isBookmarked) PhpAmber else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        IconButton(onClick = onToggleComplete) {
                            Icon(
                                imageVector = if (isCompleted) Icons.Default.CheckCircle else Icons.Outlined.CheckCircle,
                                contentDescription = "Complete",
                                tint = if (isCompleted) PhpEmerald else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        },
        bottomBar = {
            Surface(
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 4.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = { onOpenInCodeLab(lesson.interactiveCode) },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .testTag("open_in_lab_btn"),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Default.Terminal, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Open in IDE")
                    }

                    Button(
                        onClick = onToggleComplete,
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .testTag("complete_lesson_action_btn"),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isCompleted) PhpEmerald else PhpIndigoDark
                        )
                    ) {
                        Icon(
                            imageVector = if (isCompleted) Icons.Default.DoneAll else Icons.Default.Check,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (isCompleted) "Completed (+50 XP)" else "Mark Done",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(top = 12.dp, bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header Info
            item {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(PhpIndigo.copy(alpha = 0.15f))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = "${lesson.readTimeMinutes} MIN LESSON",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = PhpIndigo
                                )
                            }
                            FreeBadge()
                        }

                        if (isCompleted) {
                            Text(
                                text = "PASSED",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = PhpEmerald
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = lesson.title,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = lesson.shortSummary,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Section 1: Concept Overview
            item {
                SectionCard(title = "What is it?", icon = Icons.Default.Info) {
                    Text(
                        text = lesson.conceptOverview,
                        style = MaterialTheme.typography.bodyMedium,
                        lineHeight = 22.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            // Section 2: Real-World Usage
            item {
                SectionCard(title = "Why is it useful in production?", icon = Icons.Default.Work) {
                    Text(
                        text = lesson.realWorldUsage,
                        style = MaterialTheme.typography.bodyMedium,
                        lineHeight = 22.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            // Section 3: Server Execution Flow
            item {
                SectionCard(title = "Server-Side Execution Flow", icon = Icons.Default.Dns) {
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(
                            text = lesson.serverFlowDescription,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium,
                            color = PhpCyanLight,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 12.sp
                        )
                        Text(
                            text = "Every PHP script processes on the server inside the Zend Engine before sending HTTP headers and HTML payload back to the browser.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            // Section 4: Interactive Code & Output
            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "Interactive Code Example",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    CodeSnippetCard(
                        code = lesson.interactiveCode,
                        onRunInLab = { onOpenInCodeLab(lesson.interactiveCode) }
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Simulated Server Output:",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(containerColor = CodeEditorBg)
                    ) {
                        Text(
                            text = lesson.simulatedOutput,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 12.sp,
                            color = Color(0xFF7EE787),
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                }
            }

            // Section 5: Common Mistakes
            item {
                SectionCard(title = "Common Mistakes to Avoid", icon = Icons.Default.Warning, headerTint = PhpAmber) {
                    Text(
                        text = lesson.commonMistakes,
                        style = MaterialTheme.typography.bodyMedium,
                        lineHeight = 20.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            // Section 6: Security Considerations
            item {
                SectionCard(title = "Security Best Practices", icon = Icons.Default.Shield, headerTint = PhpRose) {
                    Text(
                        text = lesson.securityTip,
                        style = MaterialTheme.typography.bodyMedium,
                        lineHeight = 20.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            // Section 7: Practice Challenge
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Icon(Icons.Default.Code, contentDescription = null, tint = PhpIndigo)
                            Text("Interactive Practice Challenge", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = lesson.practiceTask,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        OutlinedButton(
                            onClick = { showSolution = !showSolution },
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(if (showSolution) "Hide Solution" else "Reveal Solution")
                        }

                        AnimatedVisibility(visible = showSolution) {
                            Column(modifier = Modifier.padding(top = 10.dp)) {
                                CodeSnippetCard(code = lesson.practiceSolution)
                            }
                        }
                    }
                }
            }

            item {
                CreatorCreditCard()
            }
        }
    }
}

@Composable
private fun SectionCard(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    headerTint: Color = MaterialTheme.colorScheme.primary,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(icon, contentDescription = null, tint = headerTint, modifier = Modifier.size(18.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            content()
        }
    }
}
