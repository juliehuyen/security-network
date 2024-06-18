package com.dauphine.security.tp2;

class State {
    private Block[][] bytes;

    public State() {
        this.bytes = new Block[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                this.bytes[i][j] = new Block(8);
            }
        }
    }

    public State(Block block) {
        this.bytes = new Block[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                this.bytes[i][j] = block.portion(16, i + j * 4);
            }
        }
    }

    public State(State toCopy) {
        this.bytes = new Block[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                this.bytes[i][j] = toCopy.bytes[i][j].clone();
            }
        }
    }

    public State(int[][] val) {
        this.bytes = new Block[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                this.bytes[i][j] = new Block(8, val[i][j]);
            }
        }
    }

    public State(Block[][] newBytes) {
        this.bytes = new Block[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                this.bytes[i][j] = newBytes[i][j].clone();
            }
        }
    }

    public State substitute(SBox sbox) {
        Block[][] newBytes = new Block[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                newBytes[i][j] = sbox.cypher(this.bytes[i][j]);
            }
        }
        return new State(newBytes);
    }

    public State shift() {
        Block[][] newBytes = new Block[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                newBytes[i][j] = this.bytes[i][(j + i) % 4];
            }
        }
        return new State(newBytes);
    }

    public State mult(State mixState) {
        Block[][] newBytes = new Block[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Block sum = new Block(8, 0);
                for (int k = 0; k < 4; k++) {
                    sum = sum.xOr(this.bytes[i][k].modularMult(mixState.bytes[k][j]));
                }
                newBytes[i][j] = sum;
            }
        }
        return new State(newBytes);
    }

    public State xOr(Key key) {
        Block[][] newBytes = new Block[4][4];
        if(key.toString().length() > 40) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    newBytes[i][j] = this.bytes[i][j].xOr(key.elmnt(i, j));
                }
            }
            return new State(newBytes);
        }
        else {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    newBytes[i][j] = this.bytes[i][j].xOr(key.getByte(i));
                }
            }
            return new State(newBytes);
        }
    }

    public Block block() {
        Block[] blocks = new Block[16];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                blocks[4 * j + i] = this.bytes[i][j];
            }
        }
        return new Block(blocks);
    }

    public String toString() {
        String s = "";
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                s += this.bytes[i][j] + " ";
            }
            s += "\n";
        }
        return s;
    }
}