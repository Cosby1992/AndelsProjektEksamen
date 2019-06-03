package dk.cosby.andelsprojekt.model;

/**
 * Denne klasse repræsentere end block.
 * En block består af en transaktion mellem to brugere samt et hash til forrige block.
 * samt en sværhedsgrad som den arver fra blockchainen
 *
 * @version 1.0
 * @author Cosby
 */

import android.util.Log;

import java.util.Date;

public class Block {

    private static final String TAG = "Block";

    private String blockHash;
    private String previousBlockHash;
    private Transaction transaktion;
    private String timestamp;

    /**
     * Sværhedsgraden bygges på hvor mange calculationer det kræver at opnå et hash
     * der begynder med sværhedsgradens antal nuller.
     */
    private int difficulty;

    /**
     * Det magiske tal repræsenterer det tal der skal ligges til det andet blockinformation
     * for at skabe et hash der starter med sværhedsgradens antal nuller.
     */
    private int magicNumber;

    //Constructor
    public Block(String previousBlockHash, Transaction transaktion) {
        this.previousBlockHash = previousBlockHash;
        this.transaktion = transaktion;
        calculateBlockDifficulty(transaktion);
        magicNumber = 0;
        timestamp = new Date().toString();

        //Giver blocken et midlertidigt hash mens den mines.
        //Bliver nok fjernet senere.
        blockHash = BlockUtil.udregnHash(previousBlockHash + transaktion.toString() + timestamp + magicNumber);
        Log.i(TAG, "Constructor: Block'ens hash er = " + blockHash);
        Log.i(TAG, "Et new Block objekt blev skabt!");
    }

    private void calculateBlockDifficulty(Transaction transaction){
        double amount = transaction.getAmount();
        if(amount < 50000){
            difficulty = 3;
        } else if (amount < 250000){
            difficulty = 4;
        } else if (amount < 1000000){
            difficulty = 5;
        } else difficulty = 6;
    }


    /**
     * Metoden returnere de informationer der er nødvendige for at udregne en blocks hash
     * @return
     */
    public String getInformationTilHash(){
        return previousBlockHash + transaktion.toString() + timestamp + magicNumber;
    }

    @Override
    public String toString() {
        return "Block{" +
                "blockHash='" + blockHash + '\'' +
                ", previousBlockHash='" + previousBlockHash + '\'' +
                ", transaktion=" + transaktion +
                ", timestamp='" + timestamp + '\'' +
                ", difficulty=" + difficulty +
                ", magicNumber=" + magicNumber +
                '}';
    }

    ////////////////////////////// getters and setters //////////////////////////////////////


    public String getBlockHash() {
        return blockHash;
    }

    public void setBlockHash(String blockHash) {
        this.blockHash = blockHash;
    }

    public String getPreviousBlockHash() {
        return previousBlockHash;
    }

    public void setPreviousBlockHash(String previousBlockHash) {
        this.previousBlockHash = previousBlockHash;
    }

    public Transaction getTransaktion() {
        return transaktion;
    }

    public void setTransaktion(Transaction transaktion) {
        this.transaktion = transaktion;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getMagicNumber() {
        return magicNumber;
    }

    public void setMagicNumber(int magicNumber) {
        this.magicNumber = magicNumber;
    }


}
