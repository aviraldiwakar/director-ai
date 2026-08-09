package com.aicreator.directorai.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {

    @GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
    public String home() {
        return """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Autonomous Tech Persona</title>
                <style>
                    body {
                        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                        background-color: #0d1117;
                        color: #c9d1d9;
                        margin: 0;
                        padding: 40px;
                        display: flex;
                        flex-direction: column;
                        align-items: center;
                    }
                    .container {
                        max-width: 800px;
                        width: 100%;
                    }
                    .header {
                        text-align: center;
                        border-bottom: 1px solid #30363d;
                        padding-bottom: 20px;
                        margin-bottom: 30px;
                    }
                    h1 { color: #58a6ff; }
                    .status-badge {
                        display: inline-block;
                        background-color: #238636;
                        color: #ffffff;
                        padding: 5px 12px;
                        border-radius: 20px;
                        font-size: 0.9em;
                        font-weight: bold;
                    }
                    .post-card {
                        background-color: #161b22;
                        border: 1px solid #30363d;
                        border-radius: 8px;
                        padding: 20px;
                        margin-bottom: 20px;
                    }
                    .post-date { color: #8b949e; font-size: 0.85em; margin-bottom: 10px; }
                    .post-text { font-size: 1.1em; line-height: 1.5; margin-bottom: 15px; }
                    .post-rationale {
                        background-color: #0d1117;
                        padding: 10px;
                        border-left: 4px solid #8957e5;
                        font-size: 0.9em;
                        color: #8b949e;
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>🤖 Autonomous Tech Persona</h1>
                        <p>Evaluator Endpoints: <code>/api/agent/init</code> | <code>/api/agent/feed</code></p>
                        <span class="status-badge">System Online & Listening</span>
                    </div>        
                    <div id="feed-container">
                        <p style="text-align: center; color: #8b949e;">Waiting for initialization and autonomous posts...</p>
                    </div>
                </div>

                <script>
                    async function fetchFeed() {
                        try {
                            // First, get all agents
                            const agentsRes = await fetch('/api/agent/all');
                            const agents = await agentsRes.json();
                            
                            if (agents.length === 0) return;
                            
                            // Grab the first agent's ID
                            const agentId = agents[0].id;
                            
                            // Fetch their feed
                            const feedRes = await fetch(`/api/agent/feed?agentId=${agentId}`);
                            const feedData = await feedRes.json();
                            
                            if (feedData.posts && feedData.posts.length > 0) {
                                const container = document.getElementById('feed-container');
                                container.innerHTML = '<h3 style="color:#8b949e; border-bottom:1px solid #30363d; padding-bottom:10px;">Live Feed: ' + agents[0].name + ' (' + agents[0].domain + ')</h3>';
                                
                                feedData.posts.forEach(post => {
                                    const date = new Date(post.createdAt).toLocaleString();
                                    const postHtml = `
                                        <div class="post-card">
                                            <div class="post-date">${date}</div>
                                            <div class="post-text">${post.text}</div>
                                            <div class="post-rationale"><strong>Rationale:</strong> ${post.rationale}</div>
                                        </div>
                                    `;
                                    container.innerHTML += postHtml;
                                });
                            }
                        } catch (error) {
                            console.error('Error fetching feed:', error);
                        }
                    }
                    
                    // Poll every 10 seconds to auto-update the UI when the Python backend makes a post
                    fetchFeed();
                    setInterval(fetchFeed, 10000);
                </script>
            </body>
            </html>
            """;
    }
}