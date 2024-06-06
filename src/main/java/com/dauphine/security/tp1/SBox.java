package com.dauphine.security.tp1;

import static java.lang.Integer.toBinaryString;

public class SBox {
    private int[][] matrix;

    public SBox(int[][] matrix) {
        this.matrix = new int[matrix.length][];
        for (int i = 0; i < matrix.length; i++) {
            this.matrix[i] = new int[matrix[i].length];
            for (int j = 0; j < matrix[i].length; j++) {
                this.matrix[i][j] = matrix[i][j];
            }
        }
    }

    public Block cypher(Block toCypher) {
        //TODO
        return new Block(toBinaryString(matrix[toCypher.rowValue()][toCypher.columnValue()]));
    }
}
