package com.example;

import com.example.fox.FoxAlgorithm;
import com.example.shared.Matrix;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
public class MatrixController {

    @CrossOrigin
    @GetMapping("/")
    void hello() {
        System.out.println("Hello");
    }


    @CrossOrigin
    @GetMapping("/{size}")
    public List<int[][]> showMatrices(@PathVariable String size) {
        int s = Integer.parseInt(size);
        Matrix matrix1 = new Matrix(s, s);
        Matrix matrix2 = new Matrix(s, s);
        matrix1.generateRandomMatrix();
        matrix2.generateRandomMatrix();
        return Arrays.asList(matrix1.matrix, matrix2.matrix);
    }


    @CrossOrigin
    @PostMapping("/multiply")
    public int[][] multiplyMatrices(@RequestBody Map data) {
        final MatrixPayload payload = MatrixPayload.fromMap(data);
        Matrix firstMatrix = payload.getFirstMatrix();
        Matrix secondMatrix = payload.getSecondMatrix();
        FoxAlgorithm algorithm = new FoxAlgorithm(payload.getThreadCount(), firstMatrix, secondMatrix);
        return algorithm.run(true);
    }

    @Setter
    @Getter
    public static class MatrixPayload implements Serializable {
        private Matrix firstMatrix;
        private Matrix secondMatrix;
        private int threadCount;

        MatrixPayload(Matrix firstMatrix, Matrix secondMatrix, int threadCount) {
            this.firstMatrix = firstMatrix;
            this.secondMatrix = secondMatrix;
            this.threadCount = threadCount;
        }

        public static MatrixPayload fromMap(Map payload) {
            return new MatrixPayload(
                    new Matrix((ArrayList) payload.get("firstMatrix")),
                    new Matrix((ArrayList) payload.get("secondMatrix")),
                    Integer.parseInt(payload.get("threadCount").toString()));

        }

    }
}