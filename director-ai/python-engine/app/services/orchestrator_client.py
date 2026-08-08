import httpx
from app.config import JAVA_BACKEND_URL
from app.services.claude_service import ClaudeService
from app.services.breeth_service import BreethMemoryService

claude_service = ClaudeService()
breeth_service = BreethMemoryService()

async def process_production_pipeline(job_id: str, theme: str):
    async with httpx.AsyncClient() as client:

        # Step 0: Query Breeth for persistent context
        historical_context = await breeth_service.retrieve_context(theme)
        enriched_theme = f"{theme}. Use this background context: {historical_context}"

        # Step 1: Generate Script & Update Java Orchestrator
        script = await claude_service.generate_script(enriched_theme)
        await client.patch(
            f"{JAVA_BACKEND_URL}/jobs/{job_id}/advance",
            json={
                "newStage": "SCRIPTWRITING",
                "script": script
            }
        )

        # Step 2: Generate Shot List
        shot_list = await claude_service.generate_shot_list(script)
        await client.patch(
            f"{JAVA_BACKEND_URL}/jobs/{job_id}/advance",
            json={
                "newStage": "SHOTLIST_GENERATION",
                "shotList": shot_list
            }
        )

        # Step 3: Generate Thumbnail Prompt & Complete
        thumbnail_prompt = await claude_service.generate_thumbnail_prompt(theme, script)
        await client.patch(
            f"{JAVA_BACKEND_URL}/jobs/{job_id}/advance",
            json={
                "newStage": "COMPLETED",
                "thumbnailPrompt": thumbnail_prompt
            }
        )