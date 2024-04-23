package com.dauphine.security.tp1;

public class SBoxes {
    SBox[] sboxes;

    public SBoxes(int[][][] matrices) {
        this.sboxes = new SBox[matrices.length];
        for (int i = 0; i < matrices.length; i++) {
            this.sboxes[i] = new SBox(matrices[i]);
        }
    }

    public Block cypher(Block toCypher) {
        Block[] subBlocks = new Block[this.sboxes.length];
        for (int i = 0; i < this.sboxes.length; i++) {
            subBlocks[i] = this.sboxes[i].cypher(toCypher.portion(this.sboxes.length, i));
        }
        return new Block(subBlocks);
    }
}
