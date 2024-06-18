package com.dauphine.security.tp3;

class Block implements Cloneable{
    boolean block[];

    public Block(int taille) {
        this.block=new boolean[taille];
    }

    public Block(boolean[] s){
        this(s.length);
        for(int i = 0; i < s.length; i++) {
            this.block[i] = s[i];
        }
    }

    public Block(String s){
        this(s.length());
        for(int i = 0; i < s.length(); i++) {
            if(s.charAt(i)=='0')
                this.block[i]=false;
            else {
                if(s.charAt(i)=='1') {
                    this.block[i]=true;
                }
                else {
                    System.out.println("Block: bit "+i+" has value "+s.charAt(i)+" different from 0 or 1");
                }
            }
        }
    }

    public Block(Block[] blockList) {
        int taille = 0, cpt = 0;
        for(int i = 0; i < blockList.length; i++) {
            taille += blockList[i].block.length;
        }
        this.block=new boolean[taille];
        for(int i = 0; i < blockList.length; i++) {
            for(int j = 0; j < blockList[i].block.length; j++) {
                this.block[cpt++] = blockList[i].block[j];
            }
        }
    }

    public static Block[] stringToBlock(String chaine, int size) {
        Block[] toReturn = new Block[chaine.length() / size];
        for(int i = 0; i < toReturn.length; i++) {
            Block[] temp = new Block[size];
            for(int j = 0; j < size; j++) {
                Block octetBlock = new Block(8);
                char octet = chaine.charAt(i * 8 + j);
                for(int k = 0; k < 8; k++) {
                    octetBlock.block[7 - k] = (octet % 2 == 1);
                    octet = (char)(octet / 2);
                }
                temp[j] = octetBlock;
            }
            toReturn[i] = new Block(temp);
        }
        return toReturn;
    }

    public static Block stringToBlockHI(String chaine) {
        Block toReturn = new Block(4 * chaine.length());
        for(int j = 0; j < chaine.length(); j++) {
            char hex = chaine.charAt(chaine.length()-j-1);
            int val;
            if(hex >= '0' && hex <= '9') {
                val = hex - '0';
            }
            else {
                val = hex - 'A' + 10;
            }
            for(int k = 0; k < 4; k++) {
                toReturn.block[4 * j + k] = (val % 2 == 1);
                val /= 2;
            }
        }
        return toReturn;
    }

    public String blockToStringI() {
        String toReturn = "";
        for(int j = 0; j < this.block.length / 8; j++) {
            char val = 0, pow2 = 1;
            for(int k = 4; k < 8; k++) {
                if(this.block[j * 8 + k]) {
                    val += pow2;
                }
                pow2 *= 2;
            }
            toReturn += ((char)(val > 9? 'A' + val - 10: val + '0'));
            val = 0;
            pow2 = 1;
            for(int k = 0; k < 4; k++) {
                if(this.block[j * 8 + k]) {
                    val += pow2;
                }
                pow2 *= 2;
            }
            toReturn += ((char)(val > 9? 'A' + val - 10: val + '0'));
        }
        return toReturn;
    }

    public static Block pad10x1(int taille) {
        Block toReturn = new Block(taille);
        toReturn.block[1] = true;
        toReturn.block[2] = true;
        toReturn.block[taille - 1] =true;
        return toReturn;
    }

    public Block clone() {
        Block clone=new Block(this.block);
        return clone;
    }


    public String toString() {
        String result="";
        for(int i = 0; i < this.block.length; i++) {
            result += this.block[i]? "1": "0";
        }
        return result;
    }

    public Block portion(int nbrPortion, int index) {
        boolean[] newBlock = new boolean[this.block.length / nbrPortion];
        for(int i = 0; i < newBlock.length; i++) {
            newBlock[i] = this.block[i + index * newBlock.length];
        }
        return new Block(newBlock);
    }

    public Block subBlock(int begin, int end) {
        boolean[] newBlock = new boolean[end - begin];
        for(int i = 0; i < newBlock.length; i++) {
            newBlock[i] = this.block[begin + i];
        }
        return new Block(newBlock);
    }

    public Block xOr(Block secondMember) {
        boolean[] result = this.block.clone();
        for (int i = 0; i < this.block.length; i++) {
            result[i] = this.block[i] ^ secondMember.block[i];
        }
        return new Block(result);
    }

    public Block rightShift(int nbrShift) {
        boolean[] result = this.block.clone();
        for (int i = this.block.length - 1; i > 0 ; i--) {
            result[i] = this.block[(i - 1) % this.block.length];
        }
        result[this.block.length - 1] = false;
        return new Block(result);
    }

    public Block notAnd(Block secArg) {
        boolean[] result = this.block.clone();
        for (int i = 0; i < this.block.length; i++) {
            result[i] = !(this.block[i] && secArg.block[i]);
        }
        return new Block(result);
    }

}