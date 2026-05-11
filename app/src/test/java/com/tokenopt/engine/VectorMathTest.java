package com.tokenopt.engine;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import com.tokenopt.engine.memory.VectorMath;

public class VectorMathTest {

    @Test
    public void cosineSimilarity_identicalVectors_returnsOne() {
        float[] vector1 = {1.0f, 2.0f, 3.0f};
        float[] vector2 = {1.0f, 2.0f, 3.0f};
        float result = VectorMath.cosineSimilarity(vector1, vector2);
        assertEquals(1.0f, result, 1e-6);
    }
}
