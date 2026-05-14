package com.tokenopt.engine.memory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.Comparator;

public class InMemoryMemoryStore implements MemoryStore{
    private final Map<String , List<MemoryChunk>> store = new ConcurrentHashMap<>();

    @Override
    public void save(MemoryChunk chunk){
        store.computeIfAbsent(chunk.conversationId(), k -> new CopyOnWriteArrayList<>()).add(chunk);
    }

    @Override
    public List<MemoryChunk> searchTopK(String conversationId, float[] queryVector, int k){
        List<MemoryChunk> chunks = store.getOrDefault(conversationId, List.of());
        if(chunks.isEmpty() || k <=0){
            return List.of();
        }

        record Scored(MemoryChunk chunk, float score){}

        return chunks.stream()
        .map(c -> new Scored(c, VectorMath.cosineSimilarity(queryVector, c.embedding())))
        .sorted(Comparator.comparingDouble(Scored::score).reversed())
        .limit(k)
        .map(Scored::chunk)
        .toList();
    }

    @Override
    public void deleteConversation(String conversationId){
        store.remove(conversationId);
    }

}
