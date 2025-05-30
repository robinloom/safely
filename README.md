# Safely

**Safely** is a lightweight Java utility library that makes exception handling more elegant and expressive. It wraps operations in a `Result<T>` type, so you can handle errors without cluttering your code with try-catch blocks.

---

## ✨ Features

- Fluent API for handling checked and unchecked exceptions
- Works with both value-returning (`call`) and void (`run`) operations
- Supports filtering by specific exception types
- Optional `onSuccess`, `onFailure`, and `map` hooks for composable logic
- Logging-friendly and testable
- No dependencies, Java 8+ compatible

---

## 🔧 Usage

### Wrapping a computation:

```java
Result<String> result = Safely.call(() -> someRiskyMethod())
                              .onSuccess(val -> System.out.println("Success: " + val))
                              .onFailure(err -> System.err.println("Error: " + err.getMessage()));
```

### With default fallback:

```java
String value = Safely.call(() -> fetchFromRemote()).orElse("default");
```

### Run block (void):

```java
Result<Void> result = Safely.run(() -> doSomething());
```

### Catch only specific exceptions:

```java
Result<String> result = Safely.call(() -> doSomething(), IOException.class);
```

---

## 📦 Installation

<details>
  <summary><strong>Maven</strong></summary>

```xml
<dependency>
    <groupId>com.robinloom</groupId>
    <artifactId>safely</artifactId>
    <version>1.0.0</version>
</dependency>
```
</details> <details> <summary><strong>Gradle</strong></summary>

```
implementation 'com.robinloom:safely:1.0.0'
```
</details>

---
