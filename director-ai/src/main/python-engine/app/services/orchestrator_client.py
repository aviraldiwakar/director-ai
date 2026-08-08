import httpx
from app.config import JAVA_BACKEND_URL
from app.services.claude_service import ClaudeService

claude_service = ClaudeService()

async def process_production_pipeline(job_id: str, theme: str):
    async with httpx.AsyncClient() as client:
        # Step 1: Generate Script & Update Java Orchestrator
        script = await claude_service.generate_script(theme)
        await client.patch(
            f"{JAVA_BACKEND_URL}/jobs/{job_id}/advance",
            json={
                "newStage": "SCRIPTWRITING",
                "script": script
            }
        )

        # Step 2: Generate Shot List & Update Java Orchestrator
        shot_list = await claude_service.generate_shot_list(script)
        await client.patch(
            f"{JAVA_BACKEND_URL}/jobs/{job_id}/advance",
            json={
                "newStage": "SHOTLIST_GENERATION",
                "shotList": shot_list
            }
        )

        # Step 3: Generate Thumbnail Prompt & Complete Pipeline
        thumbnail_prompt = await claude_service.generate_thumbnail_prompt(theme, script)
        await client.patch(
            f"{JAVA_BACKEND_URL}/jobs/{job_id}/advance",
            json={
                "newStage": "COMPLETED",
                "thumbnailPrompt": thumbnail_prompt
            }
        )