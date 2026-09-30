package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*
import com.example.viewmodel.ExecutionResult

@Composable
fun CodeLabScreen(
    files: Map<String, String>,
    activeFileName: String,
    isExecuting: Boolean,
    executionResult: ExecutionResult,
    onSelectFile: (String) -> Unit,
    onCodeChange: (String) -> Unit,
    onRunCode: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedInspectorTab by remember { mutableStateOf(0) } // 0: Output, 1: Variables, 2: Server Logs, 3: Request, 4: Errors
    val activeCode = files[activeFileName] ?: ""

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBg)
    ) {
        // Multi-File Tabs Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF0F172A))
                .padding(horizontal = 8.dp, vertical = 6.dp)
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            files.keys.forEach { fileName ->
                val isActive = fileName == activeFileName
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (isActive) Color(0xFF1E293B) else Color.Transparent)
                        .border(
                            width = 1.dp,
                            color = if (isActive) PhpIndigoLight.copy(alpha = 0.5f) else Color.Transparent,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clickable { onSelectFile(fileName) }
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                        .testTag("file_tab_$fileName")
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(if (fileName.endsWith(".php")) PhpIndigoLight else PhpAmber)
                        )
                        Text(
                            text = fileName,
                            fontSize = 12.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = if (isActive) FontWeight.Bold else FontWeight.Normal,
                            color = if (isActive) Color.White else Color(0xFF94A3B8)
                        )
                    }
                }
            }
        }

        // Editor Action Toolbar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF161F30))
                .padding(horizontal = 12.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "PHP 8.3 • Zend Engine",
                    fontSize = 11.sp,
                    fontFamily = FontFamily.Monospace,
                    color = PhpCyanLight
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                // Preset code loader
                TextButton(
                    onClick = {
                        val sample = "<?php\n// Database Prepared Statement Example\n\$pdo = new PDO('mysql:host=localhost;dbname=test', 'root', '');\n\$stmt = \$pdo->prepare('SELECT name, role FROM users WHERE active = :active');\n\$stmt->execute(['active' => 1]);\necho \"PDO prepared statement executed securely!\";\n?>"
                        onCodeChange(sample)
                    },
                    contentPadding = PaddingValues(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text("PDO Template", fontSize = 11.sp, color = Color(0xFF94A3B8))
                }

                IconButton(
                    onClick = {
                        val sample = "<?php\necho \"Hello from Learn PHP!\\n\";\necho \"Powered by Zend Engine 4.3\";\n?>"
                        onCodeChange(sample)
                    },
                    modifier = Modifier.size(28.dp)
                ) {
                    Icon(Icons.Default.Refresh, contentDescription = "Reset", tint = Color(0xFF94A3B8), modifier = Modifier.size(15.dp))
                }
            }
        }

        // Code Editor Box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1.1f)
                .background(CodeEditorBg)
        ) {
            val lines = activeCode.lines()

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                // Line numbers column
                Column(
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .width(26.dp)
                ) {
                    for (i in 1..lines.size.coerceAtLeast(1)) {
                        Text(
                            text = "$i",
                            fontFamily = FontFamily.Monospace,
                            fontSize = 12.sp,
                            color = Color(0xFF475569),
                            lineHeight = 20.sp
                        )
                    }
                }

                // Code Input TextField
                TextField(
                    value = activeCode,
                    onValueChange = onCodeChange,
                    modifier = Modifier
                        .fillMaxSize()
                        .testTag("code_editor_input"),
                    textStyle = androidx.compose.ui.text.TextStyle(
                        fontFamily = FontFamily.Monospace,
                        fontSize = 12.sp,
                        color = Color(0xFFE2E8F0),
                        lineHeight = 20.sp
                    ),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
            }

            // Sticky Floating "Run PHP" Button
            FloatingActionButton(
                onClick = onRunCode,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
                    .testTag("sticky_run_php_btn"),
                containerColor = PhpIndigoDark,
                contentColor = Color.White
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    if (isExecuting) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(16.dp),
                            color = Color.White,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Icon(Icons.Default.PlayArrow, contentDescription = "Run")
                    }
                    Text(
                        text = if (isExecuting) "Executing..." else "Run PHP",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                }
            }
        }

        // Result Inspector Divider & Tabs
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color(0xFF0F172A))
        ) {
            val tabs = listOf("Output", "Variables", "Server Logs", "Request", "Errors")

            TabRow(
                selectedTabIndex = selectedInspectorTab,
                containerColor = Color(0xFF1E293B),
                contentColor = Color.White,
                divider = {}
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedInspectorTab == index,
                        onClick = { selectedInspectorTab = index },
                        text = {
                            Text(
                                text = title,
                                fontSize = 11.sp,
                                fontWeight = if (selectedInspectorTab == index) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    )
                }
            }

            // Inspector Tab Content
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp)
            ) {
                when (selectedInspectorTab) {
                    0 -> OutputView(executionResult)
                    1 -> VariablesView(executionResult.variables)
                    2 -> ServerLogsView(executionResult)
                    3 -> RequestSuperglobalsView()
                    4 -> ErrorsView(executionResult)
                }
            }
        }
    }
}

@Composable
private fun OutputView(result: ExecutionResult) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "STDOUT / HTTP RESPONSE",
                    fontSize = 10.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF94A3B8)
                )
                Text(
                    text = "${result.executionTimeMs}ms • ${result.memoryUsageMb} MB",
                    fontSize = 10.sp,
                    fontFamily = FontFamily.Monospace,
                    color = PhpCyanLight
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = CodeEditorBg),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = result.output.ifEmpty { "No output generated to STDOUT." },
                    fontFamily = FontFamily.Monospace,
                    fontSize = 12.sp,
                    color = if (result.hasError) Color(0xFFFF7B72) else Color(0xFF7EE787),
                    lineHeight = 18.sp,
                    modifier = Modifier.padding(12.dp)
                )
            }
        }
    }
}

@Composable
private fun VariablesView(variables: Map<String, String>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        item {
            Text(
                text = "ZEND SYMBOL TABLE (Active Variables in Scope):",
                fontSize = 10.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF94A3B8)
            )
            Spacer(modifier = Modifier.height(4.dp))
        }

        items(variables.entries.toList()) { (name, value) ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color(0xFF1E293B))
                    .padding(horizontal = 10.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = name,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = PhpIndigoLight
                )
                Text(
                    text = value,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 11.sp,
                    color = Color(0xFFA5D6FF)
                )
            }
        }
    }
}

@Composable
private fun ServerLogsView(result: ExecutionResult) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        item {
            Text(
                text = "SERVER & PROCESS EXECUTION LOGS:",
                fontSize = 10.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF94A3B8)
            )
            Spacer(modifier = Modifier.height(4.dp))
        }

        items(result.logs) { log ->
            Text(
                text = log,
                fontFamily = FontFamily.Monospace,
                fontSize = 11.sp,
                color = Color(0xFFCBD5E1),
                lineHeight = 16.sp
            )
        }

        item {
            Text(
                text = "[HTTP 1.1] 200 OK • Content-Type: text/html; charset=UTF-8",
                fontFamily = FontFamily.Monospace,
                fontSize = 11.sp,
                color = PhpEmerald
            )
        }
    }
}

@Composable
private fun RequestSuperglobalsView() {
    val superglobals = listOf(
        "\$_SERVER['REQUEST_METHOD']" to "GET",
        "\$_SERVER['SERVER_PROTOCOL']" to "HTTP/1.1",
        "\$_SERVER['REMOTE_ADDR']" to "127.0.0.1",
        "\$_SERVER['HTTP_HOST']" to "localhost:8000",
        "\$_SERVER['PHP_SELF']" to "/index.php",
        "\$_GET['tab']" to "active",
        "\$_COOKIE['PHPSESSID']" to "php_sess_9a8f2k3j..."
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        item {
            Text(
                text = "SIMULATED HTTP REQUEST & SUPERGLOBALS:",
                fontSize = 10.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF94A3B8)
            )
            Spacer(modifier = Modifier.height(4.dp))
        }

        items(superglobals) { (k, v) ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color(0xFF1E293B))
                    .padding(horizontal = 10.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = k,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 11.sp,
                    color = PhpAmber
                )
                Text(
                    text = v,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 11.sp,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
private fun ErrorsView(result: ExecutionResult) {
    Column(modifier = Modifier.fillMaxSize()) {
        if (!result.hasError) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = PhpEmerald,
                        modifier = Modifier.size(32.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "0 Syntax or Runtime Errors Detected",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "Your script parsed and executed cleanly in the Zend Engine.",
                        fontSize = 11.sp,
                        color = Color(0xFF94A3B8)
                    )
                }
            }
        } else {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFF451A1A)),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = "Diagnostic Error Stack Trace",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFF7B72),
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = result.errorMessage,
                        fontFamily = FontFamily.Monospace,
                        fontSize = 11.sp,
                        color = Color.White
                    )
                }
            }
        }
    }
}
