package com.dauphine.security.tp4;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;

public class Test {
    private static final int BLOCK_SIZE = 16;

    public static void main(String[] args) {
        String key = "2B7E151628AED2A6ABF7158809CF4F3C";
        String key2 = "404142434445464748494A4B4C4D4E4F";
        String message = "6BC1BEE22E409F96E93D7E117393172A";
        byte[] irrPoly = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, (byte) 0x87};

        byte[] keyBytes = hexStringToByteArray(key);
        byte[] messageBytes = hexStringToByteArray(message);

        byte[] mac = generateCMAC(keyBytes, messageBytes, irrPoly);
        System.out.println("MAC: " + byteArrayToHex(mac));
    }

    public static byte[] generateCMAC(byte[] key, byte[] message, byte[] irrPoly) {
        try {
            // Generate subkeys
            byte[] k1 = generateSubkey(key, irrPoly);
            byte[] k2 = generateSubkey(k1, irrPoly);

            // Pad the message
            byte[] paddedMessage = padMessage(message);

            // XOR last block with K1 or K2
            int n = paddedMessage.length / BLOCK_SIZE;
            byte[] lastBlock = Arrays.copyOfRange(paddedMessage, (n - 1) * BLOCK_SIZE, n * BLOCK_SIZE);
            if (n == 0 || message.length % BLOCK_SIZE != 0) {
                lastBlock = xor(lastBlock, k2);
            } else {
                lastBlock = xor(lastBlock, k1);
            }

            // Encrypt the blocks
            byte[] mac = new byte[BLOCK_SIZE];
            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
            SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            for (int i = 0; i < n - 1; i++) {
                byte[] block = Arrays.copyOfRange(paddedMessage, i * BLOCK_SIZE, (i + 1) * BLOCK_SIZE);
                mac = cipher.doFinal(xor(mac, block));
            }
            mac = cipher.doFinal(xor(mac, lastBlock));

            return mac;
        } catch (Exception e) {
            throw new RuntimeException("Error generating CMAC", e);
        }
    }

    private static byte[] generateSubkey(byte[] key, byte[] irrPoly) throws Exception {
        byte[] L = encryptBlock(key, new byte[BLOCK_SIZE]);
        if ((L[0] & 0x80) == 0) {
            return shiftLeft(L);
        } else {
            return xor(shiftLeft(L), irrPoly);
        }
    }

    private static byte[] encryptBlock(byte[] key, byte[] block) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        return cipher.doFinal(block);
    }

    private static byte[] padMessage(byte[] message) {
        int paddedLength = ((message.length + BLOCK_SIZE - 1) / BLOCK_SIZE) * BLOCK_SIZE;
        return Arrays.copyOf(message, paddedLength);
    }

    private static byte[] xor(byte[] a, byte[] b) {
        byte[] result = new byte[a.length];
        for (int i = 0; i < a.length; i++) {
            result[i] = (byte) (a[i] ^ b[i]);
        }
        return result;
    }

    private static byte[] shiftLeft(byte[] input) {
        byte[] output = new byte[input.length];
        int carry = 0;
        for (int i = input.length - 1; i >= 0; i--) {
            int current = input[i] & 0xFF;
            output[i] = (byte) ((current << 1) | carry);
            carry = (current & 0x80) == 0x80 ? 1 : 0;
        }
        return output;
    }

    private static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

    private static String byteArrayToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }
}