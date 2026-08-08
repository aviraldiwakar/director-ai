# AI Usage & Prompts Log
**Hackathon:** ViCodathon
**Project:** Director AI (Autonomous AI Creator)

---

## Prompt 1: System Initialization
**Tool Used:** Claude Desktop
**Prompt:**
Please initialize the main backend for "Project Director AI" using Java 21 and Spring Boot. This core orchestrator will manage an autonomous cinematic production pipeline.

Please generate the necessary directory structure and the main application class for an empty Spring Boot project. I want the base package to be: `com.aicreator.directorai`.

Ensure the `director-ai/pom.xml` includes the standard starter dependencies for Spring Web, Spring Data JPA, H2 Database (for in-memory state persistence), and Lombok (for clean model code).

**Output Summary:**
Generated complete Spring Boot directory structure, `director-ai/pom.xml` with required dependencies, `application.properties` for H2 configuration, and the main `DirectorAiApplication.java` entry point.

---

## Prompt 2: Core State Machine & Domain Models
**Tool Used:** Claude Desktop
**Prompt:**
We are building the core domain layer for "Project Director AI" inside `com.aicreator.directorai`.

Please create the following classes and enums:
1. `model.ProductionStage` (Enum): INITIATED, IDEATION, SCRIPTWRITING, SHOTLIST_GENERATION, THUMBNAIL_PROMPTING, COMPLETED, FAILED
2. `entity.ProductionJob` (JPA Entity): UUID id, String title, String theme, ProductionStage currentStage, String generatedScript, String shotListJson, String thumbnailPrompt, LocalDateTime createdAt, LocalDateTime updatedAt
3. `repository.ProductionJobRepository` (Spring Data JPA Interface)
4. `dto.CreateJobRequest` (Record)
5. `dto.JobStatusResponse` (Record)

Please ensure proper JPA annotations, clean structure, and Lombok annotations where applicable.

**Output Summary:**
Generated `ProductionStage.java`, `ProductionJob.java`, `ProductionJobRepository.java`, `CreateJobRequest.java`, and `JobStatusResponse.java` with complete JPA and Lombok configurations.

---

## Prompt 3: Orchestration Service & REST API Layer
**Tool Used:** Claude Desktop
**Prompt:**
We need to implement the business logic and REST endpoints for "Project Director AI" inside `com.aicreator.directorai`.

Please create the following classes:
1. `service.ProductionJobService`: Methods for createJob, getJobById, getAllJobs, and updateJobStageAndArtifacts.
2. `controller.ProductionJobController`: REST endpoints for POST /api/v1/jobs, GET /api/v1/jobs/{id}, GET /api/v1/jobs, and PATCH /api/v1/jobs/{id}/advance.

Ensure proper Spring annotations (`@Service`, `@RestController`, `@RequestMapping`, `@CrossOrigin`), clean exception handling, and dependency injection via constructors.

**Output Summary:**
Generated `ProductionJobService.java`, `ProductionJobController.java`, `AdvanceJobRequest.java`, `GlobalExceptionHandler.java`, and `ResourceNotFoundException.java` to complete the REST API.

---

## Prompt 4: Python Cognitive Engine Implementation
**Tool Used:** Claude Desktop / Manual Fallback
**Prompt:**
We are building the Python AI Reasoning Service for "Project Director AI". This service acts as the cognitive engine that uses Claude to autonomously generate video scripts, shot lists, and thumbnail prompts.

Please create a clean Python application with the following structure:
1. `requirements.txt`: fastapi, uvicorn, httpx, pydantic, python-dotenv
2. `app/config.py`: Environment settings for `JAVA_BACKEND_URL` and `BREETH_API_KEY`.
3. `app/services/claude_service.py`: Functions to generate_script, generate_shot_list, and generate_thumbnail_prompt via Claude API.
4. `app/services/orchestrator_client.py`: Asynchronous function to process_production_pipeline and PATCH updates to the Java backend.
5. `app/main.py`: FastAPI app exposing POST /generate to trigger the background tasks.

**Output Summary:**
Constructed the complete FastAPI microservice structure (`requirements.txt`, `config.py`, `claude_service.py`, `orchestrator_client.py`, `main.py`) to handle autonomous API routing, integrated later with the Breeth persistent memory service.