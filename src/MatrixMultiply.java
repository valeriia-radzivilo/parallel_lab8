import fox.FoxAlgorithm;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import shared.Matrix;

@RestController
public class MatrixController {

    @PostMapping("/multiply")
    public int[][] multiplyMatrices(@RequestBody MatrixPayload payload) {
        Matrix firstMatrix = new Matrix(payload.getFirstMatrix());
        Matrix secondMatrix = new Matrix(payload.getSecondMatrix());
        FoxAlgorithm algorithm = new FoxAlgorithm(payload.getThreadCount(), firstMatrix, secondMatrix);
        return algorithm.algorithm();
    }

    public static class MatrixPayload {
        private int[][] firstMatrix;
        private int[][] secondMatrix;
        private int threadCount;

        // getters and setters
    }
}