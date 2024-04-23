package com.dauphine.security.tp1;

public abstract class Mode {
    protected TDEA cypherAlgo;

    public Mode(TDEA cypherAlgo) {
        this.cypherAlgo = cypherAlgo;
    }

    public abstract Block[] enCypher(Block[] toEncypher);

    public abstract Block[] deCypher(Block[] toDecypher);
}
