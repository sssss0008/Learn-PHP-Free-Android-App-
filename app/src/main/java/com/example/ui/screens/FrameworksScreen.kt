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
fun FrameworksScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler { onBack() }

    var selectedFrameworkTab by remember { mutableStateOf(0) } // 0: Laravel Academy, 1: WordPress PHP Lab
    var simulatedRoutePath by remember { mutableStateOf("/students") }

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
                        text = "Frameworks & Ecosystem Pathways",
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
                    text = "Master PHP Ecosystems",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Explore enterprise Laravel architecture and professional WordPress plugin/theme engineering.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Framework Tabs
            item {
                TabRow(
                    selectedTabIndex = selectedFrameworkTab,
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    modifier = Modifier.clip(RoundedCornerShape(10.dp)),
                    divider = {}
                ) {
                    Tab(
                        selected = selectedFrameworkTab == 0,
                        onClick = { selectedFrameworkTab = 0 },
                        text = { Text("Laravel Academy", fontWeight = FontWeight.Bold, fontSize = 12.sp) }
                    )
                    Tab(
                        selected = selectedFrameworkTab == 1,
                        onClick = { selectedFrameworkTab = 1 },
                        text = { Text("WordPress PHP Lab", fontWeight = FontWeight.Bold, fontSize = 12.sp) }
                    )
                }
            }

            if (selectedFrameworkTab == 0) {
                // Laravel Content
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            Text("1. Expressive Routing Engine", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            Text(
                                text = "Laravel routes HTTP URLs to controller methods with middleware protection.",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            CodeSnippetCard(
                                code = "// routes/web.php\nRoute::middleware(['auth'])->group(function () {\n    Route::get('/students', [StudentController::class, 'index']);\n    Route::post('/students', [StudentController::class, 'store']);\n});"
                            )

                            // Interactive Route Dispatcher Preview
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(containerColor = CodeEditorBg),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Column(modifier = Modifier.padding(10.dp)) {
                                    Text("LARAVEL ROUTE MATCH VISUALIZER:", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = PhpIndigoLight)
                                    Text("URI: $simulatedRoutePath -> Matches StudentController@index", fontSize = 11.sp, fontFamily = FontFamily.Monospace, color = Color(0xFF7EE787))
                                    Text("Middleware: VerifyCsrfToken, Authenticate -> PASSED", fontSize = 11.sp, fontFamily = FontFamily.Monospace, color = Color(0xFFA5D6FF))
                                }
                            }
                        }
                    }
                }

                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            Text("2. Eloquent ORM & Relationships", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            Text(
                                text = "Eloquent maps database tables to PHP models with intuitive relationship definitions.",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            CodeSnippetCard(
                                code = "<?php\nnamespace App\\Models;\nuse Illuminate\\Database\\Eloquent\\Model;\n\nclass Student extends Model {\n    public function courses() {\n        return \$this->hasMany(Course::class);\n    }\n}\n\n// Usage with eager loading to prevent N+1 queries:\n\$students = Student::with('courses')->where('active', true)->get();\n?>"
                            )
                        }
                    }
                }
            } else {
                // WordPress Content
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            Text("1. WordPress Hook System: Actions vs Filters", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            Text(
                                text = "Action Hooks execute custom functionality at specific lifecycle moments. Filter Hooks receive data, modify it, and return the modified value.",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            CodeSnippetCard(
                                code = "<?php\n// Action Hook: Execute code when post is saved\nadd_action('save_post', function(\$post_id) {\n    error_log('Post saved: ' . \$post_id);\n});\n\n// Filter Hook: Modify content before browser display\nadd_filter('the_content', function(\$content) {\n    return \$content . '<p>Learn PHP with Awiskar Acharya</p>';\n});\n?>"
                            )
                        }
                    }
                }

                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            Text("2. WordPress Theme Template Hierarchy", fontWeight = FontWeight.Bold, fontSize = 14.sp)

                            val hierarchy = listOf(
                                "Single Post View" to "single-{post-type}.php -> single.php -> singular.php -> index.php",
                                "Category Archive" to "category-{slug}.php -> category.php -> archive.php -> index.php",
                                "Static Page" to "page-{slug}.php -> page.php -> singular.php -> index.php"
                            )

                            hierarchy.forEach { (view, fallback) ->
                                Card(
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                                ) {
                                    Column(modifier = Modifier.padding(10.dp)) {
                                        Text(view, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = PhpIndigo)
                                        Text(fallback, fontFamily = FontFamily.Monospace, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurface)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
