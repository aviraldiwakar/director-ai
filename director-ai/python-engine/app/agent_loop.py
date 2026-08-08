import asyncio
import httpx
from app.config import JAVA_BACKEND_URL
from app.services.discovery_service import DiscoveryService
from app.services.claude_agent import ClaudeAgent

async def run_autonomous_loop():
    print("🤖 Autonomous Agent Loop Started...")
    discovery = DiscoveryService()
    claude = ClaudeAgent()

    while True:
        try:
            async with httpx.AsyncClient() as client:
                # 1. Find all initialized agents
                agents_response = await client.get(f"{JAVA_BACKEND_URL}/agent/all")

                if agents_response.status_code == 200:
                    agents = agents_response.json()

                    for agent in agents:
                        agent_id = agent["id"]
                        print(f"Processing agent: {agent['name']} ({agent['domain']})")

                        # 2. Fetch memory (past posts)
                        feed_response = await client.get(f"{JAVA_BACKEND_URL}/agent/feed?agentId={agent_id}")
                        memory = feed_response.json().get("posts", [])[:5] # keep recent 5 for context

                        # 3. Discover live topics
                        live_topics = discovery.fetch_live_topics()

                        # 4. Agent Judgment & Generation
                        post_decision = await claude.generate_autonomous_post(agent, memory, live_topics)

                        # 5. Publish if a topic was selected
                        if post_decision.get("selected"):
                            new_post = {
                                "agentId": agent_id,
                                "text": post_decision["text"],
                                "rationale": post_decision["rationale"],
                                "sources": post_decision["sources"]
                            }
                            await client.post(f"{JAVA_BACKEND_URL}/agent/posts", json=new_post)
                            print(f"✅ Published new post for {agent['name']}")
                        else:
                            print(f"⏭️ {agent['name']} rejected all current topics.")

        except Exception as e:
            print(f"Error in autonomous loop: {e}")

        # Wait 30 minutes before running again (For testing, you can change this to 120 seconds!)
        await asyncio.sleep(1800)