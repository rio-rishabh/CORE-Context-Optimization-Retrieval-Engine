package com.tokenopt.engine.memory;

import java.util.List;
public interface MemoryStore {

    void save(MemoryChunk chunk);

    List<MemoryChunk> searchTopK(String conversationId, float[] queryVector, int k);
    
    void deleteConversation(String conversationId);
}
