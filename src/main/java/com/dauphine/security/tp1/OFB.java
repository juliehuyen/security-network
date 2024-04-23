package com.dauphine.security.tp1;

public class OFB extends Mode {
    private Block Nonce;

    public OFB(TDEA cypherAlgo, Block Nonce) {
        super(cypherAlgo);
        this.Nonce = Nonce;
    }

    public Block[] enCypher(Block[] toEncypher) {
        return null;
    }

    public Block[] deCypher(Block[] toDecypher) {
        return null;
    }
}
