# Spring Boot + LangGraph4j + Groq Demo

This project demonstrates an agentic workflow using **LangGraph4j** (a Java port of LangGraph) integrated with **Spring Boot** and **Groq**. 

The application implements a **draft -> review -> revise** loop, where a primary agent generates an answer and a reviewer agent evaluates it, triggering revisions if necessary.

## 🚀 Quick Start

### Prerequisites
- Java 17 or higher
- Maven
- A Groq API Key

### Configuration
Set your Groq API key as an environment variable:
```bash
export GROQ_API_KEY=your_api_key_here
```

### Running the Application
```bash
mvn spring-boot:run
```

## 🛠 Usage

### 🌐 Web Interface
Once the application is running, you can access a modern chat interface in your browser:
**[http://localhost:8080](http://localhost:8080)**

The UI features:
- **Formatted Responses**: Full Markdown support for rich text, lists, and code blocks.
- **Execution Trace**: Expandable view to see the internal steps taken by the graph.
- **Revision Tracking**: Displays how many times the draft was revised before reaching the final answer.

### 🔌 REST API
You can also interact with the service directly via the API:

```bash
curl -X POST localhost:8080/api/chat \
     -H 'Content-Type: application/json' \
     -d '{"question":"Explain the CAP theorem"}'
```

## 🏗 Architecture
- **Spring Boot**: Provides the REST API and dependency injection.
- **LangGraph4j**: Orchestrates the state machine and agentic loop.
- **Groq**: Powers the LLM via OpenAI-compatible endpoints.
- **LangChain4j**: Handles the integration between Java and the LLM provider.

---
*Note: This is a demonstration project. Ensure you check the latest LangGraph4j javadoc if imports differ based on your version.*
