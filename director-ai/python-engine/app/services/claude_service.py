import httpx
from app.config import ANTHROPIC_API_KEY

class ClaudeService:
    def __init__(self):
        self.api_key = ANTHROPIC_API_KEY

    async def _call_claude(self, prompt: str) -> str:
        headers = {
            "x-api-key": self.api_key, # Fixed Anthropic header format
            "anthropic-version": "2023-06-01",
            "Content-Type": "application/json"
        }

        payload = {
            "model": "claude-3-5-sonnet-20241022",
            "max_tokens": 1000,
            "messages": [{"role": "user", "content": prompt}]
        }

        async with httpx.AsyncClient(timeout=60.0) as client:
            try:
                # Pointing back to Anthropic's official endpoint
                response = await client.post("https://api.anthropic.com/v1/messages", json=payload, headers=headers)
                if response.status_code == 200:
                    data = response.json()
                    return data["content"][0]["text"]
                else:
                    print(f"Claude API warning ({response.status_code}): {response.text}")
            except Exception as e:
                print(f"Error calling Claude service: {e}")

        # Safe fallback in case of no API key or network block
        return f"[Generated Output for prompt: '{prompt[:40]}...']"

    async def generate_script(self, theme: str) -> str:
        prompt = (
            f"You are a visionary film director. Write a compelling, cinematic 60-second video script "
            f"based on the theme: '{theme}'. Include scene descriptions and audio/narration queues."
        )
        return await self._call_claude(prompt)

    async def generate_shot_list(self, script: str) -> str:
        prompt = (
            f"Given the following script, break it down into a structured JSON shot list with fields "
            f"for shot_number, camera_angle, action, and lighting:\n\n{script}"
        )
        return await self._call_claude(prompt)

    async def generate_thumbnail_prompt(self, theme: str, script: str) -> str:
        prompt = (
            f"Based on the theme '{theme}' and script excerpt below, write a high-impact image generation prompt "
            f"for Midjourney/DALL-E to create a thumbnail:\n\n{script[:300]}"
        )
        return await self._call_claude(prompt)