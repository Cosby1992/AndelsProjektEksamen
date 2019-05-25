package dk.cosby.andelsprojekt.view;

/**
 * Indtil videre kun en test klasse.
 * Den opretter en block med de nødvendige argumenter og miner denne og dernæst add'er den
 * til blockchainen.
 *
 * ADVARSEL::: Med en sværhedsgrad på 5 tager det ca. 2-3 minutter at mine en block.
 *                  - Det kører dog på en tråd for sig.
 *
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import dk.cosby.andelsprojekt.R;
import dk.cosby.andelsprojekt.model.Block;
import dk.cosby.andelsprojekt.model.BlockUtil;
import dk.cosby.andelsprojekt.model.Blockchain;
import dk.cosby.andelsprojekt.model.Transaction;
import dk.cosby.andelsprojekt.model.User;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Skaber blockchainen
        Blockchain blockchain = new Blockchain();

        //Skaber argumenter til block
        User cosby = new User("cosby", "cosbypass");

        User nicklas = new User("nicklas", "nicklaspass");

        Transaction cosToNick = new Transaction(cosby, nicklas, 150);

        //Block skabes med de kendte argumenter
        final Block genesis = new Block("0000", cosToNick, blockchain.getSvaerhedsgrad());


        //Blocken mines i en tråd for sig
        Runnable myRunnable =
                new Runnable(){
                    public void run(){
                        //blocken mines
                        BlockUtil.mineBlock(genesis);
                        //udskriver information om blocken i Logcat
                        Log.i("Main Activity", "Thread + " + genesis.toString());

                    }
                };

        Thread thread = new Thread(myRunnable);
        thread.start();

        //blocken addes til blockchainen
        blockchain.getBlockChain().add(genesis);

        Log.i("Main activity", "Genesis block = " + genesis.toString());





    }
}
