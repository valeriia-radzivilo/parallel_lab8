package com.example;

import com.example.fox.FoxAlgorithm;
import com.example.shared.Matrix;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MatrixController {

    @PostMapping("/multiply")
    public int[][] multiplyMatrices(@RequestBody MatrixPayload payload) {
        Matrix firstMatrix = new Matrix(payload.getFirstMatrix());
        Matrix secondMatrix = new Matrix(payload.getSecondMatrix());
        FoxAlgorithm algorithm = new FoxAlgorithm(payload.getThreadCount(), firstMatrix, secondMatrix);
        return algorithm.algorithm();
    }

    @Setter
    @Getter
    public static class MatrixPayload {
        private int[][] firstMatrix;
        private int[][] secondMatrix;
        private int threadCount;

    }
}