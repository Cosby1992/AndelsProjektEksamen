package dk.cosby.andelsprojekt.model;

/**
 *  Denne klasse indeholder metoder til at udregne hashes samt at mine blocks
 *
 * @version 1.0
 * @author Cosby
 */

import android.util.Log;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Date;

public class BlockUtil {

    private static final String TAG = "BlockUtil";

    /**
     * Denne metode tilføjer SHA-256 hashing til en String og returnere resultatet som string.
      * @param input String der skal hashes.
     * @return hashed String
     */
    public static String udregnHash(String input){

        try {
            //MessageDigest objekt initialiseret med en instans af SHA-256
            //Denne benyttes til at hash'e et byte-array.
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

            //byte array'et hashes
            byte[] hashBytes = messageDigest.digest(input.getBytes(StandardCharsets.UTF_8));

            //StringBuffer instans der kommer til at indeholde den hexadecimale værdi af hashbytes.
            StringBuffer hexStringBuffer = new StringBuffer();

            //Iteration gennem hashBytes der konverterer en byte til en hexadecimal værdi og
            //konkatenere denne på hexStringBuffer.
            for (int i = 0; i < hashBytes.length; i++) {
                String hex = Integer.toHexString(0xff & hashBytes[i]);
                if(hex.length() == 1) hexStringBuffer.append('0');
                hexStringBuffer.append(hex);
            }

            return hexStringBuffer.toString();
        } catch(Exception e) {
            //Gik væk fra koden herunder da det vill kræve at vi håndterede det returnerede nul ved fejl
            //istedet for at smide en RuntimeException.
//            Log.e(TAG, "Metoden: 'udregnHash' fejlede.");
//            e.printStackTrace();
//            return "0";

            Log.e(TAG, "udregnHash(String): fejlede.");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Det magiske tal repræsenterer det tal der skal ligges til det andet blockinformation
     * for at skabe et hash der starter med sværhedsgradens antal nuller.
     */
    /**
     * For hver gang et hash ikke starter med sværhedsgradens antal nuller, beregnes et nyt hash
     * men nu med det magiske tal incremented.
     * @param block den block der skal mines.
     */
    public static void mineBlock(Block block) {
        int magicNumber = block.getMagicNumber();
        String blockHash = block.getBlockHash();
        while(!validerHashMedSværhedsgrad(blockHash, block.getDifficulty())) {

            magicNumber++;
            blockHash= udregnHash(block.getInformationTilHash());
            block.setMagicNumber(magicNumber);
            block.setBlockHash(blockHash);

        }

        Log.i(TAG, "mineBlock(Block): success \n" +
                "Udregnet hash = " + block.getBlockHash() + "\n" +
                "Magisk Tal = " + block.getMagicNumber() + "\n" +
                "Block Skabt = " + block.getTimestamp() + "\n" +
                "Mining sluttet = " + new Date().toString());


        System.out.println("Block Mined!!! : " + blockHash);
    }

    /**
     * Denne metode bruges til at checke om et hash overholder en given sværhedsgrad.
     * Sværhedsgraden fortæller hvor mange nuller der skal være i starten af et hash.
     * @param hash hash der skal checkes.
     * @param difficulty sværhedgrad der skal overholdes.
     * @return true hvis hash'et overholder sværhedsgraden, ellers false.
     */
    public static boolean validerHashMedSværhedsgrad(String hash, int difficulty){

        StringBuilder startString = new StringBuilder();

        for (int i = 0; i < difficulty; i++) {
            startString.append("0");
        }

        return hash.startsWith(startString.toString());
    }

}
