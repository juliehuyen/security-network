package com.dauphine.security.tp3;

public class SHA3 {
    static int[][] nbrRightShift = {{0, 1, 62, 28, 27}, {36, 44, 6, 55, 20}, {3, 10, 43, 25, 39}, {41, 45, 15, 21, 8}, {18, 2, 61, 56, 14}};
    static int[][][] oldPos = {{{0, 0}, {1, 1}, {2, 2}, {3, 3}, {4, 4}}, {{0, 3}, {1, 4}, {2, 0}, {3, 1}, {4, 2}}, {{0, 1}, {1, 2}, {2, 3}, {3, 4}, {4, 0}}, {{0, 4}, {1, 0}, {2, 1}, {3, 2}, {4, 3}}, {{0, 2}, {1, 3}, {2, 4}, {3, 0}, {4, 1}}};
    static int b = 1600;
    static String[] rc = {"0000000000000001", "0000000000008082", "800000000000808A", "8000000080008000", "000000000000808B", "0000000080000001", "8000000080008081", "8000000000008009", "000000000000008A", "0000000000000088", "0000000080008009", "000000008000000A", "000000008000808B", "800000000000008B", "8000000000008089", "8000000000008003", "8000000000008002", "8000000000000080", "000000000000800A", "800000008000000A", "8000000080008081", "8000000000008080", "0000000080000001", "8000000080008008"};
    private int bitrate, outputLength;

    public SHA3(int bitrate, int outputLength) {
        this.bitrate = bitrate;
        this.outputLength = outputLength;
    }

    public State f(State state) {
        State res = new State(state);
        State tmp = new State();
        int cpt = 1;
        for (String s : rc) {
            System.out.println("Round " + cpt++ + ":");
            tmp.setLane00(new Block(s));
            System.out.println("BE theta : \n" + res);
            res = res.theta();
            System.out.println("After theta : \n" + res.toString());

            res = res.rho(nbrRightShift);
            System.out.println("After rho : \n" + res.toString());
            res = res.pi(oldPos);
            System.out.println("After pi : \n" + res.toString());
            res = res.chi();
            System.out.println("After chi : \n" + res.toString());
            res = res.iota(tmp.block());
            System.out.println("After iota : \n" + res.toString());
        }
        return res;
    }

    public Block hash(Block input) {
        Block[] inputs = new Block[input.block.length / this.bitrate + 1];
        for (int i = 0; i < input.block.length / this.bitrate; i++) {
            Block[] blockPair = new Block[2];
            blockPair[0] = input.subBlock(i * this.bitrate, (i + 1) * this.bitrate);
            blockPair[1] = new Block(SHA3.b - this.bitrate);
            inputs[i] = new Block(blockPair);
        }
        Block[] lastInputs = new Block[3];
        lastInputs[0] = input.subBlock(this.bitrate * (input.block.length / this.bitrate), input.block.length);
        lastInputs[1] = Block.pad10x1(this.bitrate - (input.block.length % this.bitrate));
        lastInputs[2] = new Block(SHA3.b - this.bitrate);
        inputs[inputs.length - 1] = new Block(lastInputs);
        State state = new State();
        for (int i = 0; i < inputs.length; i++) {
            state = state.xOr(inputs[i]);
            state = this.f(state);
        }
        Block[] output = new Block[this.outputLength / this.bitrate + (this.outputLength % this.bitrate > 0 ? 1 : 0)];
        output[0] = state.block().subBlock(0, this.bitrate);
        for (int i = 1; i < output.length; i++) {
            state = this.f(state);
            output[i] = state.block().subBlock(0, this.bitrate);
        }
        return (new Block(output)).subBlock(0, this.outputLength);
    }

    public static void main(String[] args) {
        String emptyString = "";
        SHA3 sha3 = new SHA3(576, 512);
        System.out.println(sha3.hash(new Block(emptyString)).blockToStringI());
        System.out.println("==========");
        String thirtyBitsExample = "110010100001101011011110100110";
        System.out.println(sha3.hash(new Block(thirtyBitsExample)).blockToStringI());
        System.out.println("==========");
//        String poeme = "C'est un trou de verdure où chante une rivière,\r\n"
//                + "Accrochant follement aux herbes des haillons\r\n"
//                + "D'argent ; où le soleil, de la montagne fière,\r\n"
//                + "Luit : c'est un petit val qui mousse de rayons.\r\n"
//                + "\r\n"
//                + "Un soldat jeune, bouche ouverte, tête nue,\r\n"
//                + "Et la nuque baignant dans le frais cresson bleu,\r\n"
//                + "Dort ; il est étendu dans l'herbe, sous la nue,\r\n"
//                + "Pâle dans son lit vert où la lumière pleut.\r\n"
//                + "\r\n"
//                + "Les pieds dans les glaïeuls, il dort. Souriant comme\r\n"
//                + "Sourirait un enfant malade, il fait un somme :\r\n"
//                + "Nature, berce-le chaudement : il a froid.\r\n"
//                + "\r\n"
//                + "Les parfums ne font pas frissonner sa narine ;\r\n"
//                + "Il dort dans le soleil, la main sur sa poitrine,\r\n"
//                + "Tranquille. Il a deux trous rouges au côté droit.";
        String poeme = "";
        Block[] blocks = Block.stringToBlock(poeme, 8);
        System.out.println(sha3.hash(new Block(blocks)).blockToStringI());
    }
}