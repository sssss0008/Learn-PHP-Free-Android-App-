package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
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
import com.example.model.DatabaseRow
import com.example.ui.components.CodeSnippetCard
import com.example.ui.theme.*

@Composable
fun DatabaseLabScreen(
    databaseRows: List<DatabaseRow>,
    lastExecutedSql: String,
    onAddRecord: (name: String, email: String, role: String) -> Unit,
    onDeleteRecord: (id: Int) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler { onBack() }

    var showAddDialog by remember { mutableStateOf(false) }
    var newName by remember { mutableStateOf("") }
    var newEmail by remember { mutableStateOf("") }
    var newRole by remember { mutableStateOf("Student Developer") }
    var selectedDriverTab by remember { mutableStateOf(0) } // 0: PDO (Recommended), 1: MySQLi

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
                        text = "MySQL Database & CRUD Laboratory",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = PhpEmerald,
                contentColor = Color.White,
                modifier = Modifier.testTag("add_db_record_fab")
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Student")
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(top = 12.dp, bottom = 96.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Text(
                    text = "Interactive MySQL Database (Table: students)",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Perform live CRUD operations and observe generated SQL statements and PDO prepared queries.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Last Executed SQL Query Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = CodeEditorBg)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Icon(Icons.Default.Storage, contentDescription = null, tint = PhpEmerald, modifier = Modifier.size(16.dp))
                            Text(
                                text = "LAST EXECUTED SQL QUERY",
                                fontSize = 10.sp,
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold,
                                color = PhpEmerald
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = lastExecutedSql,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 12.sp,
                            color = Color(0xFFA5D6FF)
                        )
                    }
                }
            }

            // Driver Selector Tab (PDO vs MySQLi)
            item {
                TabRow(
                    selectedTabIndex = selectedDriverTab,
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    modifier = Modifier.clip(RoundedCornerShape(10.dp)),
                    divider = {}
                ) {
                    Tab(
                        selected = selectedDriverTab == 0,
                        onClick = { selectedDriverTab = 0 },
                        text = { Text("PDO Prepared Statements (Recommended)", fontWeight = FontWeight.Bold, fontSize = 12.sp) }
                    )
                    Tab(
                        selected = selectedDriverTab == 1,
                        onClick = { selectedDriverTab = 1 },
                        text = { Text("MySQLi (Procedural vs OO)", fontWeight = FontWeight.Bold, fontSize = 12.sp) }
                    )
                }
            }

            // Code Preview for Selected Driver
            item {
                if (selectedDriverTab == 0) {
                    CodeSnippetCard(
                        code = "<?php\n// PDO Prepared Statement - SQL Injection Proof\n\$stmt = \$pdo->prepare('INSERT INTO students (name, email, role) VALUES (:name, :email, :role)');\n\$stmt->execute([\n    'name' => \$name,\n    'email' => \$email,\n    'role' => \$role\n]);\n?>",
                        language = "php"
                    )
                } else {
                    CodeSnippetCard(
                        code = "<?php\n// MySQLi Prepared Statement\n\$stmt = \$mysqli->prepare('INSERT INTO students (name, email, role) VALUES (?, ?, ?)');\n\$stmt->bind_param('sss', \$name, \$email, \$role);\n\$stmt->execute();\n?>",
                        language = "php"
                    )
                }
            }

            // Records List Header
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Current Rows (${databaseRows.size})",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                    Text(
                        text = "Auto-increment ID Primary Key",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Table Records Items
            items(databaseRows) { row ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("db_row_${row.id}"),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(PhpIndigo.copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "#${row.id}",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    color = PhpIndigo
                                )
                            }

                            Column {
                                Text(
                                    text = row.name,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp
                                )
                                Text(
                                    text = "${row.email} • ${row.role}",
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        IconButton(
                            onClick = { onDeleteRecord(row.id) },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete Row",
                                tint = PhpRose,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }
        }
    }

    // Add Record Dialog
    if (showAddDialog) {
        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = { Text("Insert New Student Record") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedTextField(
                        value = newName,
                        onValueChange = { newName = it },
                        label = { Text("Student Name") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = newEmail,
                        onValueChange = { newEmail = it },
                        label = { Text("Email Address") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = newRole,
                        onValueChange = { newRole = it },
                        label = { Text("Course / Role") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (newName.isNotBlank()) {
                            onAddRecord(
                                newName.trim(),
                                if (newEmail.isBlank()) "${newName.lowercase().replace(" ", "")}@learnphp.dev" else newEmail.trim(),
                                newRole.trim()
                            )
                            newName = ""
                            newEmail = ""
                            showAddDialog = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = PhpEmerald)
                ) {
                    Text("Execute INSERT")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}
