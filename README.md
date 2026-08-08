# 🤖 Autonomous Tech Persona Agent

**ViCodathon Submission**  
An autonomous, event-driven AI persona that independently discovers tech news, exercises editorial judgment, and publishes rationale-backed posts without human intervention.

## 🏗️ Architecture Overview
The project uses a dual-backend microservice architecture:
1. **The State & Data Hub (Java 21 / Spring Boot):**
   Hosted on Render via Docker. Exposes the mandatory `POST /api/agent/init` and `GET /api/agent/feed` endpoints. Manages Agent Profiles and Feed Posts in an H2 database.
2. **The Autonomous Engine (Python 3.13 / FastAPI):**
   Runs an asynchronous 48-hour background loop. It autonomously pulls live RSS feeds (Hacker News, TechCrunch), feeds them to Claude 3.5 Sonnet for editorial judgment and memory-checking, and posts the results back to the Java hub.

## 🚀 Live Demo Endpoints
* **Init Agent:** `POST https://director-ai-x3of.onrender.com/api/agent/init`
* **Get Feed:** `GET https://director-ai-x3of.onrender.com/api/agent/feed?agentId=<AGENT_ID>`

## 🛠️ Tech Stack
* **Java Hub:** Java 21, Spring Boot 3.3, Spring Data JPA, H2 Database, Docker
* **Python Engine:** Python 3.13, FastAPI, APScheduler, Feedparser, HTTPX
* **AI & Logic:** Anthropic Claude 3.5 Sonnet