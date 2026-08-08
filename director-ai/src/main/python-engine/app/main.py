from fastapi import FastAPI, BackgroundTasks, HTTPException
from pydantic import BaseModel
from app.services.orchestrator_client import process_production_pipeline

app = FastAPI(title="Director AI - Cognitive Engine")

class GenerationRequest(BaseModel):
    job_id: str
    theme: str

@app.get("/")
def health_check():
    return {"status": "online", "service": "Director AI Cognitive Engine"}

@app.post("/generate")
async def trigger_generation(request: GenerationRequest, background_tasks: BackgroundTasks):
    if not request.job_id or not request.theme:
        raise HTTPException(status_code=400, detail="job_id and theme are required")

    # Trigger autonomous background pipeline execution
    background_tasks.add_task(process_production_pipeline, request.job_id, request.theme)
    return {"status": "processing_started", "job_id": request.job_id}