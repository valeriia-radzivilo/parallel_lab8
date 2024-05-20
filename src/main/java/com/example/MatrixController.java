package com.example;

import com.example.fox.FoxAlgorithm;
import com.example.shared.Matrix;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class MatrixController {

    @GetMapping("/")
    public List<int[][]> showMatrices() {
        int[][] matrix1 = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        int[][] matrix2 = {
                {9, 8, 7},
                {6, 5, 4},
                {3, 2, 1}
        };
        return Arrays.asList(matrix1, matrix2);
    }


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