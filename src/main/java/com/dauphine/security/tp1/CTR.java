package com.dauphine.security.tp1;

public class CTR extends Mode {
    private Block counter;

    public CTR(TDEA cypherAlgo, Block counter) {
        super(cypherAlgo);
        this.counter = counter;
    }

    public Block[] enCypher(Block[] toEncypher) {
        return null;
    }

    public Block[] deCypher(Block[] toDecypher) {
        return null;
    }
}