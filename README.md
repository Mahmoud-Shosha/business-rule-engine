# Business Rule Engine

## Overview
The **Business Rule Engine** is a Spring Boot application designed to manage and execute business rules on payment transactions.  
It allows users to define, store, and evaluate rules based on specific conditions and actions.  

### Key Features
- Store / Update / View / Delete business rules in database.
- Evaluate rules based on their conditions and execute corresponding actions in payment transactions.
- Prioritize rules for execution based on their priority.
  - Lowest priority number is the highest priority.
- Support for two rule types:
  - `ENRICHMENT`: All matched rules applied in priority order.
  - `ROUTING`: Only the highest priority rule is applied.

---

## Project Structure
The project follows a simplified clean architecture:
```
src/
├── main/
│   ├── java/
│   │   └── org/gs1eg/business_rule_engine/
│   │       ├── rule/
│   │       │   ├── domain/              # Domain logic: entities, repository interfaces, services, etc.
│   │       │   └── infrastructure/      # Infrastructure code not part of core domain logic (e.g., adapters, persistence)
│   │       ├── engine/                  # Other domain modules (e.g., rule engine logic)
│   │       ├── payment/                 # Other domain modules (e.g., payment logic)
│   │       └── shared/                  # Shared utilities, global config, AOP, exceptions, constants, etc.
│   └── resources/
│       ├── db/
│       │   ├── changelog/               # Liquibase changelogs (YAML files for schema + data changes)
│       │   └── changelog-master.yml     # Master Liquibase changelog referencing all others
│       └── application.yml              # Base application config (can import profile-specific files)
└── test/
    └── java/                            # Unit tests

```

---

## Prerequisites
- **Java 21** or higher
- **Docker and Docker Compose*** to run the database

---

## How to Run

### 1. Clone the Repository
```bash
git clone https://github.com/Mahmoud-Shosha/business-rule-engine.git
cd business-rule-engine
```

### 2. Run the Database
```bash
docker-compose -f postgres-compose.yml up -d
```

### 3. Build the Project
```bash
./gradlew clean build
```

### 4. Run the Project
```bash
./gradlew bootRun
```

---

## TODO Improvements
The following improvement should be implemented to make the project more robust, secure, and efficient:

#### 1. **Input Sanitization**
- Sanitize all user inputs to prevent code injection and other security vulnerabilities.

#### 2. **Validations**
- Add validations for rule conditions and action syntax before saving or updating rules to ensure correct syntax.

#### 3. **Caching**
- Implement caching mechanisms to improve performance:
  - Cache compiled regular expressions in memory to avoid recompilation overhead.
    - Use a notification mechanism (e.g., Redis Streams or Kafka) to invalidate or update the cache when rules change.
  - Or we can use Apache Ignite for read-through and write-through caching to handle both caching and persistence seamlessly.

#### 4. **Tracing**
- Add **Trace ID** or **Correlation ID** in every log entry to enable end-to-end tracing of requests and flows across the system.

#### 5. **Testing**
- Achieve full unit test coverage.

#### 6. **Security**
- Implement authentication and authorization.
- Enable **TLS** to encrypt communication between the client and server.
