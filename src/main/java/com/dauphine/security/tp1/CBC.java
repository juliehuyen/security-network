package com.dauphine.security.tp1;

public class CBC extends Mode {
    private Block IV;

    public CBC(TDEA cypherAlgo, Block IV) {
        super(cypherAlgo);
        this.IV = IV;
    }

    public Block[] enCypher(Block[] toEncypher) {
        return null;
    }

    public Block[] deCypher(Block[] toDecypher) {
        return null;
    }
}
