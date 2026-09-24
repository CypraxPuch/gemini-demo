# 🚀 Stop hand-writing regex and custom JSON parsers for your LLM responses.

With **Java 21**, **Spring Boot 3.x/4.x**, and **Spring AI `2.0.1`**, you can get strongly-typed, schema-validated responses directly out of Google's **`gemini-3.6-flash`** using native Java records!

Here is a step-by-step guide to setting up a production-ready structured LLM pipeline in Java. 👇

---

### 🛠️ Step-by-Step Implementation

#### 1️⃣ Configure Dependencies (`pom.xml`)
Add the Spring AI Google GenAI starter and pin `spring-ai.version` to `2.0.1`:

```xml
	<properties>
		<java.version>21</java.version>
		<spring-ai.version>2.0.1</spring-ai.version>
	</properties>
	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-webmvc</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.ai</groupId>
			<artifactId>spring-ai-starter-model-google-genai</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-webmvc-test</artifactId>
			<scope>test</scope>
		</dependency>
		<dependency>
			<groupId>org.projectlombok</groupId>
			<artifactId>lombok</artifactId>
			<scope>provided</scope>
		</dependency>
</dependencies>

<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework.ai</groupId>
            <artifactId>spring-ai-bom</artifactId>
            <version>${spring-ai.version}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

---

#### 2️⃣ Add Configuration (`application.yml`)
Set your API key and configure the target model to `gemini-3.6-flash`:

```properties
spring:
  spring:
    application:
      name: gemini-demo
  ai:
    google:
      genai:
        api-key: ${GEMINI_API_KEY}
        chat:
          options:
            model: gemini-3.6-flash
```

---

#### 3️⃣ Define Strongly Typed Outputs with Java Records
Define the exact shape of your expected JSON response:

```java
package com.ledzedev.gemini_demo.model;

import java.util.List;

public record MovieRecommendation(
        String title,
        int releaseYear,
        String director,
        List<String> genres,
        String briefSummary
) {}
```

---

#### 4️⃣ Build the AI Service with Fault-Tolerant Schema Retry
Use Spring AI’s `ChatClient` and `validateSchema()` to handle automatic schema validation retries if Gemini ever produces malformed output:

```java
package com.ledzedev.gemini_demo.service;

import com.ledzedev.gemini_demo.model.MovieRecommendation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MovieService {
    private final ChatClient chatClient;

    public MovieService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public MovieRecommendation getRecommendation(String query){
        log.debug("Recommendation service...");
        return chatClient.prompt()
                .user("Give me a movie recommendation based on this preference:" + query)
                .call()
                .entity(MovieRecommendation.class);
    }
}
```

---

#### 5️⃣ Expose the REST Endpoint

```java
package com.ledzedev.gemini_demo.controller;

import com.ledzedev.gemini_demo.model.MovieRecommendation;
import com.ledzedev.gemini_demo.service.MovieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/recommend")
    public MovieRecommendation recommend(@RequestParam(defaultValue = "mind-bending sci-fi with a plot twist") String prompt){
        log.debug("Recommendation controller.");
        return movieService.getRecommendation(prompt);
    }
}
```

---

### 🔥 Why This Stack Wins:
✅ **No Manual JSON Parsing:** Spring AI generates JSON Schema directly from Java `record` reflection.  
✅ **Zero Boilerplate:** `ChatClient` abstracts all raw HTTP logic into fluent, readable calls.  
✅ **High Speed & Low Token Cost:** `gemini-3.6-flash` delivers ultra-low latency while maintaining high reasoning power.  
✅ **Production Readiness:** Schema validation retries ensure type safety before the response hits your business logic.

Are you using structured outputs in your Spring AI projects? What AI models are you using in production?

---

#Java #SpringBoot #Java21 #SpringAI #GoogleGemini #SoftwareArchitecture #Microservices #GenerativeAI
