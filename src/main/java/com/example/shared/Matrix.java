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

    public Matrix(ArrayList<ArrayList<Integer>> arrayLists) {
        this.rows = arrayLists.size();
        this.columns = arrayLists.get(0).size();
        this.matrix = new int[columns][rows];
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                this.matrix[i][j] = arrayLists.get(i).get(j);
            }
        }
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
                this.matrix[i][j] = (int) (Math.random() * 10);
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