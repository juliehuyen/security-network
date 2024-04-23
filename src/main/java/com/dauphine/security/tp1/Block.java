package com.dauphine.security.tp1;

public class Block implements Cloneable {
    boolean[] block;

    public Block(int taille) {
        this.block = new boolean[taille];
    }

    public Block(boolean[] s) {
        this(s.length);
        for (int i = 0; i < s.length; i++) {
            this.block[i] = s[i];
        }
    }

    public Block(String s) {
        this(s.length());
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '0')
                this.block[i] = false;
            else {
                if (s.charAt(i) == '1') {
                    this.block[i] = true;
                } else {
                    System.out.println("Block: bit " + i + " has value " + s.charAt(i) + " different from 0 or 1");
                }
            }
        }
    }

    public Block(Block[] blockList) {
        int taille = 0, cpt = 0;
        for (int i = 0; i < blockList.length; i++) {
            taille += blockList[i].block.length;
        }
        this.block = new boolean[taille];
        for (int i = 0; i < blockList.length; i++) {
            for (int j = 0; j < blockList[i].block.length; j++) {
                this.block[cpt++] = blockList[i].block[j];
            }
        }
    }

    public static Block[] stringToBlock(String chaine, int size) {
        Block[] toReturn = new Block[chaine.length() / size];
        for (int i = 0; i < toReturn.length; i++) {
            Block[] temp = new Block[size];
            for (int j = 0; j < size; j++) {
                Block octetBlock = new Block(8);
                char octet = chaine.charAt(i * 8 + j);
                for (int k = 0; k < 8; k++) {
                    octetBlock.block[7 - k] = (octet % 2 == 1);
                    octet = (char) (octet / 2);
                }
                temp[j] = octetBlock;
            }
            toReturn[i] = new Block(temp);
        }
        return toReturn;
    }

    public static String blockToString(Block[] blocks) {
        String toReturn = "";
        for (int i = 0; i < blocks.length; i++) {
            Block block = blocks[i];
            for (int j = 0; j < block.block.length / 8; j++) {
                char val = 0, pow2 = 1;
                for (int k = 0; k < 8; k++) {
                    if (block.block[j * 8 + 7 - k]) {
                        val += pow2;
                    }
                    pow2 *= 2;
                }
                toReturn += val;
            }
        }
        return toReturn;
    }

    public Block clone() {
        Block clone = new Block(this.block);
        return clone;
    }


    public String toString() {
        String result = "";
        for (int i = 0; i < this.block.length; i++) {
            result += this.block[i] ? "1" : "0";
        }
        return result;
    }

    public Block portion(int nbrPortion, int index) {
        boolean[] newBlock = new boolean[this.block.length / nbrPortion];
        for (int i = 0; i < newBlock.length; i++) {
            newBlock[i] = this.block[i + index * newBlock.length];
        }
        return new Block(newBlock);
    }

    public Block permutation(int[] permutation) {
        //TODO
        return null;
    }

    public Block xOr(Block secondMember) {
        //TODO
        return null;
    }

    public Block leftShift(int nbrShift) {
        //TODO
        return null;
    }

    public int rowValue() {
        return (this.block[0] ? 2 : 0) + (this.block[5] ? 1 : 0);
    }

    public int columnValue() {
        return (this.block[1] ? 8 : 0) + (this.block[2] ? 4 : 0) + (this.block[3] ? 2 : 0) + (this.block[4] ? 1 : 0);
    }

    public Block next() {
        Block toReturn = new Block(this.block);
        int index = this.block.length;
        boolean stop = false;
        while (index > 0 && !stop) {
            index--;
            if (toReturn.block[index]) {
                toReturn.block[index] = false;
            } else {
                toReturn.block[index] = true;
                stop = true;
            }
        }
        return toReturn;
    }
}