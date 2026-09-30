package com.example.ui.screens

import androidx.activity.compose.BackHandler
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.CodeSnippetCard
import com.example.ui.theme.*

@Composable
fun OopVisualizerScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler { onBack() }

    var selectedOopTab by remember { mutableStateOf(0) } // 0: Classes & Objects, 1: Inheritance, 2: Interfaces & Traits
    var activeUserName by remember { mutableStateOf("Awiskar Acharya") }
    var activeRole by remember { mutableStateOf("Lead Software Engineer") }
    var userLoginStatus by remember { mutableStateOf("Logged Out") }

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
                        text = "Object-Oriented PHP Visualizer",
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
                    text = "Interactive PHP OOP Architecture",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Inspect classes, instantiate heap objects, trigger methods, and explore inheritance and interfaces.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // OOP Tabs
            item {
                TabRow(
                    selectedTabIndex = selectedOopTab,
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    modifier = Modifier.clip(RoundedCornerShape(10.dp)),
                    divider = {}
                ) {
                    Tab(
                        selected = selectedOopTab == 0,
                        onClick = { selectedOopTab = 0 },
                        text = { Text("Classes & Objects", fontSize = 11.sp, fontWeight = FontWeight.Bold) }
                    )
                    Tab(
                        selected = selectedOopTab == 1,
                        onClick = { selectedOopTab = 1 },
                        text = { Text("Inheritance Tree", fontSize = 11.sp, fontWeight = FontWeight.Bold) }
                    )
                    Tab(
                        selected = selectedOopTab == 2,
                        onClick = { selectedOopTab = 2 },
                        text = { Text("Interfaces & Traits", fontSize = 11.sp, fontWeight = FontWeight.Bold) }
                    )
                }
            }

            when (selectedOopTab) {
                0 -> {
                    // Class Blueprint & Heap Object Inspector
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                        ) {
                            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                Text("Class Blueprint: User", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                CodeSnippetCard(
                                    code = "<?php\nclass User {\n    public function __construct(\n        public string \$name,\n        public string \$role,\n        private string \$status = 'Logged Out'\n    ) {}\n\n    public function login(): void {\n        \$this->status = 'Authenticated';\n    }\n}\n?>"
                                )
                            }
                        }
                    }

                    // Object in Heap Visualization
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = CodeEditorBg)
                        ) {
                            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("ZEND HEAP OBJECT: \$user", fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = PhpIndigoLight)
                                    Text("Memory: 1.2 KB", fontFamily = FontFamily.Monospace, fontSize = 10.sp, color = Color(0xFF8B949E))
                                }

                                Text("\$user->name = \"$activeUserName\"", fontFamily = FontFamily.Monospace, fontSize = 12.sp, color = Color(0xFFA5D6FF))
                                Text("\$user->role = \"$activeRole\"", fontFamily = FontFamily.Monospace, fontSize = 12.sp, color = Color(0xFFA5D6FF))
                                Text("\$user->status = \"$userLoginStatus\"", fontFamily = FontFamily.Monospace, fontSize = 12.sp, color = if (userLoginStatus == "Authenticated") Color(0xFF7EE787) else Color(0xFFFF7B72))

                                Spacer(modifier = Modifier.height(4.dp))

                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    Button(
                                        onClick = { userLoginStatus = "Authenticated" },
                                        colors = ButtonDefaults.buttonColors(containerColor = PhpEmerald)
                                    ) {
                                        Text("\$user->login()")
                                    }
                                    OutlinedButton(onClick = { userLoginStatus = "Logged Out" }) {
                                        Text("\$user->logout()")
                                    }
                                }
                            }
                        }
                    }
                }

                1 -> {
                    // Inheritance Tree
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                        ) {
                            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                Text("Inheritance Hierarchy Diagram", fontWeight = FontWeight.Bold, fontSize = 14.sp)

                                val tree = listOf(
                                    "User (Base Class)" to "Properties: id, name, email | Methods: login(), logout()",
                                    "   └── Admin (extends User)" to "+ permissions, banUser(), viewAuditLogs()",
                                    "         └── SuperAdmin (extends Admin)" to "+ manageSystemConfig(), grantDatabaseAccess()"
                                )

                                tree.forEach { (cls, desc) ->
                                    Card(
                                        modifier = Modifier.fillMaxWidth(),
                                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                                    ) {
                                        Column(modifier = Modifier.padding(10.dp)) {
                                            Text(cls, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = PhpIndigo)
                                            Text(desc, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                        }
                                    }
                                }

                                CodeSnippetCard(
                                    code = "<?php\nclass Admin extends User {\n    public function __construct(\n        string \$name,\n        public array \$permissions\n    ) {\n        parent::__construct(\$name, 'Admin');\n    }\n}\n?>"
                                )
                            }
                        }
                    }
                }

                2 -> {
                    // Interfaces & Traits
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                        ) {
                            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                Text("Interface Contract & Polymorphism", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                Text(
                                    text = "Interfaces define contracts that implementing classes must satisfy.",
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )

                                CodeSnippetCard(
                                    code = "<?php\ninterface PaymentGateway {\n    public function pay(float \$amount): bool;\n}\n\nclass StripeGateway implements PaymentGateway {\n    public function pay(float \$amount): bool {\n        // Connect to Stripe API\n        return true;\n    }\n}\n?>"
                                )

                                Spacer(modifier = Modifier.height(4.dp))
                                Text("Traits (Horizontal Code Reuse):", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                CodeSnippetCard(
                                    code = "<?php\ntrait Loggable {\n    public function log(string \$msg): void {\n        error_log('[' . date('c') . '] ' . \$msg);\n    }\n}\n\nclass Order {\n    use Loggable;\n}\n?>"
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
