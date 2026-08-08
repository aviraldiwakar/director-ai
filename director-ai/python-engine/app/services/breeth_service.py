import httpx
from app.config import BREETH_API_KEY

class BreethMemoryService:
    def __init__(self):
        # Defaulting to standard REST implementation for Breeth cloud
        self.api_url = "https://api.thebreeth.com/v1"
        self.headers = {
            "Authorization": f"Bearer {BREETH_API_KEY}",
            "Content-Type": "application/json"
        }

    async def save_context(self, theme: str, details: str):
        """Writes a memory episode to the Breeth dashboard."""
        payload = {
            "intent": f"Context for theme: {theme}",
            "content": details
        }
        async with httpx.AsyncClient() as client:
            try:
                response = await client.post(f"{self.api_url}/memory/write", json=payload, headers=self.headers)
                if response.status_code == 200:
                    print("Successfully saved memory to Breeth.")
            except Exception as e:
                print(f"Breeth Write Error: {e}")

    async def retrieve_context(self, theme: str) -> str:
        """Retrieves past memories related to the current production theme."""
        payload = {"query": theme, "limit": 3}
        async with httpx.AsyncClient() as client:
            try:
                response = await client.post(f"{self.api_url}/memory/retrieve", json=payload, headers=self.headers)
                if response.status_code == 200:
                    data = response.json()
                    # Combine retrieved memory knots into a single context string
                    return " ".join([item['content'] for item in data.get('results', [])])
            except Exception as e:
                print(f"Breeth Retrieval Error: {e}")
        return "No specific past context found."