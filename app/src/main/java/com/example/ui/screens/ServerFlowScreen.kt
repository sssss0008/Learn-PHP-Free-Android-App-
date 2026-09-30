package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import com.example.ui.components.ServerFlowPipeline
import com.example.ui.theme.*

@Composable
fun ServerFlowScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler { onBack() }

    var selectedStageIndex by remember { mutableStateOf(0) }

    val stageDetails = listOf(
        StageInfo(
            title = "1. Browser (Client)",
            summary = "The user navigates to https://example.com/students or submits a form.",
            details = "The browser resolves the domain via DNS to an IP address, establishes a TCP/TLS handshake with the server, and serializes user headers (User-Agent, Accept, Cookies) into a standard HTTP Request.",
            samplePayload = "GET /students.php?sort=name HTTP/1.1\nHost: example.com\nUser-Agent: Mozilla/5.0 (Android; Mobile)\nAccept: text/html,application/xhtml+xml\nCookie: PHPSESSID=9a8b7c6d5e;"
        ),
        StageInfo(
            title = "2. HTTP Request across Network",
            summary = "The request packet travels across the internet to the host server.",
            details = "HTTP is a stateless, text-based protocol. The request contains the HTTP Method (GET/POST/PUT/DELETE), path, query string parameters, and headers.",
            samplePayload = "Request Method: GET\nProtocol: HTTP/2.0\nTLS: TLSv1.3 AES-GCM-256"
        ),
        StageInfo(
            title = "3. Web Server (Apache / Nginx)",
            summary = "Web Server accepts the connection on Port 80/443 and inspects the requested URI.",
            details = "Nginx detects the requested file extension is .php. Instead of serving it as static raw text, it passes the request via FastCGI (fastcgi_pass unix:/run/php/php8.3-fpm.sock) to the PHP-FPM process pool.",
            samplePayload = "# Nginx VirtualHost directive:\nlocation ~ \\.php\$ {\n    include snippets/fastcgi-php.conf;\n    fastcgi_pass unix:/var/run/php/php8.3-fpm.sock;\n}"
        ),
        StageInfo(
            title = "4. PHP Zend Engine & Compiler",
            summary = "Zend Engine tokenizes and parses the PHP script into execution Opcodes.",
            details = "1. Lexing: converts source text into tokens (T_ECHO, T_VARIABLE).\n2. Parsing: builds an AST (Abstract Syntax Tree).\n3. Compilation: emits bytecode Opcodes (ZEND_ECHO, ZEND_ASSIGN).\n4. OPcache: stores compiled Opcodes in shared memory to skip recompilation on subsequent visits.",
            samplePayload = "ZEND_OPCODES:\n0000 EXT_STMT\n0001 INIT_FCALL_BY_NAME 1 (name)\n0002 DO_FCALL\n0003 ECHO ~0"
        ),
        StageInfo(
            title = "5. MySQL Database Connection (PDO)",
            summary = "PHP executes prepared SQL statements against the MySQL server.",
            details = "Using PDO, PHP establishes a socket connection to MySQL, transmits the parameterized SQL blueprint, binds the sanitized values, and streams the result set rows into PHP memory as associative arrays.",
            samplePayload = "<?php\n\$pdo = new PDO('mysql:host=localhost;dbname=school', 'user', 'pass');\n\$stmt = \$pdo->prepare('SELECT * FROM students WHERE status = :status');\n\$stmt->execute(['status' => 'active']);\n\$students = \$stmt->fetchAll(PDO::FETCH_ASSOC);\n?>"
        ),
        StageInfo(
            title = "6. HTML / JSON Response Generation",
            summary = "PHP combines data with templates to generate dynamic output.",
            details = "PHP populates the Output Buffer. Any echo statements or rendered Blade views are buffered into memory. HTTP headers (Content-Type, Set-Cookie, Status code) are prepared before transmission.",
            samplePayload = "HTTP/1.1 200 OK\nContent-Type: text/html; charset=UTF-8\nSet-Cookie: PHPSESSID=9a8b7c6d5e; Path=/; HttpOnly; SameSite=Strict\n\n<!DOCTYPE html>\n<html>\n<body><h1>Active Students: 25</h1></body>\n</html>"
        ),
        StageInfo(
            title = "7. HTTP Response Sent to Client",
            summary = "Web server flushes the completed response back across TCP socket to browser.",
            details = "The web server transmits the HTTP status code (200 OK), headers, and generated body to the client device. The PHP execution process terminates or returns to the FPM worker pool for the next request.",
            samplePayload = "Status: 200 OK\nSize: 4.8 KB\nLatency: 18ms\nTTFB (Time to First Byte): 14ms"
        ),
        StageInfo(
            title = "8. Browser DOM Rendering",
            summary = "Browser parses HTML, loads stylesheets, and displays the interface to the user.",
            details = "The browser constructs the DOM tree, executes frontend Javascript, and renders the visual page. Notice: PHP code itself is NEVER sent to the browser — only the generated output reaches the client!",
            samplePayload = "Rendered DOM: Document -> html -> body -> table#students\nPHP Source Code: Fully Hidden on Server (100% Protected)"
        )
    )

    val currentStage = stageDetails[selectedStageIndex]

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
                        text = "Server-Side Execution Flow",
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
            contentPadding = PaddingValues(top = 12.dp, bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = "Understand How PHP Executes",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Tap any stage below to inspect the real-time server lifecycle, HTTP packets, and Zend Engine compilation.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Interactive horizontal pipeline
            item {
                ServerFlowPipeline(
                    selectedStage = selectedStageIndex,
                    onStageSelect = { selectedStageIndex = it }
                )
            }

            // Detail Card for Current Stage
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(PhpIndigo.copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "${selectedStageIndex + 1}",
                                    fontWeight = FontWeight.Bold,
                                    color = PhpIndigo
                                )
                            }
                            Text(
                                text = currentStage.title,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = currentStage.summary,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = currentStage.details,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            lineHeight = 22.sp
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            text = "Sample Packet / Server Payload:",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        CodeSnippetCard(
                            code = currentStage.samplePayload,
                            language = if (currentStage.samplePayload.startsWith("<?php")) "php" else "http"
                        )
                    }
                }
            }

            // Navigation Buttons between stages
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedButton(
                        onClick = { if (selectedStageIndex > 0) selectedStageIndex-- },
                        enabled = selectedStageIndex > 0
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Previous Stage")
                    }

                    Button(
                        onClick = { if (selectedStageIndex < stageDetails.size - 1) selectedStageIndex++ },
                        enabled = selectedStageIndex < stageDetails.size - 1,
                        colors = ButtonDefaults.buttonColors(containerColor = PhpIndigoDark)
                    ) {
                        Text("Next Stage")
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(Icons.Default.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp))
                    }
                }
            }
        }
    }
}

private data class StageInfo(
    val title: String,
    val summary: String,
    val details: String,
    val samplePayload: String
)
