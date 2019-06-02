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
    private String forrigeHash;
    private Transaction transaktion;
    private String timestamp;

    /**
     * Sværhedsgraden bygges på hvor mange calculationer det kræver at opnå et hash
     * der begynder med sværhedsgradens antal nuller.
     */
    private int svaerhedsgrad;

    /**
     * Det magiske tal repræsenterer det tal der skal ligges til det andet blockinformation
     * for at skabe et hash der starter med sværhedsgradens antal nuller.
     */
    private int magiskTal;

    //Constructor
    public Block(String forrigeHash, Transaction transaktion) {
        this.forrigeHash = forrigeHash;
        this.transaktion = transaktion;
        calculateBlockDifficulty(transaktion);
        magiskTal = 0;
        timestamp = new Date().toString();

        //Giver blocken et midlertidigt hash mens den mines.
        //Bliver nok fjernet senere.
        blockHash = BlockUtil.udregnHash(forrigeHash + transaktion.toString() + timestamp + magiskTal);
        Log.i(TAG, "Constructor: Block'ens hash er = " + blockHash);
        Log.i(TAG, "Et new Block objekt blev skabt!");
    }

    private void calculateBlockDifficulty(Transaction transaction){
        double amount = transaction.getBeloeb();
        if(amount < 50000){
            svaerhedsgrad = 3;
        } else if (amount < 250000){
            svaerhedsgrad = 4;
        } else if (amount < 1000000){
            svaerhedsgrad = 5;
        } else svaerhedsgrad = 6;
    }


    /**
     * Metoden returnere de informationer der er nødvendige for at udregne en blocks hash
     * @return
     */
    public String getInformationTilHash(){
        return forrigeHash + transaktion.toString() + timestamp + magiskTal;
    }

    @Override
    public String toString() {
        return "Block{" +
                "blockHash='" + blockHash + '\'' +
                ", forrigeHash='" + forrigeHash + '\'' +
                ", transaktion=" + transaktion +
                ", timestamp='" + timestamp + '\'' +
                ", svaerhedsgrad=" + svaerhedsgrad +
                ", magiskTal=" + magiskTal +
                '}';
    }

    ////////////////////////////// getters and setters //////////////////////////////////////


    public String getBlockHash() {
        return blockHash;
    }

    public void setBlockHash(String blockHash) {
        this.blockHash = blockHash;
    }

    public String getForrigeHash() {
        return forrigeHash;
    }

    public void setForrigeHash(String forrigeHash) {
        this.forrigeHash = forrigeHash;
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

    public int getSvaerhedsgrad() {
        return svaerhedsgrad;
    }

    public void setSvaerhedsgrad(int svaerhedsgrad) {
        this.svaerhedsgrad = svaerhedsgrad;
    }

    public int getMagiskTal() {
        return magiskTal;
    }

    public void setMagiskTal(int magiskTal) {
        this.magiskTal = magiskTal;
    }
}
