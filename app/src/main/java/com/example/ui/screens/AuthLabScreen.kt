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
import com.example.ui.components.CodeSnippetCard
import com.example.ui.theme.*

@Composable
fun AuthLabScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler { onBack() }

    var authStage by remember { mutableStateOf(0) } // 0: Registration, 1: Hash Generated, 2: Login Test, 3: Session Active
    var inputPassword by remember { mutableStateOf("Secret@Pass2026") }
    var loginAttemptPassword by remember { mutableStateOf("Secret@Pass2026") }
    var simulatedHash by remember { mutableStateOf("\$2y$12\$e8f9e2k1091mZv09XqpLm2Z891820... (Bcrypt 60 chars)") }
    var isAuthSuccess by remember { mutableStateOf(true) }

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
                        text = "Authentication & Session Laboratory",
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
                    text = "Learn How Secure Auth Works in PHP",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Step through registration, Bcrypt hashing, password verification, and session state creation.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Step Progress Indicator
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    val steps = listOf("Register", "Hash", "Verify", "Session")
                    steps.forEachIndexed { index, name ->
                        val active = index <= authStage
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(4.dp)
                                    .clip(RoundedCornerShape(2.dp))
                                    .background(if (active) PhpIndigo else MaterialTheme.colorScheme.surfaceVariant)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = name,
                                fontSize = 10.sp,
                                fontWeight = if (index == authStage) FontWeight.Bold else FontWeight.Normal,
                                color = if (index == authStage) PhpIndigoLight else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }

            // Step 1: Registration & Hashing
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .clip(CircleShape)
                                    .background(PhpIndigo.copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("1", fontWeight = FontWeight.Bold, color = PhpIndigo)
                            }
                            Text("User Registration & password_hash()", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        }

                        Text(
                            text = "Never store plain passwords! PHP uses Bcrypt with 2^12 cost factor and auto-salting.",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        OutlinedTextField(
                            value = inputPassword,
                            onValueChange = { inputPassword = it },
                            label = { Text("Sample User Password") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Button(
                            onClick = {
                                simulatedHash = "\$2y$12\$" + (0..20).map { ('a'..'z').random() }.joinToString("") + "...(Bcrypt)"
                                authStage = 1
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = PhpIndigoDark)
                        ) {
                            Icon(Icons.Default.Key, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Compute password_hash(\$password, PASSWORD_BCRYPT)")
                        }

                        if (authStage >= 1) {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(containerColor = CodeEditorBg),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Column(modifier = Modifier.padding(10.dp)) {
                                    Text("STORED IN MYSQL DATABASE (users.password_hash):", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = PhpEmerald)
                                    Text(simulatedHash, fontSize = 11.sp, fontFamily = FontFamily.Monospace, color = Color(0xFFA5D6FF))
                                }
                            }
                        }
                    }
                }
            }

            // Step 2: Login Verification
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .clip(CircleShape)
                                    .background(PhpEmerald.copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("2", fontWeight = FontWeight.Bold, color = PhpEmerald)
                            }
                            Text("Login Attempt & password_verify()", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        }

                        OutlinedTextField(
                            value = loginAttemptPassword,
                            onValueChange = { loginAttemptPassword = it },
                            label = { Text("Enter Password to Test Login") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Button(
                                onClick = {
                                    isAuthSuccess = (loginAttemptPassword == inputPassword)
                                    authStage = if (isAuthSuccess) 3 else 2
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = PhpEmerald)
                            ) {
                                Icon(Icons.Default.CheckCircle, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Verify Password")
                            }

                            OutlinedButton(onClick = { loginAttemptPassword = "WrongPassword999" }) {
                                Text("Simulate Wrong Password")
                            }
                        }

                        if (authStage >= 2) {
                            if (isAuthSuccess) {
                                Text(
                                    text = "Password Matched! password_verify() returned TRUE.",
                                    color = PhpEmerald,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp
                                )
                            } else {
                                Text(
                                    text = "Authentication Failed: password_verify() returned FALSE.",
                                    color = PhpRose,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp
                                )
                            }
                        }
                    }
                }
            }

            // Step 3: Session State Active
            if (authStage >= 3 && isAuthSuccess) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                    ) {
                        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Icon(Icons.Default.Shield, contentDescription = null, tint = PhpIndigoLight)
                                Text("Protected Dashboard Unlocked!", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                            }
                            Text(
                                text = "\$_SESSION['user_id'] = 42; session_regenerate_id(true);\nCookie: PHPSESSID set with HttpOnly and SameSite=Strict.",
                                fontFamily = FontFamily.Monospace,
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                }
            }

            // Code Implementation Reference
            item {
                Text("PHP Authentication Blueprint:", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                CodeSnippetCard(
                    code = "<?php\nsession_start();\n\n// 1. Registration\n\$hash = password_hash(\$password, PASSWORD_BCRYPT, ['cost' => 12]);\n\n// 2. Login Verification\nif (password_verify(\$passwordAttempt, \$hashFromDatabase)) {\n    session_regenerate_id(true); // Prevent session fixation\n    \$_SESSION['user_id'] = \$user['id'];\n    header('Location: /dashboard.php');\n    exit;\n}\n?>",
                    language = "php"
                )
            }
        }
    }
}
