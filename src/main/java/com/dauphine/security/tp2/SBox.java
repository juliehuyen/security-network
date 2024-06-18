package com.dauphine.security.tp2;

import static java.lang.Integer.toBinaryString;

class SBox {
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
        int row = toCypher.rowValue();
        int value;
        if (toCypher.block.length == 4) {
            value = this.matrix[row][0];
        } else {
            int column = toCypher.columnValue();
            value = this.matrix[row][column];
        }
        return new Block(8, value);
    }
}
