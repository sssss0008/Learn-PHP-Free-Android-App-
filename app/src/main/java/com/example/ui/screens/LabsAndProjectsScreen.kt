package com.example.ui.screens

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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.PhpProjectsAndLabsData
import com.example.model.ProjectItem
import com.example.ui.components.CodeSnippetCard
import com.example.ui.components.FreeBadge
import com.example.ui.theme.*
import com.example.viewmodel.AppDestination

@Composable
fun LabsAndProjectsScreen(
    completedProjectIds: Set<String>,
    onNavigateTo: (AppDestination) -> Unit,
    onToggleProjectComplete: (String) -> Unit,
    onOpenCodeInLab: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf(0) } // 0: Interactive Labs, 1: Real-World Projects
    val projects = PhpProjectsAndLabsData.sampleProjects

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 12.dp, bottom = 96.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Laboratories & Projects",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Hands-on server-side architectures & real applications",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                FreeBadge()
            }
        }

        item {
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                modifier = Modifier.clip(RoundedCornerShape(10.dp)),
                divider = {}
            ) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    text = { Text("Specialized Labs", fontWeight = FontWeight.Bold, fontSize = 12.sp) }
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    text = { Text("Full-Stack Projects", fontWeight = FontWeight.Bold, fontSize = 12.sp) }
                )
            }
        }

        if (selectedTab == 0) {
            // Interactive Labs Grid / Cards
            val labHubItems = listOf(
                LabHubItem("Server Execution Flow", "Interactive 8-stage diagram of how PHP executes on web servers", Icons.Default.Dns, PhpCyan) {
                    onNavigateTo(AppDestination.ServerFlowLab)
                },
                LabHubItem("MySQL Database & CRUD Lab", "Live database table simulator with PDO prepared statements", Icons.Default.Storage, PhpEmerald) {
                    onNavigateTo(AppDestination.DatabaseLab)
                },
                LabHubItem("REST API Laboratory & Client", "Test HTTP methods (GET, POST, PUT, DELETE) and JSON payloads", Icons.Default.Http, PhpAmber) {
                    onNavigateTo(AppDestination.ApiLab)
                },
                LabHubItem("Authentication & Session Lab", "Step through registration, Bcrypt hashing, and session states", Icons.Default.Lock, Color(0xFFEC4899)) {
                    onNavigateTo(AppDestination.AuthLab)
                },
                LabHubItem("Web Security Academy", "Hands-on defenses against SQL Injection, XSS, and CSRF attacks", Icons.Default.Shield, PhpRose) {
                    onNavigateTo(AppDestination.SecurityAcademy)
                },
                LabHubItem("OOP Architecture Visualizer", "Classes, inheritance, interface contracts, and trait composition", Icons.Default.Widgets, Color(0xFF8B5CF6)) {
                    onNavigateTo(AppDestination.OopLab)
                },
                LabHubItem("Frameworks: Laravel & WordPress", "Laravel routing & Eloquent, WordPress hooks & template hierarchy", Icons.Default.RocketLaunch, Color(0xFFF97316)) {
                    onNavigateTo(AppDestination.FrameworksLab)
                }
            )

            items(labHubItems) { item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { item.onClick() },
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(42.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(item.color.copy(alpha = 0.18f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(item.icon, contentDescription = null, tint = item.color, modifier = Modifier.size(24.dp))
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Text(item.title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            Text(item.description, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }

                        Icon(Icons.Default.ChevronRight, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        } else {
            // Real-World Projects
            items(projects) { project ->
                val isCompleted = completedProjectIds.contains(project.id)
                ProjectCardItem(
                    project = project,
                    isCompleted = isCompleted,
                    onToggleComplete = { onToggleProjectComplete(project.id) },
                    onOpenCode = { code -> onOpenCodeInLab(code) }
                )
            }
        }
    }
}

private data class LabHubItem(
    val title: String,
    val description: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val color: Color,
    val onClick: () -> Unit
)

@Composable
private fun ProjectCardItem(
    project: ProjectItem,
    isCompleted: Boolean,
    onToggleComplete: () -> Unit,
    onOpenCode: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(
                            when (project.level) {
                                "Beginner" -> PhpEmerald.copy(alpha = 0.15f)
                                "Intermediate" -> PhpIndigo.copy(alpha = 0.15f)
                                else -> PhpAmber.copy(alpha = 0.15f)
                            }
                        )
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = project.level.uppercase(),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = when (project.level) {
                            "Beginner" -> PhpEmerald
                            "Intermediate" -> PhpIndigo
                            else -> PhpAmber
                        }
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    if (isCompleted) {
                        Text("COMPLETED", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = PhpEmerald)
                    }
                    IconButton(onClick = onToggleComplete, modifier = Modifier.size(28.dp)) {
                        Icon(
                            imageVector = if (isCompleted) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                            contentDescription = "Toggle Complete",
                            tint = if (isCompleted) PhpEmerald else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Text(project.title, fontWeight = FontWeight.Bold, fontSize = 15.sp)
            Text(project.description, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)

            // Tags
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                project.tags.take(3).forEach { tag ->
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(tag, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }

            OutlinedButton(
                onClick = { expanded = !expanded },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (expanded) "Hide File Structure & Code" else "Explore Architecture & Files")
            }

            AnimatedVisibility(visible = expanded) {
                Column(modifier = Modifier.padding(top = 8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Project File Explorer:", fontWeight = FontWeight.Bold, fontSize = 12.sp)

                    project.fileList.forEach { file ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onOpenCode(file.content) },
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                        ) {
                            Row(
                                modifier = Modifier.padding(8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                    Icon(Icons.Default.InsertDriveFile, contentDescription = null, tint = PhpIndigoLight, modifier = Modifier.size(16.dp))
                                    Text(file.path, fontFamily = FontFamily.Monospace, fontSize = 11.sp)
                                }
                                Text("Open in Lab", fontSize = 10.sp, color = PhpIndigo, fontWeight = FontWeight.Bold)
                            }
                        }
                    }

                    if (project.fileList.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(4.dp))
                        CodeSnippetCard(code = project.fileList.first().content)
                    }
                }
            }
        }
    }
}
