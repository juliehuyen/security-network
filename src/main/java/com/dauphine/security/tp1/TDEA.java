package com.dauphine.security.tp1;

public class TDEA {
    private DES[] desAlgo;

    public TDEA(Block key) {
        this.desAlgo = new DES[3];
        for (int i = 0; i < 3; i++) {
            this.desAlgo[i] = new DES(key.portion(3, i));
        }
    }

    public Block cypher(Block plaintext) {
        //TODO
        return null;
    }

    public Block deCypher(Block cyphertext) {
        //TODO
        return null;
    }

    public static void main(String[] args) {
        String plaintext = "0000001001000110100010101100111011101100101010000110010000100000";
        String key = "0000111100010101011100011100100101000111110110011110100001011001";
        Block plaintextBlock = new Block(plaintext), keyBlock = new Block(key);
        DES des = new DES(keyBlock);
        Block cypherBlock = des.cypher(plaintextBlock);
        System.out.println(cypherBlock);
        Block deCypherBlock = des.deCypher(cypherBlock);
        System.out.println(deCypherBlock);
        System.out.println(deCypherBlock.toString().compareTo(plaintext));
        System.out.println("==========");
        String key2 = "000011110001010101110001110010010100011111011001111010000101100100101011100001110011100011011001110011101111001110101100000100111010110101000100010000110100000001100101111100110111101011001011";
        Block keyBlock2 = new Block(key2);
        TDEA tdea = new TDEA(keyBlock2);
        Block cypherBlock2 = tdea.cypher(plaintextBlock);
        System.out.println(cypherBlock2);
        System.out.println("==========");
        Block deCypherBlock2 = tdea.deCypher(cypherBlock2);
        System.out.println(deCypherBlock2);
        System.out.println(deCypherBlock2.toString().compareTo(plaintext));
        System.out.println("==========");
        String poeme = "Le temps a laiss� son manteau\r\n"
                + "De vent, de froidure et de pluie,\r\n"
                + "Et s�est v�tu de broderie,\r\n"
                + "De soleil luisant, clair et beau.\r\n"
                + "\r\n"
                + "Il n�y a b�te ni oiseau\r\n"
                + "Qu�en son jargon ne chante ou crie :\r\n"
                + "Le temps a laiss� son manteau\r\n"
                + "De vent, de froidure et de pluie.\r\n"
                + "\r\n"
                + "Rivi�re, fontaine et ruisseau\r\n"
                + "Portent en livr�e jolie,\r\n"
                + "Gouttes d�argent d�orf�vrerie ;\r\n"
                + "Chacun s�habille de nouveau :\r\n"
                + "Le temps a laiss� son manteau.";
        Block[] blocks = Block.stringToBlock(poeme, 8);
        CBC cbc = new CBC(tdea, cypherBlock2);
        Block[] blocksCypher = cbc.enCypher(blocks);
        System.out.println(Block.blockToString(cbc.deCypher(blocksCypher)));
        System.out.println("==========");
        String poeme2 = "Mignonne, allons voir si la rose\r\n"
                + "Qui ce matin avait d�close\r\n"
                + "Sa robe de pourpre au Soleil,\r\n"
                + "A point perdu cette vespr�e\r\n"
                + "Les plis de sa robe pourpr�e,\r\n"
                + "Et son teint au v�tre pareil.\r\n"
                + "\r\n"
                + "Las ! voyez comme en peu d'espace,\r\n"
                + "Mignonne, elle a dessus la place,\r\n"
                + "Las ! las ! ses beaut�s laiss� choir !\r\n"
                + "� vraiment mar�tre Nature,\r\n"
                + "Puisqu'une telle fleur ne dure\r\n"
                + "Que du matin jusques au soir !\r\n"
                + "\r\n"
                + "Donc, si vous me croyez, mignonne,\r\n"
                + "Tandis que votre �ge fleuronne\r\n"
                + "En sa plus verte nouveaut�,\r\n"
                + "Cueillez, cueillez votre jeunesse :\r\n"
                + "Comme � cette fleur, la vieillesse\r\n"
                + "Fera ternir votre beaut�.";
        Block[] blocks2 = Block.stringToBlock(poeme2, 8);
        OFB ofb = new OFB(tdea, cypherBlock2);
        blocksCypher = ofb.enCypher(blocks2);
        System.out.println(Block.blockToString(ofb.deCypher(blocksCypher)));
        System.out.println("==========");
        String poeme3 = "Demain, d�s l'aube, � l'heure o� blanchit la campagne,\r\n"
                + "Je partirai. Vois-tu, je sais que tu m'attends.\r\n"
                + "J'irai par la for�t, j'irai par la montagne.\r\n"
                + "Je ne puis demeurer loin de toi plus longtemps.\r\n"
                + "\r\n"
                + "Je marcherai les yeux fix�s sur mes pens�es,\r\n"
                + "Sans rien voir au dehors, sans entendre aucun bruit,\r\n"
                + "Seul, inconnu, le dos courb�, les mains crois�es,\r\n"
                + "Triste, et le jour pour moi sera comme la nuit.\r\n"
                + "\r\n"
                + "Je ne regarderai ni l'or du soir qui tombe,\r\n"
                + "Ni les voiles au loin descendant vers Harfleur,\r\n"
                + "Et quand j'arriverai, je mettrai sur ta tombe\r\n"
                + "Un bouquet de houx vert et de bruy�re en fleur.";
        Block[] blocks3 = Block.stringToBlock(poeme3, 8);
        CTR ctr = new CTR(tdea, cypherBlock2);
        blocksCypher = ctr.enCypher(blocks3);
        System.out.println(Block.blockToString(ctr.deCypher(blocksCypher)));
    }
}

