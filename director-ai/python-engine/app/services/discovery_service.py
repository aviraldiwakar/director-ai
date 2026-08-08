import feedparser

class DiscoveryService:
    def fetch_live_topics(self) -> list:
        # Live RSS feeds for AI & Tech
        sources = [
            "https://hnrss.org/frontpage",
            "https://techcrunch.com/feed/",
            "https://www.wired.com/feed/category/gear/latest"
        ]

        articles = []
        for url in sources:
            try:
                feed = feedparser.parse(url)
                for entry in feed.entries[:5]: # Get top 5 from each
                    articles.append({
                        "title": entry.title,
                        "link": entry.link,
                        "summary": entry.get("summary", "")[:200]
                    })
            except Exception as e:
                print(f"Error fetching from {url}: {e}")

        return articles