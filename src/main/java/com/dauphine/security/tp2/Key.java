package com.dauphine.security.tp2;

class Key {
    private Block[] bytes;


    public Key() {
        this.bytes = new Block[4];
        for (int i = 0; i < 4; i++) {
            this.bytes[i] = new Block(32);
        }
    }

    public Key(Block block) {
        this.bytes = new Block[4];
        for (int i = 0; i < 4; i++) {
            this.bytes[i] = block.portion(4, i);
        }
    }

    public Key(Block[] blocks) {
        this.bytes = new Block[4];
        for (int i = 0; i < 4; i++) {
            this.bytes[i] = blocks[i].clone();
        }
    }

    public Key(Key toCopy) {
        this.bytes = new Block[4];
        for (int i = 0; i < 4; i++) {
            this.bytes[i] = toCopy.bytes[i].clone();
        }
    }

    public Block getByte(int i) {
        return this.bytes[i];
    }
    public Key[] genSubKeys(SBox sbox) {
        Key[] subKeys = new Key[11];
        subKeys[0] = this;

        for (int i = 1; i < 11; i++) {
            Block[] newBytes = new Block[4];
            newBytes[0] = subKeys[i - 1].bytes[3].g(sbox, new Block(8, i)).xOr(subKeys[i - 1].bytes[0]);

            for (int j = 1; j < 4; j++) {
                newBytes[j] = newBytes[j - 1].xOr(subKeys[i - 1].bytes[j]);
            }

            subKeys[i] = new Key(newBytes);
        }

        return subKeys;
    }

    public Block elmnt(int i, int j) {
        return this.bytes[i].portion(4, j);
    }

    public String toString() {
        String s = "";
        for (int i = 0; i < this.bytes.length; i++) {
            s += this.bytes[i].toString() + " ";
        }
        return s;
    }

    public String toStringH() {
        String s = "";
        for (int i = 0; i < this.bytes.length; i++) {
            s += this.bytes[i].toStringH() + " ";
        }
        return s;
    }
}