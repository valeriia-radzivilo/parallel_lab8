package com.example.fox;


import com.example.shared.Matrix;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class FoxAlgorithm {


    final int threadCount;

    final Matrix firstMatrix;
    final Matrix secondMatrix;

    public FoxAlgorithm(int threadCount, Matrix firstMatrix, Matrix secondMatrix) {


        this.threadCount = threadCount;
        this.firstMatrix = firstMatrix;
        this.secondMatrix = secondMatrix;
    }

    static void GetBlocks(int threadCount, int step, int[][] blocksRows, int[][] blocksColumns) {
        int offsetRow = 0, offsetColumn = 0;
        for (int i = 0; i < threadCount; i++) {
            offsetColumn = 0;
            for (int j = 0; j < threadCount; j++) {
                blocksRows[i][j] = offsetRow;
                blocksColumns[i][j] = offsetColumn;
                offsetColumn += step;
            }
            offsetRow += step;
        }
    }

    static int gcd(int minDivisor, int dividend) {
        int currentDivisor = minDivisor;
        while (currentDivisor > 1 && dividend % currentDivisor != 0) {
            currentDivisor += currentDivisor >= minDivisor ? 1 : -1;
            if (currentDivisor > Math.sqrt(dividend)) {
                currentDivisor = Math.min(minDivisor, dividend / minDivisor) - 1;
            }
        }
        if (currentDivisor == -1)
            currentDivisor *= -1;

        return currentDivisor >= minDivisor ? currentDivisor : currentDivisor != 0 ? dividend / currentDivisor : dividend;
    }

    static ArrayList<Future> MultiplyBlocks(Matrix multiplied,
                                            Matrix multiplicator,
                                            int threadNumber,
                                            int[][] result, int step,
                                            ExecutorService exec, int[][] rowsOffsets, int[][] columnsOffsets) {
        ArrayList<Future> threads = new ArrayList<>();

        for (int l = 0; l < threadNumber; l++) {
            for (int i = 0; i < threadNumber; i++) {
                for (int j = 0; j < threadNumber; j++) {

                    int offsetRow1 = rowsOffsets[i][(i + l) % threadNumber];
                    int offsetColumn1 = columnsOffsets[i][(i + l) % threadNumber];

                    int offsetRow2 = rowsOffsets[(i + l) % threadNumber][j];
                    int offsetColumn2 = columnsOffsets[(i + l) % threadNumber][j];

                    int[][] copiedMultipliedBlock = copy(multiplied, offsetRow1, offsetColumn1, step);
                    int[][] copiedMultiplicatorBlock = copy(multiplicator, offsetRow2, offsetColumn2, step);

                    FoxAlgorithmThread t = new FoxAlgorithmThread(copiedMultipliedBlock, copiedMultiplicatorBlock, result, rowsOffsets[i][j], columnsOffsets[i][j]);
                    threads.add(exec.submit(t));
                }
            }
        }
        return threads;
    }

    static int[][] copy(Matrix matrix, int i, int j, int size) {
        int[][] block = new int[size][size];
        for (int k = 0; k < size; k++) {
            System.arraycopy(matrix.getRow(k + i), j, block[k], 0, size);
        }
        return block;
    }


    public int[][] algorithm() {
        int[][] result = new int[firstMatrix.length()][secondMatrix.length()];

        final int threadCountGCD = gcd(threadCount, firstMatrix.length());
        int step = firstMatrix.length() / threadCountGCD;

        ExecutorService exec = Executors.newFixedThreadPool(threadCountGCD);

        int[][] blocksRows = new int[threadCountGCD][threadCountGCD];
        int[][] blocksColumns = new int[threadCountGCD][threadCountGCD];

        GetBlocks(threadCountGCD, step, blocksRows, blocksColumns);

        ArrayList<Future> threads = MultiplyBlocks(firstMatrix, secondMatrix, threadCountGCD, result, step, exec, blocksRows, blocksColumns);

        for (Future mapFuture : threads) {
            try {
                mapFuture.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        exec.shutdown();

        return result;
    }


    public void printResult(Matrix result, long time) {
        result.print();
        System.out.println("Time: " + time + " milliseconds with thread count: " + threadCount);
    }


    public int[][] run(boolean print) {
        if (print) System.out.println("\n Fox Algorithm:\n");
        long startTime = System.currentTimeMillis();
        final int[][] result = algorithm();
        long endTime = System.currentTimeMillis();
        final long time = endTime - startTime;
        if (print) {
            final Matrix resultMatrix = new Matrix(result);
            printResult(resultMatrix, time);
        }

        return result;
    }

}