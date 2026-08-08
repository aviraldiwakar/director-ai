import os
from dotenv import load_dotenv

# Load variables from the .env file
load_dotenv()

JAVA_BACKEND_URL = os.getenv("JAVA_BACKEND_URL", "http://localhost:8080/api/v1")
BREETH_API_KEY = os.getenv("BREETH_API_KEY", "")