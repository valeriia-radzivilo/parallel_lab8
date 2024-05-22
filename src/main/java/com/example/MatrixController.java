package com.example;

import com.example.fox.FoxAlgorithm;
import com.example.shared.Matrix;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
public class MatrixController {

    int size = 3;
    Matrix matrix1 = new Matrix(size, size);
    Matrix matrix2 = new Matrix(size, size);

    @CrossOrigin
    @GetMapping("/")
    public List<int[][]> showMatrices() {
        matrix1.generateRandomMatrix();
        matrix2.generateRandomMatrix();
        return Arrays.asList(matrix1.matrix, matrix2.matrix);
    }


    @PostMapping("/multiply")
    public int[][] multiplyMatrices(@RequestBody MatrixPayload payload) {
        Matrix firstMatrix = payload.getFirstMatrix();
        Matrix secondMatrix = payload.getSecondMatrix();
        FoxAlgorithm algorithm = new FoxAlgorithm(payload.getThreadCount(), firstMatrix, secondMatrix);
        return algorithm.algorithm();
    }

    @Setter
    @Getter
    public static class MatrixPayload {
        private Matrix firstMatrix;
        private Matrix secondMatrix;
        private int threadCount;

    }
}