package com.tokenopt.engine.memory;

import java.util.Locale;

public class HashEmbeddingService implements EmbeddingService {
    private final int dimensions;

    public HashEmbeddingService(int dimensions) {
        this.dimensions = dimensions;
    }

    @Override
    public float[] embed(String text) {
        float[] vector = new float[dimensions];
        String[] tokens = text.toLowerCase(Locale.ROOT).split("\\W+");
        for (String token : tokens) {
            if (token.isBlank()) {
                continue;
            }
            int index = Math.floorMod(token.hashCode(), dimensions);
            vector[index] += 1.0f;
        }
        return VectorMath.normalize(vector);
    }
}
