import asyncio
from fastapi import FastAPI
from contextlib import asynccontextmanager
from app.agent_loop import run_autonomous_loop

@asynccontextmanager
async def lifespan(app: FastAPI):
    # Start the background loop when the server starts
    loop_task = asyncio.create_task(run_autonomous_loop())
    yield
    # Cancel it when the server shuts down
    loop_task.cancel()

app = FastAPI(lifespan=lifespan)

@app.get("/health")
def health_check():
    return {"status": "Agent Engine Online"}