import httpx
import json
from app.config import ANTHROPIC_API_KEY

class ClaudeAgent:
    def __init__(self):
        self.api_key = ANTHROPIC_API_KEY

    async def generate_autonomous_post(self, persona: dict, memory: list, live_topics: list) -> dict:
        prompt = f"""
You are an autonomous AI persona. 
Your Name: {persona['name']}
Your Domain/Expertise: {persona['domain']}

Here is your memory (your most recently published posts):
{json.dumps(memory, indent=2)}

Here are the latest news topics discovered on the web right now:
{json.dumps(live_topics, indent=2)}

TASK:
1. Editorial Judgment: Review the news topics. Reject any that do not fit your domain. 
2. Memory Check: Reject any topics you have already posted about recently.
3. Select ONE topic to post about.
4. Write a compelling, opinionated post in your distinct voice.
5. Explain your rationale.

Output STRICTLY as a JSON object with no markdown formatting or extra text. Format:
{{
    "selected": true,
    "text": "The actual post content written in your persona's voice.",
    "rationale": "Why this topic was selected over others, and why it is relevant now.",
    "sources": ["URL of the selected topic"]
}}
If no topics pass your editorial judgment, return {{"selected": false}}.
"""
        headers = {
            "x-api-key": self.api_key,
            "anthropic-version": "2023-06-01",
            "Content-Type": "application/json"
        }
        payload = {
            "model": "claude-3-5-sonnet-20241022",
            "max_tokens": 800,
            "messages": [{"role": "user", "content": prompt}]
        }

        async with httpx.AsyncClient(timeout=60.0) as client:
            response = await client.post("https://api.anthropic.com/v1/messages", json=payload, headers=headers)
            if response.status_code == 200:
                text_output = response.json()["content"][0]["text"]
                # Clean markdown backticks if Claude includes them
                clean_json = text_output.replace("```json", "").replace("```", "").strip()
                try:
                    return json.loads(clean_json)
                except Exception as e:
                    print(f"JSON Parse Error: {e}")
                    return {"selected": False}
            else:
                print(f"Claude Error: {response.text}")
                return {"selected": False}