package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.PhpProjectsAndLabsData
import com.example.model.SecuritySimTopic
import com.example.ui.components.CodeSnippetCard
import com.example.ui.theme.*

@Composable
fun SecurityAcademyScreen(
    selectedTopic: SecuritySimTopic,
    isDefensiveMode: Boolean,
    onSelectTopic: (SecuritySimTopic) -> Unit,
    onToggleDefenseMode: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler { onBack() }

    val topics = PhpProjectsAndLabsData.securityTopics
    var customAttackPayload by remember(selectedTopic) { mutableStateOf(selectedTopic.attackInputExample) }
    var simulatedResult by remember(selectedTopic, isDefensiveMode) {
        mutableStateOf(if (isDefensiveMode) "Query safely compiled with PDO parameter binding. 0 unauthorized records returned." else selectedTopic.attackResult)
    }

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
                        text = "Web Security Academy & Simulator",
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
                Text(
                    text = "Interactive Security Vulnerability Lab",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Understand how real attacks work and how to implement zero-trust PHP defenses.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Topic Selector
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    topics.forEach { topic ->
                        val isSelected = topic.id == selectedTopic.id
                        FilterChip(
                            selected = isSelected,
                            onClick = { onSelectTopic(topic) },
                            label = { Text(topic.type, fontSize = 11.sp) },
                            modifier = Modifier.weight(1f),
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = if (isDefensiveMode) PhpEmerald.copy(alpha = 0.2f) else PhpRose.copy(alpha = 0.2f)
                            )
                        )
                    }
                }
            }

            // Defense Mode Toggle Switch Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isDefensiveMode) PhpEmerald.copy(alpha = 0.15f) else PhpRose.copy(alpha = 0.15f)
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(if (isDefensiveMode) PhpEmerald else PhpRose),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = if (isDefensiveMode) Icons.Default.Shield else Icons.Default.Warning,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Column {
                                Text(
                                    text = if (isDefensiveMode) "DEFENSE SHIELD ACTIVE" else "VULNERABLE MODE",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = if (isDefensiveMode) PhpEmerald else PhpRose
                                )
                                Text(
                                    text = if (isDefensiveMode) "Using PDO prepared statements & escaping" else "Unsafe raw concatenation in effect",
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        Switch(
                            checked = isDefensiveMode,
                            onCheckedChange = { onToggleDefenseMode() },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = PhpEmerald,
                                uncheckedThumbColor = Color.White,
                                uncheckedTrackColor = PhpRose
                            ),
                            modifier = Modifier.testTag("security_mode_switch")
                        )
                    }
                }
            }

            // Attack Simulation Input & Result
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text(
                            text = "Simulate Exploit Payload Input:",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )

                        OutlinedTextField(
                            value = customAttackPayload,
                            onValueChange = { customAttackPayload = it },
                            label = { Text("Malicious Input String") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            textStyle = androidx.compose.ui.text.TextStyle(fontFamily = FontFamily.Monospace, fontSize = 12.sp)
                        )

                        Button(
                            onClick = {
                                simulatedResult = if (isDefensiveMode) {
                                    "SAFE: Parameter '$customAttackPayload' was treated strictly as plain string data. No code executed."
                                } else {
                                    "ATTACK EXECUTED: $customAttackPayload penetrated the query/script filter!"
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isDefensiveMode) PhpEmerald else PhpRose
                            )
                        ) {
                            Icon(Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(if (isDefensiveMode) "Test Defense with Input" else "Execute Attack Simulation")
                        }

                        // Simulation Result Box
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = CodeEditorBg),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text(
                                    text = "SERVER EXECUTION EFFECT:",
                                    fontSize = 10.sp,
                                    fontFamily = FontFamily.Monospace,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isDefensiveMode) PhpEmerald else Color(0xFFFF7B72)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = simulatedResult,
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = 11.sp,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }

            // Code Comparison
            item {
                Text(
                    text = if (isDefensiveMode) "Secure Implementation Code:" else "Vulnerable Code Sample:",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
                CodeSnippetCard(
                    code = if (isDefensiveMode) selectedTopic.secureCode else selectedTopic.vulnerableCode,
                    language = "php"
                )
            }

            // Core Defense Principle
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = PhpEmerald, modifier = Modifier.size(18.dp))
                            Text("Core Defense Principle", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = selectedTopic.defensePrinciple,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface,
                            lineHeight = 18.sp
                        )
                    }
                }
            }
        }
    }
}
