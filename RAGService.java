package com.example.chatbot;

import dev.langchain4j.model.chat.OpenAiChatModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.embedding.OpenAiEmbeddingModel;
import dev.langchain4j.store.embedding.qdrant.QdrantVectorStore;
import dev.langchain4j.store.embedding.VectorStore;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.retriever.Retriever;
import dev.langchain4j.chain.RetrievalAugmentedGenerationChain;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class RAGService {

    private final RetrievalAugmentedGenerationChain ragChain;

    public RAGService(@Value("${openai.api.key}") String apiKey,
                      @Value("${qdrant.host}") String qdrantHost,
                      @Value("${qdrant.api.key}") String qdrantKey) throws IOException {

        OpenAiChatModel chatModel = OpenAiChatModel.builder()
            .apiKey(apiKey)
            .modelName("gpt-3.5-turbo")
            .build();

        String pdfText = PdfLoader.extractText("canteen_menu.pdf");
        EmbeddingModel embeddingModel = OpenAiEmbeddingModel.withApiKey(apiKey);
        List<TextSegment> segments = TextSegment.fromText(pdfText);
        List<Embedding> embeddings = embeddingModel.embedAll(segments);

        VectorStore vectorStore = QdrantVectorStore.builder()
            .host(qdrantHost)
            .apiKey(qdrantKey)
            .build();
        vectorStore.addAll(segments, embeddings);

        Retriever retriever = vectorStore.retriever().withMaxResults(3);

        this.ragChain = RetrievalAugmentedGenerationChain.builder()
            .retriever(retriever)
            .chatModel(chatModel)
            .promptTemplate("You are a helpful canteen assistant. Answer based on this menu:\n{{retrieved}}\nUser: {{input}}")
            .build();
    }

    public String chat(String query) {
        return ragChain.generate(query);
    }
}
