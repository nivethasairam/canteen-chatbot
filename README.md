# SairamMenuIQ – Intelligent Canteen Chatbot

This project is a Retrieval-Augmented Generation (RAG) web application for the Sairam College canteen, built using Spring Boot + LangChain4j + OpenAI + Qdrant with a simple HTML/JavaScript frontend.

---

## Features

- Upload & process canteen menu PDF
- Store embeddings in Qdrant vector database
- GPT-powered chatbot interface
- Simple web frontend for student queries

---

##  Project Structure


canteen-chatbot/
├── src/
│   └── main/
│       ├── java/com/example/chatbot/
│       │   ├── ChatController.java
│       │   ├── RAGService.java
│       │   └── PdfLoader.java
│       └── resources/
│           ├── static/
│           │   ├── index.html
│           │   └── script.js
                └──  saibabaimage.jpg
│           └── application.properties
├── pom.xml
