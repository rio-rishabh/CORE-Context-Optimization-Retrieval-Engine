package com.tokenopt.engine.memory;

public final class VectorMath {
    private VectorMath() {
    }

    public static float[] normalize(float[] vector) {
        double sumSquares = 0.0;
        for (float value : vector) {
            sumSquares += value * value;
        }
        if (sumSquares == 0.0) {
            return vector;
        }
        double magnitude = Math.sqrt(sumSquares);
        float[] normalized = new float[vector.length];
        for (int i = 0; i < vector.length; i++) {
            normalized[i] = (float) (vector[i] / magnitude);
        }
        return normalized;
    }

    public static float cosineSimilarity(float[] vector1, float[] vector2){
        if(vector1.length != vector2.length){
            throw new IllegalArgumentException("Vector Dimensions Must Match");
        }
        double dotProduct = 0.0;
        double norm1 = 0.0;
        double norm2 = 0.0;

        for(int i =0 ; i< vector1.length; i++){
            dotProduct += vector1[i] * vector2[i];
            norm1 += vector1[i] * vector1[i];
            norm2 += vector2[i]* vector2[i];
        }
        if(norm1 == 0.0 || norm2 == 0.0){
            return 0.0f;
        }
        return (float)(dotProduct / (Math.sqrt(norm1) * Math.sqrt(norm2)));
    }
    
}
