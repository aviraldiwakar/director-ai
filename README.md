# 🎬 Director AI

**ViCodathon Submission**  
An autonomous, event-driven microservice architecture that completely automates the cinematic video production pipeline.

## 💡 Inspiration
Capturing cinematic mobile footage and editing high-energy videos for personal pages takes immense creative effort. The actual filming and editing are the fun parts, but the pre-production—brainstorming themes, writing exact scripts, planning shot lists, and generating thumbnail ideas—can bottleneck the creative process. **Director AI** acts as an autonomous digital showrunner, taking a simple theme and generating a complete production package so creators can focus solely on shooting and editing.

## 🏗️ Architecture
Director AI utilizes a dual-backend microservice approach to separate state orchestration from cognitive AI tasks.

1. **The Orchestrator (Java 21 / Spring Boot):**
   A robust state machine backed by an H2 in-memory database. It handles job creation, state transitions (`INITIATED` $\rightarrow$ `COMPLETED`), and stores the final artifacts.
2. **The Cognitive Engine (Python 3.13 / FastAPI):**
   An asynchronous background worker that listens for job events, interfaces with the Claude 3.5 Sonnet LLM for reasoning and generation, and seamlessly patches updates back to the Java orchestrator.

## 🛠️ Tech Stack
* **Backend:** Java 21, Spring Boot 3.3, Maven
* **Database:** H2 Database (In-Memory), Spring Data JPA
* **AI Engine:** Python 3.13, FastAPI, Uvicorn, HTTPX
* **LLM / Memory:** Anthropic Claude 3.5 Sonnet, Breeth API

---

## 🚀 How to Run Locally

### Prerequisites
* Java 21 & Maven installed
* Python 3.13 installed

### 1. Start the Java Orchestrator
Navigate to the root directory containing the `pom.xml` and run:
```bash
mvn clean install
mvn spring-boot:run

.\venv\Scripts\activate
python -m uvicorn app.main:app --reload --port 8000