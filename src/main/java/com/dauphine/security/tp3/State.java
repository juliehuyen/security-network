package com.dauphine.security.tp3;

import java.util.ArrayList;
import java.util.List;

class State {
    private Block[][] lanes;

    public State() {
        this.lanes = new Block[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                this.lanes[i][j] = new Block(64);
            }
        }
    }

    public State(Block block) {
        this.lanes = new Block[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                this.lanes[i][j] = block.portion(25, i * 5 + j);
            }
        }
    }

    public State(State toCopy) {
        this.lanes = new Block[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                this.lanes[i][j] = toCopy.lanes[i][j].clone();
            }
        }
    }

    public State theta() {
        State state = new State();

        List<Block> c = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            c.add(this.lanes[i][0].xOr(this.lanes[i][1]).xOr(this.lanes[i][2]).xOr(this.lanes[i][3]).xOr(this.lanes[i][4]));
        }

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                state.lanes[i][j] = this.lanes[i][j].xOr(c.get((i + 4) % 5).xOr(c.get((i + 1) % 5)).rightShift(1));
            }
        }

        return state;
    }

    public State rho(int[][] nbrShift) {
        State state = new State();

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                state.lanes[i][j] = this.lanes[i][j].rightShift(nbrShift[i][j]);
            }
        }

        return state;
    }

    public State pi(int[][][] oldPos) {
        State state = new State();

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                int x = oldPos[i][j][0];
                int y = oldPos[i][j][1];
                state.lanes[i][j] = this.lanes[x][y].clone();
            }
        }

        return state;
    }

    public State chi() {
        State state = new State();

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                state.lanes[i][j] = this.lanes[i][j].xOr(this.lanes[i][(j + 1) % 5].notAnd(this.lanes[i][(j + 2) % 5]));
            }
        }

        return state;
    }

    public State iota(Block rc) {
        State state = new State(this);
        state.lanes[0][0] = this.lanes[0][0].xOr(rc);
        return state;
    }


    public State xOr(Block block) {
        State res = new State();
        State state = new State(block);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                res.lanes[i][j] = this.lanes[i][j].xOr(state.lanes[i][j]);
            }
        }
        return res;
    }

    public void setLane00(Block block) {
        this.lanes[0][0] = block;
    }

    public Block block() {
        Block[] blocks = new Block[25];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                blocks[5 * i + j] = this.lanes[i][j];
            }
        }
        return new Block(blocks);
    }

    public String toString() {
        String s = "";
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                s += this.lanes[i][j].blockToStringI() + " ";
            }
            s += "\n";
        }
        return s;
    }
}