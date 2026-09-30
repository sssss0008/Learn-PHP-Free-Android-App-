package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.CreatorCreditCard
import com.example.ui.components.FreeBadge
import com.example.ui.theme.*

@Composable
fun OnboardingScreen(
    onComplete: (name: String, level: String, goals: List<String>) -> Unit,
    modifier: Modifier = Modifier
) {
    var step by remember { mutableStateOf(0) }
    var userName by remember { mutableStateOf("Awiskar") }
    var experienceLevel by remember { mutableStateOf("Beginner") }
    var selectedGoals by remember {
        mutableStateOf(
            listOf("Learn Backend Development", "Learn MySQL", "Build APIs", "Learn Laravel")
        )
    }

    val context = LocalContext.current

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(20.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Top indicator
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (step > 0) {
                    IconButton(onClick = { step-- }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                } else {
                    FreeBadge()
                }

                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    for (i in 0..4) {
                        Box(
                            modifier = Modifier
                                .size(width = if (i == step) 24.dp else 8.dp, height = 6.dp)
                                .clip(RoundedCornerShape(3.dp))
                                .background(
                                    if (i == step) PhpIndigo
                                    else if (i < step) PhpCyan
                                    else MaterialTheme.colorScheme.surfaceVariant
                                )
                        )
                    }
                }

                if (step == 0) {
                    TextButton(onClick = { step = 4 }) {
                        Text("Skip", color = MaterialTheme.colorScheme.primary)
                    }
                } else {
                    Spacer(modifier = Modifier.width(36.dp))
                }
            }

            // Animated step content
            AnimatedContent(
                targetState = step,
                transitionSpec = {
                    if (targetState > initialState) {
                        slideInHorizontally { width -> width } + fadeIn() togetherWith
                                slideOutHorizontally { width -> -width } + fadeOut()
                    } else {
                        slideInHorizontally { width -> -width } + fadeIn() togetherWith
                                slideOutHorizontally { width -> width } + fadeOut()
                    }
                },
                modifier = Modifier.weight(1f),
                label = "onboarding_step"
            ) { currentStep ->
                when (currentStep) {
                    0 -> WelcomeStep(onLinkedInClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/awiskaracharya/"))
                        context.startActivity(intent)
                    })
                    1 -> NameStep(name = userName, onNameChange = { userName = it })
                    2 -> ExperienceStep(selectedLevel = experienceLevel, onSelectLevel = { experienceLevel = it })
                    3 -> GoalsStep(
                        selectedGoals = selectedGoals,
                        onToggleGoal = { goal ->
                            selectedGoals = if (selectedGoals.contains(goal)) {
                                selectedGoals - goal
                            } else {
                                selectedGoals + goal
                            }
                        }
                    )
                    4 -> RoadmapStep(name = userName, experienceLevel = experienceLevel)
                }
            }

            // Bottom action button
            Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Button(
                    onClick = {
                        if (step < 4) {
                            step++
                        } else {
                            onComplete(userName, experienceLevel, selectedGoals)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .testTag("onboarding_continue_btn"),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PhpIndigoDark)
                ) {
                    Text(
                        text = if (step == 0) "Start Learning" else if (step == 4) "Begin My PHP Journey" else "Continue",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = if (step == 4) Icons.Default.RocketLaunch else Icons.Default.ArrowForward,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                }

                if (step == 0) {
                    OutlinedButton(
                        onClick = { step = 1 },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("onboarding_explore_btn"),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Text("Explore PHP Ecosystem", fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    }
}

@Composable
private fun WelcomeStep(onLinkedInClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Tech Vector Graphic Illustration
        Box(
            modifier = Modifier
                .size(160.dp)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        colors = listOf(PhpIndigoDark.copy(alpha = 0.4f), Color.Transparent)
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(
                        Brush.linearGradient(
                            listOf(PhpIndigoDark, Color(0xFF1E1B4B))
                        )
                    )
                    .border(1.5.dp, PhpCyan.copy(alpha = 0.5f), RoundedCornerShape(24.dp)),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "<?php",
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = PhpCyanLight
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        Icon(Icons.Default.Dns, contentDescription = null, tint = PhpEmerald, modifier = Modifier.size(16.dp))
                        Icon(Icons.Default.DataObject, contentDescription = null, tint = PhpAmber, modifier = Modifier.size(16.dp))
                        Icon(Icons.Default.Security, contentDescription = null, tint = PhpRose, modifier = Modifier.size(16.dp))
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Learn PHP From Zero",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "“Learn. Code. Build. Power the Web with PHP.”",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Master server-side programming, MySQL databases, REST APIs, authentication, web security, MVC, Laravel, WordPress, and deploy production web applications.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            lineHeight = 22.sp,
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        CreatorCreditCard(onLinkedInClick = onLinkedInClick)
    }
}

@Composable
private fun NameStep(name: String, onNameChange: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(PhpIndigo.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.Person, contentDescription = null, tint = PhpIndigoLight)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "What should we call you?",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "We will personalize your PHP learning journey and generate your certificate of completion with this name.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = name,
            onValueChange = onNameChange,
            label = { Text("Enter your name") },
            placeholder = { Text("e.g. Awiskar") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .testTag("name_input_field"),
            shape = RoundedCornerShape(14.dp),
            leadingIcon = {
                Icon(Icons.Outlined.Badge, contentDescription = null, tint = PhpCyan)
            }
        )
    }
}

@Composable
private fun ExperienceStep(selectedLevel: String, onSelectLevel: (String) -> Unit) {
    val levels = listOf(
        Triple("Complete Beginner", "No coding experience yet", Icons.Default.ChildCare),
        Triple("Beginner", "Know some HTML/CSS basics", Icons.Default.School),
        Triple("Some Programming Experience", "Familiar with Python, JS, or C", Icons.Default.Terminal),
        Triple("Frontend Developer", "Building UIs, now learning backend", Icons.Default.Web),
        Triple("Intermediate", "Built some PHP or basic scripts", Icons.Default.Code),
        Triple("Advanced", "Looking to master modern PHP 8 & Laravel", Icons.Default.Psychology)
    )

    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "What's your web development experience?",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "We tailor explanations and interactive coding challenges to your background.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(levels) { (title, subtitle, icon) ->
                val isSelected = selectedLevel == title
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSelectLevel(title) }
                        .testTag("exp_card_$title"),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isSelected) PhpIndigoDark.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surfaceVariant
                    ),
                    border = if (isSelected) CardDefaults.outlinedCardBorder().copy(brush = Brush.linearGradient(listOf(PhpIndigo, PhpCyan))) else null
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(if (isSelected) PhpIndigo else MaterialTheme.colorScheme.surface),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = icon,
                                contentDescription = null,
                                tint = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            Text(text = subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }

                        if (isSelected) {
                            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = PhpCyan)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun GoalsStep(selectedGoals: List<String>, onToggleGoal: (String) -> Unit) {
    val goalsList = listOf(
        "Learn Backend Development",
        "Build Websites",
        "Build Web Applications",
        "Learn MySQL",
        "Build APIs",
        "Learn Laravel",
        "Develop WordPress",
        "Build E-commerce",
        "Authentication Systems",
        "Database Applications",
        "Full-Stack Development",
        "Freelancing",
        "College Study",
        "Interview Preparation"
    )

    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "What do you want to build with PHP?",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Select all that apply. We adapt project recommendations accordingly.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(goalsList) { goal ->
                val isSelected = selectedGoals.contains(goal)
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onToggleGoal(goal) }
                        .testTag("goal_card_$goal"),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isSelected) PhpIndigo.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surfaceVariant
                    ),
                    border = if (isSelected) CardDefaults.outlinedCardBorder().copy(brush = Brush.linearGradient(listOf(PhpIndigo, PhpCyan))) else null
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = goal, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                        Checkbox(
                            checked = isSelected,
                            onCheckedChange = { onToggleGoal(goal) },
                            colors = CheckboxDefaults.colors(checkedColor = PhpIndigo)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun RoadmapStep(name: String, experienceLevel: String) {
    val roadmapSteps = listOf(
        "Web Fundamentals (HTTP, Client-Server)",
        "PHP Basics & Syntax",
        "Variables, Data Types & Scope",
        "Conditions, Loops & Arrays",
        "Functions & String Mastery",
        "Forms, Requests & File Uploads",
        "Object-Oriented PHP Academy",
        "Sessions & Password Security",
        "MySQL & Database Development (PDO)",
        "REST APIs & JSON Architecture",
        "Web Security (SQLi, XSS, CSRF)",
        "Composer, Modern PHP & Laravel",
        "Real-World Projects & Deployment"
    )

    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "Your Personalized PHP Journey",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Welcome, $name! Here is your generated step-by-step pathway from zero to production.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(roadmapSteps) { stepTitle ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                        .padding(horizontal = 12.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(PhpEmerald.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Check, contentDescription = null, tint = PhpEmerald, modifier = Modifier.size(14.dp))
                    }
                    Text(text = stepTitle, fontSize = 13.sp, fontWeight = FontWeight.Medium)
                }
            }
        }
    }
}
