package com.example.data

import com.example.model.PhpLesson
import com.example.model.PhpModule

object PhpCurriculum {
    val modules: List<PhpModule> = listOf(
        PhpModule(
            id = "mod-1",
            levelNumber = 1,
            title = "Web & Server Fundamentals",
            subtitle = "Client-Server Architecture & HTTP",
            description = "Understand how the web works, browsers, web servers (Apache/Nginx), HTTP request-response cycle, and where PHP executes.",
            iconKey = "dns",
            lessons = listOf(
                PhpLesson(
                    id = "web-1",
                    moduleId = "mod-1",
                    title = "How the Web Works & Client vs Server",
                    shortSummary = "Learn the difference between frontend browsers and server-side processing.",
                    readTimeMinutes = 5,
                    conceptOverview = "When you visit a website, your browser (Client) sends an HTTP Request across the internet. A Web Server receives this request. For static sites, HTML/CSS is sent directly. For dynamic sites, a server-side language like PHP executes code, queries databases, and produces fresh HTML.",
                    realWorldUsage = "Every social network, e-commerce store, and content management platform uses server-side processing to render dynamic data per user.",
                    serverFlowDescription = "Browser -> HTTP GET -> Apache/Nginx Web Server -> PHP Engine executes -> Returns HTML response -> Browser displays page.",
                    syntaxHighlight = "GET /index.php HTTP/1.1\nHost: myapp.com\nUser-Agent: Mozilla/5.0",
                    interactiveCode = "<?php\n// Server receives request and inspects headers\necho 'Server Time: ' . date('Y-m-d H:i:s') . \"\\n\";\necho 'User IP: ' . \$_SERVER['REMOTE_ADDR'] . \"\\n\";\necho 'Client Protocol: ' . \$_SERVER['SERVER_PROTOCOL'];\n?>",
                    simulatedOutput = "Server Time: 2026-09-30 08:45:00\nUser IP: 127.0.0.1\nClient Protocol: HTTP/1.1",
                    securityTip = "Never trust client-supplied headers without validation; headers like User-Agent or Referer can easily be spoofed by attackers.",
                    commonMistakes = "Thinking PHP runs inside the user's browser like JavaScript. PHP executes 100% on the web server before the user ever sees the output.",
                    practiceTask = "Output the current server software version and request method using the \$_SERVER superglobal.",
                    practiceSolution = "<?php\necho 'Method: ' . \$_SERVER['REQUEST_METHOD'];\necho 'Software: ' . \$_SERVER['SERVER_SOFTWARE'];\n?>"
                ),
                PhpLesson(
                    id = "web-2",
                    moduleId = "mod-1",
                    title = "PHP Architecture & The Zend Engine",
                    shortSummary = "Discover how PHP scripts are compiled into opcodes and executed by the Zend Engine.",
                    readTimeMinutes = 6,
                    conceptOverview = "PHP (Hypertext Preprocessor) is an interpreted/JIT-compiled server-side language. When a .php file is requested, the Zend Engine lexes the source into tokens, parses tokens into an AST (Abstract Syntax Tree), compiles AST into Opcodes, and executes opcodes in memory with OPcache for lightning-fast speeds.",
                    realWorldUsage = "PHP powers over 75% of all CMS-backed websites worldwide, including WordPress, Wikipedia, and large portions of Slack and Facebook infrastructure.",
                    serverFlowDescription = "PHP Source (.php) -> Lexer -> Parser -> AST -> Zend Compiler -> Opcodes (cached in OPcache) -> Execution Engine -> Output Buffer.",
                    syntaxHighlight = "<?php\nphpinfo(); // Outputs PHP Engine architecture and runtime modules\n?>",
                    interactiveCode = "<?php\n\$phpVersion = phpversion();\n\$zendVersion = zend_version();\n\necho \"PHP Version: \$phpVersion\\n\";\necho \"Zend Engine Version: \$zendVersion\\n\";\necho \"OPcache Active: \" . (function_exists('opcache_get_status') ? 'Yes' : 'Simulated');\n?>",
                    simulatedOutput = "PHP Version: 8.3.4\nZend Engine Version: 4.3.4\nOPcache Active: Yes",
                    securityTip = "Never leave phpinfo() script publicly accessible in production, as it exposes PHP versions, extensions, and server configurations to potential adversaries.",
                    commonMistakes = "Confusing PHP 5/7 procedural legacy code with modern PHP 8.x, which has strict types, JIT, attributes, and match expressions.",
                    practiceTask = "Write a script that detects whether PHP is currently running via CLI or Web Server.",
                    practiceSolution = "<?php\necho (php_sapi_name() === 'cli') ? 'Running from CLI' : 'Running via Web Server';\n?>"
                )
            )
        ),
        PhpModule(
            id = "mod-2",
            levelNumber = 2,
            title = "PHP Basics, Syntax & Variables",
            subtitle = "Tags, Statements, Data Types & Output",
            description = "Master <?php tags, statements, echo vs print, variable scope (\$variable), scalar types, and strict type casting.",
            iconKey = "code",
            lessons = listOf(
                PhpLesson(
                    id = "syntax-1",
                    moduleId = "mod-2",
                    title = "PHP Syntax, Tags & Output",
                    shortSummary = "Write your first script using echo, statements, and comments.",
                    readTimeMinutes = 5,
                    conceptOverview = "A PHP script starts with <?php and ends with ?> (optional at the end of pure PHP files to prevent accidental whitespace injection). Statements end with semicolons (;). 'echo' and 'print' send content to the response output stream.",
                    realWorldUsage = "Rendering dynamic headers, page titles, and user greetings in server-rendered templates.",
                    serverFlowDescription = "PHP file -> Zend Engine reads <?php block -> evaluates echo statement -> writes string into HTTP Response body -> closes stream.",
                    syntaxHighlight = "<?php\necho \"Hello, World!\";\n\$message = 'Learning PHP with Awiskar';\n?>",
                    interactiveCode = "<?php\n\$appName = \"Learn PHP\";\n\$author = \"Awiskar Acharya\";\n\necho \"Welcome to \" . \$appName . \"!\\n\";\necho \"Curated by: \$author\\n\";\necho \"Status: 100% Free Learning Platform\";\n?>",
                    simulatedOutput = "Welcome to Learn PHP!\nCurated by: Awiskar Acharya\nStatus: 100% Free Learning Platform",
                    securityTip = "Always omit the closing ?> tag in pure PHP files. Accidental trailing whitespace after ?> can corrupt HTTP response headers or binary file downloads.",
                    commonMistakes = "Forgetting the trailing semicolon (;) which leads to a Parse Error / Syntax Error.",
                    practiceTask = "Declare a variable for your name and university/country, then print them in a formatted sentence.",
                    practiceSolution = "<?php\n\$name = \"Awiskar\";\n\$country = \"Nepal\";\necho \"Hello, my name is \$name from \$country!\";\n?>"
                ),
                PhpLesson(
                    id = "syntax-2",
                    moduleId = "mod-2",
                    title = "Variables, Data Types & Scope",
                    shortSummary = "Learn \$variable naming, integers, strings, floats, booleans, and variable scopes.",
                    readTimeMinutes = 7,
                    conceptOverview = "All PHP variables begin with a dollar sign ($) followed by a letter or underscore. PHP supports 8 primitive types: 4 scalar (int, float, string, bool), 2 compound (array, object), and 2 special (resource, NULL). Variables have local, global, and static scope.",
                    realWorldUsage = "Storing database query parameters, current logged-in user details, shopping cart counts, and configuration flags.",
                    serverFlowDescription = "Zend Engine allocates a zval container in the symbol table with a type tag and value pointer. Garbage collection automatically frees unused zvals.",
                    syntaxHighlight = "<?php\n\$age = 22;               // int\n\$rating = 4.95;          // float\n\$isStudent = true;       // bool\n\$skills = ['PHP', 'SQL'];// array\n?>",
                    interactiveCode = "<?php\n\$studentName = \"Awiskar Acharya\";\n\$lessonsCompleted = 12;\n\$completionPercentage = 75.5;\n\$isCertified = false;\n\nvar_dump(\$studentName);\nvar_dump(\$lessonsCompleted);\nvar_dump(\$completionPercentage);\nvar_dump(\$isCertified);\n?>",
                    simulatedOutput = "string(15) \"Awiskar Acharya\"\nint(12)\nfloat(75.5)\nbool(false)",
                    securityTip = "Avoid using global variables or superglobals without proper encapsulation. Global mutable state introduces security flaws and unpredictable side-effects.",
                    commonMistakes = "Attempting to access a global variable inside a function without the 'global' keyword or passing it as a parameter.",
                    practiceTask = "Create a script with static variable counter inside a function to track invocation count across multiple calls.",
                    practiceSolution = "<?php\nfunction trackVisits() {\n    static \$count = 0;\n    \$count++;\n    return \$count;\n}\necho trackVisits() . \" \";\necho trackVisits();\n?>"
                )
            )
        ),
        PhpModule(
            id = "mod-3",
            levelNumber = 3,
            title = "Conditions, Loops & Arrays",
            subtitle = "Control Flow, Iterations & Data Structures",
            description = "Master if-else, modern match expressions, while, for, foreach, indexed arrays, and associative dictionaries.",
            iconKey = "account_tree",
            lessons = listOf(
                PhpLesson(
                    id = "control-1",
                    moduleId = "mod-3",
                    title = "Conditions & Modern Match Expressions",
                    shortSummary = "Control application flow with if, elseif, ternary, and PHP 8 match expressions.",
                    readTimeMinutes = 6,
                    conceptOverview = "Conditions allow branching logic based on boolean expressions. PHP 8 introduced 'match', an expressive, type-safe replacement for switch that returns a value and performs strict (===) comparisons without requiring 'break' statements.",
                    realWorldUsage = "Checking user authentication state, processing HTTP status codes, and routing permissions.",
                    serverFlowDescription = "CPU evaluates opcode conditionals; branches jump to corresponding opcode block.",
                    syntaxHighlight = "<?php\n\$role = 'admin';\n\$access = match(\$role) {\n    'admin' => 'Full Access',\n    'editor' => 'Edit Content',\n    default => 'Read Only'\n};\n?>",
                    interactiveCode = "<?php\n\$statusCode = 200;\n\n\$statusMessage = match(\$statusCode) {\n    200, 201 => 'Request Succeeded!',\n    400 => 'Bad Request',\n    401 => 'Unauthorized',\n    404 => 'Resource Not Found',\n    500 => 'Internal Server Error',\n    default => 'Unknown Status'\n};\n\necho \"HTTP \$statusCode: \$statusMessage\";\n?>",
                    simulatedOutput = "HTTP 200: Request Succeeded!",
                    securityTip = "Always use strict equality operators (===) instead of loose equality (==) to prevent type coercion vulnerabilities (e.g. '0e123' == '0e456' returning true in legacy comparisons).",
                    commonMistakes = "Accidentally writing single equals = instead of comparison == or === in an if statement condition, which performs assignment rather than comparison.",
                    practiceTask = "Use a match expression to assign user permission levels based on roles: 'superadmin', 'manager', and 'guest'.",
                    practiceSolution = "<?php\n\$role = 'manager';\n\$permission = match(\$role) {\n    'superadmin' => 'all',\n    'manager' => 'manage_staff',\n    default => 'view'\n};\necho \$permission;\n?>"
                ),
                PhpLesson(
                    id = "control-2",
                    moduleId = "mod-3",
                    title = "Arrays & Associative Data Laboratories",
                    shortSummary = "Harness indexed arrays, associative key-value maps, and multidimensional collections.",
                    readTimeMinutes = 8,
                    conceptOverview = "Arrays in PHP are ordered hash maps. They can hold indexed sequences or associative key-value pairs. Powerful built-in functions like array_map(), array_filter(), and in_array() make array manipulation effortless.",
                    realWorldUsage = "Storing database record sets, form payloads, API response lists, and session user data.",
                    serverFlowDescription = "Zend Engine stores arrays as HashTable structs providing O(1) average lookup times by integer index or hashed string keys.",
                    syntaxHighlight = "<?php\n\$user = [\n    'id' => 101,\n    'name' => 'Awiskar Acharya',\n    'role' => 'Lead Developer'\n];\n?>",
                    interactiveCode = "<?php\n\$frameworks = [\n    ['name' => 'Laravel', 'type' => 'Full-Stack', 'stars' => 74000],\n    ['name' => 'Symfony', 'type' => 'Components', 'stars' => 29000],\n    ['name' => 'WordPress', 'type' => 'CMS Engine', 'stars' => 19000]\n];\n\nforeach (\$frameworks as \$fw) {\n    echo \"[#] \" . \$fw['name'] . \" (\" . \$fw['type'] . \") - \" . \$fw['stars'] . \" Stars\\n\";\n}\n?>",
                    simulatedOutput = "[#] Laravel (Full-Stack) - 74000 Stars\n[#] Symfony (Components) - 29000 Stars\n[#] WordPress (CMS Engine) - 19000 Stars",
                    securityTip = "Always verify if a key exists in array using array_key_exists() or null coalescing operator ?? before accessing untrusted request input to avoid PHP Warning notices.",
                    commonMistakes = "Accessing undefined array keys without checking, which triggers an Undefined array key warning in PHP 8.",
                    practiceTask = "Filter an array of numbers to keep only even numbers using array_filter.",
                    practiceSolution = "<?php\n\$numbers = [1, 2, 3, 4, 5, 6, 7, 8];\n\$evens = array_filter(\$numbers, fn(\$n) => \$n % 2 === 0);\nprint_r(\$evens);\n?>"
                )
            )
        ),
        PhpModule(
            id = "mod-4",
            levelNumber = 4,
            title = "Functions & String Mastery",
            subtitle = "Type Declarations, Return Types & Closures",
            description = "Write modular, reusable functions with strict type hinting, arrow functions, and comprehensive string operations.",
            iconKey = "terminal",
            lessons = listOf(
                PhpLesson(
                    id = "func-1",
                    moduleId = "mod-4",
                    title = "PHP Functions & Strict Type Declarations",
                    shortSummary = "Declare clean functions with typed parameters and return types.",
                    readTimeMinutes = 6,
                    conceptOverview = "Functions encapsulate reusable logic. By adding 'declare(strict_types=1);' at the top of a file, PHP enforces that arguments match parameter type declarations exactly, preventing subtle bugs.",
                    realWorldUsage = "Building calculation services, payment processors, tax engines, and data formatters.",
                    serverFlowDescription = "Function definitions compile into Zend function tables. During invocation, a new call stack frame is allocated for parameters and local scope.",
                    syntaxHighlight = "<?php\ndeclare(strict_types=1);\n\nfunction calculateTax(float \$subtotal, float \$rate = 0.13): float {\n    return \$subtotal * \$rate;\n}\n?>",
                    interactiveCode = "<?php\ndeclare(strict_types=1);\n\nfunction formatCurrency(float \$amount, string \$currency = 'USD'): string {\n    return \$currency . ' ' . number_format(\$amount, 2);\n}\n\n\$itemTotal = 149.99;\necho \"Formatted price: \" . formatCurrency(\$itemTotal, 'USD') . \"\\n\";\necho \"In EUR: \" . formatCurrency(139.50, 'EUR');\n?>",
                    simulatedOutput = "Formatted price: USD 149.99\nIn EUR: EUR 139.50",
                    securityTip = "Validate function inputs for expected ranges and sanitary limits before performing business logic or passing them into database layers.",
                    commonMistakes = "Passing strings to integer type-hinted parameters when strict_types is enabled, resulting in a TypeError.",
                    practiceTask = "Write a function that accepts an array of floats and returns their average, type hinted float.",
                    practiceSolution = "<?php\nfunction getAverage(array \$nums): float {\n    return empty(\$nums) ? 0.0 : array_sum(\$nums) / count(\$nums);\n}\necho getAverage([10.5, 20.5, 30.0]);\n?>"
                )
            )
        ),
        PhpModule(
            id = "mod-5",
            levelNumber = 5,
            title = "Forms, Requests & File Uploads",
            subtitle = "GET vs POST, Sanitization & Secure Uploads",
            description = "Process web forms, validate user inputs, handle multipart file uploads safely, and inspect HTTP headers.",
            iconKey = "input",
            lessons = listOf(
                PhpLesson(
                    id = "form-1",
                    moduleId = "mod-5",
                    title = "Processing GET and POST Requests",
                    shortSummary = "Read form submissions and sanitize user input data.",
                    readTimeMinutes = 7,
                    conceptOverview = "\$_GET retrieves URL query parameters visible in the address bar. \$_POST reads parameters submitted inside the HTTP request body. Never trust user input; always sanitize and validate using filter_var() and htmlspecialchars().",
                    realWorldUsage = "User registrations, login forms, search queries, contact forms, and e-commerce checkout flows.",
                    serverFlowDescription = "Web server parses HTTP request body -> populates \$_GET, \$_POST, \$_COOKIE, \$_FILES superglobals before invoking your PHP script.",
                    syntaxHighlight = "<?php\nif (\$_SERVER['REQUEST_METHOD'] === 'POST') {\n    \$email = filter_var(\$_POST['email'] ?? '', FILTER_VALIDATE_EMAIL);\n}\n?>",
                    interactiveCode = "<?php\n// Simulated POST payload\n\$_POST['username'] = \"Awiskar\";\n\$_POST['email'] = \"awiskar@example.com\";\n\nif (\$_SERVER['REQUEST_METHOD'] === 'POST' || true) {\n    \$name = htmlspecialchars(trim(\$_POST['username'] ?? ''));\n    \$email = filter_var(\$_POST['email'] ?? '', FILTER_VALIDATE_EMAIL);\n    \n    if (\$email === false) {\n        echo \"Error: Invalid email format!\";\n    } else {\n        echo \"Form valid! Registered: \$name <\$email>\";\n    }\n}\n?>",
                    simulatedOutput = "Form valid! Registered: Awiskar <awiskar@example.com>",
                    securityTip = "Always use POST for actions that modify data (login, create, delete). Never use GET for sensitive actions like password changes because GET parameters are stored in server access logs and browser history.",
                    commonMistakes = "Outputting raw \$_POST['comment'] directly into HTML, leading to Cross-Site Scripting (XSS).",
                    practiceTask = "Write a validation check that ensures a password has at least 8 characters.",
                    practiceSolution = "<?php\n\$password = 'secret123';\nif (strlen(\$password) < 8) {\n    echo 'Password must be 8+ characters';\n} else {\n    echo 'Valid password length';\n}\n?>"
                )
            )
        ),
        PhpModule(
            id = "mod-6",
            levelNumber = 6,
            title = "Object-Oriented PHP Academy",
            subtitle = "Classes, Encapsulation, Inheritance & Interfaces",
            description = "Master enterprise OOP in PHP: classes, properties, methods, constructors, access modifiers, traits, and abstract interfaces.",
            iconKey = "widgets",
            lessons = listOf(
                PhpLesson(
                    id = "oop-1",
                    moduleId = "mod-6",
                    title = "Classes, Objects & Constructor Property Promotion",
                    shortSummary = "Learn OOP principles and modern PHP 8 constructor syntax.",
                    readTimeMinutes = 8,
                    conceptOverview = "A class is a blueprint for objects. Encapsulation restricts direct access to an object's internal state using private and protected modifiers. Modern PHP 8 provides 'Constructor Property Promotion', declaring and initializing class properties directly inside the __construct arguments.",
                    realWorldUsage = "Building domain models (User, Product, Order) and services in modern frameworks like Laravel and Symfony.",
                    serverFlowDescription = "Zend Engine allocates an object instance on the heap with a class entry pointer and property table.",
                    syntaxHighlight = "<?php\nclass User {\n    public function __construct(\n        public readonly int \$id,\n        public string \$name,\n        private string \$email\n    ) {}\n}\n?>",
                    interactiveCode = "<?php\nclass Developer {\n    public function __construct(\n        public string \$name,\n        public string \$specialty,\n        private int \$experienceYears\n    ) {}\n\n    public function getBio(): string {\n        return \"\$this->name specializes in \$this->specialty with \$this->experienceYears years experience.\";\n    }\n}\n\n\$dev = new Developer(\"Awiskar Acharya\", \"Full-Stack PHP\", 4);\necho \$dev->getBio();\n?>",
                    simulatedOutput = "Awiskar Acharya specializes in Full-Stack PHP with 4 years experience.",
                    securityTip = "Make properties private or protected by default to enforce encapsulation and prevent untrusted external code from mutating internal state.",
                    commonMistakes = "Attempting to access private properties from outside the class without a getter method.",
                    practiceTask = "Define an interface 'PaymentGateway' with a method pay(float \$amount): bool.",
                    practiceSolution = "<?php\ninterface PaymentGateway {\n    public function pay(float \$amount): bool;\n}\n?>"
                )
            )
        ),
        PhpModule(
            id = "mod-7",
            levelNumber = 7,
            title = "Sessions, Authentication & Web Security",
            subtitle = "password_hash, XSS, SQLi & CSRF Defenses",
            description = "Implement bulletproof login systems with secure password hashing, session tokens, and defenses against SQL Injection, XSS, and CSRF.",
            iconKey = "security",
            lessons = listOf(
                PhpLesson(
                    id = "auth-1",
                    moduleId = "mod-7",
                    title = "Secure Authentication & Password Hashing",
                    shortSummary = "Hash passwords with bcrypt/argon2 and manage session state.",
                    readTimeMinutes = 8,
                    conceptOverview = "Never store plaintext passwords or use obsolete algorithms like MD5 or SHA1! PHP provides built-in, secure password_hash() using Bcrypt or Argon2id with automatic salt generation, paired with password_verify() to authenticate credentials.",
                    realWorldUsage = "Every production login system, user account registration, and password reset flow.",
                    serverFlowDescription = "Client submits password -> PHP hashes with slow memory-hard algorithm -> compares stored hash in DB -> calls session_start() -> assigns session ID cookie.",
                    syntaxHighlight = "<?php\n\$hash = password_hash(\$password, PASSWORD_BCRYPT);\nif (password_verify(\$password, \$hash)) {\n    \$_SESSION['user_id'] = \$user['id'];\n}\n?>",
                    interactiveCode = "<?php\n\$userPassword = \"Secret@Pass2026\";\n\n// Generate secure Bcrypt hash\n\$hashedPassword = password_hash(\$userPassword, PASSWORD_BCRYPT, ['cost' => 12]);\necho \"Stored Hash: \" . substr(\$hashedPassword, 0, 30) . \"...\\n\";\n\n// Test login attempt\n\$attempt = \"Secret@Pass2026\";\nif (password_verify(\$attempt, \$hashedPassword)) {\n    echo \"Login Success! Password matched securely.\";\n} else {\n    echo \"Login Failed: Invalid credentials.\";\n}\n?>",
                    simulatedOutput = "Stored Hash: \$2y\$12\$e8f9e2k1091... \nLogin Success! Password matched securely.",
                    securityTip = "Always regenerate session ID upon authentication using session_regenerate_id(true) to protect against Session Fixation attacks.",
                    commonMistakes = "Using MD5 or SHA-256 for passwords. These algorithms are designed for speed and can be cracked at billions of guesses per second via GPUs.",
                    practiceTask = "Write a login verification check that checks password validity using password_verify().",
                    practiceSolution = "<?php\n\$input = 'pass';\n\$stored = password_hash('pass', PASSWORD_DEFAULT);\necho password_verify(\$input, \$stored) ? 'OK' : 'Error';\n?>"
                )
            )
        ),
        PhpModule(
            id = "mod-8",
            levelNumber = 8,
            title = "MySQL & Database Development",
            subtitle = "PDO, Prepared Statements & Complete CRUD",
            description = "Connect PHP to MySQL databases using PDO, write safe parameterized queries, and build Complete CRUD operations.",
            iconKey = "database",
            lessons = listOf(
                PhpLesson(
                    id = "db-1",
                    moduleId = "mod-8",
                    title = "PHP PDO & Parameterized Prepared Statements",
                    shortSummary = "Learn why PDO and prepared statements make SQL injection impossible.",
                    readTimeMinutes = 8,
                    conceptOverview = "PDO (PHP Data Objects) is a lightweight, consistent interface for accessing databases. Parameterized prepared statements separate SQL instructions from user data: user data is sent in a separate packet, making SQL injection mathematically impossible.",
                    realWorldUsage = "Querying databases across MySQL, PostgreSQL, SQLite, and MariaDB in professional production backends.",
                    serverFlowDescription = "PHP sends SQL blueprint to MySQL engine -> MySQL prepares execution plan -> PHP binds parameters -> MySQL executes safely.",
                    syntaxHighlight = "<?php\n\$stmt = \$pdo->prepare('SELECT * FROM users WHERE email = :email');\n\$stmt->execute(['email' => \$inputEmail]);\n\$user = \$stmt->fetch(PDO::FETCH_ASSOC);\n?>",
                    interactiveCode = "<?php\n// Simulated PDO Prepared Query\n\$sql = \"SELECT id, name, email FROM students WHERE status = :status\";\n\$params = ['status' => 'active'];\n\necho \"Executing Prepared SQL: \$sql\\n\";\necho \"Bound Parameters: \" . json_encode(\$params) . \"\\n\";\necho \"Fetched 2 active student records safely!\";\n?>",
                    simulatedOutput = "Executing Prepared SQL: SELECT id, name, email FROM students WHERE status = :status\nBound Parameters: {\"status\":\"active\"}\nFetched 2 active student records safely!",
                    securityTip = "Never concatenate user variables directly into SQL queries like 'SELECT * FROM users WHERE name = ' . \$name. Doing so creates catastrophic SQL Injection flaws.",
                    commonMistakes = "Using legacy mysql_* functions which were removed in PHP 7. Always use PDO or MySQLi.",
                    practiceTask = "Write a PDO INSERT statement using named parameter placeholders.",
                    practiceSolution = "<?php\n\$sql = 'INSERT INTO products (name, price) VALUES (:name, :price)';\n\$stmt = \$pdo->prepare(\$sql);\n\$stmt->execute(['name' => 'Book', 'price' => 19.99]);\n?>"
                )
            )
        ),
        PhpModule(
            id = "mod-9",
            levelNumber = 9,
            title = "REST APIs & MVC Architecture",
            subtitle = "JSON Responses, HTTP Status Codes & MVC",
            description = "Build production RESTful APIs with JSON endpoints, appropriate HTTP status codes, and clean Model-View-Controller separation.",
            iconKey = "api",
            lessons = listOf(
                PhpLesson(
                    id = "api-1",
                    moduleId = "mod-9",
                    title = "Building RESTful JSON APIs in PHP",
                    shortSummary = "Set HTTP headers, parse JSON request payloads, and return structured JSON responses.",
                    readTimeMinutes = 7,
                    conceptOverview = "Modern backends serve mobile apps and frontend single-page apps via REST APIs. In PHP, set 'Content-Type: application/json', use json_encode() for payloads, and use http_response_code() to emit proper semantic status codes (200, 201, 400, 404, 500).",
                    realWorldUsage = "Powering mobile apps, microservices, and modern frontend frameworks like React, Vue, and Android Kotlin apps.",
                    serverFlowDescription = "Client HTTP Request -> PHP Router -> Controller handles logic -> json_encode(data) -> HTTP 200 JSON Response sent.",
                    syntaxHighlight = "<?php\nheader('Content-Type: application/json');\nhttp_response_code(200);\necho json_encode(['status' => 'success', 'data' => \$users]);\n?>",
                    interactiveCode = "<?php\nheader('Content-Type: application/json');\n\n\$response = [\n    'status' => 'success',\n    'timestamp' => time(),\n    'payload' => [\n        'course' => 'Learn PHP',\n        'mentor' => 'Awiskar Acharya',\n        'curriculum_version' => '2026.1',\n        'modules_count' => 12\n    ]\n];\n\necho json_encode(\$response, JSON_PRETTY_PRINT);\n?>",
                    simulatedOutput = "{\n    \"status\": \"success\",\n    \"timestamp\": 1790758400,\n    \"payload\": {\n        \"course\": \"Learn PHP\",\n        \"mentor\": \"Awiskar Acharya\",\n        \"curriculum_version\": \"2026.1\",\n        \"modules_count\": 12\n    }\n}",
                    securityTip = "Always authenticate API endpoints using JWTs or secure Bearer tokens, and implement Rate Limiting to prevent API abuse and DoS.",
                    commonMistakes = "Returning a 200 OK status code even when an error occurs. Always return appropriate 4xx or 5xx codes on failures.",
                    practiceTask = "Return a JSON error response with HTTP status code 404.",
                    practiceSolution = "<?php\nhttp_response_code(404);\necho json_encode(['error' => 'Resource not found']);\n?>"
                )
            )
        ),
        PhpModule(
            id = "mod-10",
            levelNumber = 10,
            title = "Composer & Modern PHP Ecosystem",
            subtitle = "Dependency Management & PSR Standards",
            description = "Manage third-party packages with Composer, understand composer.json, PSR-4 autoloading, and modern PHP language features.",
            iconKey = "inventory_2",
            lessons = listOf(
                PhpLesson(
                    id = "composer-1",
                    moduleId = "mod-10",
                    title = "Composer & Dependency Management",
                    shortSummary = "Install external libraries and set up PSR-4 class autoloading.",
                    readTimeMinutes = 6,
                    conceptOverview = "Composer is the standard dependency manager for PHP. It manages packages from Packagist, resolves version constraints, and creates an automatic vendor/autoload.php script so you never have to manually require_once class files.",
                    realWorldUsage = "Integrating libraries for email (PHPMailer), payment (Stripe), database ORM, and entire frameworks like Laravel and Symfony.",
                    serverFlowDescription = "composer install -> reads composer.json -> downloads packages to vendor/ -> generates classmap & autoload.php.",
                    syntaxHighlight = "{\n  \"require\": {\n    \"php\": \">=8.2\",\n    \"guzzlehttp/guzzle\": \"^7.8\"\n  }\n}",
                    interactiveCode = "<?php\n// Simulated Composer autoloader check\n\$composerJson = [\n    'name' => 'awiskar/learn-php-app',\n    'require' => [\n        'php' => '>=8.2',\n        'monolog/monolog' => '^3.5'\n    ],\n    'autoload' => [\n        'psr-4' => ['App\\\\' => 'src/']\n    ]\n];\n\necho \"Project: \" . \$composerJson['name'] . \"\\n\";\necho \"Autoloading: PSR-4 mapped App\\\\ to src/ directory!\";\n?>",
                    simulatedOutput = "Project: awiskar/learn-php-app\nAutoloading: PSR-4 mapped App\\ to src/ directory!",
                    securityTip = "Always run 'composer audit' in your CI/CD pipelines to detect known security vulnerabilities in your vendor dependencies.",
                    commonMistakes = "Committing the vendor/ directory to Git repository. Only commit composer.json and composer.lock.",
                    practiceTask = "Inspect a sample composer.json file with a PSR-4 namespace configuration.",
                    practiceSolution = "<?php\necho 'Ensure composer.lock is committed to lock package versions.';\n?>"
                )
            )
        ),
        PhpModule(
            id = "mod-11",
            levelNumber = 11,
            title = "Laravel & WordPress Development",
            subtitle = "Frameworks, Routing, Eloquent & WordPress Hooks",
            description = "Explore the two biggest ecosystems in PHP: modern Laravel (Eloquent, Blade, artisan) and professional WordPress (Hooks, Plugins, Themes).",
            iconKey = "rocket_launch",
            lessons = listOf(
                PhpLesson(
                    id = "framework-1",
                    moduleId = "mod-11",
                    title = "Laravel Fundamentals: Routes & Eloquent",
                    shortSummary = "Learn Laravel elegant routing and database ORM relationships.",
                    readTimeMinutes = 8,
                    conceptOverview = "Laravel is the premier PHP web application framework. It provides expressive routing, Blade templating, Eloquent ORM (ActiveRecord pattern), migration schemas, authentication scaffolding, and powerful Artisan CLI tools.",
                    realWorldUsage = "Building enterprise SaaS applications, high-scale web platforms, and REST APIs.",
                    serverFlowDescription = "Request -> public/index.php -> HTTP Kernel -> Middleware -> Router -> Controller -> Eloquent -> Blade View -> Response.",
                    syntaxHighlight = "Route::get('/students', [StudentController::class, 'index']);",
                    interactiveCode = "<?php\n// Simulated Laravel Eloquent Query\nclass Student {\n    public static function whereActive() {\n        return [\n            ['id' => 1, 'name' => 'Awiskar Acharya', 'course' => 'Full-Stack PHP'],\n            ['id' => 2, 'name' => 'Rohan Sharma', 'course' => 'Laravel Mastery']\n        ];\n    }\n}\n\n\$activeStudents = Student::whereActive();\necho \"Laravel Eloquent returned \" . count(\$activeStudents) . \" records.\\n\";\nforeach (\$activeStudents as \$s) {\n    echo \"-> Student: \" . \$s['name'] . \" [\" . \$s['course'] . \"]\\n\";\n}\n?>",
                    simulatedOutput = "Laravel Eloquent returned 2 records.\n-> Student: Awiskar Acharya [Full-Stack PHP]\n-> Student: Rohan Sharma [Laravel Mastery]",
                    securityTip = "Always use Laravel form requests or \$request->validate() to enforce validation rules before running controller logic.",
                    commonMistakes = "Executing N+1 database queries in Eloquent by not using eager loading (with()).",
                    practiceTask = "Define a Laravel route that returns a JSON list of products.",
                    practiceSolution = "<?php\n// Route::get('/products', fn() => Product::all());\n?>"
                ),
                PhpLesson(
                    id = "framework-2",
                    moduleId = "mod-11",
                    title = "WordPress PHP: Actions, Filters & Plugins",
                    shortSummary = "Learn the WordPress event-driven architecture with Actions and Filters.",
                    readTimeMinutes = 7,
                    conceptOverview = "WordPress is built on PHP hooks. Action Hooks (add_action) let you execute custom code when specific WordPress events occur. Filter Hooks (add_filter) allow you to intercept, modify, and return content before it is displayed.",
                    realWorldUsage = "Creating custom WordPress plugins, WooCommerce extensions, and custom client themes.",
                    serverFlowDescription = "WordPress Core fires do_action() or apply_filters() -> registered callback functions execute in priority order.",
                    syntaxHighlight = "add_action('init', 'register_custom_post_type');\nadd_filter('the_content', 'add_custom_signature');",
                    interactiveCode = "<?php\n// Simulated WordPress Hook System\nfunction addAuthorSignature(\$content) {\n    return \$content . \"<p><em>Curated by Awiskar Acharya (Learn PHP)</em></p>\";\n}\n\n\$originalPost = \"<p>Welcome to modern WordPress development.</p>\";\n\$finalPost = addAuthorSignature(\$originalPost);\n\necho \$finalPost;\n?>",
                    simulatedOutput = "<p>Welcome to modern WordPress development.</p><p><em>Curated by Awiskar Acharya (Learn PHP)</em></p>",
                    securityTip = "Always use sanitize_text_field() and esc_html() when working with user input and output in WordPress themes and plugins.",
                    commonMistakes = "Forgetting to return the modified value inside a WordPress filter function.",
                    practiceTask = "Write a sample add_action call for WordPress 'wp_enqueue_scripts'.",
                    practiceSolution = "<?php\n// add_action('wp_enqueue_scripts', 'load_my_assets');\n?>"
                )
            )
        ),
        PhpModule(
            id = "mod-12",
            levelNumber = 12,
            title = "Real-World Projects & Deployment",
            subtitle = "Environment Variables, Production & Portfolio",
            description = "Deploy applications to production, configure .env secrets, setup Linux servers with Nginx/PHP-FPM, and build your developer portfolio.",
            iconKey = "cloud_done",
            lessons = listOf(
                PhpLesson(
                    id = "deploy-1",
                    moduleId = "mod-12",
                    title = "Deployment, Environment Variables & Nginx",
                    shortSummary = "Prepare PHP apps for production with .env and Nginx reverse proxy.",
                    readTimeMinutes = 7,
                    conceptOverview = "In production, sensitive database passwords and API tokens must never be hardcoded in Git. Use environment variables via .env files. Nginx acts as a high-performance web server passing .php requests to PHP-FPM via fastcgi_pass.",
                    realWorldUsage = "Deploying backends to DigitalOcean, AWS EC2, Render, Google Cloud, and production VPS servers.",
                    serverFlowDescription = "Client -> Nginx (SSL/TLS termination) -> FastCGI socket -> PHP-FPM worker pool executes -> response returned.",
                    syntaxHighlight = "DB_HOST=127.0.0.1\nDB_DATABASE=app_production\nDB_USERNAME=admin\nDB_PASSWORD=secret_env_key",
                    interactiveCode = "<?php\n// Simulated Environment Config Reader\n\$env = [\n    'APP_ENV' => 'production',\n    'APP_DEBUG' => 'false',\n    'DB_CONNECTION' => 'mysql',\n    'DB_HOST' => 'db.internal.aws'\n];\n\necho \"Deployment Environment: \" . \$env['APP_ENV'] . \"\\n\";\necho \"Debug Mode: \" . \$env['APP_DEBUG'] . \" (Safe for production)\\n\";\necho \"Database Host: \" . \$env['DB_HOST'];\n?>",
                    simulatedOutput = "Deployment Environment: production\nDebug Mode: false (Safe for production)\nDatabase Host: db.internal.aws",
                    securityTip = "Never set display_errors = On in production php.ini, as it leaks sensitive paths, database table names, and stack traces.",
                    commonMistakes = "Committing .env file containing production credentials to public GitHub repositories.",
                    practiceTask = "Write a check to ensure debug mode is disabled in production.",
                    practiceSolution = "<?php\nif (\$env['APP_ENV'] === 'production' && \$env['APP_DEBUG'] === 'true') {\n    throw new Exception('Fatal: Debug must be off in production');\n}\n?>"
                )
            )
        )
    )

    fun getAllLessons(): List<PhpLesson> {
        return modules.flatMap { it.lessons }
    }

    fun getLessonById(id: String): PhpLesson? {
        return getAllLessons().find { it.id == id }
    }
}
