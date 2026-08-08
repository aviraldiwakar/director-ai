# AI Prompts & Usage Log
**Project:** Autonomous Tech Persona Agent (originally Director AI)
**Hackathon:** ViCodathon
**Developer:** Aviral Diwakar

## 📝 Overview
This document serves as the official AI usage log for the ViCodathon Authenticity Review. The project underwent a significant pivot mid-hackathon. Initially, the architecture was designed for an automated video scripting pipeline. After reviewing the strict guidelines for Problem Statement 3, the entire backend was rapidly refactored into an Autonomous Tech Persona Agent, utilizing the same event-driven microservice pattern (Java Spring Boot + Python FastAPI).

---

## Phase 1: Initial Architecture (Video Production Pipeline)
*We initially built a state machine to handle sequential AI video generation tasks. These prompts reflect the foundational setup of the dual-backend system.*

**Prompt 1:**
> "Design a microservice architecture using Java 21 Spring Boot for an orchestrator and Python FastAPI for an AI processing engine. The Java app needs to store jobs in an H2 database with stages INITIATED, SCRIPTWRITING, and COMPLETED."

**Prompt 2:**
> "Write the Python FastAPI code to receive a job_id, call the Claude 3.5 Sonnet API to write a video script based on a theme, and patch the results back to the Java server."

**Prompt 3:**
> "I am getting a 404 error from the Breeth API when trying to call Claude. How do I fix the `claude_service.py` to call Anthropic directly using my `sk-ant-...` key?"

**Prompt 4 (Testing Payload):**
> "Generate a test JSON payload to send via Postman to the Spring Boot server to test the video pipeline. Use the theme of a 'Gorakhpur Travel Vlog'."

---

## Phase 2: The Pivot (Problem Statement 3 - Autonomous Agent)
*Upon realizing the submission required an autonomous social media persona (not a video script tool), the architecture was pivoted.*

**Prompt 5:**
> "[Pasted full ViCodathon Problem Statement 3 guidelines] these are the guidelines for the project. go through it and find if we miss anything"

**Prompt 6:**
> "okay lets fix everything one by one. Update the Java Spring Boot Data Models to include AgentProfile and FeedPost. Add the endpoints POST /api/agent/init and GET /api/agent/feed?agentId=..."

**Prompt 7:**
> "Step 1 Done and we will move to Step 2: Python Autonomous Engine (Live Topic Discovery, Editorial Judgment & 48-Hour Loop)!"

**Prompt 8:**
> "Write a DiscoveryService in Python using feedparser to pull live RSS feeds from TechCrunch and Hacker News. Then write the background loop using asyncio to run every 30 minutes, evaluate topics using Claude, and post to the Java backend."

---

## Phase 3: Cloud Deployment & Troubleshooting
*Deploying the microservices to Render to meet the 'Live Demo URL' requirement.*

**Prompt 9:**
> "I am trying to deploy the Java Spring Boot app to Render but it don't have java option. [Attached screenshot of Render environment dropdown]"

**Prompt 10:**
> "Provide the exact Dockerfile needed to deploy a Maven Spring Boot application on Render using the Docker environment setting."

**Prompt 11:**
> "I am getting a Maven error: 'there is no POM in this directory' when running mvn clean install. I am in D:\Projects\director-ai\director-ai."

**Prompt 12:**
> "Docker build is failing on Render with: 'failed to calculate checksum of ref... /src: not found'. My pom.xml and src folder are nested inside the director-ai folder."

**Prompt 13:**
> "Render Python deployment is failing with: 'Root directory python-engine does not exist'. The folder is actually inside the director-ai folder."

**Prompt 14:**
> "we have to submit in this form means we need to show the frontend. [Attached screenshot of submission form showing 'Live URL' placeholder as a vercel.app link]."

**Prompt 15:**
> "Write a RootController for the Java Spring Boot app that returns a friendly 'System Online' message so the judges don't see a Whitelabel Error Page when they click the Live URL."