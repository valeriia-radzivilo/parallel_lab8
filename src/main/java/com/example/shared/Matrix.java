package com.example.shared;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class Matrix implements Serializable {
    private final int rows;
    private final int columns;
    public int[][] matrix;

    public Matrix(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.matrix = new int[columns][rows];
    }

    public Matrix(int[][] matrix) {
        this.rows = matrix.length;
        this.columns = matrix[0].length;
        this.matrix = matrix;
    }

    public Matrix(ArrayList arrayLists) {
        if (arrayLists.get(0) instanceof ArrayList) {
            this.rows = arrayLists.size();
            this.columns = ((ArrayList<?>) arrayLists.get(0)).size();
            this.matrix = new int[columns][rows];
            for (int i = 0; i < columns; i++) {
                for (int j = 0; j < rows; j++) {
                    final ArrayList<Integer> row = (ArrayList<Integer>) arrayLists.get(i);
                    this.matrix[i][j] = row.get(j);
                }
            }
        } else if (arrayLists.get(0) instanceof Integer) {
            this.rows = (int) Math.sqrt(arrayLists.size());
            this.columns = (int) Math.sqrt(arrayLists.size());
            this.matrix = new int[columns][rows];
            for (int i = 0; i < columns; i++) {
                for (int j = 0; j < rows; j++) {
                    this.matrix[i][j] = (int) arrayLists.get(i * rows + j);
                }
            }

        } else {
            throw new IllegalArgumentException("Invalid input");
        }

    }

    public Matrix fromFlatList(ArrayList<Integer> matrix) {
        int rows = (int) Math.sqrt(matrix.size());
        int columns = (int) Math.sqrt(matrix.size());
        int[][] matrixArray = new int[columns][rows];
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                matrixArray[i][j] = matrix.get(i * rows + j);
            }
        }
        return new Matrix(matrixArray);
    }

    public void print() {
        for (int i = 0; i < this.columns; i++) {
            for (int j = 0; j < this.rows; j++) {
                System.out.printf(this.matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public int length() {
        return this.matrix.length;
    }


    public int[] getRow(int index) {
        return this.matrix[index];
    }


    public void generateRandomMatrix() {
        for (int i = 0; i < this.columns; i++) {
            for (int j = 0; j < this.rows; j++) {
                this.matrix[i][j] = (int) (Math.random() * 10) + 1;
            }
        }
    }

    public int getRowsLength() {
        return this.rows;
    }

    public int getColumnsLength() {
        return this.columns;
    }


    @Serial
    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {

        this.matrix = (int[][]) in.readObject();

    }


    @Serial
    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        out.writeObject(this.matrix);
    }

}