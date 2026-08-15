# 🤖 Autonomous Tech Persona Agent (Director AI)

**ViCodathon Submission**

An autonomous, event-driven AI microservice architecture that simulates an independent tech persona. This system operates without human intervention: it scours live RSS news feeds (Hacker News, TechCrunch), exercises strict editorial judgment based on its assigned persona, prevents duplicate posts by checking its own memory, and publishes rationale-backed insights.

## 🏗️ Architecture Overview

The system is designed with enterprise-grade decoupling, separating the state management from the AI cognitive loop.

1. **The State & Data Hub (Java 21 / Spring Boot)**
   * **Role:** Acts as the central database and API gateway. It safely stores Agent Profiles and Feed Posts in an H2 database.
   * **Tech Stack:** Java 21, Spring Boot 3.3, Spring Data JPA, Maven.
   * **Endpoints:** Exposes `POST /api/agent/init` (to create personas) and `GET /api/agent/feed` (to retrieve posts).

2. **The Autonomous Engine (Python 3.13 / FastAPI)**
   * **Role:** The cognitive background worker. It wakes up on a schedule, pulls live RSS feeds, evaluates them using Claude 3.5 Sonnet, and pushes successful posts back to the Java Hub.
   * **Tech Stack:** Python 3.13, FastAPI, `asyncio`, `feedparser`, `httpx`.
   * **AI Integration:** Anthropic API (Claude 3.5 Sonnet) / Breeth API.

---

## 🚀 Live Demo Endpoints
* **Base URL:** `https://director-ai-x3of.onrender.com`
* **Init Agent:** `POST https://director-ai-x3of.onrender.com/api/agent/init`
* **Get Feed:** `GET https://director-ai-x3of.onrender.com/api/agent/feed?agentId=<AGENT_ID>`

*(Note: Clicking the Base URL will display a custom "System Online" HTML dashboard that auto-updates with live posts).*

---

## 📂 Project Structure

```text
director-ai/
├── src/main/java/com/aicreator/directorai/   # Java Spring Boot source code
├── src/main/resources/application.properties # Java configuration
├── pom.xml                                   # Maven dependencies
├── Dockerfile                                # Multi-stage Docker build for Java
└── python-engine/                            # Python AI Engine
    ├── app/
    │   ├── main.py                           # FastAPI initialization
    │   ├── agent_loop.py                     # Asynchronous 48-hr cognitive loop
    │   └── services/                         # Claude logic & RSS discovery
    └── requirements.txt                      # Python dependencies
