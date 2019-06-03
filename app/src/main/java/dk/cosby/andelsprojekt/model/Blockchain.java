package dk.cosby.andelsprojekt.model;

/**
 * Denne klasse indeholder en blockchain (en liste af blocks)
 *
 * @version 1.0
 * @author Cosby
 */

import java.util.ArrayList;
import java.util.List;


public class Blockchain {

    private List<Block> blockChain;

    //Constructor
    public Blockchain() {
        blockChain = new ArrayList<Block>();

        //Skaber genesis block ved skabelsen af en blockchain
        blockChain.add(new Block("0000", new Transaction(new User(), 0)));
    }


    //////////////////////////////////// getters and setters /////////////////////////////////////

    public List<Block> getBlockChain() {
        return blockChain;
    }

    public void setBlockChain(List<Block> blockChain) {
        this.blockChain = blockChain;
    }

}
