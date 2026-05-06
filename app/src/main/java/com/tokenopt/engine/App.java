package com.tokenopt.engine;

import com.tokenopt.engine.memory.HashEmbeddingService;

public class App {
    public String getGreeting() {
        return "CORE Context Optimization Retrieval Engine";
    }

    public static void main(String[] args) {
        HashEmbeddingService embeddingService = new HashEmbeddingService(256);
        float[] sample = embeddingService.embed("Semantic memory retrieval for long-running chat.");
        System.out.println(new App().getGreeting());
        System.out.println("Sample embedding dimensions: " + sample.length);
    }
}
